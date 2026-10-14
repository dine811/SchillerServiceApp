package com.schillerindiaservices.repository;

import com.schillerindiaservices.entity.RepairMaster;
import com.schillerindiaservices.entity.RepairStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<RepairMaster, Integer>, JpaSpecificationExecutor<RepairMaster> {
    boolean existsByServiceIdAndStatus(Integer serviceId, RepairStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RepairMaster r WHERE r.id = :id")
    Optional<RepairMaster> findByIdForUpdate(@Param("id") Integer id);
}
