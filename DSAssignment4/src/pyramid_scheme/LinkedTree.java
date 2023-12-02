package pyramid_scheme;

import ADTs.TreeADT;
import DataStructures.MultiTreeNode;
import Exceptions.ElementNotFoundException;
import java.util.ArrayList;

/**
 * Generic LinkedTree class for ITSC 2214.
 * 
 * @author nblong
 * @param <T>
 * @version Oct. 2021
 * 
 * Adapted from Perrin Mele's excellent work in the spring semester of 2019
 */
public class LinkedTree<T> implements TreeADT<T> {

    /**
     * The root of the tree is a generic MultiTreeNode.
     */
    protected MultiTreeNode<T> root;

    /**
     * Constructor with element provided.
     *
     * @param startElem - The starting element of this tree.
     */
    public LinkedTree(T startElem) {
        root = new MultiTreeNode<>(startElem);
    }

    /**
     * Constructor with node provided.
     *
     * @param startNode - The starting node of this tree.
     */
    public LinkedTree(MultiTreeNode<T> startNode) {
        root = startNode;
    }

    /**
     * Returns the element stored at the root.
     *
     * @return root element
     */
    @Override
    public T getRootElement() {    
        return root.getElement();  
    }

    /**
     * Step 1 of a two-step process to add a node containing the child element 
     * to an existing node that contains the parent element. This particular
     * overload of the addChild method is responsible for locating the parent
     * node then calling the other overload to actually add the child node.
     *
     * @param parent An element contained by a particular node in the tree,
     * which will ultimately be given a child node
     * @param child The element to be packaged into a node and added to the tree
     * @throws ElementNotFoundException (when the parent doesnt exist
     */
    public void addChild(T parent, T child) throws ElementNotFoundException {
 
        // start at the root  
        MultiTreeNode<T> start = root; 
        
        // search for the parent
        MultiTreeNode<T>parentNodeWeAreLookingfor = this.findNode(parent); 
        
        
        // if the parent we found doesnt exist throw exception
        if (parentNodeWeAreLookingfor == null){throw new ElementNotFoundException("element isnt found");}
        
        // else add the child to the parent 
        else{this.addChild(parentNodeWeAreLookingfor, child);} 
        
        
    }

    /**
     * Step 2 of a two-step process to add a node containing the child element 
     * to an existing node that contains the parent element. This particular
     * overload of the addChild method is responsible for creating a node for
     * the child and adding it to the children of the parent node.  Note the
     * addChild method exists in multiple classes.
     *
     * @param parentNode the node receiving a child node
     * @param child the element to be packaged and added as a child node
     */
    protected void addChild(MultiTreeNode<T> parentNode, T child) {
        MultiTreeNode<T> childNodeToAdd = new MultiTreeNode(child);// create a new node for the child
        parentNode.addChild(childNodeToAdd);// add the node to the children of the parent node
    }

    /**
     * Finds the node that contains the target element by calling the recursive
     * overload to search for the target starting at the root of the tree.
     *
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target or null if not found
     */
    public MultiTreeNode<T> findNode(T target) {
        return findNode(root, target);
    }

    /**
     * Finds the node that contains a target element.  Checks the current node
     * and if it contains the target, returns it.  Otherwise, recursively
     * check each of the current node's children, to see if they can find it.
     * If none of this node's children can find it then null is returned.
     *
     * @param node the node currently being examined
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target, or null if not found
     */
    private MultiTreeNode<T> findNode(MultiTreeNode<T> node, T target) {
        // If this node is the one containing the target...
        if (node.getElement().equals(target)) {
            return node; // Return this node
        } else { //Otherwise...
            MultiTreeNode<T> temp;
            // For each child of this node...
            for (MultiTreeNode<T> child : node.getChildren()) {
                // Call this method to see if the target is in a child node
                temp = findNode(child, target);
                // If findNode doesn't return null then the target was found
                if (temp != null) {
                    return temp; // Return node where target was found
                }
            }
            // If none of the children contained the target return null
            return null; //The target wasn't found
        }
    }

    /**
     * Tries to find a node that contains the target element and returns true
     * if so, false otherwise
     *
     * @param target the element being searched for.
     * @return a boolean representing whether or not the tree has a node
     * containing the target element.
     */
    @Override
    public boolean contains(T target) {
        MultiTreeNode<T> nodeTryingToBeFound = this.findNode(target); // search for the node from the root 
        if (nodeTryingToBeFound != null) {return true;} // if it doesnt return null, return true
        else {return false;}
    }

    /**
     * Returns the size of the tree measured by the number of nodes in it.  
     * Calls the recursive countDown method to determine this.
     *
     * @return the size of the tree.
     */
    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return countDown(root);
    }

    /**
     * Recursively determines the number of nodes in the tree.  Starts with 
     * the current node then recursively calls itself for each of that node's
     * children and so on.  When originally called on the root node it will
     * return the number of nodes in the entire tree.  Used by size() to 
     * determine how many nodes are in the tree but could be used starting at
     * any node to determine how many nodes there are in that subtree.
     *
     * @param node the node currently being examined.
     * @return the amount of nodes from the starting node down.
     */
    private int countDown(MultiTreeNode<T> node) {
        
        // if the node doesnt exist return 0
        if (node == null){return 0;} 
        
        //if the node exists but has no children, return 1
        if (node.getNumChildren() == 0 && node != null){return 1;} 
        
       
        
        // when children do exist, create a counter value 
        int counter = 1;
        
        //for every node in the childen,
        for (int i = 0; i < node.getNumChildren() ; i++){
            
            // call countDown on it to count their children 
            counter += countDown(node.getChild(i)); 
        }
        return counter;     
        
    }

    /**
     * String representation of a LinkedTree.
     * 
     * @return a series of Strings illustrating the elements in each level of
     * the tree.
     */
    @Override
    public String toString() {
        String print = "Linked Tree: \nLayer 1 (Root): " + root.getElement();
        ArrayList<MultiTreeNode<T>> layer = root.getChildren();
        ArrayList<MultiTreeNode<T>> nextLayer;
        int layerNum = 2;
        while (!layer.isEmpty()) {
            print += "\nLayer " + layerNum + ":";
            nextLayer = new ArrayList<>();
            for (MultiTreeNode<T> node : layer) {
                nextLayer.addAll(node.getChildren());
                print += " " + node;
            }
            layer = nextLayer;
            layerNum++;
        }
        return print;
    }
}
