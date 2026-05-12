package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findBySupName(String supName);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT m.supName FROM Model m WHERE m.supName IS NOT NULL")
    List<String> findDistinctSupNames();
}
