package FloodMaxSimulator;

/**
 *
 * @author Shraddha, Priyanka
 */
public class MessagePassing {
  boolean ACK;
  boolean COMM;
  boolean terminate;
  int UID;
  boolean parent;

  MessagePassing(boolean ACK, boolean terminate, boolean COMM, int UID, boolean parent) {
    this.ACK = ACK;
    this.terminate = terminate;
    this.UID = UID;
    this.parent = parent;
  }
}
