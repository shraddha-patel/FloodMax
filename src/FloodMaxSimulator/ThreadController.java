
package FloodMaxSimulator;

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
  int leader;
 
  Set<Integer> nodesUnderMe = Collections.synchronizedSet(new HashSet<Integer>(100));
  Set<Integer> receivedUIDs = new HashSet<Integer>(100);
  HashMap<Integer, ThreadController> neighbours = new HashMap<Integer, ThreadController>();
  HashMap<Integer,Boolean> activeChildren = new HashMap<Integer,Boolean>();
  HashMap<Integer, ThreadController> children = new HashMap<Integer, ThreadController>();
  ThreadController parent;

  BlockingQueue<MessagePassing> messageQueue = new ArrayBlockingQueue<MessagePassing>(1000, true);

  ThreadController(int threadID) {
    this.threadID = threadID;
    this.maxSeenSofar = threadID;
    this.isAlive = true;
  }

  public void sendMessage(ThreadController receiver, boolean ACK, boolean terminate, boolean COMM, int UID, boolean parent) {
    MessagePassing message = new MessagePassing(ACK, terminate, COMM, UID, parent, this.threadID);
    receiver.messageQueue.add(message);
  }

  public int countActiveChildren() {
    return this.activeChildren.size();
  }

  public void run() {
    try {
      while (true) {
        sleep(500);
        Iterator iterator = neighbours.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry mapEntry = (Map.Entry) iterator.next();
          sendMessage((ThreadController)mapEntry.getValue(), false, false, true, maxSeenSofar, false);
        }
        sleep(500);
        while (!messageQueue.isEmpty()) {

          MessagePassing receivedMessage = messageQueue.poll();

          synchronized(this) {

            if (this.parent == null) {
              ThreadController parentContender = neighbours.get(receivedMessage.senderID);
              if (receivedMessage.COMM) {
                if (!nodesUnderMe.contains(parentContender.threadID)) {
                    if (this.nodesUnderMe.size() > 0) {
                        parentContender.nodesUnderMe.addAll(this.nodesUnderMe);
                    }
                    parentContender.nodesUnderMe.add(this.threadID);
                    this.parent = parentContender;
                    System.out.println("Parent of " + threadID + " is " + receivedMessage.senderID);
                    sendMessage(this.parent, true, false, false, 0, true);
                }
              }
            }
          }
          parseMessage(receivedMessage);
          sleep(500);
        }
        if (receivedUIDs.size() > 0) {
          maxSeenSofar = Math.max(maxSeenSofar, Collections.max(receivedUIDs, null));
        }
        if (this.parent != null) {
          sendMessage(this.parent, false, false, true, maxSeenSofar, false);
        }
        if (countActiveChildren() == 0) {
          isAlive = false;
          sendMessage(this.parent, true, true, false, 0, false);
          if (this.parent != null) {
            System.out.println(threadID + " terminating...parent is " + this.parent.threadID);
          } else {
            System.out.println(threadID + " terminating...leader is " + maxSeenSofar );
          }
          this.interrupt();
        }
      }
    } catch (InterruptedException e) {
      e.getMessage();
    }
  }
   public void parseMessage(MessagePassing receivedMessage){
       if(receivedMessage.ACK == true && receivedMessage.parent == true){
         children.put(receivedMessage.senderID, neighbours.get(receivedMessage.senderID));
         activeChildren.put(receivedMessage.senderID,true);
       }
       else if(receivedMessage.ACK == true && receivedMessage.terminate == true){
         activeChildren.remove(receivedMessage.senderID);
       } else if (receivedMessage.COMM == true && receivedMessage.maxUID > 0) {
          receivedUIDs.add(receivedMessage.maxUID);
       }
   }
}

  

