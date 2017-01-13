#Epidemic Simulator Game

The world is a **MxN** grid of countries with unlimited number of people.
<br/><br/>
A contagious ***virus*** is making people sick. When a person becomes sick, she looks unhealthy.
<br/><br/>
When a person becomes infected, she does not immediately get sick, though, but enters a phase
of incubation in which she is infectious <br/>
(i.e. can transmit the virus to other people) but not sick (so she looks as if she is healthy).
<br/><br/>
People infected with the virus (whether visibly or not) may transmit
 it to other people in the same country.
<br/><br/>
To avoid the virus, people ***travel*** across countries. They avoid countries that hold visibly sick people.
<br/><br/>
Unfortunately, travelling people means the virus finds opportunity to spread to the whole world.
<br/><br/>

![] (doc/ui.png)

###Rules

1. Initially %X of people are infected with the virus (but they are not sick yet)

2. After 6 days of becoming infected, a person becomes sick and is therefore visibly infectious

3. After 14 days of becoming infected, a person dies with a probability of 25%

4. After 16 days of becoming infected, a person becomes immune and is no longer visibly infectious, but remains infectious.

5. After 18 days of becoming infected, a person turns healthy. He is now in the same state as he was before his infection, which means that he can get infected again

6. Each person picks the day to move uniformly in between 0-5. Consequently, a person will pick one of the countries which does not have any visibly infectious people -with equal probability, and will move to it.

7. When a person moves into a country with an infectious person she might get infected according to the transmissibility rate of 40%, unless the person is already infected or immune.

#Code Documentation

TODO: flow-chart
<br/>
TODO: main class relations
<br/>

###Simulator
![] (doc/Country.svg)

###Human
![] (doc/Human.svg)

###Simulator
![] (doc/Simulator.svg)

###WorldController
![] (doc/WorldController.svg)

###WorldView
![] (doc/WorldView.svg)

##Compile

`./gradlew jar`

##Run Test

`./gradlew runApp`

