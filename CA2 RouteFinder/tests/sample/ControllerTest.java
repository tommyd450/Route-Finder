package sample;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    ArrayList<GraphNode<?>> list = new ArrayList<>();
    AdjacencyMatrix mat = new AdjacencyMatrix(20);
    @Before
    public void setUp() throws Exception
    {
         list = new ArrayList<>();
         mat = new AdjacencyMatrix(20);

    }


    @Test
    void findPathBreadthFirstIteratively() {
        for (int i =0; i<20;i++) {
            GraphNode node = new GraphNode(i, mat);
            list.add(node);
            if (i>0&&i<=10)
            {
                list.get(i).connectToNodeUndirected(list.get(i-1),0);
                System.out.println(list.get(i)+" Connected to "+list.get(i-1));
            }
            if (i >10 && i < 20)
            {
                list.get(i).connectToNodeUndirected(list.get(i-10),2);
                System.out.println(list.get(i)+" Connected to "+list.get(i-10));
            }
        }
    }
}