package sample;

public class GraphLinkAL
{
    public GraphNodeAL<?> dest;
    public int cost;

    public GraphLinkAL(GraphNodeAL<?> dest, int cost)
    {
        this.dest = dest;
        this.cost = cost;
    }

    public GraphNodeAL<?> getDest() {
        return dest;
    }

    public void setDest(GraphNodeAL<?> dest) {
        this.dest = dest;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "GraphLinkAL{" +
                "dest=" + dest +
                ", cost=" + cost +
                '}';
    }
}

