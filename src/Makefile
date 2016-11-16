JFLAGS = -g
JC = javac

CLASSES = \
	simulation/HealthGlobals.java \
	simulation/SimulationGlobals.java \
	simulation/Human.java \
	simulation/Country.java \
	simulation/HealthState.java \
	simulation/Healthy.java \
	simulation/Infected.java \
	simulation/Sick.java \
	simulation/Immune.java \
	simulation/Simulator.java \
	test/Test.java \

default: 
	$(JC) $(JFLAGS) $(CLASSES)

clean:
	$(RM) simulation/*.class
	$(RM) test/*.class
