package FloodMaxSimulator;
import java.util.*;

/**
 *
 * @author Shraddha, Priyanka
 */
public class FloodMaxSimulatorMain {
//<<<<<<< HEAD
  //  public static int[][] conn =  {{0,1,1,0,0}, {1,0,1,1,0}, {1,1,0,1,1}, {0,1,1,0,1}, {0,0,1,1,0}};
 /*   public static void main(String args[]) {
        HashMap map = new HashMap();
   //     conn =  {{0,1,1,0,0}, {1,0,1,1,0}, {1,1,0,1,1}, {0,1,1,0,1}, {0,0,1,1,0}};
        int[] arrayIDs = {1,2,3,4,5};
        
        for (int i = 1; i <= 5; i++) {
            ThreadController t = new ThreadController(i);
            map.put(i, t);
            t.start();
//=======
        }
    }*/
  HashMap<Integer, ThreadController> Nodes = new HashMap<Integer, ThreadController>();

  public FloodMaxSimulatorMain(int threadCount, int[] arrayIds, int[][] conn) {

    for (int i = 0; i < threadCount; i++) {
      ThreadController t = new ThreadController(arrayIds[i]);
      this.Nodes.put(arrayIds[i], t);
    }
    createLinks(conn, threadCount, arrayIds);
    Init();
  }
  public void createLinks(int[][] conn, int threadCount, int[] arrayIds) {
    for (int i = 0; i < threadCount; i++) {
      for (int j = 0; j < threadCount; j++) {
        if (conn[i][j] == 1) {
          ThreadController t1 = Nodes.get(arrayIds[i]);
          ThreadController t2 = Nodes.get(arrayIds[j]);
          t1.neighbours.put(arrayIds[j], t2);
          t2.neighbours.put(arrayIds[i], t1);
//>>>>>>> origin/master
        }
      }
    }
  }
  public void Init() {
    try {
      for (ThreadController thread : this.Nodes.values()) {
        System.out.println("starting thread .." + thread.threadID);
        thread.start();
      }
    } catch (Exception e) {
        System.out.println("Exception Raised");
    }

  }
  public static void main(String args[]) {
    int threadCount = 5;
    int[] arrayIds = {1, 2, 3, 4, 5};
    int[][] conn = {{0,1,1,0,0}, {1,0,1,1,0}, {1,1,0,1,1}, {0,1,1,0,1}, {0,0,1,1,0}};
    FloodMaxSimulatorMain floodMax = new FloodMaxSimulatorMain(threadCount, arrayIds, conn);
  }
}
