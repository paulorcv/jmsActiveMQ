package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorTopico {
	
	public static void main (String args []) throws NamingException, JMSException {
		
		InitialContext context;
	
			context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Connection connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Topic topico = (Topic) context.lookup("loja");
			MessageProducer producer = session.createProducer(topico);
			
		
			for(int i=0; i<=1000; i++) {
				Message message = session.createTextMessage("(" + i + ") " + "Teste de envio de mensagem por um Producer");
				producer.send(message);
			}
			
			
			
			
			new Scanner(System.in).nextLine();

			session.close();
			connection.close();
			context.close();
		
	}
}
