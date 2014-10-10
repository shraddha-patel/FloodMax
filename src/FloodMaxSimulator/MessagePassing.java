package FloodMaxSimulator;

/**
 *
 * @author Shraddha, Priyanka
 */
public class MessagePassing {
  static boolean ACK;
  static boolean COMM;
  static boolean terminate;
  static int UID;
  static boolean parent;

  MessagePassing(boolean ACK, boolean terminate, boolean COMM, int UID, boolean parent) {
    this.ACK = ACK;
    this.terminate = terminate;
    this.UID = UID;
    this.parent = parent;
  }
}
