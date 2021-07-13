package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidorTopicoComercial {
	
	public static void main (String args []) throws NamingException, JMSException {
		
		InitialContext context;
	
			context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Connection connection = factory.createConnection();
			connection.setClientID("comercial");
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Topic topico = (Topic) context.lookup("loja");
			MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
			
			
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("---> Recebendo mensagem: " + textMessage.getText());
					} catch (JMSException e) {
						System.err.println("Erro:  " + e.getMessage());						
					}
				}
			});
			
			
			new Scanner(System.in).nextLine();

			session.close();
			connection.close();
			context.close();
		
	}
}
