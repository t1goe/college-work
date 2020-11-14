package service.message;

import java.io.Serializable;

import service.core.QuotationService;

import java.io.Serializable;

public class Init implements Serializable{

    private QuotationService service;

    public Init(QuotationService service){
        this.service = service;
    }

    public QuotationService getService(){
        return this.service;
    }
} 