package com.example.seatingarrangement.api;


import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.dto.TeamObjectDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("Allocation")
@CrossOrigin
public interface AllocationApi {

    @PostMapping() //to be
    ResponseEntity<ResponseDto> addAllocation(@RequestBody TeamObjectDto teamObjectDto) throws BadRequestException;

    @PostMapping("/csvFile")  //checked
    ResponseEntity<ResponseDto> convertCsvFile(@RequestBody MultipartFile file) throws IOException;

    @GetMapping("{layoutId}")
    ResponseEntity<ResponseDto> getAllocations(@PathVariable String layoutId);
}
