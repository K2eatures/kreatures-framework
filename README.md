
#KReatures Framework

The KReatures framework (Knowledge Representation for Epistemic Agents with Timed Unified Resources) is a framework for the implementation of knowledge-based agents with a strong focus on flexibility, extensibility and compatibility with diverse knowledge representation formalisms.
In addition, it will be able to represent spatio-temporal agent scenarios in a unified way such that agents can be used in different kinds of scenarios without changing their implementation (e.g. to evaluate their adaptivity and generality across multiple scenarios).
Its basis is a flexible Java plug-in architecture for the mental state of an agent as well as for the agent's functional component.
Different knowledge representation formalisms can be used within one agent and different agents in the same system can be based on different agent architectures and can use different knowledge representation formalisms.
Partially instantiated plug-ins define sub-frameworks for, e.g., the development of BDI agents and variants thereof.
The knowledge representation plug-ins are based on the [Tweety library](http://tweetyproject.org/) for knowledge representation, which provides various ready-for-use implementations and knowledge representation formalisms and a framework for the implementation of additional ones.
KReatures already contains several partial and complete instantiations that implement several approaches from the literature. 
KReatures also features an environment plug-in for communicating agents and a flexible GUI to monitor the simulation and the agents, particularly including the inspection of the dynamics of their mental states.


##Features of the current Version

The KReatures agent architecture can be freely defined by specifying the types of operators to be used and their order of execution.
This way KReatures allows to easily design different types of agents.
Not only the used language for knowledge representation can differ, but also to which amount an agent's functionality is logic based. It is, for instance, easily possible to realize the agent's deliberation and means-ends reasoning by Java operators and simple data components, or by simple Java operators which make use of logical formalisms, e.g. answer set programming (ASP), ordinal conditional functions (OCF), argumentation formalisms, or propositional logic or horn logic, or any other formalism from the Tweety library.

While the general KReatures framework allows for a high degree of flexibility it also allows to define partially instantiated plug-ins and default agent configurations which represent sub-frameworks with more predefined structure and functionality.
The latter might fix the general agent cycle by specifying the types of operators to be used and provide different implementations for these.
Hence, the sub-frameworks provide more support for easy and rapid development of agents.
We distinguish three different types of users in the KReatures framework.
The *core developer* that uses KReatures as a toolkit to define its own agent types.
The *plug-in developer* that uses provided agent types and instantiates them with given or its own plug-ins.
And the *knowledge engineer* that defines the background and initial knowledge, and all other initial instances of the components of the agents.

KReatures provides default implementations for BDI style (and other) agents and diverse extensions that can be modularly used to build agents.
Complete multiagent systems of communicating agents using answer set programming, propositional logic and ordinal conditional functions for knowledge representation, including change operators for these based on belief change theory are implemented and available.

KReatures also features a plug-in interface for different environments, with a communication environment for communicating agents implemented.

A graphical user interface (GUI) allows the selection, execution, observation, and inspection of multi-agent simulations.
The GUI can be extended by plug-ins to feature displays of specific knowledge representation formalisms, for instance dependency graphs.
