package com.example.seatingarrangement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    @JsonProperty("TeamName")
    private String TeamName;

    @JsonProperty("TeamCount")
    private Integer TeamCount;
}
