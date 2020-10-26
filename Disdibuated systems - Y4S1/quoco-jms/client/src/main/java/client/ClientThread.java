package client;

import java.lang.Runnable;
import java.text.NumberFormat;

import javax.jms.MessageConsumer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;

import service.core.ClientInfo;
import service.core.Quotation;

import service.core.ClientApplicationMessage;

public class ClientThread implements Runnable {

    String host;

    public ClientThread(String host){
        this.host = host;
    }

    public void run(){
        try{
            ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://" + this.host + ":61616");
            Connection connection = factory.createConnection();
            connection.setClientID("clientThread");

            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue quotaitonsQueue = session.createQueue("RESPONSES");
            MessageConsumer consumer = session.createConsumer(quotaitonsQueue);
            connection.start();

            System.out.println("DEBUG: ClientThread successfully started");

            while(true){
                Message message = consumer.receive(20000);
                if (message instanceof ObjectMessage) {
                    System.out.println("DEBUG: ClientThread Message recieved");
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof ClientApplicationMessage) {
                        ClientApplicationMessage response = (ClientApplicationMessage) content;
    
                        displayProfile(response.clientinfo);
                        for(Quotation quote : response.quotations){
                            displayQuotation(quote);
                        }
                        System.out.println("\n");
                    }
                    message.acknowledge();
                } else if (message == null){
                    // if null then the reciever timed out waiting for more requests, so assume done.
                    break;
                } else {
                    System.out.println("Unknown message type: " + message.getClass().getCanonicalName());
                }
            }
            
            System.out.println("DEBUG: ClientThread loop exited, Clienthread finished");
            
            connection.stop();
            
        } catch(JMSException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| License Number: " + String.format("%1$-19s", info.licenseNumber) + 
				" | No Claims: " + String.format("%1$-24s", info.noClaims+" years") +
				" | Penalty Points: " + String.format("%1$-19s", info.points)+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
}