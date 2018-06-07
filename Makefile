TestAdapt: BSTAdapt.class BST12Tester.class
	java -cp '.:/Users/shencheng/java/junit.jar'\
		org.junit.runner.JUnitCore BST12Tester 

.SUFFIXES: .class .java
.java.class:
	javac -cp '.:/Users/shencheng/java/junit.jar' $<
