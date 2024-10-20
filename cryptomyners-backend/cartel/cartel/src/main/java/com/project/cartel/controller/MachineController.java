package com.project.cartel.controller;

import com.project.cartel.dto.MachinesDTO;
import com.project.cartel.entity.Machines;
import com.project.cartel.repository.MachineRepository;
import com.project.cartel.service.MachineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/machine")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class MachineController {

    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private MachineService machineService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getall+machines")
    public ResponseEntity<List<Machines>> getAllMachines(){
        try {
            List<Machines> machinesList = machineRepository.findAll();
            return new ResponseEntity<>(machinesList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/get+single+machine/{id}")
    public ResponseEntity<Machines> getSingleMachineById(@PathVariable  int id){
        try {
            Machines machine = machineRepository.findById(id).orElse(null);
            if (machine != null) {
                return new ResponseEntity<>(machine,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save+machine")
    public ResponseEntity<Machines> saveMachine(@RequestBody Machines machine){
        try {
            Machines savedMachine = machineRepository.save(machine);
            return new ResponseEntity<>(savedMachine, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }


}
