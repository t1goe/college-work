import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Constants;

import broker.LocalBrokerService;

import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LocalBrokerUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        try {
            registry = LocateRegistry.createRegistry(1099);
            BrokerService brokerService = (BrokerService) UnicastRemoteObject.exportObject(new LocalBrokerService(), 0);

            // Register the object with the RMI Registry
            registry.bind(Constants.BROKER_SERVICE, brokerService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectionTest() throws Exception {
        //Test that a connection can be established
        BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void getListTest() throws Exception {
        //Test that brokerService.getQuotation() returns a list object
        BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
        assertTrue(service.getQuotations(new ClientInfo("Jim", ClientInfo.MALE, 40, 0, 5, "ABC")) instanceof List);
    }
}