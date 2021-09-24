package sample;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch
{

    public <T> ArrayList<GraphNode<?>> findPath(ArrayList<GraphNode<?>> agenda,GraphNode<?> start, T lookingFor, ArrayList<GraphNode<?>> encountered,ArrayList<GraphNode<?>> path)
    {
        if (agenda == null) {
            agenda = new ArrayList<>();
            agenda.add(start);
        }

        if (path == null) path= new ArrayList<>();
        if (path.contains(lookingFor)) return path;
        if (encountered==null) encountered= new ArrayList<>();
        encountered.add(start);
        if (start==null || encountered.contains(start))
        {
            start=agenda.remove(0);
        }

        for (int i = 0; i < 4 ; i++)
        {
            if (start.mat.amat[start.nodeId][i] && start.adjacent[i]!=null && !encountered.contains(start.adjacent[i]))
            {
                path.add(start);
                encountered.add(start.adjacent[i]);
                agenda.add(0,start.adjacent[i]);
            }


        }

        return findPath(agenda,start,lookingFor,encountered,path);
    }

    public <T> ArrayList<GraphNode<?>> singlePath(ArrayList<GraphNode<?>> path,GraphNode<?> startNode, ArrayList<GraphNode<?>> encountered , T lookingFor)
    {
        if (path==null) path = new ArrayList<>();
        if (path.contains(lookingFor)) return path;

       if (encountered == null) encountered = new ArrayList<>();
        encountered.add(startNode);

        for (int i = 0; i < 4; i++) {

            if (!encountered.contains(startNode.adjacent[i]))

                    encountered.add(startNode.adjacent[i]);
                    path.add(startNode.adjacent[i]);
                    startNode = startNode.adjacent[i];


            }

        return singlePath(path,startNode,encountered,lookingFor);
    }

}