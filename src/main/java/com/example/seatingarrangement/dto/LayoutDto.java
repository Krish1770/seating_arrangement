package com.example.seatingarrangement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayoutDto {

    private String companyName;

    private int row;
    private int column;

    private int[][] layOut;
}
