package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatingCalculationDto {

      private int[][] layOut;

       private LinkedHashMap<String,Character> teamIdList;

    private LinkedHashMap<String,Integer> toBeAllocated;
}
