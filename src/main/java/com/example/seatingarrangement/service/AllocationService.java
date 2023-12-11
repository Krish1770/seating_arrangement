package com.example.seatingarrangement.service;


import com.example.seatingarrangement.dto.AllocationDto;
import com.example.seatingarrangement.dto.LayoutDto;
import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.dto.TeamDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AllocationService {
    ResponseEntity<ResponseDto> add(LayoutDto layoutDto);

    ResponseEntity<ResponseDto> addAllocation(AllocationDto allocationDto);

    ResponseEntity<ResponseDto> getDivumLayout();

    ResponseEntity<ResponseDto> addTeamList(String teamName,List<TeamDto> teamDtoList);
}
