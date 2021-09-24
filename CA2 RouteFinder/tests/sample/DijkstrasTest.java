package sample;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DijkstrasTest
{
    public DijkstrasController dc;

    @BeforeEach
    void setUp()
    {
        dc = new DijkstrasController();
        assertEquals(0, dc.getNodes().size()); //initial state

        //add 10 nodes
        dc.createNode("Dublin");
        dc.createNode("Waterford");
        dc.createNode("Cork");
        dc.createNode("Limerick");

        assertEquals(4, dc.getNodes().size()); //check if the 4 have been added fine

        dc.createNode("Sligo");
        dc.createNode("Galway");
        dc.createNode("Drogheda");
        dc.createNode("Belfast");
        dc.createNode("Letterkenny");
        dc.createNode("Athlone");

        assertEquals(10, dc.getNodes().size()); //check if all 10 have been added fine


    }

    @AfterEach
    void tearDown()
    {
        dc = null;
    }

    @Test
    void addNodes()
    {
        //now check if their in the right place
        assertEquals("Sligo", dc.getNodes().get(4).getData());
        assertEquals("Drogheda", dc.getNodes().get(6).getData());
    }

    @Test
    void linkNodes()
    {
        //test initially
        assertEquals(0, dc.getNodeLinkCountByName("Dublin"));

        //connect some nodes undirectional
        dc.linkNodeUndirByName("Dublin", "Cork", 220);
        dc.linkNodeUndirByName("Limerick", "Cork", 85);
        dc.linkNodeUndirByName("Dublin", "Waterford", 135);

        assertEquals("Cork, Waterford, ", dc.getNodeLinksByName("Dublin"));
        assertEquals(2, dc.getNodeLinkCountByName("Dublin"));
        assertEquals(1, dc.getNodeLinkCountByName("Limerick"));

        dc.linkNodeUndirByName("Dublin", "Galway", 185);
        assertEquals(135, dc.getCheapestLinkCost("Dublin"));
    }
}