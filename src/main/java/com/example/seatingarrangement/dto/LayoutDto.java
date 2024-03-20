package com.example.seatingarrangement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayoutDto {

    private String companyName;

    private String layoutId;

//    private int row;
//    private int column;

    private List<int[][]> layOut;
}
