package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.Productmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductmasterRepository extends JpaRepository<Productmaster, Integer> {
}
