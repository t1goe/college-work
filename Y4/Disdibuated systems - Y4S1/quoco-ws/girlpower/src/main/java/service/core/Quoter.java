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
 * Implementation of the Girl Power insurance quotation service.
 * 
 * @author Rem
 *
 */
@WebService
@SOAPBinding
public class Quoter extends AbstractQuotationService {
	// All references are to be prefixed with an DD (e.g. DD001000)
	public static final String PREFIX = "GP";
	public static final String COMPANY = "Girl Power Inc.";

	public static void main(String[] args) {
		String host = args.length > 0 ? args[0] : "localhost";
		if (args.length > 0) {
			host = args[0];
		}
		Endpoint.publish("http://" + host + ":9003/quotation", new Quoter());
		jmdnsAdvertise(host);
	}

	private static void jmdnsAdvertise(String host) {
		try {
			String config = "path=http://" + host + ":9003/quotation?wsdl";
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			// Register a service
			ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "ws-service", 9003, config);
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
	 * 50% discount for being female
	 * 20% discount for no penalty points
	 * 15% discount for < 3 penalty points
	 * no discount for 3-5 penalty points
	 * 100% penalty for > 5 penalty points
	 * 5% discount per year no claims
	 */
	@WebMethod
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1000
		double price = generatePrice(600, 400);
		
		// Automatic 50% discount for being female
		int discount = (info.gender == ClientInfo.FEMALE) ? 50:0;
		
		// Add a points discount
		discount += getPointsDiscount(info);
		
		// Add a no claims discount
		discount += getNoClaimsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	private int getNoClaimsDiscount(ClientInfo info) {
		return 5*info.noClaims;
	}

	private int getPointsDiscount(ClientInfo info) {
		if (info.points == 0) return 20;
		if (info.points < 3) return 15;
		if (info.points < 6) return 0;
		return -100;
		
	}

}
