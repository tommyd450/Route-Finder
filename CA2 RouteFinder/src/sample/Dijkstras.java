package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstras
{
    public static <T> CostedPath findCheapestPathDijkstra(GraphNodeAL<?> startNode, T lookingfor)
    {
        CostedPath cp = new CostedPath(); //Create result object for cheapest path
        ArrayList<GraphNodeAL<?>> enc = new ArrayList<>(), unenc = new ArrayList<>();
        startNode.cost = 0; //Sets starting nodes to zero so it doesnt add to total cost
        unenc.add(startNode);
        GraphNodeAL<?> currNode;
        do
        {
            currNode = unenc.remove(0);
            enc.add(currNode);
            if (currNode.data.equals(lookingfor))
            {
                cp.pathList.add(currNode);
                cp.pathCost = currNode.cost; //Updates the cost
                while (currNode != startNode)
                {
                    boolean foundPrevPathNode = false;
                    for (GraphNodeAL<?> node : enc)//Loop through nodes and their links
                    {
                        for (GraphLinkAL link : node.adjList) //For each edge from that node...
                        {
                            if (link.dest == currNode && currNode.cost - link.cost == node.cost)
                            {

                                cp.pathList.add(0, node);
                                currNode = node;
                                foundPrevPathNode = true;
                                break;

                            }
                        }
                        if (foundPrevPathNode) // If encountered a previously found node
                        {
                            break;
                        }
                    }
                }

                for (GraphNodeAL<?> node : enc) node.cost = Integer.MAX_VALUE; //Resetting the nodes
                for (GraphNodeAL<?> node : unenc) node.cost = Integer.MAX_VALUE;
                return cp;
            }

            for (GraphLinkAL link : currNode.adjList)
            {
                if (!enc.contains(link.dest)) {
                    link.dest.cost = Integer.min(link.dest.cost, currNode.cost + link.cost);

                    unenc.add(link.dest);
                }
            }
            Collections.sort(unenc, (n1, n2) -> n1.cost - n2.cost);
        }
        while (!unenc.isEmpty());
        return null;
    }

    //TESTS
    public static <T> ExpensivestPath findMostExpensivePathDijkstra(GraphNodeAL<?> startNode, T lookingfor) {
        ExpensivestPath ep = new ExpensivestPath();
        ArrayList<GraphNodeAL<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        startNode.cost = 0;
        unencountered.add(startNode);
        GraphNodeAL<?> currentNode;
        do {
            currentNode = unencountered.remove(0);
            encountered.add(currentNode);
            if (currentNode.data.equals(lookingfor)) {
                ep.pathList.add(currentNode);
                ep.pathCost = currentNode.cost;
                while (currentNode != startNode) {
                    boolean foundPrevPathNode = false;
                    for (GraphNodeAL<?> node : encountered) {
                        for (GraphLinkAL link : node.adjList)
                            if (link.dest == currentNode && currentNode.cost - link.cost == node.cost)
                            {

                                ep.pathList.add(0, node);
                                currentNode = node;
                                foundPrevPathNode = true;
                                break;

                            }
                        if (foundPrevPathNode)
                            break;
                    }
                }

                for (GraphNodeAL<?> node : encountered) node.cost = Integer.MAX_VALUE;
                for (GraphNodeAL<?> node : unencountered) node.cost = Integer.MAX_VALUE;
                return ep; //The costed (cheapest) path has been assembled, so return it!
            }

            for (GraphLinkAL link : currentNode.adjList)
                if (!encountered.contains(link.dest)) {
                    link.dest.cost = Integer.min(link.dest.cost, currentNode.cost + link.cost);

                    unencountered.add(link.dest);
                }
            Collections.sort(unencountered, (n1, n2) -> n1.cost - n2.cost);
        } while (!unencountered.isEmpty());
        return null;
    }
}
