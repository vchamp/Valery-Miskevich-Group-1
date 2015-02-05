package com.epam.test.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.epam.test.jms.model.User;

public class FirstClient {
	Context context = null;
	TopicConnectionFactory factory = null;
	TopicConnection connection = null;
	Topic destination = null;
	TopicSession session = null;
	MessageProducer producer = null;

	public FirstClient() {

	}

	public void sendMessage()  {
		Properties initialProperties = new Properties();
		initialProperties.put(InitialContext.INITIAL_CONTEXT_FACTORY,
				"org.exolab.jms.jndi.InitialContextFactory");
		initialProperties.put(InitialContext.PROVIDER_URL,
				"tcp://localhost:3035");

		try {

			context = new InitialContext(initialProperties);
			factory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
			destination = (Topic) context.lookup("topic1");
			connection = factory.createTopicConnection();
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(destination);
			
			TopicPublisher topicPublisher = session.createPublisher(destination);
			ObjectMessage message = session.createObjectMessage();
			
			User user = new User();
			user.setName("Ilya");
			user.setPhone("+375291234567");
			message.setObject(user);
			
			topicPublisher.publish(message);
			System.out.println("Sent: " + user.toString());
			connection.close();

		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}

		if (context != null) {
			try {
				context.close();
			} catch (NamingException ex) {
				ex.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		FirstClient firstClient = new FirstClient();
		firstClient.sendMessage();
	}
}
