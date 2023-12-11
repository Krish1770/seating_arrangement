package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.entity.Allocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface AllocationRepository extends MongoRepository<Allocation,String> {
}
