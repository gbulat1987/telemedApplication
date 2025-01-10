package com.telemed.telemed.controller.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {
    List<PatientRecord> findByAppUserId(Long appUserId);
    PatientRecord findFirstByAppUserIdOrderByDatumDesc(Long appUserId);


}
