package sample;

import java.lang.reflect.Array;
import java.util.Arrays;

public class AdjacencyMatrix {

    public boolean[][] amat;
    public GraphNode<?>[] nodes;
    public int nodeCount = 0;

    //originally N squared, now its just the size of the image by 4
    public AdjacencyMatrix(int size)
    {
        amat = new boolean [size][4];
    }


}
