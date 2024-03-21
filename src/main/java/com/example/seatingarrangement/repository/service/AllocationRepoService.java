package com.example.seatingarrangement.repository.service;

import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.entity.Type;
import org.springframework.stereotype.Service;


@Service
public interface AllocationRepoService {
    Allocation findByDefaultLayoutIdAndAllocationType(String layoutId, Type allocationType);


}
