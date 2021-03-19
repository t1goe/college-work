package broker;

import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Connection;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import javax.jms.Queue;
import javax.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;

import service.core.QuotationRequestMessage;

public class Broker {

	public static void main(String[] args) throws JMSException {
		String host = args.length > 0 ? args[0] : "localhost";

		ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
		Connection connection = factory.createConnection();
		connection.setClientID("broker");
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

		Queue requestQueue = session.createQueue("REQUESTS");

		MessageConsumer consumer = session.createConsumer(requestQueue);

		connection.start();

		// Quickly clear the requests from the queue; just grab and acknowledge if there are any left
		while(true){
			Message message = consumer.receive(10);
			if(message == null){
				break;
			}
			message.acknowledge();
		}

		System.out.println("DEBUG: Broker Successfully started");

		while (true) {
			// Get the next message from the REQUESTS topic
			Message message = consumer.receive();
			// Check it is the right type of message
			if (message instanceof ObjectMessage) {
				System.out.println("DEBUG: Broker succesfully recieved message");
				// It’s an Object Message
				Object content = ((ObjectMessage) message).getObject();
				if (content instanceof QuotationRequestMessage) {
					// It’s a Quotation Request Message
					QuotationRequestMessage request = (QuotationRequestMessage) content;

					// Start up a new brokerthread
					Thread brokerThread = new Thread(new BrokerThread(request, host)); 
            		brokerThread.start();
				}
			} else {
				System.out.println("Unknown message type: " + message.getClass().getCanonicalName());
			}
		}
	}
}