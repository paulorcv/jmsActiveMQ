package br.com.caelum.jms;

import java.io.StringWriter;
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
import javax.xml.bind.JAXB;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

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
		
//			for(int i=0; i<=100; i++) {
//				Message message = session.createTextMessage("(" + i + ") " + "Teste de envio de mensagem (ebook) ");
//				message.setBooleanProperty("ebook", false);
//				producer.send(message);
//			}
			
			Pedido pedido = new PedidoFactory().geraPedidoComValores();

//			StringWriter writer = new StringWriter();
//
//			JAXB.marshal(pedido, writer);
//
//			String xml = writer.toString();

//			Message message = session.createTextMessage(xml);
			
			Message message = session.createObjectMessage(pedido);
			message.setBooleanProperty("ebook", false);
			producer.send(message);
			
			
			new Scanner(System.in).nextLine();

			session.close();
			connection.close();
			context.close();
		
	}
}
