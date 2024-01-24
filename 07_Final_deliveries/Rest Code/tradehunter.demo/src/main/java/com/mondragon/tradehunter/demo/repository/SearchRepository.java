package com.mondragon.tradehunter.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mondragon.tradehunter.demo.model.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer>{

    
}
