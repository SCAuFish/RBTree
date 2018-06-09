/**
 * This file defines a binary search tree with necessary methods.
 * @author Cheng Shen
 * @version 1.0
 */

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * This class defines a binary search tree that implements from
 * BinSearchTree12 and adapts BST12RB as its inner structure
 */
public class BST12<E extends Comparable<? super E>> extends BST12RB
	implements BinSearchTree12<E>{
		/**
		 * No-arg constructor
		 */
		public BST12(){
			super();
		}

		/**
		 * Constructor that copies from a whole collection given
		 * @param c the collection to be copied from
		 */
		public BST12(Collection<? extends E> c){
			super(c);
		}
}