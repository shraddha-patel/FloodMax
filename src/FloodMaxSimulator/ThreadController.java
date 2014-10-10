
package FloodMaxSimulator;

import java.util.*;
/**
*
* @author Shraddha, Priyanka
*/
public class ThreadController extends Thread{
  int threadID;
  boolean isAlive;
  int maxSeenSofar;
  boolean startNode;
  HashMap<Integer, ThreadController> neighbours = new HashMap<Integer, ThreadController>();
  ThreadController parent;

  Queue<Integer> messageQueue;

  ThreadController(int threadID) {
    this.threadID = threadID;
    this.maxSeenSofar = threadID;
    this.isAlive = true;
  }

  public void sendMessage(ThreadController receiver, boolean ACK, boolean terminate, boolean COMM, int UID, boolean parent) {
    //MessagePassing message = new MessagePassing(ACK, terminate, COMM, UID, parent);
    System.out.println("sending message to " + receiver.threadID);
    receiver.messageQueue.add(UID);
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
//        while (!messageQueue.isEmpty()) {
//          MessagePassing msg = messageQueue.poll();
//          System.out.println("received " + msg.UID);
//        }
        this.interrupt();
      }
    } catch (InterruptedException e) {
      e.getMessage();
    }
  }
}
