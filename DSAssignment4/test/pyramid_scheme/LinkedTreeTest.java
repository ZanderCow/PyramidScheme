package pyramid_scheme;

import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Solution Test file for LinkedTree
 *
 * @author pmele
 * @version 3/28/2019
 */
public class LinkedTreeTest {

    private LinkedTree<String> instance;
    private MultiTreeNode<String> root;
    private String s01;
    private String s02;
    private String s03;
    private String s04;
    private String s05;

    /**
     * Sets up the later tests.
     */
    @Before
    public void setUp() {
        s01 = "Elem 1";
        s02 = "Elem 2";
        s03 = "Elem 3";
        s04 = "Elem 4";
        s05 = "Elem 5";
        root = new MultiTreeNode<>(s01);
        instance = new LinkedTree<>(root);
    }

    /**
     * Test of getRootElement method, of class LinkedTree.
     */
    @Test
    public void testGetRootElement() {
        
        //returns null when root element is null
        root = new MultiTreeNode<>(null);
        instance = new LinkedTree<>(root);
        assertEquals(instance.getRootElement(), null);
        
        //returns the correct value when the root has a value
        root = new MultiTreeNode<>("7");
        instance = new LinkedTree<>(root);
        assertTrue(instance.getRootElement().equals("7"));
        
    }

    /**
     * Test of addChild method, of class LinkedTree.
     */
    @Test
    public void testAddChild() {
        
                  //0
                   
           //1     //2     //3
           
        root = new MultiTreeNode<>("0");
        instance = new LinkedTree<>(root);         
        instance.addChild(root, "1");               
        instance.addChild(root, "2");
        instance.addChild(root, "3");
        
        
        //case 1: doesnt doesnt exist. Should throw a elementnotfound exception
        try{ instance.addChild("4", "5"); } 
        catch (Exception e){ assertTrue(e instanceof ElementNotFoundException);}
        
        
        //case 2: element does exist
        
        // adds 4 to the desired parent 1 without failing
        try{ instance.addChild("1", "4"); } 
        catch (Exception e){ fail("This shouldnt happen");}

        // 4 is a child of 1
        MultiTreeNode<String> parent = instance.findNode("1");
        assertTrue(parent.getChild(0).getElement().equals("4"));
        
                    
    }

    /**
     * Test of findNode method, of class LinkedTree.
     */
    @Test
    public void testFindNode() {
        System.out.println("testFindNode");
        try {
            //Can find root
            assertEquals(s01, instance.findNode(s01).getElement());
            instance.addChild(s01, s02);
            //System.out.println("Successfully added " + s02 + " to " + s01);
            //Can find a child node
            assertEquals(s02, instance.findNode(s02).getElement());
            instance.addChild(s01, s03); //Add several deep
            instance.addChild(s02, s04);
            instance.addChild(s04, s05);
            //Can find a child node deep within tree
            assertEquals(s05, instance.findNode(s05).getElement());
            //Trying to find things not in the tree returns null
            assertEquals(null, instance.findNode("Unreal element"));
        } catch (Exception ex) {
            fail("Unexpected Exception");
        }
    }

    /**
     * Test of contains method, of class LinkedTree.
     */
    @Test
    public void testContains() {
                  //0
                   
           //1     //2     //3
           
        root = new MultiTreeNode<>("0");
        instance = new LinkedTree<>(root);         
        instance.addChild(root, "1");               
        instance.addChild(root, "2");
        instance.addChild(root, "3");
        
        // case 1: node doesnt exist should return false
        assertFalse(instance.contains("4"));
         
        //case 2: node does contain the root. Should return true
        assertTrue(instance.contains("2"));
 
    }

    /**
     * Test of size method, of class LinkedTree.
     */
    @Test
    public void testSize() {
        // case 1: root is null, should return 0 
        root = null;
        instance = new LinkedTree<>(root);
        assertEquals(instance.size(), 0);
        
        //case 2: tree is 
        //        0
        //
        //   1    2     3
        // it should return 3  
        
        root = new MultiTreeNode<>("0");
        instance = new LinkedTree<>(root);    
        try{ 
            instance.addChild("0", "1");               
            instance.addChild("0", "2");
            instance.addChild("0", "3");
        } 
        catch (Exception e){ fail("This shouldnt happen");}
        
        System.out.print(instance.size());
        
        assertEquals(instance.size(), 4);
    }

}
