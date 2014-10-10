
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
  ThreadController parent;

  BlockingQueue<MessagePassing> messageQueue = new ArrayBlockingQueue<MessagePassing>(100, true);
  BlockingQueue<MessagePassing> messageQueue1 = new ArrayBlockingQueue<MessagePassing>(100, true);

  ThreadController(int threadID) {
    this.threadID = threadID;
    this.maxSeenSofar = threadID;
    this.isAlive = true;
  }

  public void sendMessage(ThreadController receiver, boolean ACK, boolean terminate, boolean COMM, int UID, boolean parent) {
    MessagePassing message = new MessagePassing(ACK, terminate, COMM, UID, parent);
    System.out.println("sending message to " + receiver.threadID);
    receiver.messageQueue.add(message);
  }

  public void run() {
    try {
      while (true) {
        sleep(4000);
        Iterator iterator = neighbours.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry mapEntry = (Map.Entry) iterator.next();
          sendMessage((ThreadController)mapEntry.getValue(), false, false, true, maxSeenSofar, false);
        }
        sleep(4000);
        while (!messageQueue.isEmpty()) {
          messageQueue1 = (BlockingQueue) messageQueue.poll();
          System.out.println("received " + messageQueue1);
        }
        this.interrupt();
      }
    } catch (InterruptedException e) {
      e.getMessage();
    }
  }
   
   public boolean isStartNode() {   //return if current thread represents the start node
       return this.threadID == 1;
   }
   
   public int countChildNodes() {
        //returns a count of child nodes
   }
   
   public int countActiveChildNodes() {
       
   }
   
   public void start() {
       FindAllNeighbours();
       /*
       for each j in neighbours {
        send message to j with UID
       }
       
       */
       
   }
}

  

