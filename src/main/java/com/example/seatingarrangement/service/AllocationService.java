package com.example.seatingarrangement.service;


import com.example.seatingarrangement.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public interface AllocationService {
    ResponseEntity<ResponseDto> add(LayoutDto layoutDto);

    ResponseEntity<ResponseDto> addAllocation(AllocationDto allocationDto);

    ResponseEntity<ResponseDto> getLayOut(String companyName);

    CsvOutputDto convertCsvFile(InputStream inputStream) throws IOException;
}
