package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Controller
{
    ImageConversion ic = new ImageConversion();
    Image originalMap;
    Image bwMap;
    DepthFirstSearch depth = new DepthFirstSearch();
    BreadthFirstSearch bread = new BreadthFirstSearch();
    AdjacencyMatrix mat;
    @FXML ImageView image1 = new ImageView();
    @FXML ImageView image2 = new ImageView();
    @FXML Label bfsLength,bfsEnc;
    @FXML RadioButton rb1,rb2;
    @FXML SplitMenuButton menu = new SplitMenuButton();
    @FXML SplitMenuButton menu2 = new SplitMenuButton();
    @FXML TextField one,two,three,djCreate,djInfo,linkStart,linkDest,linkCost,routeStart,routeDes,routeWay;
    @FXML TextArea nodeLinks, existNodes, genRoute;

    ArrayList<GraphNode<?>> graphNodeArrayList = new ArrayList<>();
    ArrayList<GraphNode<?>> validNodes = new ArrayList<>();
    int search1,search2,search3;
    int lenghtOfU [];
    private boolean startPositionSelected = false;
    WritableImage writableImage2;

    //public ArrayList<GraphNodeAL<String>> nodes = new ArrayList<>();

    private int imageSize;
    private int imageHeight;
    private int imageWidth;

    public Dijkstras da = new Dijkstras();
    public DijkstrasController dc = new DijkstrasController();

    public void importImage()
    {

        FileChooser fc = new FileChooser();
        fc.setTitle("Select an Image");

        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png");
        fc.getExtensionFilters().add(ef);

        Stage st = new Stage();
        File file = fc.showOpenDialog(st);

        originalMap = new Image(file.toURI().toString());
        bwMap = new Image(file.toURI().toString());
        bwMap = ic.convertBlackWhite(bwMap); //convert to black and white
        image1.setImage(originalMap);
        image2.setImage(bwMap);
        imageSize= (int) (image2.getImage().getWidth()*image2.getImage().getHeight());
        imageWidth = (int) (image2.getImage().getWidth());
        imageHeight = (int) (image2.getImage().getHeight());
        writableImage2 = new WritableImage(image2.getImage().getPixelReader(),imageWidth,imageHeight);
        nodeCreator();
    }



    public ArrayList<GraphNode<?>> findPathBreadthFirst(GraphNode<?> startNode,GraphNode<?> avoid, GraphNode<?> lookingFor) {
        ArrayList<ArrayList<GraphNode<?>>> agenda = new ArrayList<>(); // Will be used to store path taken
        ArrayList<GraphNode<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        //System.out.println("agenda"+firstAgendaPath);
        resultPath = findPathBreadthFirstIteratively(agenda,avoid, null, lookingFor);

        Collections.reverse(resultPath);
        return resultPath;
    }



    public ArrayList<GraphNode<?>> findPathBreadthFirstIteratively(ArrayList<ArrayList<GraphNode<?>>> agenda,GraphNode<?> avoid, ArrayList<GraphNode<?>> encountered, GraphNode<?> lookingFor)
    {
        double red = 1.0;
        double green = 0;
        double blue = 0;
        double alpha = 1;
        Color c;
        ArrayList<GraphNode<?>> nextPath = new ArrayList<>();
        if (encountered == null) encountered = new ArrayList<>();
        while (!agenda.isEmpty() && !encountered.contains(lookingFor)) {
            nextPath = agenda.remove(0); //first item in the agenda list is used for processing
            GraphNode<?> currentNode = nextPath.get(0); // the first item on this list is removed so that it wont be processed again
            encountered.add(currentNode);// adds the current node to encountered as it is going to be processed
            for (int i = 0; i < 4; i++) {
                if (currentNode.mat.amat[currentNode.nodeId][i] && currentNode.adjacent[i] != null && currentNode!=avoid && !encountered.contains(currentNode.adjacent[i]) && !agenda.contains(encountered)) {
                    if (blue>0.9)
                    {
                        blue = 0;
                    }
                    else
                    {

                        blue += 0.01;
                    }
                    c = new Color(red,green,blue,alpha);
                    int s = (int)currentNode.adjacent[i].data;
                    int x = s%imageWidth;
                    int y = s/imageWidth;
                    writableImage2.getPixelWriter().setColor(x,y,c);
                    image2.setImage(writableImage2);
                    ArrayList<GraphNode<?>> newPath = new ArrayList<>(nextPath); //creates a new possible path
                    newPath.add(0, currentNode.adjacent[i]);
                    agenda.add(newPath);
                    encountered.add(currentNode.adjacent[i]); //stops the same node being processed twice in the iterating
                    if (newPath.contains(lookingFor))
                    {
                        bfsLength.setText(""+newPath.size());
                        bfsEnc.setText(""+encountered.size());
                        return newPath;
                    }
                }
            }
        }
        System.out.println("Returns here");
        return null;
    }




    //this is where it creates the nodes
    public void nodeCreator()
    {
        int[] imgProc= new int[imageSize];
        int s= 0;
        mat = new AdjacencyMatrix(imageSize);


        //makes a node for every white pixel
        for (int y= 0;y<imageHeight;y++)
        {
            for (int x = 0; x<imageWidth;x++)
            {
                if (image2.getImage().getPixelReader().getColor(x,y).equals(Color.WHITE))
                {
                    imgProc[s]=1;
                    GraphNode<Integer> plus = new GraphNode(s,mat);

                    validNodes.add(plus);
                    graphNodeArrayList.add(plus);
                    graphNodeArrayList.get(s).nodeId = validNodes.size()-1;
                    validNodes.get(validNodes.size()-1).nodeId= validNodes.size()-1;


                    //System.out.println(validNodes.get(validNodes.size()-1));
                }else graphNodeArrayList.add(new GraphNode(-1,mat)); //check the node behind the currentnone and the one above, link both ways
                if (x>0&&(int)graphNodeArrayList.get(s).data>0&&image2.getImage().getPixelReader().getColor(x-1,y).equals(Color.WHITE))
                {
                    validNodes.get(graphNodeArrayList.get(s).nodeId).connectToNodeUndirected(validNodes.get(graphNodeArrayList.get(s-1).nodeId),0);
                    //System.out.println(validNodes.get(graphNodeArrayList.get(s).nodeId)+" Connected to "+validNodes.get(graphNodeArrayList.get(s-1).nodeId));
                }
                if (y>0&&(int)graphNodeArrayList.get(s).data>0&&image2.getImage().getPixelReader().getColor(x,y-1).equals(Color.WHITE))
                {
                    validNodes.get(graphNodeArrayList.get(s).nodeId).connectToNodeUndirected(validNodes.get(graphNodeArrayList.get(s-imageWidth).nodeId),2);
                    //System.out.println(validNodes.get(graphNodeArrayList.get(s).nodeId)+" Connected to "+validNodes.get(graphNodeArrayList.get(s-imageWidth).nodeId));
                }

                s++;
            }


        }

    }

    public void drawSingleShortestRoute(ArrayList<GraphNode<?>> path)
    {
        WritableImage writableImage = new WritableImage(image1.getImage().getPixelReader(),imageWidth,imageHeight);

        for (int i = 0;i  < path.size();i++)
        {
            int s =(int)path.get(i).data;
            int x = s%imageWidth;
            int y = s/imageWidth;
            writableImage.getPixelWriter().setColor(x,y,Color.MAGENTA);
            //System.out.println(path.size());
            /*for (int y = 0 ; y <imageHeight;y++)
            {
                for (int x = 0; x < imageWidth;x++)
                {
                    if((int) path.get(i).data == s )
                    {
                       // System.out.println(s);
                        writableImage.getPixelWriter().setColor(x,y,Color.MAGENTA);

                    }


                   // System.out.println(s);
                    s++;
                }
             */
        }

        image1.setImage(writableImage);
    }

    public void drawMultipleRoute(ArrayList<ArrayList<GraphNode<?>>> path)
    {
        WritableImage writableImage = new WritableImage(image1.getImage().getPixelReader(),imageWidth,imageHeight);

        for (int i = 0;i  < path.size();i++) {
            for (int p = 0; p < path.get(i).size(); p++) {
                int s = 0;
                //System.out.println(path.size());
                for (int y = 0; y < imageHeight; y++) {
                    for (int x = 0; x < imageWidth; x++) {
                        if ((int) path.get(i).get(p).data == s) {
                            // System.out.println(s);
                            writableImage.getPixelWriter().setColor(x, y, Color.hsb(10*i,1,1,1));

                        }


                        // System.out.println(s);
                        s++;
                    }
                }
            }
        }
        image1.setImage(writableImage);
    }

    public int calcPos(int x, int y, int width)
    {
        return x+(width*y); //how far we are into the current row multiplied by the rows before!
    }

    public void searchForNodes()
    {

        int firstSearch = Integer.parseInt(one.getText());
        int secondSearch = Integer.parseInt(two.getText());
        int avoidSearch = Integer.parseInt(three.getText());
        for (int s = 0;s<validNodes.size();s++)
        {
            if ((int)validNodes.get(s).data==firstSearch) {
                System.out.println("Found1");
                search1 = validNodes.get(s).nodeId;
            }
            if ((int)validNodes.get(s).data==secondSearch){
                System.out.println("Found2");
                search2 = validNodes.get(s).nodeId;
            }
            if ((int)validNodes.get(s).data==avoidSearch)
            {
                search3 = validNodes.get(s).nodeId;
            }

        }


    }




    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void navigateMap(ActionEvent actionEvent) throws Exception {
        if (rb1.isSelected())
        {
            ArrayList<GraphNode<?>> path = new ArrayList<>();
            System.out.println(graphNodeArrayList.size());
            path = findPathBreadthFirst(validNodes.get(search1),validNodes.get(search3),validNodes.get(search2));


            System.out.println(path.size());
            drawSingleShortestRoute(path);

        }
        if(rb2.isSelected())
        {


            dc.load();

            //dc.save();
        }
    }

    public void save() throws Exception
    {
        dc.save();
    }

    public void load() throws Exception
    {
        dc.load();
    }


    public void mouseCoords(MouseEvent event) {
        boolean oneOrTwo = false;
        int pos1;
        int pos2;
        image1.setOnMouseClicked(e ->
        {

            //get the pixel reader
            //PixelReader pr = bwMap.getPixelReader();

            System.out.println(e.getX() + " " +  e.getY());

            //gets the ratio of the original image to the imageView, we can use this to calculate the actual click pos!
            double ratioX = image1.getImage().getWidth()/image1.getFitWidth();
            double ratioY = image1.getImage().getHeight()/image1.getFitHeight();

            System.out.println(ratioX + " " + ratioY);
            //the actual image coords we clicked
            double actX = e.getX()*ratioX;
            double actY = e.getY()*ratioY;

            System.out.println(actX + " " + actY);
            System.out.println(getMouseCoords((int)actX,(int)actY));

            int s = getMouseCoords((int)actX,(int)actY);
            if (image2.getImage().getPixelReader().getColor((int)actX,(int)actY).equals(Color.WHITE)) {
                if (!startPositionSelected) {
                    one.setText("" + s);
                    startPositionSelected = true;
                } else {
                    two.setText("" + s);
                    startPositionSelected = false;
                }
            }
        });

    }

    public int getMouseCoords(int x, int y) {

        return x+(imageWidth*y); //how far we are into the current row multiplied by the rows before!

    }

    public void getMouseCoords(MouseEvent event) {

    }

    public void createNode()
    {
        String existnodeList = "";
        String nodeName = djCreate.getText();
        dc.createNode(nodeName);
        for(int i = 0; i < dc.nodes.size();i++)
        {

            existnodeList += dc.nodes.get(i)+"\n";
            existNodes.setText(existnodeList);
        }
    }
    public void removeNode()
    {
        dc.removeNode(djCreate.getText());
    }

    public void nodeLinks()
    {
        String adjacent= "";
        GraphNodeAL temp = new GraphNodeAL("null");
        String nodeName = djInfo.getText();
        for (int i = 0 ; i<dc.nodes.size();i++)
        {
            if (dc.nodes.get(i).data.equalsIgnoreCase(nodeName))  temp = dc.nodes.get(i);

        }

        for (int i = 0; i<temp.adjList.size();i++)
        {
            adjacent += temp.adjList.get(i)+"\n";
            nodeLinks.setText(adjacent);
        }
    }

    public void linkNodes()
    {
        dc.linkNodeUndirByName(linkStart.getText(),linkDest.getText(),Integer.parseInt(linkCost.getText()));
    }
    public void unlinkNodes()
    {
        dc.unlinkNodes(linkStart.getText(), linkDest.getText());
    }

    public void geneateDjRoute(ActionEvent actionEvent)
    {
        if (routeWay.getText()=="")
        {
            genRoute.setText(dc.cheapestPathByName(routeStart.getText(),routeDes.getText()));
        }else
            {

                genRoute.setText(dc.cheapestPathWaypoint(routeStart.getText(),routeWay.getText(),routeDes.getText()));
            }
    }
}
