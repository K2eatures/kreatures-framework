#KReatures Framework

The KReatures framework (Knowledge Representation for Epistemic Agents with Timed Unified Resources) is a framework for the implementation of knowledge-based agents with a strong focus on flexibility, extensibility and compatibility with diverse knowledge representation formalisms.
In addition, it will be able to represent spatio-temporal agent scenarios in a unified way such that agents can be used in different kinds of scenarios without changing their implementation (e.g. to evaluate their adaptivity and generality across multiple scenarios).
Its basis is a flexible Java plug-in architecture for the mental state of an agent as well as for the agent's functional component.
The knowledge representation plug-ins are based on the [Tweety library](http://tweetyproject.org/) for knowledge representation, which provides various ready-for-use implementations and knowledge representation formalisms.
KReatures already contains several instantiations that implement several approaches from the literature. 
KReatures also features an environment plug-in for communicating agents and a flexible GUI to monitor the simulation and the agents, particularly including the inspection of the dynamics of their mental states.

## Structure of KReatures

The basic structure of KReatures contains an **Agent** (*com.github.kreatures.core.Agent*) and it's **Environment** (*com.github.kreatures.core.EnvironmentBehavior*),
which delivers **Perceptions** (*com.github.kreatures.core.Perception*) to the Agent and is manipulated through **Actions** (*com.github.kreatures.core.Action*) by the Agent.
An Agent holds a set of **Data-Components** (*com.github.kreatures.core.AgentComponent*), which contain and manage the Agent's data. Moreover an Agent is defined by it's agent-cycle,
containing a list of **Operators** (*com.github.kreatures.core.operators.BaseOperator*), which have to be stateless themselves, but generate and manipulate data within the Agent's Data-Components.
Most Agents implemented in KReatures follow the BDI-architecture (Beliefs, Desires, Intentions), where **Beliefs** (*com.github.kreatures.core.logic.Beliefs*) represent the Agent's subjective knowledge of the world,
**Desires** (*com.github.kreatures.core.Desire* managed by the component *com.github.kreatures.core.logic.Desires*) are possible options an Agent has in the current situation and
**Intentions** (*com.github.kreatures.core.Intention* managed by the component *com.github.kreatures.core.PlanComponent*) representing the goal, the agent actually has chosen to pursue, holding a sequence of actions to fullfill that goal.
Moreover the BDI-architecture defines the related Operators: **UpdateBeliefs**, **GenerateOptions**, **UpdateIntentions** und **Execute**.

To make new Components, Operators or EnvironmentBehaviors usable, you have to manifest them within a **Plugin**.
To do so, create a new class that extends *com.github.kreatures.core.KReaturesPluginAdapter* and override the suitable methods, where you insert your own classes.

## The Island scenario  

The Island scenario creates an environment with varying conditions, the agent has to deal with.
The agent's task is to build a transmitting station on a tropical island.
There are four **locations** the agent can reside at:

* **AT_HQ** where the agent can recharge his battery and find shelter
* **AT_SITE** where the agent assembles the transmitting station
* **IN_CAVE** nearby the site, where the agent can find shelter in urgent situation
* **ON_THE_WAY** between the hq and the site

The island exhibits changing **weather conditions** with various impacts:

* **CLOUDS** weather is neutral and has no impact at all on the agent
* **SUN** the agent automatically recharges his battery with solar panels
* **STORM_OR_RAIN** the agent moves and works more slowly then usual, which the agent has to consider when planning his battery duration
* **THUNDERSTORM** has the same effect as storm or rain, but additional agent and site are endangered by lightling, if the site is not secured respectively the agent did not seek shelter

Eventually the agent can perform the following **actions**, to interact with it's environment:

* **ASSEMBLE_PARTS** work at the transmitting station
* **CHARGE_BATTERY** recharge battery at hq
* **COVER_SITE** secure site to make it insusceptible for thunderstorm
* **UNCOVER_SITE** uncover site before continue to work
* **MOVE_TO_HQ** move from site to hq
* **MOVE_TO_SITE** move from hq to site
* **ENTER_CAVE** find shelter in urgent danger
* **LEAVE_CAVE** leave cave before continue to work

For further information have a look at the bachelor thesis "Implementierung einer Motivations Komponente für wissensbasierte Agenten". 

There are some default implemented components allowing the simulation:
The IslandBehavior (*com.github.kreatures.island.behavior.IslandBehavior*) respectively the DynamicIslandBehavior (*com.github.kreatures.island.behavior.DynamicIslandBehavior*),
which represent the agent's environment with constant respectively changing weather. Moreover there are default implementations for all Operators of the of the BDI-architecture.

* *com.github.kreatures.island.operators.IslandOptionsOperator*
* *com.github.kreatures.island.operators.IslandFilterOperator*
* *com.github.kreatures.island.operators.IslandPlanningOperator*

To make your own scenario related to the island, the easiest way is to inherit from single classes and override methods as desired.
Then manifest the new created classes within a new Plugin and configure the the changes with the *asml*-script.

## Install Prerequisites for Development

First of all we need to install and configure following list of software:
* Java JDK 1.8 or higher - Should already be installed on most systems. If this is not the case you can download it from the [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html) website
* Git client - To get access to KReaturesFW repository, you will need a git client. For Windows users we recommend using [Git for Windows](https://git-for-windows.github.io/) or [Tortoise Git](https://tortoisegit.org/). In case you are not familiar with git, have a look at [Git-scm](https://git-scm.com/documentation) or [Atlassian](https://www.atlassian.com/git/tutorials) for further information.
* Eclipse Mars or higher - You will need a Java IDE. We recommend to use Eclipse since most code was developed and tested this way. If it's not already installed on your computer, get it from the [Eclipse](https://eclipse.org/home/index.php) website.
* Maven 3.3 or higher - Maven is already integrated in current versions of Eclipse Bundles, so nothing to do! If you insist using an external tool, check out the [Apache Maven](https://maven.apache.org/download.cgi) webside and follow the instructions.
* ASP-Solver - to execute some scenarios, you will need an ASP-Solver. Get a suitable version for your operation system from [DLV](http://www.dlvsystem.com/dlv/).

## Fetch the KReatures Repository

After installing the software we need to checkout the public git repository of KReatures.
Create a new workspace folder and navigate into, then execute the command:
git clone https://github.com/KReaturesFW/kreatures-framework.git

Since the repository is public, you won't need to enter your github credentials.
However we recomend to include your username in the repository-url like:
https://username@github.com/KReaturesFW/kreatures-framework.git
so you won't be asked each time you want to push you changes.

## Getting started

* Open Eclipse and navigate to your workspace.
* Open the import menu via "File -> Import" or rightclick within the package explorer and click on "Import".
* Choose "Maven -> Existing Maven Projects" and then "Browse" where you native to the KReatures project within your workspace.
* A list of projects should appear. Click on "Finish". Now after a while all KReatures projects should be imported in Eclipse.
* Right click on "kreatures-framework" and Natigate to "Run as -> Maven install". That makes sure, all plugins are compiled.
* Open the project "app" and navigate to the "target/tools/asp-solver" and copy the ASP-Solver into. If needed, rename the executable to it to "dlv"
* Right click on "com.github.kreatures.app.GUIApplication" and navigate to "Run as/Run configurations" and Choose "Java application" in the list on the left. Click on the tab "Arguments" and change the working directory to "app/target", then click run.
* The Simulation-app should open and you can choose a simulation, for example double click on "island/Static island" and then press the button "init" to execute.
* Click on "Run" to perform on tick or "Complete" to run the whole simulation.