import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import service.core.ClientInfo;
import service.core.Constants;
import service.core.QuotationService;
import service.core.Quotation;

import girlpower.GPQService;

import org.junit.*;
import static org.junit.Assert.assertNotNull;

public class GirlpowerUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService) 
                UnicastRemoteObject.exportObject(new GPQService(),0);

            // Register the object with the RMI Registry
            registry.bind(Constants.GIRL_POWER_SERVICE, quotationService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectionTest() throws Exception {
        // Test that a connection can be established
        QuotationService service = (QuotationService) registry.lookup(Constants.GIRL_POWER_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void generateQuotationTest() throws Exception {
        //Test that generateQuotaiton can return a quotation
        ClientInfo client = new ClientInfo("Jim", ClientInfo.MALE, 23, 1, 1, "123");
        QuotationService service = (QuotationService) registry.lookup(Constants.GIRL_POWER_SERVICE);
        Assert.assertTrue(service.generateQuotation(client) instanceof Quotation);
    }
}