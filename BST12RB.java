/**
 * This file contains 2 classes, Red-black tree and Node calss.
 * @author Cheng Shen
 * @version 1.0
 */

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.TreeSet;
import java.util.HashSet;

import org.junit.*;
import static org.junit.Assert.*;

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

    /**
     * Constructor that copies a whole collection
     * @param c the collection with elements to be copied
     */
    public BST12RB(Collection<? extends E> c){
        this.root = null;
        this.size = 0;
        this.addAll(c);
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
            toAdd.color = true;
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
        this.size = 0;
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
        return this.size;
    }

    /**
     * @return an iterator that traverses the tree
     */
    public Iterator<E> iterator(){
        return new RBIterator(this);
    }

    /**
     * remove the given element if it exists
     * @param e the element to be removed
     * @return true if it exists and is removed
     */
    public boolean remove(E e) throws NullPointerException, ClassCastException{
        //System.out.println("Entering remove(E)");
        //Check edge case
        if(e == null){
            throw new NullPointerException();
        }
        if(root == null){
            throw new NullPointerException();
        }

        if(root.element.compareTo(e) == 0){
            //When the element is stored in the root
            //System.out.println("Calling remove root");
            remove(this.root);
            this.size--;
            return true;
        }

        //Find the parent of the node to be removed
        Node toRemove = search(e);
        if(toRemove == null){
            return false;
        }

        remove(toRemove);
        this.size--;
        return true;
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

    /**
     * @return String representation of the tree
     */
    public String toString(){
        Iterator iter = iterator();
        String result = "[";
        if(iter.hasNext()){
            result += iter.next().toString();
        }
        while(iter.hasNext()){
            result += ", ";
            result += iter.next();
        }
        result += "]";
        return result;
    }

    /**
     * Temporary main method for testing
     */
    public static void main(String[] args){
        BST12RB<Integer> tree = new BST12RB<Integer>();
        tree.add(5);
        tree.add(4);
        tree.add(2);
        tree.add(3);
        tree.add(6);
        tree.add(1);
        Random rnd = new Random();
        TreeSet<Integer> oracle = new TreeSet<Integer>();
        for (int n = 1; n <= 6; n++)
            oracle.add(n);

        for (int n = 0; n < 1000; n++) {
            System.out.print("tree: ");
            tree.printFamily(tree.root);
            int toAdd = rnd.nextInt(10);
            oracle.add(toAdd);
            tree.add(toAdd);
            System.out.print("add: "+toAdd);

            int toRemove = rnd.nextInt(10);
            oracle.remove(toRemove);
            tree.remove(toRemove);
            System.out.println("; remove: "+toRemove);

            if(!oracle.toString().equals(tree.toString())){
                System.out.println("set: "+oracle.toString());
                System.out.println("treeString: "+tree.toString());
                System.out.print("tree: ");
                tree.printFamily(tree.root);
                break;
            }
        }
        /*
        tree.root =  tree.new Node(4);
        tree.root.color = true;
        tree.root.left = tree.new Node(1);
        tree.root.left.color = true;
        tree.root.right = tree.new Node(8);
        tree.root.right.color = true;
        BST12RB.Node oneLeft = tree.root.left; 
        BST12RB.Node oneRight = tree.root.right;
        oneLeft.parent = tree.root;
        oneRight.parent = tree.root;
        oneLeft.left = tree.new Node(0);
        oneLeft.left.parent = oneLeft;
        oneLeft.right = tree.new Node(2);
        oneLeft.right.parent = oneRight;
        oneRight.left = tree.new Node(6);
        oneRight.left.parent = oneRight;

        System.out.println("After constructing");
        tree.printFamily(tree.root);
        tree.add(7);
        tree.printFamily(tree.root);
        tree.remove(7);
        tree.printFamily(tree.root);
        */
    }

    /*--------------------------Helper methods--------------------------*/
    /**
     * private helper method for remove
     * @param toRemove node to remove
     * @return true if the Node is removed
     */
    private boolean remove(Node toRemove) throws NullPointerException{
        if(toRemove == null){
            throw new NullPointerException();
        }


        //Start the long process
        //When the toRemove node is red
        if(!toRemove.color){
            //System.out.println("removing red Node");
            //When it's a red leaf to be removed
            //Red node can be leaf only when both children are null
            if(toRemove.left == null && toRemove.right == null){
                //Directly forget this Node
                Node curParent = toRemove.parent;
                if(curParent.left == toRemove){
                    curParent.left = null;
                }else{
                    curParent.right = null;
                }
                return true;
            }else{
                //When it's an inner red node
                Node successor = getNext(toRemove);
                //Replace the element in the Node that will be removed
                toRemove.element = successor.element;
                //Remove successor from its original position
                return remove(successor);
            }
        }
        //When toRemove is the root Node
        else if(toRemove == root){
            //System.out.println("removing root");
            //root Node must be black
            //If there's no Child
            if(toRemove.left == null && toRemove.right == null){
                this.root = null;
                return true;
            }
            //If there's only one Child
            if(toRemove.left == null && toRemove.right != null){
                this.root = toRemove.right;
                this.root.parent = null;
                balanceRB(this.root);
                return true;
            }
            if(toRemove.right == null && toRemove.left != null){
                this.root = toRemove.left;
                this.root.parent = null;
                balanceRB(this.root);
                return true;
            }
            //If there are two Children
            Node successor = getNext(root);
            root.element = successor.element;
            remove(successor);
        }
        //Black Node to be removed
        else{
            //System.out.println("removing black Node");
            //When at least one child is null(base case)
            if(toRemove.left == null || toRemove.right == null){
                Node curParent = toRemove.parent;
                //When there's a red child
                if(toRemove.left!=null || toRemove.right!=null){
                    if(toRemove.left != null){
                        toRemove.element = toRemove.left.element;
                        toRemove.left = null;
                    }else{
                        toRemove.element = toRemove.right.element;
                        toRemove.right = null;
                    }
                }
                //When the parent is red(easy)
                else if(curParent.color == false){
                    if(toRemove == curParent.left){
                        //Before rotation, remember the left Child of sibling
                        Node nephew = curParent.right.left;
                        //Rotate with curParent as pivot
                        rotateCClockWise(curParent);
                        //Adopt the child of toRemove and resolve 2-red defect
                        curParent.left = 
                            toRemove.left==null? toRemove.right : toRemove.left;
                        if(curParent.left != null){
                            curParent.left.parent = curParent;
                            balanceRB(curParent.left);  
                        }

                        //Whereever the nephew is now balance it
                        if(nephew != null && nephew.color == false){
                            balanceRB(nephew);
                        }
                        return true;
                    }else{
                        //Before rotation, remember the right Child of sibling
                        Node nephew = curParent.left.right;
                        //Rotate with curParent as pivot
                        rotateClockWise(toRemove.parent);
                        //Adopt the child of toRemove and resolve 2-red defect
                        curParent.right = 
                            toRemove.left==null? toRemove.right : toRemove.left;
                        if(curParent.right != null){
                            curParent.right.parent = curParent;
                            balanceRB(curParent.right);
                        }

                        //Whereever the nephew is now, balance it
                        if(nephew != null){
                            balanceRB(nephew);
                        }
                        return true;
                    }
                }
                //When the parent Node is black and there's no red Child
                //There are 2 situations, when the sibling is red
                //or when the sibling is black
                //sibling cannot be null
                else{
                    Node sibling = 
                        toRemove == curParent.left? curParent.right : curParent.left;
                    //If parent is root and the sibling is black
                    if(curParent == root && sibling.color == true){
                        //System.out.println("temp1");
                        //Stop parentship
                        Node child = 
                            toRemove.left==null?toRemove.right:toRemove.left;
                        if(sibling == curParent.left){
                            curParent.right = child;
                        }else{
                            curParent.left = child;
                        }
                        if(child!=null) child.parent = curParent;

                        sibling.color = false;
                        Node nephew1 = sibling.left;
                        Node nephew2 = sibling.right;
                        if(nephew1 != null) balanceRB(nephew1);
                        if(nephew2 != null) balanceRB(nephew2);
                        return true;
                    }
                    //When the sibling is red, it's quite easy and doesn't
                    //matter if the parent is root
                    if(sibling.color == false){
                        if(toRemove == curParent.right){
                            //Stop parentship
                            curParent.right = 
                                toRemove.left==null?toRemove.right:toRemove.left;
                            if(curParent.right!=null) 
                                curParent.right.parent = curParent;
                            rotateClockWise(curParent);
                        }else{
                            //Stop parentship
                            curParent.left =
                                toRemove.left==null?toRemove.right:toRemove.left;
                            if(curParent.left!=null) 
                                curParent.left.parent = curParent;
                            rotateCClockWise(curParent);
                        }
                        //Recolor
                        sibling.color = true;

                        Node child1 = curParent.left;
                        Node child2 = curParent.right;
                        if(child1 != null) child1.color = false;
                        if(child2 != null) child2.color = false;
                        if(child1!=null){
                            Node grandChild1 = child1.left;
                            Node grandChild2 = child1.right;
                            if(grandChild1 != null) balanceRB(grandChild1);
                            if(grandChild2 != null) balanceRB(grandChild2);
                        }
                        if(child2 != null){
                            Node grandChild1 = child2.left;
                            Node grandChild2 = child2.right;
                            if(grandChild1 != null) balanceRB(grandChild1);
                            if(grandChild2 != null) balanceRB(grandChild2);;
                        }
                        return true;
                    }
                    //When the sibling is black and curParent is not root
                    else{
                        //When the sibling still has a red child
                        boolean siblingOnLeft = 
                            curParent.left==sibling? true : false;
                        boolean redOnLeft = 
                            sibling.left!=null && !sibling.left.color;
                        boolean redOnRight = 
                            sibling.right!=null && !sibling.right.color;
                        if(redOnLeft || redOnRight){
                            if(toRemove == curParent.right){
                                //Stop parentship
                                curParent.right = 
                                    toRemove.left==null?toRemove.right:toRemove.left;
                                if(curParent.right!=null) 
                                    curParent.right.parent = curParent;
                            }else{
                                //Stop parentship
                                curParent.left =
                                    toRemove.left==null?toRemove.right:toRemove.left;
                                if(curParent.left!=null) 
                                    curParent.left.parent = curParent;
                            }
                            if(siblingOnLeft){
                                if(!redOnLeft){
                                    rotateCClockWise(sibling);
                                    sibling.parent.color = true;
                                    rotateClockWise(curParent);
                                }else{
                                    sibling.left.color = true;
                                    rotateClockWise(curParent);
                                }
                                return true;
                            }else{
                                if(!redOnRight){
                                    rotateClockWise(sibling);
                                    sibling.parent.color = true;
                                    rotateCClockWise(curParent);
                                }else{
                                    sibling.right.color = true;
                                    rotateCClockWise(curParent);
                                }
                                return true;
                            }
                        }
                        //When sibling has no red child
                        //Sadly, you are surrounded by black!!!
                        //In Chinese we call this '四面楚歌'
                        else{
                            //Stop parentship
                            if(siblingOnLeft){
                                curParent.right = null;
                            }else{
                                curParent.left = null;
                            }
                            //Balance the black number under this parent
                            sibling.color = false;
                            //ask the parent to figure out what to do
                            hasLessBlack(curParent);
                            return true;
                        }
                        
                    }
                }
            }else{
                //When the toRemove black Node is inner
                Node successor = getNext(toRemove);
                toRemove.element = successor.element;
                return remove(successor);
            }
        }
        return false;
    }

    /**
     * Helper method to balance a Node when paths through it has one less
     * black then others
     * Precondition: defect is itself black(or why not sacrificing yourself!)
     * defect is one less black than its sibling!
     * It's not a parent of branches with different blacks!!
     * @param defect the Node with 1 black Node less
     */
    private void hasLessBlack(Node defect) 
        throws NullPointerException, IllegalStateException{
        //System.out.println("Entering hasLessBlack");
        if(defect == null){
            throw new NullPointerException();
        }
        if(defect.color == false){
            throw new IllegalStateException();
        }

        //Base case
        if(defect == root){
            return;
        }
        Node curParent = defect.parent;
        Node sibling = defect==curParent.left?curParent.right:curParent.left;
        //Also a base case(curParent is red)
        if(!curParent.color){
            //Remember the nephew that will be taken by CurParent
            Node stepSibling;
            if(curParent.left == sibling){
                stepSibling = sibling.right;
                rotateClockWise(curParent);
            }else{
                stepSibling = sibling.left;
                rotateCClockWise(curParent);
            }
            balanceRB(stepSibling);
            return;
        }
        //Another base case that repels the previous one
        //(sibling is red)
        if(sibling.color == false){
            if(sibling == curParent.left){
                rotateClockWise(curParent);
                sibling.color = true;
                curParent.color = false;
                hasLessBlack(defect);
                return;
            }else{
                rotateCClockWise(curParent);
                sibling.color = true;
                curParent.color = false;
                hasLessBlack(defect);
                return;
            }
        }
        //Or if the sibling is not black but both its children are red
        if(sibling.left!=null&&sibling.left.color==false
            &&sibling.right!=null&&sibling.right.color==false){
            sibling.color = false;
            sibling.left.color = true;
            sibling.right.color = true;
            //Now fits in the previous situation
            hasLessBlack(defect);
            return;
        }
        //Surprisingly, if one of its child is red, it's still base case(almost)
        /*if(sibling.left!=null&&sibling.left.color==false){
            if(sibling==curParent.left){
                rotateClockWise(sibling);
                hasLessBlack(defect);
                return;
            }else{
                //Now aligned
                rotateClockWise(sibling);
                sibling.color = false;
                sibling.parent.color = true;
                hasLessBlack(defect);
                return;
            }
        }else if(sibling.right!=null && sibling.right.color==false){
            if(sibling==curParent.right){
                rotateCClockWise(sibling);
                hasLessBlack(defect);
                return;
            }else{
                //Now align
                rotateCClockWise(sibling);
                sibling.color = false;
                sibling.parent.color = true;
                hasLessBlack(defect);
                return;
            }
        }*/

        //When the parent,sibling and uncle are black
        sibling.color = false;
        if(curParent == root){
            if(sibling.left != null) balanceRB(sibling.left);
            if(sibling.right != null) balanceRB(sibling.right);
            hasLessBlack(root);
        }else{
            Node grandParent = curParent.parent;
            boolean parentOnLeft = grandParent.left==curParent?true:false;
            if(sibling.left != null) balanceRB(sibling.left);
            if(sibling.right != null) balanceRB(sibling.right);
            if(parentOnLeft) hasLessBlack(grandParent.left);
            else hasLessBlack(grandParent.right);
        }
    }

    /**
     * Another very important helper method, find the successor of current Node
     * @param curNode the node whose successor is needed
     * @return the node with value that is JUST after the passed in Node
     */
    private Node getNext(Node curNode) throws NullPointerException{
        if(curNode == null){
            throw new NullPointerException();
        }

        Node parent = curNode.parent;
        //The best choice is the biggest Child of right Child
        if(curNode.right != null){
            Node tempLeft = curNode.right;
            while(tempLeft != null){
                curNode = tempLeft;
                tempLeft = curNode.left;
            }
            return curNode;
        }
        //Or its parent if curNode is left Child
        else if(parent != null && curNode == parent.left){
            return parent;
        }
        //or if the curNode is the right Child of its parent(sad :( )
        else{
            while(parent != null){
                Node tempGrand = parent.parent;
                if(tempGrand != null && parent == tempGrand.left){
                    return tempGrand;
                }
                parent = tempGrand;
            }
            //If the while loop ends and no successor is found
            //Unfortunately, you are the last in the tree
            return null;
        }
    }

    /**
     * Temporary Stringalize method to see the structure
     * @return string representation of the tree
     */
    private void printFamily(Node n){
        Queue<Node> nodes = new ArrayDeque<Node>();
        if(n!=null) nodes.add(n);
        while(!nodes.isEmpty()){
            Node toPrint = nodes.poll();
            System.out.print(toPrint);
            System.out.print("h("+this.height(toPrint)+") ");
            if(toPrint.left != null) nodes.add(toPrint.left);
            if(toPrint.right != null) nodes.add(toPrint.right);
        }
        System.out.println();
    }

    /**
     * This method searches for the appropriate parent for the given element
     * Precondition: The tree should not be empty
     * !!!This method cannot be used to search an element stored in the root
     * @param e the element who needs a parent
     * @param the parent for the passed in element
     * (null if the element already exists)
     */
    private Node searchParent(E e) throws NullPointerException{
        if(this.isEmpty() || e==null){
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
     * Unlike searchParent, this searches the Node itself
     * @param e the element in the tree
     * @return the Node that stores e
     */
    private Node search(E e) throws NullPointerException{
        if(this.isEmpty() || e==null){
            throw new NullPointerException();
        }

        Node current = root;
        while(current!=null && current.element.compareTo(e) != 0){
            if(current.element.compareTo(e) < 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }

        return current;
    }

    /**
     * This is a critical method when adding some new Nodes to the tree
     * It balances the possible 2-red defect brought by the new Node
     * @param newNode the newly implemanted Node
     * (might exist before but property being changed)
     */
    private void balanceRB(Node newNode) throws IllegalStateException{
        //System.out.println("Entering balanceRB");
        if(newNode.color){
            return;
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

        //If the uncle is black or null
        if(curParent.color == false && (uncle == null || uncle.color == true)){
            grandParent.color = false;
            curParent.color = true;
            //Rotate, make parent become the new root of this partial tree
            if(grandParent.left == curParent){
                //To make sure 2-red defects do not appear after rotating, make
                //new red node, parent red node and grandparent on one line
                if(curParent.right == newNode){
                    rotateCClockWise(curParent);
                    curParent.color = false;
                    newNode.color = true;
                }
                rotateClockWise(grandParent);
            }else{
                if(curParent.left == newNode){
                    rotateClockWise(curParent);
                    curParent.color = false;
                    newNode.color = true;
                }
                rotateCClockWise(grandParent);
            }
            return;
        }

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
        //The pivot is the root element
        if(curParent == null){
            //current parent adopt current left child
            root = pivot.left;
            root.parent = curParent;
            //pivot adopt the right child of its previous left child
            pivot.left = root.right;
            if(pivot.left != null) pivot.left.parent = pivot;
            //previous left child adopt pivot
            root.right = pivot;
            pivot.parent = root;

            return;
        }
        //If parent is child of some other parent
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
        if(curParent == null){
            //current parent adopt current right child
            root = pivot.right;
            root.parent = curParent;
            //pivot adopt the left child of its previous right child
            pivot.right = root.left;
            if(pivot.right != null) pivot.right.parent = pivot;
            //previous left child adopt pivot
            root.left = pivot;
            pivot.parent = root;

            return;
        }
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
     * This method tests if there are two continuous red Nodes
     * @param n the Node whose subfamily will be tested
     * @return true if there are two continuos red Node
     */
    private boolean hasTwoRed(Node n){
        //base cases
        if(n == null) return false;
        if(n.left == null && n.right == null) return false;
        if(n.left != null && (!n.left.color && !n.color)) return true;
        if(n.right != null && (!n.right.color && !n.color)) return true;
        
        //recurse
        return hasTwoRed(n.left) || hasTwoRed(n.right);   
    }

    /**
     * This helper method calculates the black height of a Node
     * null is considered as 0
     * @param n the Node whose black height is expected
     * @return the black height of n
     */
    private int blackHeight(Node n) throws IllegalStateException{
        //base case
        if(n == null) return 0;

        int leftBlack = blackHeight(n.left);
        int rightBlack = blackHeight(n.right);
        if(leftBlack != rightBlack){
            throw new IllegalStateException();
        }else{
            if(n.color == false) return leftBlack;
            else return 1+leftBlack;
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

        /**
         * toString method that prints out basic information of the Node
         * @return String representation of the Node
         */
        public String toString(){
            String result = this.element.toString()+"(";
            result += color ? "B" : "R";
            result += ")";

            return result;
        }
        //There are no getters and setters 
        //because the class is already made private and the
        //instance variables are visible for the BST12RB class
    }

    /**
     * this class defines the iterator for this tree
     * It implements Iterator class
     */
    private class RBIterator implements Iterator{
        Node cursor = null;
        /**
         * Constructor
         * @param tree the tree that the iterator iterates through
         */
        public RBIterator(BST12RB tree) throws NullPointerException{
            if(tree == null){
                throw new NullPointerException();
            }

            if(tree.isEmpty()){
                cursor = null;
            }else{
                cursor = search((E)tree.first());
            }
        }

        /**
         * @return true if there's still next element
         */
        public boolean hasNext(){
            if(cursor == null){
                return false;
            }else{
                return true;
            }
        }

        /**
         * @return next value in ascending order
         */
        public E next() throws NoSuchElementException{
            if(cursor == null){
                throw new NoSuchElementException();
            }

            E toReturn = cursor.element;
            cursor = getNext(cursor);
            return toReturn;
        }
    }

    /**
     * This class is used for BST12RB's specific tests
     */
    public static class RBTester extends junit.framework.TestCase{
        BST12RB tree = new BST12RB<Integer>(); 
        @Before
        public void setUp(){
            System.out.println("Running");
        }

        @Test
        //This test is specially for BST12RB. Disable it when testing other trees
        public void testRBProperty(){
            assertTrue(tree.isEmpty());
            Random generator = new Random();
            for(int i = 0; i < 1000; i++){
                tree.clear();
                int size = generator.nextInt(100);
                int removeNum = generator.nextInt(size+1);
                HashSet<Integer> toAdd = new HashSet<Integer>();
            
                while(toAdd.size() < size){
                    toAdd.add(generator.nextInt(10*size));
                }
                Iterator getAdd = toAdd.iterator();
                for(int j = 0; j < size; j++){
                    tree.add((Integer)getAdd.next());
                    assertTrue(tree.toString(),!tree.hasTwoRed(tree.root));
                    try{
                        tree.blackHeight(tree.root);
                    }catch(IllegalStateException ex){
                        tree.printFamily(tree.root);
                        fail(tree.toString());
                    }
                }

                Object[] allEle = toAdd.toArray();
                for(int j = 0; j < removeNum; j++){
                    tree.printFamily(tree.root);
                    int toRemove = generator.nextInt(allEle.length);
                    tree.remove((Integer)allEle[toRemove]);
                    assertTrue(tree.toString(),!tree.hasTwoRed(tree.root));
                    try{
                        tree.blackHeight(tree.root);
                    }catch(IllegalStateException ex){
                        tree.printFamily(tree.root);
                        fail(tree.toString());
                    }
                }
            }
        }
    }
}
