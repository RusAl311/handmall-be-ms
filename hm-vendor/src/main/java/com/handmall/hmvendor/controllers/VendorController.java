package com.handmall.hmvendor.controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handmall.hmvendor.dtos.Vendor.VendorRequest;
import com.handmall.hmvendor.dtos.Vendor.VendorResponse;
import com.handmall.hmvendor.services.VendorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/vendor")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @GetMapping("/getAll")
    public List<VendorResponse> getVendors() {
        return vendorService.getVendors();
    }

    @GetMapping("/get/{vendorId}")
    public VendorResponse getVendor(@PathVariable("vendorId") Integer vendorId) throws NotFoundException {
        return vendorService.getVendor(vendorId);
    }

    @PostMapping("/addNew")
    public void addNewVendor(@RequestBody VendorRequest vendorRequest) {
        vendorService.addNew(vendorRequest);
    }

    @DeleteMapping("/delete/{vendorId}")
    public void deleteVendor(@PathVariable("vendorId") Integer vendorId) {
        vendorService.delete(vendorId);
    }

    @PutMapping("/update/{vendorId}")
    public void updateVendor(@RequestBody VendorRequest vendorRequest, @PathVariable("vendorId") Integer vendorId) {
        vendorService.update(vendorRequest, vendorId);
    }
}
