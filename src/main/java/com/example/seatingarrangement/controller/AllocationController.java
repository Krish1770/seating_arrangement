package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.AllocationApi;
import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.service.AllocationService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@RestController
public class AllocationController implements AllocationApi {

    @Autowired
    private AllocationService allocationService;

    @Override
    public ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) throws BadRequestException {

        return allocationService.addAllocation(teamObjectDto);
    }



    @Override
    public ResponseEntity<ResponseDto> getAllocations(String companyName) {
        return allocationService.getAllocations(companyName);
    }


    @Override
    public ResponseEntity<ResponseDto> convertCsvFile(MultipartFile file) throws IOException {
        System.out.println(file);
        InputStream inputStream=file.getInputStream();
        CsvOutputDto csvOutputDto=allocationService.convertCsvFile(inputStream);
        if(csvOutputDto.isFlag())
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(csvOutputDto,"file converted",HttpStatus.OK));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("","file not converted",HttpStatus.OK));

    }


}
