package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllocationDto {

    private String companyName;

    private LinkedHashMap<String,Integer> toBeAllocated;

}
