TestAdapt: BSTAdapt.class BST12Tester.class
	java -cp '.:/Users/shencheng/java/junit.jar'\
		org.junit.runner.JUnitCore BST12Tester

TestRB: BST12RB.class BST12Tester.class
	java -cp '.:/Users/shencheng/java/junit.jar'\
		org.junit.runner.JUnitCore BST12Tester

QuickRB: BST12RB.class
	java BST12RB



.SUFFIXES: .class .java
.java.class:
	javac -cp '.:/Users/shencheng/java/junit.jar' $<


