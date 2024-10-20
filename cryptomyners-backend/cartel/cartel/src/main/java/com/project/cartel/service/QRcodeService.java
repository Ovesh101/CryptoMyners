package com.project.cartel.service;

import com.project.cartel.entity.QRcode;
import com.project.cartel.repository.QRcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QRcodeService {
    @Autowired
    private QRcodeRepository qRcodeRepository;

    public QRcode updateQRCode(QRcode newQRCode,int id){
        QRcode oldQRcode = qRcodeRepository.findById(id).orElse(null);
        if(oldQRcode != null){
            oldQRcode.setQrcode_image(newQRCode.getQrcode_image());
            oldQRcode.setOwner_name(newQRCode.getOwner_name());
            oldQRcode.setUpi_id(newQRCode.getUpi_id());
            oldQRcode.setBank_name(newQRCode.getBank_name());

            qRcodeRepository.save(oldQRcode);
            return oldQRcode;
        }else {
            return null;
        }
    }
}
