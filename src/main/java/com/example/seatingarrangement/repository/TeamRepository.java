package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends JpaRepository<Team,String> {
}
