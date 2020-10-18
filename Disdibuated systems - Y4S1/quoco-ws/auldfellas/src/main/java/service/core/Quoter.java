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
 * Implementation of the AuldFellas insurance quotation service.
 *
 * @author Rem
 */

@WebService
@SOAPBinding
public class Quoter extends AbstractQuotationService {
    // All references are to be prefixed with an AF (e.g. AF001000)
    public static final String PREFIX = "AF";
    public static final String COMPANY = "Auld Fellas Ltd.";

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        if (args.length > 0) {
            host = args[0];
        }
        Endpoint.publish("http://" + host + ":9001/quotation", new Quoter());
        jmdnsAdvertise(host);
    }

    private static void jmdnsAdvertise(String host) {
        try {
            String config = "path=http://" + host + ":9001/quotation?wsdl";
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "ws-service", 9001, config);
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
     * 30% discount for being male
     * 2% discount per year over 60
     * 20% discount for less than 3 penalty points
     * 50% penalty (i.e. reduction in discount) for more than 60 penalty points
     */
    @WebMethod
    public Quotation generateQuotation(ClientInfo info) {
        // Create an initial quotation between 600 and 1200
        double price = generatePrice(600, 600);

        // Automatic 30% discount for being male
        int discount = (info.gender == ClientInfo.MALE) ? 30 : 0;

        // Automatic 2% discount per year over 60...
        discount += (info.age > 60) ? (2 * (info.age - 60)) : 0;

        // Add a points discount
        discount += getPointsDiscount(info);

        // Generate the quotation and send it back
        return new Quotation(COMPANY, generateReference(PREFIX), (price * (100 - discount)) / 100);
    }

    private int getPointsDiscount(ClientInfo info) {
        if (info.points < 3) return 20;
        if (info.points <= 6) return 0;
        return -50;

    }
}
