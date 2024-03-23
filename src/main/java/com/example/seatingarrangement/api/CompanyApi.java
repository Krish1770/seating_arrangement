package com.example.seatingarrangement.api;

import com.example.seatingarrangement.dto.CompanyDto;
import com.example.seatingarrangement.dto.LayoutDto;
import com.example.seatingarrangement.dto.ResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Layout")
@CrossOrigin
public interface CompanyApi {
    @GetMapping("{companyName}")  // query to be added
    ResponseEntity<ResponseDto> getAllLayOut(@PathVariable String companyName);

    @PostMapping("/updateLayout")  //to be checked
    ResponseEntity<ResponseDto> updateLayout(@RequestBody LayoutDto layoutDto) throws BadRequestException;

    @PostMapping() //checked
    ResponseEntity<ResponseDto> add(@RequestBody CompanyDto companyDto) ;

}
