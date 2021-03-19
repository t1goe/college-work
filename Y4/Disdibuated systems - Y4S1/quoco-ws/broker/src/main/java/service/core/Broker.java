package service.core;

import java.util.*;

import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuoterService;

import javax.xml.ws.Endpoint;
import javax.jws.WebService;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public class Broker implements ServiceListener {

    private static LinkedList<QuoterService> quoteServices = new LinkedList<QuoterService>();
    private static LinkedList<String> completedServices = new LinkedList<String>();

    public static void main(String[] args) {
        try{
            Thread.sleep(20000);
        } catch(Exception e){
            e.printStackTrace();
        }

        String host = args.length > 0 ? args[0] : "localhost";

        Endpoint.publish("http://" + host + ":9000/broker", new Broker());

        try {
            //Create JmDNS
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            //Add a service listener
            jmdns.addServiceListener("_http._tcp.local.", new Broker());

            //Wait a bit
            Thread.sleep(300000);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @WebMethod
    public List<Quotation> getQuotations(ClientInfo info) {
        LinkedList<Quotation> quotations = new LinkedList<Quotation>();
        for (QuoterService service : quoteServices) {
            try {
                quotations.add(service.generateQuotation(info));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return quotations;
    }

    @Override
    public void serviceAdded(ServiceEvent event) {
        System.out.println("Service added: " + event.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent event){
        System.out.println("Service removed: " + event.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent event){
        System.out.println("Service resolved: " + event.getInfo());
        String path = event.getInfo().getPropertyString("path");
        if (path != null && !completedServices.contains(path)) {
            completedServices.add(path);
            try{
                invokeQuoteService(path);
            } catch (Exception e) {
                System.out.println("Problem with service: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void invokeQuoteService(String url) throws Exception {
        URL wsdlUrl = new URL(url);
        QName serviceName = new QName("http://core.service/", "QuoterService");

        Service service = Service.create(wsdlUrl, serviceName);

        QName portName = new QName("http://core.service/", "QuoterPort");
        QuoterService quoteService = service.getPort(portName, QuoterService.class);
        quoteServices.add(quoteService);
    }
}
