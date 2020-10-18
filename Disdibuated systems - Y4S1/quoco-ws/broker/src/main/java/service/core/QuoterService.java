package service.core;

import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuoterService;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface QuoterService {
    @WebMethod Quotation generateQuotation(ClientInfo info);
}