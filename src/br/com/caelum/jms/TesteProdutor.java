package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutor {
	
	public static void main (String args []) throws NamingException, JMSException {
		
		InitialContext context;
	
			context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Connection connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination fila = (Destination) context.lookup("financeiro");
			MessageProducer producer = session.createProducer(fila);
			
		
			for(int i=0; i<=10000; i++) {
				Message message = session.createTextMessage("(" + i + ") " + "Teste de envio de mensagem por um Producer");
				producer.send(message);
			}
			
			
			
			
			new Scanner(System.in).nextLine();

			session.close();
			connection.close();
			context.close();
		
	}
}
