/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FloodMaxSimulator;
import java.util.*;

/**
 *
 * @author Shraddha
 */
public class FloodMaxSimulatorMain {
    public int[][] conn =  {{0,1,1,0,0}, {1,0,1,1,0}, {1,1,0,1,1}, {0,1,1,0,1}, {0,0,1,1,0}};
    public static void main(String args[]) {
        HashMap map = new HashMap();
   //     conn =  {{0,1,1,0,0}, {1,0,1,1,0}, {1,1,0,1,1}, {0,1,1,0,1}, {0,0,1,1,0}};
        int[] arrayIDs = {1,2,3,4,5};
        
        for (int i = 1; i <= 5; i++) {
            ThreadController t = new ThreadController(i);
            map.put(i, t);
            t.start();
        }
    }
}
