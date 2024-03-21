package com.example.seatingarrangement.service;


import com.example.seatingarrangement.dto.*;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public interface AllocationService {
    ResponseEntity<ResponseDto> add(CompanyDto companyDto) throws BadRequestException;

    ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) throws BadRequestException;

    ResponseEntity<ResponseDto> getAllLayOut(String companyName);

    CsvOutputDto convertCsvFile(InputStream inputStream) throws IOException;

    ResponseEntity<ResponseDto> updateLayout(LayoutDto layoutDto) throws BadRequestException;
}
