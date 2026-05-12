package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.Birmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BirmasterRepository extends JpaRepository<Birmaster, Integer>,
        JpaSpecificationExecutor<Birmaster> {
}
