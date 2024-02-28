package com.example.seatingarrangement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
public class UserReferenceDto {
    private String[][] allocation;

//    private LinkedHashMap<String,Character> teamIds;

    private List<TeamReference> teamReferenceList;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamReference {
        private String name;

        private Character Key;
    }
}
