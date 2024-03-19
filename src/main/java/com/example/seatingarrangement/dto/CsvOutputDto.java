package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvOutputDto {
    private List<TeamDto> teamDtoList;
    private boolean flag;
    private Integer spacesOccupied;
}
