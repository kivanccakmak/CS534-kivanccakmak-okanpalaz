JFLAGS = -g
JC = javac

CLASSES = \
	simulation/HealthGlobals.java \
	simulation/SimulationGlobals.java \
	simulation/Human.java \
	simulation/Country.java \
	simulation/Simulator.java \
	test/Test.java \

default:
	$(JC) $(JFLAGS) $(CLASSES)

clean:
	$(RM) simulation/*.class
	$(RM) test/*.class
