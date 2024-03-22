package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.enums.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends MongoRepository<Allocation,String> {
    Allocation findByDefaultLayoutIdAndAllocationType(String layoutId, Type allocationType);
}
