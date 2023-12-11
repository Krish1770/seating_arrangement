package com.example.seatingarrangement.entity;

import com.example.seatingarrangement.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "LayOut")
public class LayOut {


    @Id
    private String id;
    private String companyName;

    private int[][] layOut;

    private LinkedHashMap<String,Character> teamIdList;

    private List<TeamDto> teamDtoList;

    private int availableSpaces;
}
