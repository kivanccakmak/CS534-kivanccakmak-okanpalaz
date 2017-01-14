# Project Post Mortem

## Design Decisions

### Neighbors as a List

Countries keep their neighbors in a list and the list is set at initialization.
They don't query the Simulator for their neighbors. They didn't keep a handle
to the Simulator until air travel was introduced.

### Country Responsible for Viewed State

All of the information displayed comes from a subclass in Country called
HealthStats that outgrew its original scope. Simulator (Model) goes through the
list of countries and collects the stat objects and then passes it to the
Controller at the end of each day. Originally it kept only the health related
stats, however as development went on it became the core data for the View,
even passing the country name and move counts.  View processes the stats and
generates various charts. It is limited in the sense that it doesn't track
individual Humans actions.

### Rules as a Singleton

The initial decision to make SimulationRules a singleton was made to keep the
rules in a single class that is accessible from anywhere, instead of passing a
rules object around or having a handle to the Simulator object everywhere to be
able to query the rules. However, this limits how many Simulator objects can be
run. With a singleton they have to share the rules.

SimulationRules also has a random number generator and is used for "dice throw"
methods.

### Code Repetition in HealthState Subclasses

Not all HealthState subclasses count days (Healthy, Dead, Super). The ones that
do repeat the day counting code. Maybe there could've been another level of
abstract class that did the day counting.

### Type Queries for Human

There are type queries for all HealthState subclasses like isHealthy, isDead.
Also for Doctor class as isDoctor. It was made this way for easier stat
collecting but adding these queries for all was a bit tedious.

Human and Doctor also query isDead to not take non healthstate related actions.

## New Requirements

### Round World

[Related Change (Link)](https://github.com/ozusrl/CS534-kivanccakmak-okanpalaz/commit/824cb1fdc5306ff98c4ce2375f623f892dedcf70)

This change was fairly simple as it only required changing methods that
calculated the neighbor indices.

### Super

[Related Change (Link)](https://github.com/ozusrl/CS534-kivanccakmak-okanpalaz/commit/28ae1e4e1043619a4b947b959962962ce707260c)

This change introduced a new subclass for HealthState. For Human constructor to
start with Super we modified the constructor from a isInfected boolean to a
type enum. However the enum was later removed for the Doctor change below as it
complicated the initialization.

### Doctors

[Related Change (Link)](https://github.com/ozusrl/CS534-kivanccakmak-okanpalaz/commit/5e09ceda9b9bde3a40f469fe9705150255f5d9a5)

Doctor was implemented as a subclass of Human and was fairly simple. It has its
own passDay that runs `super.passDay()` in the end.

A "isVaccineCandidate" was added to HealthState abstract class to determine
targets.

The ugly part was the populate method of Simulator. This is because the rules
indicate doctor and regular Humans to be unrelated to the healthy, infected and
super percentages. So we made a decision to just change the constructor to
initiate Humans and Doctors first with the default constructors and then assign
initial health states. Otherwise, assigning overlapping Doctor/Human and
Healthy/Infected/Super percentages was going to be complicated.

### Air Travel

[Related Change (Link)](https://github.com/ozusrl/CS534-kivanccakmak-okanpalaz/commit/882c76ba24abf62756eb4cacd3f5f828fe21f1e6)

This change required us to pass a Simulator handle to Country for querying the
list of countries. Previously, they only had a list of neighbors.

We added another dice throw method to the SimulationRules object.

## Some Outstanding Bugs

### Neighbor Index Calculation Bug
![] (doc/index.png)

Different map sizes weren't tested so "rows" related "cols" bug in neighbor
association was caught late.

### Countries Neighboring Themselves
![] (doc/move.png)

If countries were neighboring themselves due to round world rules, Human
objects considered their own country a move candidate. In this case they were
added to their population lists multiple times breaking the population
calculations.


### Dead Doctors Still Vaccinating
![] (doc/deaddoctor.png)

HealthState and Human's passDay actions unrelated to the health state are kept
separate. So it was easy to miss Dead state check for Doctor as it was doing
vaccination in it's own passDay and then just calling `super.passDay()`.
