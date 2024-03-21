package com.example.seatingarrangement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//@Getter
//@Setter
public class LayoutDto {
    private String layoutId;
    private String companyName;
    private int[][] defaultLayout;


}