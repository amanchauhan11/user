package com.example.user.model;

import com.example.user.entity.UserInfo;

public class UserProfile {
    private UserInfo userInfo;
    private Object[] orderHistory;
    private Object[] addressList;

    public Object[] getAddressList() {
        return addressList;
    }

    public void setAddressList(Object[] addressList) {
        this.addressList = addressList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Object[] getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(Object[] orderHistory) {
        this.orderHistory = orderHistory;
    }
}
