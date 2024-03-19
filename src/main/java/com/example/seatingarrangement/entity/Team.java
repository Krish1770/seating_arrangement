//package com.example.seatingarrangement.entity;
//
//import lombok.Data;
//import org.hibernate.annotations.UuidGenerator;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Document
//
//@Data
//
//public class Team {
//
//    @Id
//    @UuidGenerator
//    private String teamId;
//
//    private String layoutId;
//
//    private List<TeamInfo> teams;
//
//    @CreatedDate
//
//    private Date createdDated;
//
//    @LastModifiedDate
//
//    private Date lastModifiedDate;
//
//    @Data
//
//    public class TeamInfo {
//
//        private String teamName;
//
//        private int totalMembers;
//
//        private String teamCode;
//
//    }
//
//}