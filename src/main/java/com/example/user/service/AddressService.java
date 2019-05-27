package com.example.user.service;

import com.example.user.entity.AddressInfo;

import java.util.List;

public interface AddressService {

    public AddressInfo addAddress(AddressInfo addressInfo);
    public void deleteAddress(AddressInfo addressInfo);
    public List<String> readAddressAll(Integer uid);

}
