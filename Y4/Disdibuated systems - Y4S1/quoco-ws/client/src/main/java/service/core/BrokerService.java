package service.core;

import java.util.*;

import service.core.ClientInfo;
import service.core.Quotation;
//import service.core.QuoterService;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface BrokerService {
    @WebMethod LinkedList<Quotation> getQuotations(ClientInfo info);
} 