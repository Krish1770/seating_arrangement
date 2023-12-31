package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.AllocationApi;
import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.repository.Service.AllocationRepoService;
import com.example.seatingarrangement.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;


@RestController
public class AllocationController implements AllocationApi {

    @Autowired
    private AllocationService allocationService;
    @Override
    public ResponseEntity<ResponseDto> add(LayoutDto layoutDto) {
        return allocationService.add(layoutDto);
    }

    @Override
    public ResponseEntity<ResponseDto> addTeamList( String teamName,List<TeamDto> teamDtoList) {
        return allocationService.addTeamList(teamName,teamDtoList);
    }

    @Override
    public ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) {

        LinkedHashMap<String, Integer> toBeAllocated=new LinkedHashMap<>();
        System.out.println("i    "+teamObjectDto);
        for(TeamDto i:teamObjectDto.getTeamDtoList())
        {
            toBeAllocated.put(i.getTeamName(),i.getTeamCount());
        }
        System.out.println(toBeAllocated);
        AllocationDto allocationDto=new AllocationDto(teamObjectDto.getCompanyName(),toBeAllocated);
        return allocationService.addAllocation(allocationDto);
    }

    @Override
    public ResponseEntity<ResponseDto> getDivumLayOut() {
        return allocationService.getDivumLayout();
    }


}
