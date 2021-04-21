JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
	.java.class:
	        $(JC) $(JFLAGS) $*.java

CLASSES = \
	          Instruction.java \
		          IFormat.java \
			          JFormat.java \
				          RFormat.java \
						lab2.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	        $(RM) *.class
