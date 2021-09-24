package sample;

import javafx.scene.paint.Color;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class BreadthFirstSearch {

    //this is the initialisation, list of list of graphnodes, and make sa first one and does some magic
    public ArrayList<GraphNode<?>> findPathBreadthFirst(GraphNode<?> startNode, GraphNode<?> lookingFor) {
        ArrayList<ArrayList<GraphNode<?>>> agenda = new ArrayList<>();
        ArrayList<GraphNode<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        //System.out.println("agenda"+firstAgendaPath);
        resultPath = findPathBreadthFirstIteratively(agenda, null, lookingFor);

        Collections.reverse(resultPath);
        return resultPath;
    }
    //originally recursive, but not iterative, cycles through agenda and adds things until its full
    public ArrayList<GraphNode<?>> findPathBreadthFirstIteratively(ArrayList<ArrayList<GraphNode<?>>> agenda, ArrayList<GraphNode<?>> encountered, GraphNode<?> lookingFor)
    {

        ArrayList<GraphNode<?>> currentPath = new ArrayList<>();
        if (encountered == null) encountered = new ArrayList<>();
        while (!agenda.isEmpty() && !encountered.contains(lookingFor)) {
            currentPath = agenda.remove(0);
            GraphNode<?> currentNode = currentPath.get(0);
            encountered.add(currentNode);
            for (int i = 0; i < 4; i++) {
                if (currentNode.mat.amat[currentNode.nodeId][i] && currentNode.adjacent[i] != null && !encountered.contains(currentNode.adjacent[i]) && !agenda.contains(encountered)) {
                    ArrayList<GraphNode<?>> possPath = new ArrayList<>(currentPath);
                    possPath.add(0, currentNode.adjacent[i]);
                    agenda.add(possPath);
                    encountered.add(currentNode.adjacent[i]);
                    if (possPath.contains(lookingFor)) return possPath;
                }
            }
        }
        System.out.println("Returns here");
        return null;
    }

    public <T> ArrayList<ArrayList<GraphNode<?>>> findMultiplePaths(GraphNode<?> startNode, T lookingFor) {
        ArrayList<ArrayList<GraphNode<?>>> agenda = new ArrayList<>();
        ArrayList<ArrayList<GraphNode<?>>> encountered = new ArrayList<>();
        ArrayList<GraphNode<?>> firstAgendaPath = new ArrayList<>() , resultpath;
        ArrayList<ArrayList<GraphNode<?>>> paths = new ArrayList<>();
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        while (paths.size()!=15)
        {
            resultpath = new ArrayList<>();
            resultpath = findPathBreadthMultiIteratively(agenda, null,encountered, lookingFor);
            paths.add(resultpath);
            System.out.println(resultpath);
            encountered.add(resultpath);
        }
        System.out.println("Done");
        Collections.reverse(paths);
        return paths;
    }

    public <T> ArrayList<GraphNode<?>> findPathBreadthMultiIteratively(ArrayList<ArrayList<GraphNode<?>>> agenda, ArrayList<GraphNode<?>> encountered,ArrayList<ArrayList<GraphNode<?>>> encounteredPaths, T lookingFor)
    {
        ArrayList<GraphNode<?>> nextPath = new ArrayList<>();
        if (encountered == null) encountered = new ArrayList<>();
        while (!agenda.isEmpty() && !encountered.contains(lookingFor)) {
            nextPath = agenda.remove(0);
            GraphNode<?> currentNode = nextPath.get(0);
            encountered.add(currentNode);
            for (int i = 0; i < 4; i++) {
                if (currentNode.mat.amat[currentNode.nodeId][i] && currentNode.adjacent[i] != null && !encountered.contains(currentNode.adjacent[i]) && !agenda.contains(encountered)) {
                    ArrayList<GraphNode<?>> newPath = new ArrayList<>(nextPath);
                    newPath.add(0, currentNode.adjacent[i]);
                    agenda.add(newPath);
                    encountered.add(currentNode.adjacent[i]);
                    if (newPath.contains(lookingFor) && !encounteredPaths.contains(newPath))
                    {
                        return newPath;
                    }
                }
            }
        }
        System.out.println("Returns here");
        return null;
    }









}
