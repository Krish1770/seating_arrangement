package com.example.seatingarrangement.repository.Service.Impl;

import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.entity.LayOut;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.Service.AllocationRepoService;
import com.example.seatingarrangement.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllocationRepoServiceImpl implements AllocationRepoService {

    @Autowired
    private AllocationRepository allocationRepository;



}
