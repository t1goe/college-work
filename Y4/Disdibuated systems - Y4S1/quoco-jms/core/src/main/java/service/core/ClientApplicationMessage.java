package service.core;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ClientApplicationMessage implements Serializable{
    public ClientInfo clientinfo;
    public List<Quotation> quotations;

    public ClientApplicationMessage(ClientInfo info){
        this.clientinfo = info;
        this.quotations = new LinkedList<Quotation>();
    }
    
}
