package sample;

import java.util.ArrayList;

public class GraphNodeAL<T>
{
    public T data;
    public int cost = Integer.MAX_VALUE;

    public ArrayList<GraphLinkAL> adjList = new ArrayList<>();

    public GraphNodeAL(T data)
    {
        this.data = data;
    }
    public void connectToDirected(GraphNodeAL<T> dest, int cost)
    {
        adjList.add(new GraphLinkAL(dest, cost));
    }
    public void connectToUndirected(GraphNodeAL<T> dest, int cost)
    {
        adjList.add(new GraphLinkAL(dest, cost));
        dest.adjList.add(new GraphLinkAL(this, cost));
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<GraphLinkAL> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<GraphLinkAL> adjList) {
        this.adjList = adjList;
    }

    @Override
    public String toString() {
        return "GraphNodeAL{" +
                "data=" + data +
                ", cost=" + cost +
                '}';
    }
}
