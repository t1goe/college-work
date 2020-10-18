package service.core;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.text.NumberFormat;
import java.net.*;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.WebMethod;
import javax.xml.ws.Endpoint;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

/**
 * Implementation of Quotation Service for Dodgy Drivers Insurance Company
 *
 * @author Rem
 */
@WebService
@SOAPBinding
public class Quoter extends AbstractQuotationService {
    // All references are to be prefixed with an DD (e.g. DD001000)
    public static final String PREFIX = "DD";
    public static final String COMPANY = "Dodgy Drivers Corp.";

    public static void main(String[] args) {

        String host = args.length > 0 ? args[0] : "localhost";
        if (args.length > 0) {
            host = args[0];
        }
        Endpoint.publish("http://" + host + ":9002/quotation", new Quoter());
        jmdnsAdvertise(host);
    }

    private static void jmdnsAdvertise(String host) {
        try {
            String config = "path=http://" + host + ":9002/quotation?wsdl";
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "ws-service", 9002, config);
            jmdns.registerService(serviceInfo);

            // Wait a bit
            Thread.sleep(1000000);

            // Unregister all services
            jmdns.unregisterAllServices();
        } catch (Exception e) {
            System.out.println("Problem Advertising Service: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Quote generation:
     * 5% discount per penalty point (3 points required for qualification)
     * 50% penalty for <= 3 penalty points
     * 10% discount per year no claims
     */
    @WebMethod
    public Quotation generateQuotation(ClientInfo info) {
        // Create an initial quotation between 800 and 1000
        double price = generatePrice(800, 200);

        // 5% discount per penalty point (3 points required for qualification)
        int discount = (info.points > 3) ? 5 * info.points : -50;

        // Add a no claims discount
        discount += getNoClaimsDiscount(info);

        // Generate the quotation and send it back
        return new Quotation(COMPANY, generateReference(PREFIX), (price * (100 - discount)) / 100);
    }

    private int getNoClaimsDiscount(ClientInfo info) {
        return 10 * info.noClaims;
    }

}
