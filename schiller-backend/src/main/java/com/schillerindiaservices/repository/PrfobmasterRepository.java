package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.Prfobmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrfobmasterRepository extends JpaRepository<Prfobmaster, Integer>,
        JpaSpecificationExecutor<Prfobmaster> {
}
