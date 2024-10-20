package com.project.cartel.controller;

import com.project.cartel.entity.UserMachineDepositSuccess;
import com.project.cartel.repository.UserMachineDepositSuccessRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deposit+success")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserMachineDepositSuccessController {
    @Autowired
    private UserMachineDepositSuccessRepository depositSuccessRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getall+success+request")
    public ResponseEntity<List<UserMachineDepositSuccess>> getAllSuccessDeposit(){
        try{
            List<UserMachineDepositSuccess> successDeposit = depositSuccessRepository.findAll();
            return new ResponseEntity<>(successDeposit, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
