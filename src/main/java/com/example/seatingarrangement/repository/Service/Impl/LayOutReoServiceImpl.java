package com.example.seatingarrangement.repository.Service.Impl;

import com.example.seatingarrangement.entity.LayOut;
import com.example.seatingarrangement.repository.LayOutRepository;
import com.example.seatingarrangement.repository.Service.LayOutRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LayOutReoServiceImpl implements LayOutRepoService {

    @Autowired
    private LayOutRepository layOutRepository;

    @Override
    public void insert(LayOut layout) {
      layOutRepository.insert(layout);
    }

    @Override
    public Optional<LayOut> findByCompanyName(String companyName) {
        return layOutRepository.findByCompanyName(companyName);
    }
}
