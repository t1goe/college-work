package client;

import javax.jms.MessageProducer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;

import service.core.ClientInfo;
import service.core.QuotationRequestMessage;

public class Main {
	
	private static int SEED_ID = 0;

	/**
	 * This is the starting point for the application. Here, we must
	 * get a reference to the Broker Service and then invoke the
	 * getQuotations() method on that service.
	 * 
	 * Finally, you should print out all quotations returned
	 * by the service.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws JMSException{
		String host = args.length > 0 ? args[0] : "localhost";

		// Start up a new ClientThread
		Thread clientThread = new Thread(new ClientThread(host)); 
		clientThread.start();

		ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
 		Connection connection = factory.createConnection();
 		connection.setClientID("client");
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		 
 		Queue requestQueue = session.createQueue("REQUESTS");
 		MessageProducer producer = session.createProducer(requestQueue);

		connection.start();
		
		try{
			Thread.sleep(2000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}

		int i = 0;
		for(ClientInfo client : clients){
			QuotationRequestMessage quotationRequest = new QuotationRequestMessage(SEED_ID++, client);
			Message request = session.createObjectMessage(quotationRequest);
			producer.send(request);
			System.out.println("DEBUG: Client request " + Integer.toString(i++) + " made");
		}
		System.out.println("DEBUG: Client concluded");
	}
	
	/**
	 * Test Data
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")
	};
}