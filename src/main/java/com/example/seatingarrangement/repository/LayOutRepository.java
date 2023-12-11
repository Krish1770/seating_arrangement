package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.entity.LayOut;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface LayOutRepository extends MongoRepository<LayOut,String> {

    Optional<LayOut> findByCompanyName(String companyName);
}
