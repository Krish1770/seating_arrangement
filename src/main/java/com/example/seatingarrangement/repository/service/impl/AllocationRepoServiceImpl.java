package com.example.seatingarrangement.repository.service.impl;

import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.enums.Type;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.service.AllocationRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllocationRepoServiceImpl implements AllocationRepoService {

    @Autowired
    private AllocationRepository allocationRepository;


    @Override
    public Allocation findByDefaultLayoutIdAndAllocationType(String layoutId, Type allocationType) {
        return allocationRepository.findByDefaultLayoutIdAndAllocationType(layoutId,allocationType);
    }
}
