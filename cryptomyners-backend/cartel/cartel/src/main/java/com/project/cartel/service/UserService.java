package com.project.cartel.service;

import com.project.cartel.entity.Enums.Roles;
import com.project.cartel.entity.Machines;
import com.project.cartel.entity.User;
import com.project.cartel.entity.UserInterestEarned;
import com.project.cartel.entity.UserMachine;
import com.project.cartel.repository.UserInterestEarnedRepository;
import com.project.cartel.repository.UserMachineRepository;
import com.project.cartel.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInterestEarnedRepository userInterestEarnedRepository;
    @Autowired
    private UserMachineRepository userMachineRepository;

    // Return the Parent User(Invited By ) User
    public User getSingleUserByInvitedRefralCode(String refralCode){
    User user = userRepository.getUserByRefralCode(refralCode);
    if(user == null){
        return null;
    }
    return user;
    }

    public User saveUser(User user){
        if(user != null){
            user.setRole(Roles.USER);
            user.setSelf_referral_code(getSelfRefralCode());
            user.setJoined_date(LocalDate.now());
            // saving the new user to the db first
            User newSavedUser =  userRepository.save(user);
            // managing the data if used any referral code
            if(user.getInvited_referral_code() != null){
                                User invitedByUSer = userRepository.getUserByRefralCode(user.getInvited_referral_code());
                invitedByUSer.getInvites_users().add(user);
                this.updateUser(invitedByUSer,invitedByUSer.getUser_id().intValue());
            }
            return newSavedUser;
        }else{
            return null;
        }
    }

    public User getUserByNumber(Long number){
        User user = userRepository.getUserByNumber(number);
        if(user == null){
            return null;
        }
        return user;
    }

    @Transactional
    public User updateUser(User newUser,int  id){
        User oldUser = userRepository.findById(id).orElse(null);
        if(oldUser != null){
            oldUser.setFirst_name(newUser.getFirst_name());
            oldUser.setLast_name(newUser.getLast_name());
            oldUser.setTotal__referral_earned(newUser.getTotal__referral_earned());
            oldUser.setReferral_amount_withdraw(newUser.getReferral_amount_withdraw());
            userRepository.save(oldUser);
            return oldUser;
        }else{
            return null;
        }
    }

    public User updateBasicUserData(User newuser){
        User olduser = userRepository.findById(newuser.getUser_id().intValue()).orElse(null);
        if(olduser != null){

            olduser.setFirst_name(newuser.getFirst_name());
            olduser.setLast_name(newuser.getLast_name());
            olduser.setEmail(newuser.getEmail());
            olduser.setBank_name(newuser.getBank_name());
            olduser.setAccount_no(newuser.getAccount_no());
            olduser.setUpi_id(newuser.getUpi_id());
            olduser.setIfsc_code(newuser.getIfsc_code());
            olduser.setAadhaar_number(newuser.getAadhaar_number());
            olduser.setPan_card(newuser.getPan_card());

        return userRepository.save(olduser);
        }else{
            return null;
        }

    }

    public List<User> getAllRefralDetails(Long phone_number){

        return userRepository.getUserByNumber(phone_number).getInvites_users();
    }

    public String getSelfRefralCode(){
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for(int i=0;i<5;i++){
            Double index = Math.floor(string.length()* (Math.random()));
            int index1 = index.intValue();
            char variable = string.charAt(index1);
            String str = String.valueOf(variable);
            code.append(str);
        }
        return code.toString();
    }


    @Scheduled(cron = "0 22 23 * * *",zone = "Asia/Kolkata")
    @Transactional
    @Async
    public CompletableFuture<String> allUsersInterestEarned(){
        try{
            List<User> allUsers = userRepository.findAll();
            List<UserInterestEarned> allUsersInterest = new ArrayList<>();
            allUsers.forEach(singleUser->{
                AtomicInteger singleUserTotalInterest = new AtomicInteger(singleUser.getTotal_interest_earned());
                AtomicInteger singleUserAvailableToWithdraw =  new AtomicInteger(singleUser.getAvailable_to_withdraw());
                List<UserMachine> userMachines = singleUser.getUser_machines();
                List<Machines> allMachines = userMachines.stream().map(UserMachine::getMachine).toList();
                int size = allMachines.size();
                if(size > 0) {
                    allMachines.forEach(singleMachine -> {

                        LocalDate todaysDate = LocalDate.now();
                        if (todaysDate.isBefore(singleMachine.getEnd_date())) {
                            UserInterestEarned user = new UserInterestEarned();
                            user.setUser_id(singleUser.getUser_id());
                            user.setMachine_id(singleMachine.getMachine_id());
                            user.setMachine_name(singleMachine.getMachine_name());
                            user.setDate(LocalDate.now());
                            int interest_amount = (int) (singleMachine.getPrice() * (singleMachine.getInterest_per_day() / 100.0));
                            user.setInterest_amount(interest_amount);
                            allUsersInterest.add(user);

                            singleUserTotalInterest.set(singleUserTotalInterest.get() + interest_amount);
                            singleUserAvailableToWithdraw.set(singleUserAvailableToWithdraw.get()+interest_amount);

                        }
                    });
                    updateInterestAndWithdrawal(singleUser.getUser_id().intValue(),
                            singleUserTotalInterest.get(),singleUserAvailableToWithdraw.get());
                }

            });

//             updating User Interest Earned Data
            int batchsize = 500;
            for(int i=0;i<allUsersInterest.size();i+=batchsize){
                int end = Math.min(i + batchsize, allUsersInterest.size());
                List<UserInterestEarned> batch = allUsersInterest.subList(i, end);
                userInterestEarnedRepository.saveAll(batch);
            }

            return CompletableFuture.completedFuture("success");
        }catch (Exception e){
            return CompletableFuture.completedFuture(e.getMessage());
        }
    }

    @Transactional
    public void updateInterestAndWithdrawal(int id,int interest,int withdraw){
        userRepository.updateInterestAndWithdrawal(id,interest,withdraw);
    }


}
