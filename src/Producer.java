import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    private String queueName;
    private String brokerURL;
    private Connection connection;
    private Session session;
    private Queue queue;


    Producer(String brokerURL, String queueName){
        this.brokerURL = brokerURL;
        this.queueName = queueName;
    }

    void connect() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        this.connection = connectionFactory.createConnection();
        this.connection.start();
    }

    void startSession() throws JMSException {
        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.queue = this.session.createQueue(queueName);
    }

    void sendMsg(String msg) throws JMSException {
        MessageProducer messageProducer = this.session.createProducer(this.queue);
        TextMessage textMessage = this.session.createTextMessage();
        textMessage.setText(msg);
        messageProducer.send(textMessage);
    }

    void closeCon() throws JMSException {
        this.connection.close();
    }

    public static void main(String[] args) throws Exception{
        Producer activeProd = new Producer("tcp://localhost:61616", "ActiveMQ");
        activeProd.connect();
        activeProd.startSession();
        activeProd.sendMsg("Hello World");
        activeProd.closeCon();
    }
}