package com.example.vugs;

public class RequestData {
    public String totalamount,producttype, unit,status;

    public RequestData() {
    }

    public RequestData(String unit, String totalamount, String producttype, String status) {
        this.unit = unit;
        this.totalamount = totalamount;
        this.producttype = producttype;
        this.status = status;
    }
}
