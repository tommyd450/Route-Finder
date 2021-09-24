package sample;
import java.util.ArrayList;

public class GraphNode<T> {
    public T data;



    public AdjacencyMatrix mat;
    public int nodeId;
    public GraphNode<?>[] adjacent = new GraphNode[4]; //keeps track of adjacent

    public GraphNode(T data,AdjacencyMatrix mat)
    {
        this.data=data;
        this.mat=mat;
    }

    //important
    public void connectToNodeUndirected(GraphNode<?> destNode,int direc)
    {
        //a < b (link a to b) ----> a <> b
        //ID is valid node number, its basically all the white nodes in number
        //if they're link its truel, if not false
        mat.amat[nodeId][direc]=mat.amat[destNode.nodeId][direc+1]=true; //takes in node you want to link and its direction
        this.adjacent[direc] = destNode; //adds to the adjancecy list so we can use it
        destNode.adjacent[direc+1] = this;
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "data=" + data +
                ", mat=" + mat +
                ", nodeId=" + nodeId +
                '}';
    }

}
