package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findByRegionId(Long regionId);
}
