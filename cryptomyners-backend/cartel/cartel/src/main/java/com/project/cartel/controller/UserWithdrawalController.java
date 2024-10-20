package com.project.cartel.controller;

import com.project.cartel.entity.User;
import com.project.cartel.entity.UserWithdrawalPending;
import com.project.cartel.entity.UserWithdrawalSuccess;
import com.project.cartel.repository.UserRepository;
import com.project.cartel.repository.UserWithdrawalPendingRepository;
import com.project.cartel.repository.UserWithdrawalSuccessRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user+withdrawal")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserWithdrawalController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserWithdrawalPendingRepository userWithdrawalPendingRepository;
    @Autowired
    private UserWithdrawalSuccessRepository userWithdrawalSuccessRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save+pending+request")
    public ResponseEntity<UserWithdrawalPending> savePendingRequest(@RequestBody UserWithdrawalPending pendingRequest){

        try{
            LocalDateTime date = LocalDateTime.now();
            pendingRequest.setWithdrawal_date(date);
                UserWithdrawalPending savedPendingUser = userWithdrawalPendingRepository.save(pendingRequest);
            return new ResponseEntity<>(savedPendingUser,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll+pending+request")
    public ResponseEntity<List<UserWithdrawalPending>> getAllPendingRequest(){
        try{
            List<UserWithdrawalPending> pendingUsersList = userWithdrawalPendingRepository.findAll();
            return new ResponseEntity<>(pendingUsersList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll+approved+withdrawals")
    public ResponseEntity<List<UserWithdrawalSuccess>> getAllApprovedWithdrawals(){
        try{
            List<UserWithdrawalSuccess> list = userWithdrawalSuccessRepository.findAll();
            return  new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/approve+request/{id}/{user_id}")
    @Transactional
    public ResponseEntity<UserWithdrawalSuccess> approveWithdrawalRequest(@PathVariable int id, @PathVariable int user_id){
        try{
            UserWithdrawalPending pendingUserData = userWithdrawalPendingRepository.findById(id).orElse(null);

            UserWithdrawalSuccess withdrawalSuccess = new UserWithdrawalSuccess();
            withdrawalSuccess.setWithdrawal_amount(pendingUserData.getWithdrawal_amount());
            withdrawalSuccess.setWithdrawal_date(LocalDateTime.now());
            withdrawalSuccess.setUser_id(pendingUserData.getUser_id());
            withdrawalSuccess.setIs_success(true);
            UserWithdrawalSuccess savedSuccessUser = userWithdrawalSuccessRepository.save(withdrawalSuccess);

            User currentUser = userRepository.findById(user_id).orElse(null);
            int withdrawalAmount = currentUser.getAvailable_to_withdraw();
            withdrawalAmount-= pendingUserData.getWithdrawal_amount();
            currentUser.setAvailable_to_withdraw(withdrawalAmount);
            userRepository.save(currentUser);

            userWithdrawalPendingRepository.deleteById(id);
            return new ResponseEntity<>(savedSuccessUser,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getsingle+success+withdrawals/{user_id}")
    public ResponseEntity<List<UserWithdrawalSuccess>> getSingleUserSuccessWithdrawals(@PathVariable int user_id){
        try{
            List<UserWithdrawalSuccess> list = userWithdrawalSuccessRepository.getSingleUserWithdrawals(user_id);
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getsingle+pending+withdrawals/{user_id}")
    public ResponseEntity<List<UserWithdrawalPending>> getSingleUserPendingWithdrawals(@PathVariable int user_id){
        try{
            List<UserWithdrawalPending> list = userWithdrawalPendingRepository.getSinglePendingWithdrawals(user_id);
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
