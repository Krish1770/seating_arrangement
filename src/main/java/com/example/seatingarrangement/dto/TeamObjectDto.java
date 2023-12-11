package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamObjectDto {

    private String companyName;

    private List<TeamDto> teamDtoList;
}