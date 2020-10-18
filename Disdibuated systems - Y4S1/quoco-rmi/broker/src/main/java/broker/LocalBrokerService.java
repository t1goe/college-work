package broker;

import java.util.LinkedList;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {

	private static Registry registry;

	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException{
		List<Quotation> quotations = new LinkedList<Quotation>();
		
		registry = LocateRegistry.getRegistry(1099);

		for (String name : registry.list()) {
			if (name.startsWith("qs-")) {
				QuotationService service;
				try {
					service = (QuotationService) registry.lookup(name);
					quotations.add(service.generateQuotation(info));
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		}

		return quotations;
	}

	public void createRegistry(int port) throws RemoteException{
		registry= LocateRegistry.createRegistry(port);
	}

	public void registerService(String name, Remote service) throws RemoteException{
		try{
			registry.bind(name, service);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
