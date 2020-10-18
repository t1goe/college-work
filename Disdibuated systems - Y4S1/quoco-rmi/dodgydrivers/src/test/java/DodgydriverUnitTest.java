import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import service.core.ClientInfo;
import service.core.Constants;
import service.core.QuotationService;
import service.core.Quotation;

import dodgydrivers.DDQService;

import org.junit.*;
import static org.junit.Assert.assertNotNull;

public class DodgydriverUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService) 
                UnicastRemoteObject.exportObject(new DDQService(),0);

            // Register the object with the RMI Registry
            registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectionTest() throws Exception {
        // Asserts that a connection can be established
        QuotationService service = (QuotationService) registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void generateQuotationTest() throws Exception {
        // Assert that generateQuotation() returns a quotation
        ClientInfo client = new ClientInfo("Jim", ClientInfo.MALE, 23, 1, 1, "123");
        QuotationService service = (QuotationService) registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
        Assert.assertTrue(service.generateQuotation(client) instanceof Quotation);
    }
}