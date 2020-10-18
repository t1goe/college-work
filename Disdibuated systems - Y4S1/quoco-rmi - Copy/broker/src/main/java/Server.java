import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import broker.LocalBrokerService;

import service.core.BrokerService;
import service.core.Constants;

public class Server {
    public static void main(String[] args) {
        BrokerService bService = new LocalBrokerService();
        try {
            // Connect to the RMI Registry - creating the registry will be the
            // responsibility of the broker.
            // Registry registry = LocateRegistry.createRegistry(1099);
            Registry registry = null;
            if (args.length == 0) {
                registry = LocateRegistry.createRegistry(1099);
            } else {
                registry = LocateRegistry.getRegistry(args[0], 1099);
            }
            // Create the Remote Object
            BrokerService brokerService = (BrokerService) UnicastRemoteObject.exportObject(bService, 0);
            // Register the object with the RMI Registry
            registry.bind(Constants.BROKER_SERVICE, brokerService);
            
            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}