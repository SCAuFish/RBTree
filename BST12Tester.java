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
import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * In this class is the testers
 */
public class BST12Tester extends junit.framework.TestCase{
    //By default it is BST12 tree
    private BinSearchTree12<Integer> tree = new BST12Adapt<Integer>();
    private BinSearchTree12<String> stringTree = new BST12Adapt<String>();
    private Random generator;

    @Before
    public void setUp() throws Exception {
        //System.out.println("setting up ");
        tree.add(5);
        tree.add(4);
        tree.add(2);
        tree.add(3);
        tree.add(6);
        tree.add(1);
        generator = new Random();
    }

    @Test
    public void testEmptyTree() {
        tree.clear();
        assertEquals(0, tree.size());
        assertEquals(0, tree.height());
        assertEquals("[]", tree.toString());
    }

    @Test
    public void testAddUnique() {
        for (int n = 1; n <= 6; n++) {
            assertTrue(tree.contains(n));
        }
    }

    @Test
    public void testSize() {
        assertEquals(6, tree.size());
    }

    @Test
    public void testDepth() {
        if(tree instanceof BST12Adapt){
            assertEquals(6, tree.height());
        }
        if(tree instanceof BST12RB){
            assertEquals(3, tree.height());
        }

    }

    @Test
    public void testToString() {
        assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testAddDuplicates() {
        for (int n = 1; n <= 6; n += 2)
            assertFalse(tree.add(n));
    }

    @Test
    public void testRemoveExistingLeaf() {
        assertTrue(tree.remove(1));
        assertEquals(5, tree.size());
        assertEquals("[2, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithEmptyRightChild() {
        assertTrue(tree.remove(4));
        assertEquals(5, tree.size());
        assertEquals("[1, 2, 3, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithEmptyLeftChild() {
        tree.add(7);
        assertTrue(tree.remove(6));
        assertEquals(6, tree.size());
        assertEquals("[1, 2, 3, 4, 5, 7]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithTwoChildren() {
        assertTrue(tree.remove(2));
        assertEquals(5, tree.size());
        assertEquals("[1, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveRoot() {
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
            int toAdd = rnd.nextInt(1000);
            assertEquals(oracle.add(toAdd), tree.add(toAdd));
            int toRemove = rnd.nextInt(1000);
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

    /*---------------------------New Testers---------------------------*/
    @Test
    public void testFirst(){
        TreeSet model = new TreeSet<Integer>();
        tree.clear();
        for(int i = 0; i < 1000; i++){
            Integer toAdd = generator.nextInt();
            model.add(toAdd);
            tree.add(toAdd);
        }

        assertEquals(tree.first(), model.first());
    }

    @Test
    public void testlast(){
        TreeSet model = new TreeSet<Integer>();
        tree.clear();
        for(int i = 0; i < 1000; i++){
            Integer toAdd = generator.nextInt();
            model.add(toAdd);
            tree.add(toAdd);
        }

        assertEquals(tree.last(), model.last());
    }

    @Test
    public void testNumChildren(){
        tree.clear();
        for(int i = 0; i < 10; i++){
            tree.add(i);
        }

        if(tree instanceof BST12Adapt){
            assertEquals(tree.numChildren(0), -1);
        }
        if(tree instanceof BST12RB){
            assertEquals(tree.numChildren(3), 9);
            assertEquals(tree.numChildren(tree.first()), 0);
            assertEquals(tree.numChildren(tree.last()), 0);
        }
    }

    @Test
    public void testHeight(){
        if(tree instanceof BST12Adapt){
            assertEquals(tree.height(), 6);
            return;
        }
        if(tree instanceof BST12RB){
            assertEquals(tree.height(), 3);
        }
    }

    @Test
    public void testIteratorHasNext(){
        Iterator iter = tree.iterator();
        for(int i = 0; i < 6; i++){
            assertTrue(iter.hasNext());
            iter.next();
        }
    }

    @Test
    public void testIteratorNext(){
        Iterator iter = tree.iterator();
        for(int i = 0; i < 6; i++){
            assertEquals(iter.next(), i+1);
        }
    }

    @Test
    public void testIterRemove(){
        Iterator iter = tree.iterator();
        iter.next();
        try{
            iter.remove();
            fail("No Exception");
        }catch(UnsupportedOperationException e){
            //correct
        }catch(Exception e){
            fail("Wrong exception");
        }
    }

    @Test
    public void testContains(){
        for(int i = 0; i < 6; i++){
            assertTrue(tree.contains(i+1));
        }
        for(int i = 7; i < 10; i++){
            assertTrue(!tree.contains(i));
        }
    }

    @Test
    public void testAddAll(){
        Collection<Integer> allEle = new HashSet<Integer>();
        for(int i = 0; i < 100; i++){
            allEle.add(generator.nextInt(1000));
        }

        BinSearchTree12 copy = new BST12(allEle);
        if(tree instanceof BST12Adapt){
            copy = new BST12Adapt(allEle);
        }
        if(tree instanceof BST12RB){
            copy = new BST12RB(allEle);
        }

        Iterator setIter = allEle.iterator();
        while(setIter.hasNext()){
            Integer ele = (Integer)setIter.next();
            assertTrue(copy.contains(ele));
        }
    }

    @Test
    public void testBalancedHeight(){
        if(tree instanceof BST12Adapt){
            return;
        }
        if(tree instanceof BST12RB){
            //50 tests
            for(int i = 0; i < 50; i++){
                tree.clear();
                int size = generator.nextInt(1000);
                int removeNum = generator.nextInt(size+1);
                HashSet<Integer> toAdd = new HashSet<Integer>();

                while(toAdd.size() < size){
                    toAdd.add(generator.nextInt(10*size));
                }
                Iterator getAdd = toAdd.iterator();
                for(int j = 0; j < size; j++){
                    tree.add((Integer)getAdd.next());
                }
                assertEquals(tree.size(), toAdd.size());
                assertTrue(tree.height() <= 20);
            }
        }
    }
}
