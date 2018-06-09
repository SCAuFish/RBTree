/**
 * This file contains the class defining a binary search tree that
 * adapts the structure of a TreeSet.
 * @author Cheng Shen
 * @version 1.0
 */

import java.util.TreeSet;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * This class defines a binary search tree that adapts the TreeSet
 * from Java Collections Framework.
 * It also implements BinSearchTree12 interface.
 */
public class BSTAdapt<E extends Comparable<? super E>> 
    implements BinSearchTree12<E>{
    //Instance variable
    TreeSet<E> tree;

    //Constructors
    /**
     * No-arg constructor
     */
    public BSTAdapt(){
        this.tree = new TreeSet<E>();
    }

    /**
     * @param c a Collection of objects of class E or extending from
     * E
     */
    public BSTAdapt(Collection<? extends E> c){
        this.tree = new TreeSet<E>(c);
    }

    /**
     * Add a specified element to the binary tree
     * @param e element to be added
     * @return if the adding is successful
     */
    public boolean add(E e) 
            throws NullPointerException, ClassCastException{
        if(e == null){
            throw new NullPointerException();
        }
        try{
            return this.tree.add((E)e);
        }catch(ClassCastException ex){
            throw ex;
        }
    }

    /**
     * Add a whole Collection with valid elements to the tree
     * @param c the collection to be added
     * @return whether it is successfully returned
     */
    public boolean addAll(Collection c) 
            throws NullPointerException{
       try{
           this.tree.addAll(c);
       } catch(NullPointerException e){
           throw e;
       } catch(ClassCastException e){
           throw e;
       }

       return true;
    }

    /**
     * remove all the elements from the tree
     */
    public void clear(){
        this.tree.clear();
    }

    /**
     * tests if an element exists in this tree
     * @param o the element to be tested
     */
    public boolean contains(E o) 
            throws NullPointerException,ClassCastException{
        if(o == null){
            throw new NullPointerException();
        }

        try{
            return this.tree.contains((E)o);
        }catch(NullPointerException e){
            throw e;
        }
    }

    /**
     * the tree returns the first(lowest) eleement
     * @return the lowest element
     */
    public E first() throws NoSuchElementException{
        try{
            return this.tree.first();
        }catch(NoSuchElementException e){
            throw e;
        }
    }

    /**
     * tests if the tree is empty
     * @return whether the tree is empty
     */
    public boolean isEmpty(){
        return this.tree.isEmpty();
    }

    /**
     * returns an iterator over the elements in this search tree in
     * ascending order
     * @return an Iterator over the tree
     */
    public Iterator<E> iterator(){
        return new adaptIterator();
    }

    /**
     * returns the last element currently in the search tree
     * @return the last element currently in the tree
     */
    public E last() throws NoSuchElementException{
        try{
            return this.tree.last();
        }catch(NoSuchElementException e){
            throw e;
        }
    }

    /**
     * remove the speicified element from the search tree if present
     * @return whether the removing is successful
     */
    public boolean remove(E o) 
            throws NullPointerException,ClassCastException{
        if(o == null){
            throw new NullPointerException();
        }
        try{
            return this.tree.remove(o);
        }catch(ClassCastException e){
            throw e;
        }
    }

    /**
     * returns the size of the search tree
     * @return size
     */
    public int size(){
        return this.tree.size();
    }

    /**
     * @return the height of the search tree
     */
    public int height(){
        return this.size();
    }

    /**
     * returns -1 if the given target is in the tree. Throws exceptions if
     * there are faults.
     * @return the number of children of the Node that references target.
     */
    public int numChildren(E target) 
            throws NoSuchElementException, IllegalStateException{
        try{
            if(this.contains((E)target)){
                return -1;
            }else{
                throw new NoSuchElementException();
            }
        }catch(Exception e){
            throw new IllegalStateException();
        }
    }

    /**
     * @return the String representation of this tree
     */
    public String toString(){
        return this.tree.toString();
    }

    /**
     * This constructor defines the iterator class for the adapt tree
     * It does not define the removing method
     */
    private class adaptIterator implements Iterator{
        Iterator<E> treeIterator;
        
        /**
         * No-arg constructor
         */
        public adaptIterator(){
            this.treeIterator = tree.iterator();
        }

        /**
         * has next method
         * @return true if there's still element
         */
        @Override
        public boolean hasNext(){
            return this.treeIterator.hasNext();
        }

        /**
         * @return the next element
         */
        @Override
        public E next() throws NoSuchElementException{
            try{
                return this.treeIterator.next();
            }catch(NoSuchElementException ex){
                throw ex;
            }
        }
    }
}
