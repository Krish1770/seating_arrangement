package com.example.seatingarrangement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;

@AllArgsConstructor
@Data
public class UserReferenceDto {
    private String[][] allocation;

    private LinkedHashMap<String,Character> teamIds;

}
