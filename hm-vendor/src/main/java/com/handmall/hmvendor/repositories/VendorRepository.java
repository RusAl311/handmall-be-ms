package com.handmall.hmvendor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.handmall.hmvendor.entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer>{
    Optional<Vendor> findVendorByName(String name);    
}
