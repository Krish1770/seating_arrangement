package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllocationDto {

    private String LayoutId;

//    private LinkedHashMap<String,Integer> toBeAllocated;
private HashMap<String,Integer> toBeAllocated;

    private Integer preference;

}
