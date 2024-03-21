package com.example.seatingarrangement.api;


import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.seatingarrangement.dto.*;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public interface AllocationApi {

    @PostMapping("layout") //checked
    ResponseEntity<ResponseDto> add(@RequestBody CompanyDto companyDto) throws BadRequestException;

    @PostMapping("allocation") //to be
    ResponseEntity<ResponseDto> addAllocation(@RequestBody TeamObjectDto teamObjectDto) throws BadRequestException;

    @GetMapping("/layout/{companyName}")  // query to be added
    ResponseEntity<ResponseDto> getAllLayOut(@PathVariable String companyName);

    @PostMapping("/csvFile")  //checked
    ResponseEntity<ResponseDto> convertCsvFile(@RequestBody MultipartFile file) throws IOException;

    @PostMapping("/updateLayout")  //to be checked
    ResponseEntity<ResponseDto> updateLayout(@RequestBody LayoutDto layoutDto) throws BadRequestException;
}
