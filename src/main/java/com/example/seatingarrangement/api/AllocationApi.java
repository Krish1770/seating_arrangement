package com.example.seatingarrangement.api;


import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.seatingarrangement.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public interface AllocationApi {

    @PostMapping("layout")
    ResponseEntity<ResponseDto> add(@RequestBody LayoutDto layoutDto);

    @PostMapping("TeamList/{companyName}")   //not needed
    ResponseEntity<ResponseDto> addTeamList( @PathVariable String teamName,@RequestBody List<TeamDto> teamDtoList);

    @PostMapping("allocation")
    ResponseEntity<ResponseDto> addAllocation(@RequestBody TeamObjectDto teamObjectDto);

    @GetMapping("Divum-layout")
    ResponseEntity<ResponseDto> getDivumLayOut();

    @GetMapping("/layout/{companyName}")
    ResponseEntity<ResponseDto> getLayOut(@PathVariable String companyName);
}
