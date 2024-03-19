package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.AllocationApi;
import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.repository.Service.AllocationRepoService;
import com.example.seatingarrangement.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@RestController
public class AllocationController implements AllocationApi {

    @Autowired
    private AllocationService allocationService;
    @Override
    public ResponseEntity<ResponseDto> add(LayoutDto layoutDto) {
        System.out.println("hi");
        return allocationService.add(layoutDto);
    }
    @Override
    public ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) {

        HashMap<String, Integer> toBeAllocated=new HashMap<>();
        System.out.println("i    "+teamObjectDto);
        for(TeamDto i:teamObjectDto.getTeamDtoList())
        {
            toBeAllocated.put(i.getTeamName(),i.getTeamCount());
        }
        System.out.println( "toBeAllocated"+toBeAllocated);
        AllocationDto allocationDto=new AllocationDto(teamObjectDto.getCompanyName(),toBeAllocated, teamObjectDto.getPreference());
        return allocationService.addAllocation(allocationDto);
    }
    @Override
    public ResponseEntity<ResponseDto> getLayOut(String companyName) {
        return allocationService.getLayOut(companyName);
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
