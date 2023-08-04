package com.handmall.hmvendor.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.handmall.hmvendor.dtos.Vendor.VendorRequest;
import com.handmall.hmvendor.dtos.Vendor.VendorResponse;
import com.handmall.hmvendor.entities.Vendor;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    VendorResponse toVendor(Vendor vendor);

    List<VendorResponse> toVendorList(List<Vendor> vendors);

    Vendor toVendorRequest(VendorRequest vendorRequest);
}
