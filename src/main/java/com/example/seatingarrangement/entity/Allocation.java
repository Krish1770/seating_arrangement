package com.example.seatingarrangement.entity;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "seating_allocation")
public class Allocation {

    @Id
    @UuidGenerator
    private String allocationId;
//    private String companyName;
     private String defaultLayoutId;
   private String teamId;

   @Enumerated
   private Type allocationType;
   private String[][] allocationLayout;
}

//{
//        "companyName":"abc",
//        "row":7,
//        "column":10,
//        "layOut":[
//        [1, 1, 0, 1, 1, 1, 1, 0, 1, 1],
//        [1, 1, 0, 1, 1, 1, 1, 0, 1, 1],
//        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
//        [1, 1, 0, 1, 1, 1, 1, 0, 1, 1],
//        [1, 1, 0, 1, 1, 1, 1, 0, 1, 1],
//        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
//        [1, 1, 0, 1, 1, 1, 1, 0, 1, 1]
//        ]
//        }



//{
//        "companyName":"abc",
//        "toBeAllocated":
//        {
//        "ac":7,
//        "cde":10,
//        "wer":3,
//        "gfd":6,
//        "aed":10
//        }
//        }
