package com.example.seatingarrangement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    @JsonProperty("teamName")
    private String teamName;

    @JsonProperty("teamCount")
    private Integer teamCount;
}
