/**
 * This file contains testers for three classes that implement interface
 * BinarySearchTree12.
 * @author CSE 12 Staff
 * @author Cheng Shen
 * @version 1.0
 */
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * In this class is the testers
 */
public class BST12Tester extends junit.framework.TestCase{
    //By default it is BST12 tree
	private BinSearchTree12<Integer> tree = new BST12RB<Integer>();
	private BinSearchTree12<String> stringTree = new BST12RB<String>();
    
    @Before
	public void setUp() throws Exception {
        //System.out.println("setting up ");
		tree.add(5);
		tree.add(4);
		tree.add(2);
		tree.add(3);
		tree.add(6);
		tree.add(1);
	}
	
    @Test
	public void testEmptyTree() {
        System.out.println(1);
		tree.clear();
		assertEquals(0, tree.size());
		assertEquals(0, tree.height());
		assertEquals("[]", tree.toString());
	}

	@Test
	public void testAddUnique() {
        System.out.println(2);
		for (int n = 1; n <= 6; n++) {
			assertTrue(tree.contains(n));
		}
	}

	@Test
	public void testSize() {
        System.out.println(3);
		assertEquals(6, tree.size());
	}

	@Test
	public void testDepth() {
        System.out.println(4);
		assertEquals(3, tree.height());
	}

	@Test
	public void testToString() {
        System.out.println(5);
		assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testAddDuplicates() {
        System.out.println(6);
		for (int n = 1; n <= 6; n += 2)
			assertFalse(tree.add(n));
	}

	@Test
	public void testRemoveExistingLeaf() {
        System.out.println(7);
		assertTrue(tree.remove(1));
		assertEquals(5, tree.size());
		assertEquals("[2, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithEmptyRightChild() {
        System.out.println(8);
		assertTrue(tree.remove(4));
		assertEquals(5, tree.size());
		assertEquals("[1, 2, 3, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithEmptyLeftChild() {
        System.out.println(9);
		tree.add(7);
		assertTrue(tree.remove(6));
		assertEquals(6, tree.size());
		assertEquals("[1, 2, 3, 4, 5, 7]", tree.toString());
	}

	@Test
	public void testRemoveExistingMiddleItemWithTwoChildren() {
        System.out.println(10);
		assertTrue(tree.remove(2));
		assertEquals(5, tree.size());
		assertEquals("[1, 3, 4, 5, 6]", tree.toString());
	}

	@Test
	public void testRemoveRoot() {
        System.out.println(11);
		assertTrue(tree.remove(5));
		assertEquals(5, tree.size());
		assertEquals("[1, 2, 3, 4, 6]", tree.toString());
	}

    
	@Test
	public void testRandomAddAndRemove() {
		Random rnd = new Random();

		SortedSet<Integer> oracle = new TreeSet<Integer>();
		for (int n = 1; n <= 6; n++)
			oracle.add(n);

		for (int n = 0; n < 1000; n++) {
			int toAdd = rnd.nextInt(10);
			assertEquals(oracle.add(toAdd), tree.add(toAdd));
			int toRemove = rnd.nextInt(10);
			assertEquals(oracle.remove(toRemove), tree.remove(toRemove));
			int checkExists = rnd.nextInt(10);
			assertEquals(oracle.contains(checkExists), tree
					.contains(checkExists));
			assertEquals(oracle.size(), tree.size());
			assertEquals(oracle.toString(), tree.toString());
		}
	}
    

	@Test
	public void testOtherType(){
        System.out.println(12);
		stringTree.add("D");
		stringTree.add("A");
		stringTree.add("C");
		stringTree.add("A");
		stringTree.add("B");
		assertEquals(4, stringTree.size());
		assertTrue(stringTree.contains("C"));
		stringTree.remove("C");
		assertFalse(stringTree.contains("C"));
	}
	
}
