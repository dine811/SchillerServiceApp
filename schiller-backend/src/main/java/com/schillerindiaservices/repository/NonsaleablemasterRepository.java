package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.Nonsaleablemaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NonsaleablemasterRepository extends JpaRepository<Nonsaleablemaster, Integer>,
        JpaSpecificationExecutor<Nonsaleablemaster> {
}
