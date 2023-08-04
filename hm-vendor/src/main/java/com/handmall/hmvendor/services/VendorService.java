package com.handmall.hmvendor.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.handmall.hmvendor.dtos.Vendor.VendorRequest;
import com.handmall.hmvendor.dtos.Vendor.VendorResponse;
import com.handmall.hmvendor.entities.Vendor;
import com.handmall.hmvendor.mappers.VendorMapper;
import com.handmall.hmvendor.repositories.VendorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public List<VendorResponse> getVendors() {
        return vendorMapper.toVendorList(vendorRepository.findAll());
    }

    public VendorResponse getVendor(Integer vendorId) throws NotFoundException {
        Optional<Vendor> vendor = vendorRepository.findById(vendorId);

        if (vendor.isEmpty()) {
            throw new NotFoundException();
        }

        return vendorMapper.toVendor(vendor.get());
    }

    public void addNew(VendorRequest vendorRequest) {
        Optional<Vendor> vendorByName = vendorRepository.findVendorByName(vendorRequest.getName());

        if (vendorByName.isPresent()) {
            throw new IllegalStateException("name is exist");
        }

        vendorRepository.save(vendorMapper.toVendorRequest(vendorRequest));
    }

    public void delete(Integer vendorId) {
        boolean exists = vendorRepository.existsById(vendorId);

        if (!exists) {
            throw new IllegalStateException("vendor with id " + vendorId + " does not exist");
        }

        vendorRepository.deleteById(vendorId);
    }

    public void update(VendorRequest vendorRequest, Integer vendorId) {
        Optional<Vendor> vendorById = vendorRepository.findById(vendorId);

        if (vendorById.isEmpty()) {
            throw new IllegalStateException("vendor with id " + vendorId + " does not exist");
        }

        var vendor = Vendor
                .builder()
                .id(vendorId)
                .name(vendorRequest.getName())
                .build();

        vendorRepository.save(vendor);
    }
}
