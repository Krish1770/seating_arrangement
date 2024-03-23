package com.example.seatingarrangement.api;


import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.seatingarrangement.dto.*;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
