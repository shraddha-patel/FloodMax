package FloodMaxSimulator;

/**
 *
 * @author Shraddha, Priyanka
 */
public class MessagePassing {
  int maxUID;
  int senderID;
  boolean ACK;
  boolean COMM;
  boolean terminate;
  boolean parent;

  MessagePassing(boolean ACK, boolean terminate, boolean COMM, int UID, boolean parent, int senderID) {
    this.ACK = ACK;
    this.terminate = terminate;
    this.maxUID = UID;
    this.parent = parent;
    this.senderID = senderID;
  }
}
