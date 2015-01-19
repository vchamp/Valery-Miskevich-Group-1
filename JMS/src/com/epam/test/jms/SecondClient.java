package com.epam.test.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.epam.test.jms.model.User;

public class SecondClient implements MessageListener{
    Context context = null;
    TopicConnectionFactory factory = null;
    TopicConnection connection = null;
    Topic destination = null;
    TopicSession session = null;
    MessageConsumer consumer = null;

    public SecondClient() {

    }

    public void initReceiverMessages() {

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
            TopicSubscriber topicSubscriber =  session.createSubscriber(destination);
            SecondClient asyncSubscriber = new SecondClient();
            topicSubscriber.setMessageListener(asyncSubscriber);
            connection.start();
            
        } catch (JMSException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
		SecondClient secondClient = new SecondClient();
		secondClient.initReceiverMessages();
    }

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage userObjectMessage = (ObjectMessage) message;
				User user;

				user = (User) userObjectMessage.getObject();

				System.out.println("Message is : " + user.toString());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
