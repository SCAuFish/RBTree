/**
 * This file contains 2 classes, Red-black tree and Node calss.
 * @author Cheng Shen
 * @version 1.0
 */

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * This class defines the black-red balanced tree
 * It implements from BinSearchTree12 interface and contains an inner class
 * that defines the Node
 */
public class BST12RB<E extends Comparable<? super E>> 
    implements BinSearchTree12<E>{
    //Instance varialbe(the root)
    private Node root;
    private int size;

    /**
     * no-arg Constructor
     */
    public BST12RB(){
        this.root = null;
        this.size = 0;
    }

    /*--------------------------Overriding methods--------------------------*/
    /**
     * adding a new element to the tree
     * @param e the element to be added
     * @return whether the adding is successful(w/o repetitive element)
     */
    public boolean add(E e) throws NullPointerException, ClassCastException{
        //Check exceptional situation
        if(e == null){
            throw new NullPointerException();
        }

        //Create a Node to add
        Node toAdd = new Node(e);
        //If the class is still empty
        if(this.isEmpty()){
            this.root = toAdd;
            toAdd.parent = null;
            this.size++;
            return true;
        }
        //If not, find the place to add the toAdd node
        Node parent = searchParent(e);
        //When the elment already exists
        if(parent == null){
            return false;
        }

        if(e.compareTo((E)parent.element) < 0){
            //Add to the left
            parent.left = toAdd;
            toAdd.parent = parent;
        }else{
            //Add to the right
            parent.right = toAdd;
            toAdd.parent = parent;
        }
        //Balance the effects brought by the newly added Node
        balanceRB(toAdd);

        size++;
        return true;
    }

    /**
     * addAll elements from a given Collection to the balanced tree
     * @param c collection with elements
     * @return true if the set is changed based on this call
     */
    public boolean addAll(Collection<? extends E> c) 
            throws NullPointerException, ClassCastException{
            if(c == null){
                throw new NullPointerException();
            }

            boolean result = true;
            for(E each: c){
                result &= this.add(each);
            }
            return result;
    }

    /**
     * clear the current tree
     */
    public void clear(){
        this.root = null;
    }

    /**
     * check if a particular element is contained in the tre
     * @param e the element to be searched
     * @return true if the passed-in element exists
     */
    public boolean contains(E o) throws NullPointerException, ClassCastException{
        if(o == null){
            throw new NullPointerException();
        }

        //Check root
        if(root != null && root.element.compareTo((E)o) == 0){
            return true;
        }
        //Make use of helper method
        Node parent = searchParent(o);
        if(parent == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * return the lowest element in the tree
     * @return lowest element
     */
    public E first() throws NoSuchElementException{
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }

        Node current = root;
        while(current.left != null){
            current = current.left;
        }
        return current.element;
    }

    /**
     * Tests if the tree is empty
     * @return true if the tree is empty
     */
    public boolean isEmpty(){
        if(this.root == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * gives the last element in the tree
     * @return the last element
     */
    public E last() throws NoSuchElementException{
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }

        Node current = root;
        while(current.right != null){
            current = current.right;
        }
        return current.element;
    }

    /**
     * @return the number of elements in the tree
     */
    public int size(){
        return this.size();
    }

    /**
     * @return an iterator that traverses the tree
     */
    public Iterator<E> iterator(){
        //to do
        return null;
    }

    /**
     * remove the given element if it exists
     * @param e the element to be removed
     * @return true if it exists and is removed
     */
    public boolean remove(E e) throws NullPointerException, ClassCastException{
        //todo
        return false;
    }

    /**
     * height: 0(empty); 1(one element)
     * @return the height of current tree
     */
    public int height(){
        return height(root);
    }

    /**
     * @return number of Children under current Node
     */
    public int numChildren(E e) 
            throws IllegalArgumentException, NoSuchElementException{
            Node parent;
            try{
                parent = searchParent(e);
            }catch(Exception ex){
                throw new IllegalArgumentException();
            }

            Node toCheck;
            if(e.compareTo(parent.element) < 0){
                if(parent.left == null){
                    throw new NoSuchElementException();
                }else{
                    toCheck = parent.left;
                }
            }else{
                if(parent.right == null){
                    throw new NoSuchElementException();
                }else{
                    toCheck = parent.right;
                }
            }

            return numChildren(toCheck);
    }

    /*--------------------------Helper methods--------------------------*/
    /**
     * This method searches for the appropriate parent for the given element
     * Precondition: The tree should not be empty
     * @param e the element who needs a parent
     * @param the parent for the passed in element
     * (null if the element already exists)
     */
    private Node searchParent(E e) throws NullPointerException{
        if(this.isEmpty()){
            throw new NullPointerException();
        }

        Node curParent = null;
        Node current = root;
        while(current != null){
            curParent = current;
            if(e.compareTo(curParent.element) < 0){
                current = curParent.left;
            }else if(e.compareTo(curParent.element) > 0){
                current = curParent.right;
            }else{
                return null;
            }
        }

        return curParent;
    }

    /**
     * This is a critical method when adding some new Nodes to the tree
     * It balances the possible 2-red defect brought by the new Node
     * @param newNode the newly implemanted Node
     * (might exist before but property being changed)
     */
    private void balanceRB(Node newNode) throws IllegalStateException{
        if(newNode.color){
            throw new IllegalStateException();
        }
        //If the node itself is the root, simply change it to black
        if(root == newNode){
            newNode.color = true;
            return;
        }
        //If the parent Node is black, return
        if(newNode.parent.color){
            return;
        }

        //When both the newNode and its parent Node is red, 
        //it depends on the newNode's uncle Node
        Node curParent = newNode.parent;
        Node grandParent = curParent.parent;
        Node uncle = 
            curParent==grandParent.left ? grandParent.right : grandParent.left;
        //If the uncle is red, change both parent and uncle to black, 
        //change grandparent to red
        //and recursively call
        if(curParent.color == false && uncle.color == false){
            curParent.color = true;
            uncle.color = true;
            grandParent.color = false;
            balanceRB(grandParent);
            return;
        }
        //If the uncle is black or null
        if(curParent.color == false && (uncle.color == true || uncle == null)){
            grandParent.color = false;
            //Rotate, make parent become the new root of this partial tree
            if(grandParent.left == curParent){
                //To make sure 2-red defects do not appear after rotating, make
                //new red node, parent red node and grandparent on one line
                if(curParent.right == newNode){
                    rotateCClockWise(curParent);
                }
                rotateClockWise(grandParent);
            }else{
                if(curParent.left == newNode){
                    rotateClockWise(curParent);
                }
                rotateCClockWise(grandParent);
            }
        }
    }

    /**
     * This method rotates a part of the tree based on the pivot given clockwisely
     * Notice that this method does not promise the property of RB tree 
     * so it should only be called when you are sure
     * @param pivot the Node that will be readopted by its child
     */
    private void rotateClockWise(Node pivot) throws IllegalStateException{
        if(pivot.left == null){
            throw new IllegalStateException();
        }

        Node curParent = pivot.parent;
        if(curParent.left == pivot){
            //current parent adopt current left child
            curParent.left = pivot.left;
            curParent.left.parent = curParent;
            //pivot adopt the right child of its previous left child
            pivot.left = curParent.left.right;
            if(pivot.left != null) pivot.left.parent = pivot;
            //previous left child adopt pivot
            curParent.left.right = pivot;
            pivot.parent = curParent.left;
        }else{
            //current parent adopt current left child
            curParent.right = pivot.left;
            curParent.right.parent = curParent;
            //pivot adopt the right child of its previous left child
            pivot.left = curParent.right.right;
            if(pivot.left != null) pivot.left.parent = pivot;
            //previous left child adopt pivot
            curParent.right.right = pivot;
            pivot.parent = curParent.right;
        }
    }

    /**
     * This method rotates a part of the tree based on the pivot 
     * given counter clockwisely
     * Notice that this method does not promise the property of RB tree 
     * so it should only be called when you are sure
     * @param pivot the Node that will be readopted by its child
     */
    private void rotateCClockWise(Node pivot) throws IllegalStateException{
        if(pivot.right == null){
            throw new IllegalStateException();
        }

        Node curParent = pivot.parent;
        if(curParent.left == pivot){
            //current parent adopt current right child
            curParent.left = pivot.right;
            curParent.left.parent = curParent;
            //pivot adopt the left child of its previous right child
            pivot.right = curParent.left.left;
            if(pivot.right != null) pivot.right.parent = pivot;
            //previous left child adopt pivot
            curParent.left.left = pivot;
            pivot.parent = curParent.left;
        }else{
            //current parent adopt current right child
            curParent.right = pivot.right;
            curParent.right.parent = curParent;
            //pivot adopt the left child of its previous right child
            pivot.right = curParent.right.left;
            if(pivot.right != null) pivot.right.parent = pivot;
            //previous left child adopt pivot
            curParent.right.left = pivot;
            pivot.parent = curParent.right;
        }
    }

    /**
     * find the height of a given node
     * @return the height of the given node
     */
    private int height(Node n){
        if(n == null){
            return 0;
        }else{
            return 1+Math.max(height(n.left), height(n.right));
        }
    }

    /**
     * find the number of children under current given Node
     * @return the number of children
     */
    private int numChildren(Node n){
        if(n == null){
            return 0;
        }else{
            int directChild = 0;
            if(n.left != null) directChild++;
            if(n.right != null) directChild++;
            return numChildren(n.left)+numChildren(n.right)+directChild;
        }
    }
    /**
     * This class defines the elements that constitute of a BST12RBTree
     */
    private class Node{
        //Instance variables are of default scope 
        //so in BST12RB they can be direcly accessed
        Node parent;
        Node left;
        Node right;
        boolean color;
        E element;

        /**
         * default constructor which will initialize the element and make it red
         * @param e the element to be assigned
         */
        public Node(E e){
            //default setting for new Node
            this.parent = null;
            this.left = null;
            this.right = null;
            //Color is initialized to be red
            this.color = false;
            this.element = e;
        }

        //There are no getters and setters 
        //because the class is already made private and the
        //instance variables are visible for the BST12RB class
    }
}
