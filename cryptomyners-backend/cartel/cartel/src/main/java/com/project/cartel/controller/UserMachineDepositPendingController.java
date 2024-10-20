package com.project.cartel.controller;

import com.project.cartel.dto.DepositPendingDTO;
import com.project.cartel.dto.DepositSuccessDTO;
import com.project.cartel.entity.*;
import com.project.cartel.repository.*;
import com.project.cartel.service.UserMachineDepositPendingService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pending+request")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserMachineDepositPendingController {
    @Autowired
    private UserMachineDepositPendingService userMachineDepositPendingService;
    @Autowired
    private UserMachineDepositPendingRepository userMachineDepositPendingRepository;
    @Autowired
    private UserMachineDepositSuccessRepository userMachineDepositSuccessRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserReferalRepository userReferalRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private UserMachineRepository userMachineRepository;
    @Autowired
    private DisplayMachineRepository displayMachineRepository;

    @GetMapping("/getall")
    public  ResponseEntity<List<UserMachineDepositPending>> getALlPendingRequest(){
        try{
            List<UserMachineDepositPending> pendingList = userMachineDepositPendingRepository.findAll();
            return new ResponseEntity<>(pendingList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/submit+request")
    public ResponseEntity<UserMachineDepositPending> submitBuyRequest(@RequestBody UserMachineDepositPending pendingData){
        try{
            LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
            pendingData.setDeposited_date(date);
            UserMachineDepositPending savedData = userMachineDepositPendingRepository.save(pendingData);
            return new ResponseEntity<>(savedData,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping("/approve+request")
    public ResponseEntity<UserMachineDepositSuccess> approveDeposit(@RequestBody UserMachineDepositSuccess approvedData){
        try{

            // giving referral amount only on the first deposit

            User currentUser = userRepository.findById(approvedData.getUser_id().intValue()).orElse(null);
            if(currentUser != null){
                int machinesize =  currentUser.getUser_machines().size();
                if((machinesize == 0) && !currentUser.getInvited_referral_code().isEmpty()){  // first time user making deposit
                    User invitedByUser = userRepository.getUserByRefralCode(currentUser.getInvited_referral_code());
                    int refral_amount = (int) (approvedData.getMachine_price() * 0.05);
                    invitedByUser.setTotal__referral_earned(invitedByUser.getTotal__referral_earned()+refral_amount);
                    invitedByUser.setReferral_amount_withdraw(invitedByUser.getReferral_amount_withdraw()+refral_amount);
                    // save the invited by user total referral and invited referral
                    userRepository.save(invitedByUser);
                    // saving the data to referral table
                    UserReferralAmountEarned obj = new UserReferralAmountEarned();
                    obj.setUser_id(invitedByUser.getUser_id());
                    obj.setUser_phone(invitedByUser.getPhone_number());
                    obj.setReferred_userid(approvedData.getUser_id());
                    obj.setReferred_username(approvedData.getFirst_name());
                    obj.setReffered_date( LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
                    obj.setFirst_deposit(approvedData.getMachine_price());
                    obj.setReferral_amount(approvedData.getMachine_price()*(0.05));
                    userReferalRepository.save(obj);
                }
            }
            // saving the buyed machine to the user
            DisplayMachine buyedMachine = displayMachineRepository.findById(approvedData.getMachine_id()).orElse(null);
           assert buyedMachine != null;

            LocalDate todaysdate = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDate();
            LocalDate endDate = todaysdate.plusDays((long) buyedMachine.getValid_days());
            Machines newMachine = new Machines();
            newMachine.setMachine_name(buyedMachine.getMachine_name());
            newMachine.setUrl(buyedMachine.getUrl());
            newMachine.setPrice(buyedMachine.getPrice());
            newMachine.setStart_date(todaysdate);
            newMachine.setEnd_date(endDate);
            newMachine.setInterest_per_day(buyedMachine.getInterest_per_day());
            newMachine.setValid_days(buyedMachine.getValid_days());
            machineRepository.save(newMachine);

            UserMachine userMachine = new UserMachine();
            userMachine.setMachine(newMachine);
            userMachine.setUser(currentUser);
            userMachineRepository.save(userMachine);
            // set start and end date for the machine

            // updating the total deposited amount
            int total_deposit = currentUser.getTotal_deposited_amount();
            total_deposit+=buyedMachine.getPrice();
            currentUser.setTotal_deposited_amount(total_deposit);


            approvedData.setIs_success(true);
            LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
            approvedData.setApproved_date(date);
            UserMachineDepositSuccess successData = userMachineDepositSuccessRepository.save(approvedData);

            User s =   userRepository.save(currentUser);
            userMachineDepositPendingRepository.deleteById(approvedData.getId().intValue());
            return new ResponseEntity<>(successData,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPendingDepositDto/{id}")
    public ResponseEntity<List<DepositPendingDTO>> checkPending(@PathVariable int id){
        try{
            List<UserMachineDepositPending> pendingList = userMachineDepositPendingRepository.getPendingListById(id);

            List<DepositPendingDTO> pendingListDTO = pendingList.stream().map(user->modelMapper.map(user,DepositPendingDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(pendingListDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getSuccessDepositDto/{id}")
    public ResponseEntity<List<DepositSuccessDTO>> checkSuccess(@PathVariable int id){
        try{
            List<UserMachineDepositSuccess> successList = userMachineDepositSuccessRepository.getAllSuccessList(id);

            List<DepositSuccessDTO> successListDTO = successList.stream().map(user->modelMapper.map(user,DepositSuccessDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(successListDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
