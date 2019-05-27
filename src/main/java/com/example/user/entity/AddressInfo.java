package com.example.user.entity;


import javax.persistence.*;

@Entity
@Table(name = "addressinfo")
public class AddressInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    Integer uid;
    String address;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return  id+" "+uid+" "+address;
    }
}

