package com.example.user.service.impl;

import com.example.user.entity.AddressInfo;
import com.example.user.repository.AddressRepository;
import com.example.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository repository;

    @Override
    public AddressInfo addAddress(AddressInfo addressInfo){
        return repository.save(addressInfo);
    }
    @Override
    public void deleteAddress(AddressInfo addressInfo){
        repository.delete(addressInfo);
    }
    @Override
    public List<String> readAddressAll(Integer uid){
        return repository.getAddressByUser(uid);
    }

}
