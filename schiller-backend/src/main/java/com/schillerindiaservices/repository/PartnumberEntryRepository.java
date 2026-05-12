package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.PartnumberEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnumberEntryRepository extends JpaRepository<PartnumberEntry, Integer> {
}
