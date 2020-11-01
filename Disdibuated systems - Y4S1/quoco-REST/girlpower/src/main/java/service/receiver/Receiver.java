package service.receiver;

import service.girlpower.GPQService;

import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Connection;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import javax.jms.Topic;
import javax.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;

import service.core.Quotation;
import service.core.QuotationRequestMessage;
import service.core.QuotationResponseMessage;

public class Receiver{

	private static GPQService gpqservice = new GPQService();

	public static void main(String args[]) throws JMSException {
        String host = args.length > 0 ? args[0] : "localhost";

		ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
		Connection connection = factory.createConnection();
		connection.setClientID("girlpower");
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE); 
		
		Topic quotationsTopic = session.createTopic("QUOTATIONS");
		Topic applicationTopic = session.createTopic("APPLICATIONS");
		MessageConsumer consumer = session.createConsumer(applicationTopic);
		MessageProducer producer = session.createProducer(quotationsTopic);
		
		connection.start();
		
		while (true) {
			// Get the next message from the APPLICATION topic
			Message message = consumer.receive();
			// Check it is the right type of message
			if (message instanceof ObjectMessage) {
				// It’s an Object Message
				Object content = ((ObjectMessage) message).getObject();
				if (content instanceof QuotationRequestMessage) {
					// It’s a Quotation Request Message
					QuotationRequestMessage request = (QuotationRequestMessage) content;
					// Generate a quotation and send a quotation response message…
					Quotation quotation = gpqservice.generateQuotation(request.info);
					Message response = session.createObjectMessage(new QuotationResponseMessage(request.id, quotation));
					producer.send(response);
				}
			} else {
				System.out.println("Unknown message type: " + message.getClass().getCanonicalName());
			}
		}
	}
}