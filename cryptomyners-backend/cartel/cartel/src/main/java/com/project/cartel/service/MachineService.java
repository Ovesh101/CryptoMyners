package com.project.cartel.service;

import com.project.cartel.entity.Machines;
import com.project.cartel.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineService {
    @Autowired
    private MachineRepository machineRepository;

    public Machines updateMachine(Machines newMachine, int id){
        Machines oldMachine = machineRepository.findById(id).orElse(null);
        if(oldMachine != null){
            oldMachine.setPrice(newMachine.getPrice());
            oldMachine.setInterest_per_day(newMachine.getInterest_per_day());
            oldMachine.setValid_days(newMachine.getValid_days());
            oldMachine.setUrl(newMachine.getUrl());
            machineRepository.save(oldMachine);
            return oldMachine;
        }else{
            return null;
        }
    }
}
