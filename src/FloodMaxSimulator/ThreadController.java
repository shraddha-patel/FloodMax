/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FloodMaxSimulator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Shraddha
 */
public class ThreadController extends Thread  {
   
   int threadID;
   int maxSeenSofar;
   boolean startNode;
   ThreadController parent;
   ArrayList neighbours = new ArrayList();
   ArrayList children = new ArrayList();
   // ThreadID = (boolean)Status
   HashMap ChildStatus = new HashMap();
   
   ThreadController(int threadID) {
       this.threadID = threadID;
       this.maxSeenSofar = threadID;
   }
   
   public void FindAllNeighbours() {        //return neighbours from conn array;  
       for (int i = 1; i<=5; i++){
            for (int j = 1; j<=5; j++){
                if(FloodMaxSimulatorMain.conn[i][j] == 1)
                    neighbours.add(FloodMaxSimulatorMain.conn[i][j]);
            }
       }
    }
   
   public boolean isStartNode() {
       //return if current thread represents the start node
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
