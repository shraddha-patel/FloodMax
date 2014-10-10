
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
      while (true) {                                                //send uid   --- children hashmap (id,obj   )
                                                                    //hashmap childstatus (id true)
                                                                    //ack ter 0         ;;;; every uid 0  comm=true uid set
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
         
            parseMessage(messageQueue1);    
     /*       Object[] queueArray = messageQueue.toArray();
            Object isACK = queueArray[0];
            Object isterminate = queueArray[1];
            Object isCOMM = queueArray[2];
            Object UID = queueArray[3];
            Object isParent = queueArray[4];
            if(isACK == "true" && isParent == "true"){
                
            }
            if(isACK == "true" && isterminate == "true"){
                children.remove(this.threadID);
            }
            if(isCOMM == "true")        */
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
   
  /* public int countChildNodes() {
        //returns a count of child nodes
   }
   
   public int countActiveChildNodes() {
       
   }    */
   
  /* public void start() {
       FindAllNeighbours();
       /*
       for each j in neighbours {
        send message to j with UID
       }
       
       */
       
   //}    
   public void parseMessage(BlockingQueue<MessagePassing> messageQueue){
       if(MessagePassing.ACK == true && MessagePassing.parent == true){
           children.put(this.threadID,this);
           childStatus.put(this.threadID,true);
       }
       if(MessagePassing.ACK == true && MessagePassing.terminate == true){
           children.remove(this.threadID);
           
       }
   }
}

  

