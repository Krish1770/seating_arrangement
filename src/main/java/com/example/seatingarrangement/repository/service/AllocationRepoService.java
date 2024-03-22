package com.example.seatingarrangement.repository.service;

import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.enums.Type;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AllocationRepoService {


    Allocation findByDefaultLayoutIdAndAllocationType(String layoutId, Type allocationType);


}
