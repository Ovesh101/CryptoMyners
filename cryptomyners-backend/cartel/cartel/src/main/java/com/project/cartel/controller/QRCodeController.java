package com.project.cartel.controller;

import com.project.cartel.dto.QRCodeDTO;
import com.project.cartel.entity.QRcode;
import com.project.cartel.repository.QRcodeRepository;
import com.project.cartel.service.QRcodeService;
import lombok.Getter;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qrcode")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class QRCodeController {

    @Autowired
    private QRcodeRepository qRcodeRepository;
    @Autowired
    private QRcodeService qRcodeService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getall+qrcode")
    public ResponseEntity<List<QRCodeDTO>> getAllQRCode(){
        try{

            List<QRcode> qRcodes = qRcodeRepository.findAll();

            List<QRCodeDTO> qrCodeDTOS = qRcodes.stream().map(qrcode-> modelMapper.map(qrcode, QRCodeDTO.class))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(qrCodeDTOS,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getsingle+qrcode")
    public ResponseEntity<QRCodeDTO> getSingleQRCodeById(int id){
        try{
            QRcode qRcode = qRcodeRepository.findById(id).orElse(null);

            QRCodeDTO  qrCodeDTO = modelMapper.map(qRcode, QRCodeDTO.class);
            return new ResponseEntity<>(qrCodeDTO,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getactive+qrcode")
    public ResponseEntity<QRcode> getActiveQRCode(){
        try{
            List<QRcode> Listqrcode = qRcodeRepository.findAll();
            Random random = new Random();
            int randomIndex = random.nextInt(Listqrcode.size());
            QRcode qRcode = Listqrcode.get(randomIndex);
            if(qRcode == null){
             return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(qRcode,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/save+qrcode")
    public ResponseEntity<QRCodeDTO> saveQRCode(@RequestBody QRCodeDTO qrCodeDTO){
        try{
            QRcode qRcode = modelMapper.map(qrCodeDTO,QRcode.class);
            QRcode savedQrCode = qRcodeRepository.save(qRcode);

            QRCodeDTO qrCodeDTO1 = modelMapper.map(savedQrCode, QRCodeDTO.class);
            return new ResponseEntity<>(qrCodeDTO1,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update+qrcode/{id}")
    public ResponseEntity<QRCodeDTO> updateQRCode(@RequestBody QRCodeDTO newQRCodeDTO, @PathVariable int id){
       try{
           QRcode qRcode = modelMapper.map(newQRCodeDTO, QRcode.class);
           QRcode qRcode1 = qRcodeService.updateQRCode(qRcode,id);
           if(qRcode1 == null){
               return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
           }
           QRCodeDTO qrCodeDTO = modelMapper.map(qRcode1, QRCodeDTO.class);
           return new ResponseEntity<>(qrCodeDTO,HttpStatus.OK);

       }catch (Exception e){
           return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
       }
    }

    @DeleteMapping("/delete+qrcode/{id}")
    public ResponseEntity<QRCodeDTO> deleteById(@PathVariable int id){
        try{
            QRcode qRcode = qRcodeRepository.findById(id).orElse(null);
            if(qRcode != null){
                qRcodeRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

}
