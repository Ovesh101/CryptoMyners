package com.project.cartel.controller;

import com.project.cartel.dto.MachinesDTO;
import com.project.cartel.entity.Machines;
import com.project.cartel.entity.UserMachine;
import com.project.cartel.repository.UserMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user+machines")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserMachineController {
    @Autowired
    private UserMachineRepository userMachineRepository;

    @PostMapping("/save+usermachine")
    public ResponseEntity<UserMachine> saveUserMachine(@RequestBody UserMachine userMachine){
        try{
            UserMachine savedUserMachine = userMachineRepository.save(userMachine);
            return new ResponseEntity<>(savedUserMachine,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getmachinelist/{userId}")
    public ResponseEntity<List<Machines>> getSingleUserMachine(@PathVariable Long userId){
        try{
            List<Machines> machines = userMachineRepository.findMachinesByUserId(userId);
            return new ResponseEntity<>(machines,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
