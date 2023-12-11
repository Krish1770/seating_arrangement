package com.example.seatingarrangement.repository.Service;


import com.example.seatingarrangement.entity.LayOut;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LayOutRepoService {
    void insert(LayOut layout);

    Optional<LayOut> findByCompanyName(String companyName);
}
