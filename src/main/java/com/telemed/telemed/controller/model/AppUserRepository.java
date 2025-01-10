package com.telemed.telemed.controller.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
    AppUser save(AppUser appUser);
    List<AppUser> findByDoctorId(Long doctorId);
}