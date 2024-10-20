package com.project.cartel.controller;

import com.project.cartel.dto.UserBasicDetailsDTO;
import com.project.cartel.dto.UserDTO;
import com.project.cartel.dto.UserRefferalDTO;
import com.project.cartel.dto.UserRegisterDTO;
import com.project.cartel.entity.*;
import com.project.cartel.repository.MachineRepository;
import com.project.cartel.repository.UserInterestEarnedRepository;
import com.project.cartel.repository.UserReferalRepository;
import com.project.cartel.repository.UserRepository;
import com.project.cartel.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private UserReferalRepository userReferalRepository;
    @Autowired
    private UserInterestEarnedRepository userInterestEarnedRepository;


    @GetMapping("/get+all+users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        try{
            List<User> users = userRepository.findAll();

            List<UserDTO> userDTOList = users.stream().map(user1 -> modelMapper.map(user1, UserDTO.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(userDTOList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getSingleUserByNumber/{number}")
    public ResponseEntity<User> getSingleUserByNumber(@PathVariable Long number){
        try {

            User user = userService.getUserByNumber(number);
            if(user == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
//            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getSingleUser/{id}")
    public ResponseEntity<UserDTO> getSingleUserById(@PathVariable int id){
        try {
            User user = userRepository.findById(id).orElse(null);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getuser+basic+details/{id}")
    public ResponseEntity<UserBasicDetailsDTO> getUserBasicDetails(@PathVariable int id){
        try{
            User user = userRepository.findById(id).orElse(null);
            UserBasicDetailsDTO userBasicDetailsDTO = modelMapper.map(user,UserBasicDetailsDTO.class);
            return new ResponseEntity<>(userBasicDetailsDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save+user")
    public ResponseEntity<User> saveUser(@RequestBody UserRegisterDTO user){
        try{
            User u = modelMapper.map(user,User.class);
            User savedUser = userService.saveUser(u);
            return new ResponseEntity<>(savedUser,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update+user/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO,@PathVariable int id){
        try {
            User user = modelMapper.map(userDTO, User.class);
            User updatedUser = userService.updateUser(user, id);
            if(updatedUser == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            UserDTO userDTO1 = modelMapper.map(updatedUser, UserDTO.class);
            return new ResponseEntity<>(userDTO1, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update+basicdata/{id}")
    public ResponseEntity<UserBasicDetailsDTO> updateBasicData(@RequestBody UserBasicDetailsDTO userBasicDetailsDTO ,@PathVariable int id){
        try{
            User user = modelMapper.map(userBasicDetailsDTO,User.class);

            User savedUser = userService.updateBasicUserData(user);
            UserBasicDetailsDTO userBasicDetailsDTO1 = modelMapper.map(savedUser,UserBasicDetailsDTO.class);
            return new ResponseEntity<>(userBasicDetailsDTO1,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete+user/{id}")
    public ResponseEntity<UserDTO> DeleteUser(@PathVariable  int id){
       try{
           userRepository.deleteById(id);
           return new ResponseEntity<>(HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/getall+refral+details/{phone_number}")
    public ResponseEntity<List<UserDTO>> getAllRefralDetails(@PathVariable Long phone_number){
        try{
           List<User> allInvitedUsers = userService.getAllRefralDetails(phone_number);
           List<UserDTO> allInvitedUserDTO = allInvitedUsers.stream()
                       .map(user->modelMapper.map(user,UserDTO.class)).toList();
           return new ResponseEntity<>(allInvitedUserDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/getall+usermachines/{id}")
//    public ResponseEntity<List<User>> getAllUserMachines(@PathVariable int id){
//        try{
//            List<User> data = userRepository.getSingleUserMachines(id);
//            return new ResponseEntity<>(data,HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping("/getallmachines/{id}")
//    public ResponseEntity<List<Machines>> getAllMachines(@PathVariable int id){
//        List<Machines> m=  userRepository.getUserAllMachines(id);
//        return new ResponseEntity<>(m,HttpStatus.OK);
//    }

    @GetMapping("/getall+refferalusers/{id}")
    public ResponseEntity<List<UserReferralAmountEarned>> getAllReferralUsers(@PathVariable int id){
        try{
            List<UserReferralAmountEarned> list  = userReferalRepository.getAllReferralsList(id);
           return  new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e ){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getuser+referraldetails/{id}")
    public ResponseEntity<UserRefferalDTO> getUserReferralDetails(@PathVariable int id){
        try{
            User user = userRepository.findById(id).orElse(null);
            if(user != null){
                 UserRefferalDTO userRefferalDTO = modelMapper.map(user,UserRefferalDTO.class);
                 return new ResponseEntity<>(userRefferalDTO,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getSingleUser+InterestEarned/{id}")
    public ResponseEntity<List<UserInterestEarned>> getAllUserInterestEarned(@PathVariable  int id){
        try{
            List<UserInterestEarned> allInterest = userInterestEarnedRepository.getUserInterestEarned(id);
            return new ResponseEntity<>(allInterest,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }


}
