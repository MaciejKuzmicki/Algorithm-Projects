package pl.edu.pw.ee;

import org.junit.Assert;
import org.junit.Test;

public class PrimAlgorithmTest {
    //Adjacency matrix
    private int [][] graph = {
            {0,3,5,7,0},
            {3,0,1,0,0},
            {5,1,0,1,0},
            {7,0,1,0,7},
            {0,0,0,7,0}
    };
    //Adjacency matrix
    private int [][] anotherGraph = {
            {0,2,0,4,0,0,0},
            {2,0,3,0,1,0,0},
            {0,3,0,0,0,5,0},
            {4,0,0,0,7,0,3},
            {0,1,0,7,0,4,2},
            {0,0,5,0,4,0,3},
            {0,0,0,3,2,3,0}
    };

    @Test
    public void test() {
        PrimAlgorithm primAlgorithm = new PrimAlgorithm(graph);
        primAlgorithm.findMST("graph.txt");
        primAlgorithm.show();
        //CORRECT OUTPUT:
        //A_B_3
        //B_C_1
        //C_D_1
        //D_E_7
    }

    @Test
    public void test2() {
        PrimAlgorithm primAlgorithm = new PrimAlgorithm(anotherGraph);
        primAlgorithm.findMST("graph.txt");
        primAlgorithm.show();
        //CORRECT OUTPUT:
        //A_B_2
        //B_E_1
        //E_G_2
        //G_F_3
        //B_C_3
        //G_D_3
    }

}
