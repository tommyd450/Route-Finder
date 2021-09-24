package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DijkstrasController
{
    //stores the nodes for easier management with saving/loading
    public ArrayList<GraphNodeAL<String>> nodes = new ArrayList<>(); //stores the nodes and connections
    public Dijkstras da = new Dijkstras();

    //create/initialise a node
    public void createNode(String name)
    {
        GraphNodeAL<String> node = new GraphNodeAL<>(name);
        nodes.add(node);
    }

    //link nodes by index
    public void linkNodeUndir(int nodeIndex1, int nodeIndex2, int cost)
    {
        if(nodes.get(nodeIndex1) != null && nodes.get(nodeIndex2) != null)
        {
            nodes.get(nodeIndex1).connectToUndirected(nodes.get(nodeIndex2), cost);
        }
    }
    public void linkNodeDir(int nodeIndex1, int nodeIndex2, int cost)
    {
        if (nodes.get(nodeIndex1) != null && nodes.get(nodeIndex2) != null)
        {
            nodes.get(nodeIndex1).connectToDirected(nodes.get(nodeIndex2), cost);
        }
    }

    //link the nodes by finding their name!
    public void linkNodeUndirByName(String node1, String node2, int cost)
    {
        //initialise indexes
        int i1 = -1;
        int i2 = -1;

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).data.equalsIgnoreCase(node1))
            {
                i1 = i;
            }
            if(nodes.get(i).data.equalsIgnoreCase(node2))
            {
                i2 = i;
            }
        }

        if(i1 >=0 && i2 >= 0)
        {
            nodes.get(i1).connectToUndirected(nodes.get(i2), cost);
        }
    }
    public void linkNodeDirByName(String node1, String node2, int cost)
    {
        //initialise indexes
        int i1 = -1;
        int i2 = -1;

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).data.equalsIgnoreCase(node1))
            {
                i1 = i;
            }
            if(nodes.get(i).data.equalsIgnoreCase(node2))
            {
                i2 = i;
            }
        }

        if(i1 >=0 && i2 >= 0)
        {
            nodes.get(i1).connectToDirected(nodes.get(i2), cost);
        }
    }

    public void unlinkNodes(String node1, String node2)
    {
        //initialise indexes
        int i1 = -1;

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).data.equalsIgnoreCase(node1))
            {
                i1 = i;
            }
        }
        for(int i = 0; i < nodes.get(i1).getAdjList().size(); i++)
        {
            if(nodes.get(i1).getAdjList().get(i).getDest().toString().equalsIgnoreCase(node2))
            {
                nodes.get(i1).getAdjList().remove(i);
            }
        }
    }

    public void removeNode(String node)
    {
        int i1 = -1;

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).data.equalsIgnoreCase(node))
            {
                i1 = i;
            }
        }
        if(i1 >= 0)
        {
            nodes.remove(i1);
        }
    }

    //uses dijkstras to find the cheapest path
    public String cheapestPath(int start, String dest)
    {
        String str = "Cheapest path between " +  nodes.get(start).data + " and " + dest + "\n";
        CostedPath cp=da.findCheapestPathDijkstra(nodes.get(start),dest);
        for(GraphNodeAL<?> n : cp.pathList)
        {
            str += n.data + "\n";
        }
        str += "The total path cost is: "+cp.pathCost;
        return str;
    }

    public String cheapestPathByName(String start, String dest)
    {
        int iStart = -1;
        int iFin = -1;

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).getData().equalsIgnoreCase(start))
            {
                iStart = i;
            }
            if(nodes.get(i).getData().equalsIgnoreCase(dest))
            {
                iFin = i;
            }
        }
        if(iStart >= 0 && iFin >= 0)
        {
            String str = "Cheapest path between " + nodes.get(iStart).data + " and " + nodes.get(iFin).getData() + "\n";
            CostedPath cp = da.findCheapestPathDijkstra(nodes.get(iStart), nodes.get(iFin).getData());
            for (GraphNodeAL<?> n : cp.pathList) {
                str += n.data + "\n";
            }
            str += "The total path cost is: " + cp.pathCost;
            return str;
        }
        else
        {
            return "Invalid Node!";
        }
    }

    //this one takes in strings for each point
    public String cheapestPathWaypoint(String start, String waypoint ,String dest)
    {
        int iStart = -1;
        int iWay = -1;
        int iFin = -1;

        //stores indexes that we can use to get the nodes, since its a very small list performance won't matter
        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).data.equalsIgnoreCase(start))
            {
                iStart = i;
            }
            if(nodes.get(i).data.equalsIgnoreCase(waypoint))
            {
                iWay = i;
            }
            if(nodes.get(i).data.equalsIgnoreCase(dest))
            {
                iFin = i;
            }
        }
        if(iStart >= 0 && iWay >= 0 && iFin >= 0)
        {
            String str = "Path between " + nodes.get(iStart).data + " and " +nodes.get(iFin).data + " while going through " + nodes.get(iWay).data + "\n";
            CostedPath cp1 = da.findCheapestPathDijkstra(nodes.get(iStart), nodes.get(iWay).data);
            CostedPath cp2 = da.findCheapestPathDijkstra(nodes.get(iWay), nodes.get(iFin).data);
            for(GraphNodeAL<?> nodes : cp1.pathList)
            {
                str += nodes.data + "\n";
            }
            for(int i = 1;  i < cp2.pathList.size(); i++) //this is so we can avoid printing the waypoint -node twice!
            {
                str += cp2.pathList.get(i).data + "\n";
            }
            str += "The total path cost is: "+ (cp1.pathCost+cp2.pathCost);
            return str;
        }
        else
        {
            return "No valid path selected! Try Again!";
        }
    }

    //prints the nodes out by name and cost!
    public String toString()
    {
        String str = "";
        for(GraphNodeAL<?> node : nodes)
        {
            str += node.data + ", " + node.cost + "\n";
        }
        return str;
    }

    public String getNodeLinksByName(String node)
    {
        int index = -1; //init as -1

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).getData().equalsIgnoreCase(node))
            {
                index = i;
            }
        }
        if(index >= 0)
        {
            String str = "";
            for(int i = 0; i < nodes.get(index).getAdjList().size(); i++)
            {
                str += nodes.get(index).getAdjList().get(i).getDest().getData() + ", ";
            }
            return str;
        }
        else
        {
            return "Invalid node!";
        }
    }
    public int getNodeLinkCountByName(String node)
    {
        int index = -1; //init as -1

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).getData().equalsIgnoreCase(node))
            {
                index = i;
            }
        }
        if(index >= 0)
        {
            return nodes.get(index).getAdjList().size();
        }
        else
        {
            return 0;
        }
    }

    public int getCheapestLinkCost(String node)
    {
        int index = -1; //init as -1
        int cheapest = Integer.MAX_VALUE;

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).getData().equalsIgnoreCase(node))
            {
                index = i;
            }
        }
        if(index >= 0)
        {
            for(int i = 0; i < nodes.get(index).getAdjList().size(); i++)
            {
                if(nodes.get(index).getAdjList().get(i).getCost() < cheapest)
                {
                    cheapest = nodes.get(index).getAdjList().get(i).getCost();
                }
            }
        }
        else
        {
            return cheapest;
        }
        return cheapest;
    }

    public String getCheapestLinkName(String node)
    {
        int index = -1; //init as -1
        int cheapest = Integer.MAX_VALUE;
        String cheapestName = "Invalid Node!";

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).getData().equalsIgnoreCase(node))
            {
                index = i;
            }
        }
        if(index >= 0)
        {
            for(int i = 0; i < nodes.get(index).getAdjList().size(); i++)
            {
                if(nodes.get(index).getAdjList().get(i).getCost() < cheapest)
                {
                    cheapest = nodes.get(index).getAdjList().get(i).getCost();
                    cheapestName = nodes.get(index).getAdjList().get(i).getDest().getData().toString();
                }
            }
        }
        else
        {
            return cheapestName;
        }
        return cheapestName;
    }

    public String printLinksOfNode(String node)
    {
        int index = -1; //init as -1
        String str = "";

        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).getData().equalsIgnoreCase(node))
            {
                index = i;
            }
        }
        if(index >= 0)
        {
            for(int i = 0; i < nodes.get(index).getAdjList().size(); i++)
            {
                str += "Destination: " + nodes.get(index).getAdjList().get(i).getDest().getData() + ", Cost: " + nodes.get(index).getAdjList().get(i).getDest().getData() + "\n";
            }
        }
        else
        {
            return str += "Invalid Node Selected!";
        }
        return str;
    }

    //get and set nodes
    public ArrayList<GraphNodeAL<String>> getNodes()
    {
        return nodes;
    }
    public void setNodes(ArrayList<GraphNodeAL<String>> nodes)
    {
        this.nodes = nodes;
    }

    //save and load the nodes so we can have a saved route
    public void save() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("landmarks.xml"));
        out.writeObject(nodes);
        out.close();
    }
    public void load() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader("landmarks.xml"));
        nodes = (ArrayList<GraphNodeAL<String>>) in.readObject();
        in.close();
    }
}
