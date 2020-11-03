package broker;

import java.lang.Runnable;

import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Connection;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.Response;

import service.core.ClientInfo;
import service.core.Quotation;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jms.ObjectMessage;

import javax.jms.Message;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationRequestMessage;
import service.core.QuotationResponseMessage;
import service.core.ClientApplicationMessage;

public class BrokerThread implements Runnable {

    QuotationRequestMessage requestMessage;
    String host;

    public BrokerThread(QuotationRequestMessage message, String host){
        this.requestMessage = message;
        this.host = host;
    }

    public void run(){
        try{
            ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://" + this.host + ":61616");
            Connection connection = factory.createConnection();

            // it needs to be unique, set it as brokerThread + (requestMessage.id) AKA brokerThread1 etc
            String brokerName = "broker" + Long.toString(requestMessage.id);
            System.out.println(brokerName);
            connection.setClientID(brokerName);

            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Topic quotationsTopic = session.createTopic("QUOTATIONS");
            Topic applicationsTopic = session.createTopic("APPLICATIONS");

            MessageProducer producer = session.createProducer(applicationsTopic);
            MessageConsumer consumer = session.createConsumer(quotationsTopic);
    
            connection.start();

            ClientApplicationMessage outputMessage = new ClientApplicationMessage(requestMessage.info);

            System.out.println("DEBUG: Brokerthread successfully started");

            Message request = session.createObjectMessage(requestMessage);
            producer.send(request);

            Thread.sleep(3000);

            while(true){
                Message message = consumer.receive(15000);
                if (message instanceof ObjectMessage) {
                    System.out.println("DEBUG: Thread " + brokerName + " Message recieved");
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof QuotationResponseMessage) {
                        QuotationResponseMessage response = (QuotationResponseMessage) content;
                        if (response.id == requestMessage.id) {
                            outputMessage.quotations.add(response.quotation);
                            message.acknowledge();
                        }
                    }
                } else if (message == null){
                    break;
                } else {
                    System.out.println("Unknown message type: " + message.getClass().getCanonicalName());
                }
            }
            
            System.out.println("DEBUG: " + brokerName + " loop exited");
            
            connection.stop();

            Queue responseQueue = session.createQueue("RESPONSES");
            producer = session.createProducer(responseQueue);

            connection.start();

            Message response = session.createObjectMessage(outputMessage);
            producer.send(response);

            if(outputMessage.quotations.isEmpty()){
                System.out.println("DEBUG: " + brokerName + " Response empty");
            }

            System.out.println("DEBUG: Thread response sent, " + brokerName + " finished");
            
        } catch(JMSException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }    
}
