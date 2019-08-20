import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

    private Message message;
    public TextMessage textMessage;
    private String queueName;
    private String brokerURL;
    private Connection connection;
    private Session session;
    private Queue queue;
    private Queue queue2;

    Consumer(String brokerURL, String queueName) {
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

    void rcvMsg() throws JMSException {
        MessageConsumer messageConsumer = this.session.createConsumer(this.queue);
        this.message = messageConsumer.receive();
    }

    void printMsg() throws JMSException {
        if (this.message instanceof TextMessage) {
            this.textMessage = (TextMessage) this.message;
            System.out.println("Received message '" + this.textMessage.getText() + "'");
        }
    }

    void transMsg(String Q) throws JMSException {
        this.queue2 = this.session.createQueue(Q);
        Producer TransProd = new Producer("tcp://localhost:61616", "ActiveMQ2");
        TransProd.connect();
        TransProd.startSession();
        TransProd.sendMsg(textMessage.getText());
    }

    public void closeCon() throws JMSException {
        this.connection.close();
    }

    public static void main(String[] args) throws Exception{
        Consumer ActiveCons = new Consumer("tcp://localhost:61616", "ActiveMQ");
        ActiveCons.connect();
        ActiveCons.startSession();
        ActiveCons.rcvMsg();
        ActiveCons.printMsg();
        ActiveCons.closeCon();
    }
}