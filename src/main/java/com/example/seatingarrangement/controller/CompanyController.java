package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.CompanyApi;
import com.example.seatingarrangement.dto.CompanyDto;
import com.example.seatingarrangement.dto.LayoutDto;
import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.repository.CompanyRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public class CompanyController  implements CompanyApi {


    @Override
    public ResponseEntity<ResponseDto> getAllLayOut(String companyName) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> updateLayout(LayoutDto layoutDto) throws BadRequestException {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> add(CompanyDto companyDto) {
        return null;
    }
}
