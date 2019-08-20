
public class QueueHandler {

    public static void main(String[] args) throws Exception {

        for (int i=0; i<10; i++){
            Producer activeProd = new Producer("tcp://localhost:61616", "ActiveMQ");
            activeProd.connect();
            activeProd.startSession();
            activeProd.sendMsg("Hello World");
            activeProd.closeCon();

            Consumer ActiveCons = new Consumer("tcp://localhost:61616", "ActiveMQ");
            ActiveCons.connect();
            ActiveCons.startSession();
            ActiveCons.rcvMsg();
            ActiveCons.printMsg();
            ActiveCons.transMsg("ActiveMQ2");
            ActiveCons.closeCon();
        }
    }
}
