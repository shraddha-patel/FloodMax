/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FloodMaxSimulator;

import java.util.ArrayList;

/**
 *
 * @author Shraddha
 */
public class ThreadController extends Thread{
   int threadID;
   int maxSeenSofar;
   boolean startNode;
   ThreadController parent;
   ArrayList neighbours = new ArrayList();
   ArrayList children = new ArrayList();
   Map ChildStatus = new Map();
   
   ThreadController(int threadID) {
       this.threadID = threadID;
       this.maxSeenSofar = threadID;
   }
   
   public void FindAllNeighbours() {
     //return neighbours from conn array;   
   }
   
   public boolean isStartNode() {
       //return if current thread represents the start node
   }
   
   public int countChildNodes() {
       //returns a count of child nodes
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
