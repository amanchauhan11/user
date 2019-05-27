package com.example.user.model;

import java.util.Date;

public class OrderDTO {
    private Date orderdate;
    private Integer userid;
    private String emailid;
    private String paymentoption;
    private String billingaddress;
    private String shippingaddress;
    private CartDTO[] productlist;
    private String accesstoken;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPaymentoption() {
        return paymentoption;
    }

    public void setPaymentoption(String paymentoption) {
        this.paymentoption = paymentoption;
    }

    public String getBillingaddress() {
        return billingaddress;
    }

    public void setBillingaddress(String billingaddress) {
        this.billingaddress = billingaddress;
    }

    public String getShippingaddress() {
        return shippingaddress;
    }

    public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

    public CartDTO[] getProductlist() {
        return productlist;
    }

    public void setProductlist(CartDTO[] productlist) {
        this.productlist = productlist;
    }
}
