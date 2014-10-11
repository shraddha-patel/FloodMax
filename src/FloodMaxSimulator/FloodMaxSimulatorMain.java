package FloodMaxSimulator;

import java.io.FileReader;
import java.io.StreamTokenizer;
import java.util.*;

/**
 *
 * @author Shraddha, Priyanka
 */
public class FloodMaxSimulatorMain {
  HashMap<Integer, ThreadController> Nodes = new HashMap<Integer, ThreadController>();

  public FloodMaxSimulatorMain(int threadCount, int[] arrayIds, int[][] conn) {

    for (int i = 0; i < threadCount; i++) {
      ThreadController t = new ThreadController(arrayIds[i]);
      if (i == 0) {
        t.startNode = true;
      }
      this.Nodes.put(arrayIds[i], t);
    }
  }
  public void createLinks(int[][] conn, int threadCount, int[] arrayIds) {
    for (int i = 0; i < threadCount; i++) {
      for (int j = 0; j < threadCount; j++) {
        if (conn[i][j] == 1) {
          ThreadController t1 = Nodes.get(arrayIds[i]);
          ThreadController t2 = Nodes.get(arrayIds[j]);
          t1.neighbours.put(arrayIds[j], t2);
          t2.neighbours.put(arrayIds[i], t1);
          System.out.println("Creating link between threads "+ (i+1) +"and "+ (j+1));
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
      int threadCount = 0;
      int[] arrayIds = null;
      int[][] conn = null;
     
      try{
            StreamTokenizer tokenizer = new StreamTokenizer(new FileReader("Input-File.txt"));
            tokenizer.slashSlashComments(true);
            tokenizer.eolIsSignificant(false);
            tokenizer.nextToken();
            threadCount = (int)tokenizer.nval;
            arrayIds = new int[threadCount];
            conn = new int[threadCount][threadCount];
         //   System.out.println("Thread Count::"+threadCount);
            for(int i=0;i<threadCount;i++){
                tokenizer.nextToken();
                if (tokenizer.ttype == StreamTokenizer.TT_NUMBER){
                //    System.out.println("In if::"+(int)tokenizer.nval);
                    arrayIds[i] = (int)tokenizer.nval;
                //    System.out.println("ArrayID::"+arrayIds[i]);
                }
            }
            for(int i=0;i<threadCount;i++){
                for(int j=0;j<threadCount;j++){
                    tokenizer.nextToken();
                    if (tokenizer.ttype == StreamTokenizer.TT_NUMBER){
                 //       System.out.println("In if::"+(int)tokenizer.nval);
                        conn[i][j] = (int)tokenizer.nval;
                 //       System.out.println("Conn::"+conn[i][j]);
                    }
                }
            }
      }catch(Exception e){
          System.out.println("Exception::" +e);
    }
   
    FloodMaxSimulatorMain floodMax = new FloodMaxSimulatorMain(threadCount, arrayIds, conn);
    floodMax.createLinks(conn, threadCount, arrayIds);
    floodMax.Init();
  }
}
