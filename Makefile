TestAdapt: BST12Adapt.class BST12Tester.class
	java -cp '.:/Users/shencheng/java/junit.jar'\
		org.junit.runner.JUnitCore BST12Tester

TestRB: BST12RB.class BST12Tester.class
	java -cp '.:/Users/shencheng/java/junit.jar'\
		org.junit.runner.JUnitCore BST12Tester

QuickRB: BST12RB.class
	java BST12RB

TestBST: BST12.class BST12Tester.class
	java -cp '.:/Users/shencheng/java/junit.jar'\
		org.junit.runner.JUnitCore BST12Tester

TestRBPro: BST12RB.class
	className='BST12RB$RBTester'
	java -cp '.:/Users/shencheng/java/junit.jar'\
		org.junit.runner.JUnitCore 'BST12RB$RBTester'




.SUFFIXES: .class .java
.java.class:
	javac -cp '.:/Users/shencheng/java/junit.jar' $<


