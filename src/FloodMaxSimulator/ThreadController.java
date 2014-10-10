
package FloodMaxSimulator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
*
* @author Shraddha, Priyanka
*/
public class ThreadController extends Thread{
  int threadID;
  boolean isAlive;
  int maxSeenSofar;
  boolean startNode;
  Set uid = new HashSet();
  HashMap<Integer, ThreadController> neighbours = new HashMap<Integer, ThreadController>();
  HashMap<Integer,Boolean> childStatus = new HashMap<Integer,Boolean>();
  HashMap<Integer, ThreadController> children = new HashMap<Integer, ThreadController>();
  ThreadController parent;

  BlockingQueue<MessagePassing> messageQueue = new ArrayBlockingQueue<MessagePassing>(100, true);
  //BlockingQueue<MessagePassing> messageQueue1 = new ArrayBlockingQueue<MessagePassing>(100, true);

  ThreadController(int threadID) {
    this.threadID = threadID;
    this.maxSeenSofar = threadID;
    this.isAlive = true;
  }

  public void sendMessage(ThreadController receiver, boolean ACK, boolean terminate, boolean COMM, int UID, boolean parent) {
      System.out.println("In sendMessage()");
    MessagePassing message = new MessagePassing(ACK, terminate, COMM, UID, parent, this.threadID);
    //System.out.println("sending message to " + receiver.threadID);
    receiver.messageQueue.add(message);
  }

  public void run() {
    try {
      while (true) {
        System.out.println("In run()..while 1");
        sleep(4000);
        Iterator iterator = neighbours.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println("In run()..while 2::");
        Map.Entry mapEntry = (Map.Entry) iterator.next();
        System.out.print("Before sendMessage::"+(ThreadController)mapEntry.getValue());
        sendMessage((ThreadController)mapEntry.getValue(), false, false, true, maxSeenSofar, false);
    }
        sleep(4000);
        while (!messageQueue.isEmpty()) {
            System.out.println("In run()..while 3:::");
          MessagePassing receivedMessage = messageQueue.poll();
          System.out.print("Received Message:::" + receivedMessage);
          if (this.parent == null) {
            this.parent = neighbours.get(receivedMessage.senderID);
            sendMessage(this.parent, true, false, false, 0, true);
          }
          parseMessage(receivedMessage);
        }
        this.interrupt();
      }
    } catch (InterruptedException e) {
      e.getMessage();
    }
  }
   public void parseMessage(MessagePassing receivedMessage){
       System.out.println("In parseMessage():::");
       System.out.println("receivedMessage.ACK::"+ receivedMessage.ACK);
       System.out.println("receivedMessage.terminate::"+ receivedMessage.terminate);
       System.out.println("receivedMessage.COMM::"+ receivedMessage.COMM);
       System.out.println("receivedMessage.UID::"+ receivedMessage.maxUID);
       System.out.println("receivedMessage.parent::"+ receivedMessage.parent);
       System.out.println("receivedMessage.senderID::"+ receivedMessage.senderID);
       if(receivedMessage.ACK == true && receivedMessage.parent == true){
           children.put(this.threadID,this);
           childStatus.put(this.threadID,true);
       }
       if(receivedMessage.ACK == true && receivedMessage.terminate == true){
           children.remove(this.threadID);
           
       }
   }
}

  

