 %##################### Erfüllbarkeit von Necessity ###################################
%NecAgentStation(AgentName,StationName,Nec). 
%Nec=Min(NecAgent,NecStation) zeigt, wieviel oft die Station <s> vom Agent <a> besucht wurde.

NecErfull(AgentName,StationName):-NecAgentStation(AgentName,StationName,Nec),FctSup(AgentName,StationName,MinNec),Nec>=MinNec.
%FctSup(AgentName,StationName,min(NecStation,NecAgent))
FctSup(AgentName,StationName,NecStation):-Station(StationName,StationTypeName,_,_,_),Agent(AgentName,AgentTypeName,_,_,_),StationType(StationTypeName,_,NecStation,_,_,_,_,_,_),AgentType(AgentTypeName,_,NecAgent,_,_,_,_,_,_),NecStation<NecAgent.
FctSup(AgentName,StationName,NecAgent):-Station(StationName,StationTypeName,_,_,_),Agent(AgentName,AgentTypeName,_,_,_),StationType(StationTypeName,_,NecStation,_,_,_,_,_,_),AgentType(AgentTypeName,_,NecAgent,_,_,_,_,_,_),NecAgent<=NecStation.
%##################### Erfüllbarkeit von Priority ###################################
%Pour un agent donné (Raison pour laquelle il faut un seul agent et non tous, sinon ca ne fonctionne pas.), rechercher toutes les stations qui lui sont connectés et determinés laquelle à la plus grande priorité. 
MaxPriorityStation(AgentName,StationName,Prio):-AllVisitEdgeStation(AgentName,StationName,Prio),#max{UsePrio: AllVisitEdgeStation(AgentName,_,UsePrio)}=Prio.
%Pour un agent donné, rechercher toutes les stations qui lui sont connectés et dont on peut encore y acceder.
AllVisitEdgeStation(AgentName,StationName,Prio):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),StationType(StationTypeName,_,_,_,Prio,_,_,Space,_),Station(StationName,StationTypeName,_,_,StationSpace),Space>StationSpace.

%Pour une station donné, rechercher toutes les agents qui lui sont connectés et determinés lequelle à la plus grande priorité. 
MaxPriorityAgent(AgentName,StationName,Prio):-AllVisitEdgeAgent(AgentName,StationName,Prio),#max{UsePrio: AllVisitEdgeAgent(_,StationName,UsePrio)}=Prio.
%Pour un agent donné, rechercher toutes les stations qui lui sont connectés 
AllVisitEdgeAgent(AgentName,StationName,Prio):-Agent(AgentName,AgentTypeName,_,_,_),AgentType(AgentTypeName,_,_,_,Prio,_,_,_,_),AllVisitEdgeStation(AgentName,StationName,_).

%Gibt genau visitEdge(agentType und stationType) mit höhe priority.
MaxPriority(AgentTypeName,StationTypeName):-MaxPriorityStation(AgentTypeName,StationTypeName,_),MaxPriorityAgent(AgentTypeName,StationTypeName,_).
%##################### Erfüllbarkeit von Frequency ###################################
FreqErfullStation(StationName):-Station(StationName,StationTypeName,FreqStation,_,_),StationType(StationTypeName,Freq,_,_,_,_,_,_,_),FreqStation>Freq.
FreqErfullAgent(AgentName):-Agent(AgentName,AgentTypeName,FreqAgent,_,_),AgentType(AgentTypeName,Freq,_,_,_,_,_,_,_),FreqAgent>Freq.
%################################# Erfüllbarkeit von Capacity und Item ###################################################################
%+++++++++++++++++++++++++++++++++++++++++++++++++++++Item+++++++++++++++++++++++++++++++++++++++++++
%Eine Station <s> hat das Attribut Item, wenn es eine ausgehende Kante gibt.
%Und Sie kann Items erzeugen, entwerde wenn es keine eingehende Kante gibt,
% oder wenn sie alle Items aus allen Stationen bekommen hat.
%Eingehende ist ----><S> und Ausgehende ist <S>------>
%%%%%%%%%%%%%%%%%%%%% Alle Stationen ohne ausgehenden Kanten.
ItemErfullNoAus(StationName,StationTypeName):-Station(StationName,StationTypeName,_,_,_),#count{X:PlacedEdge(StationName,StationTypeName,_,_,X,true)}=0.
%%%%%%%%%%%%%%%%%%%%%% Alle Stationen ohne eingehenden Kanten.
ItemErfullNoEin(StationName,StationTypeName):-Station(StationName,StationTypeName,_,_,_),#count{X:PlacedEdge(_,_,StationName,StationTypeName,X,true)}=0. 
%Siehe Item beschreibung.
%ItemBeladen(AgentName,StationName,true). where true means that the agent can take more items and false that agent can take one item.
%True means there are no prerequis for that agent can take items.
%False means there are prerequis for that agent can take items.
%+++Gibt zurück, alle Stationen, auf der Agenten unendliche Item laden können.
ItemBeladen(StationName,StationTypeName):-ItemErfullNoEin(StationName,_),PlacedEdge(StationName,StationTypeName,_,_,_,true).
%+++++Dies prüft, ob ein Agent noch genug Platz hat, um Items von Station s zu nehmen.
canAgentLoadItem(AgentName,StationName):-AgentType(AgentTypeName,_,_,_,_,_,AgentCap,_,_),Agent(AgentName,AgentTypeName,_,_,Cap),StationType(StationTypeName,_,_,_,_,_,Item,_,_),Station(StationName,StationTypeName,_,_,_),Z=Cap+Item,Z<=AgentCap. 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%When there are incoming edge.
%ItemSetLoadingAgent(AgentName,StationTypeName,ItemNumber). Diese gibt an, wie viele Items von dieser StationType der Agent trägt.

%ItemSetLoadingStation(StationTypeNameIn,StationNameOut,ItemNumber).<s_T_in=StationTypeNameIn> -----> <s_out=StationNameOut>: Diese gibt an, wie viele Items von <s_T_in> in <s_out> beladen wurden.
%Dies ist bestandteil des Agenten's Wissens und Alle müssen am Anfang initialisiert werden.

%Wie viele Items können in einer Station mit ausgehender Kante produktiert werden, wird in Programm berechnet.
%ItemProductInStation(StationNameOut). Gibt zurück, alle Stationen die bereit ein Item zu erzeugen sind.
ItemProductInStation(StationNameOut):-ItemSetLoadingStation(_,StationNameOut,_),#count{StationTypeNameIn:ItemSetLoadingStation(StationTypeNameIn,StationNameOut,ItemNumber),StationType(StationTypeNameIn,_,_,_,_,_,_,_,Count),Count>ItemNumber}=0. %Hilfsvariable
%+++Gibt zurück, alle Stationen die bereit ein Item zu erzeugen sind und wie viele Items in dieser Station produktiert werden können.
%NumberItemToProductInStation(StationNameOut,Number): StationNameOut= station, die Item produktieren kann und Number=Menge von produktierbaren Items.
NumberItemToProductInStation(StationNameOut,Number):-ItemProductInStation(StationNameOut),Number=#min{X:ItemSetLoadingStation(StationTypeNameIn,StationNameOut,ItemNumber),StationType(StationTypeNameIn,_,_,_,_,_,_,_,Count),X=ItemNumber/Count}.

%################################################## TimeEdge eigenschaft ############################################


%+++++++++++++++++++++++++++ Hierunter sind alle timeEdgeWaiting: D.h: Die varaible Iswaiting kann auf true gesetzt werden.+++++++++++++++++++
%TimeEdgeNoconnectedNoIncomingReady(Name,TypeName,Type). 
%Gibt alle Komponenten ohne incoming Kanten zurück, die keine <Isconnected=true und nicht directed> timeEdge hat.
TimeEdgeNoconnectedNoIncomingReady(Name,TypeName,Type):- TimeEdgeAll(Name,TypeName,Type), #count{X: NoTimeEdge(X,TypeName,_)}=0,#count{Y:TimeEdgeOnlyOutgoing(Name,TypeName,Y,_)}=0,#count{Z:TimeEdge(Z,_,Name,TypeName,_,true,_,_)}=0,#count{K:TimeEdge(K,_,Name,TypeName,_,false,true,_)}=0,#count{H:TimeEdgeOutIn(H,_,Name,TypeName,_,true,both,_)}=0.

%TimeEdgeConnectedNoIcomingReady(Name,TypeName,Type). 
%Gibt alle Komponenten ohne incoming Kanten zurück, die mindesten eine <Isconnected=true und nicht directed> timeEdge hat.
TimeEdgeConnectedNoIcomingReady(Name,TypeName,Type):- TimeEdgeAll(Name,TypeName,Type), #count{X: NoTimeEdge(X,TypeName,_)}=0,#count{Y:TimeEdge(Y,_,Name,TypeName,_,true,_,_)}=0,#count{Z:TimeEdgeOutInAnd(Z,_,Name,TypeName,_,false,true,_)}>0.

%TimeEdgeAllIncomingReady(NameIn,TypeNameIn,WeightMin,Type)
%Gibt alle incoming Komponenten zurück, auf der ein Agent die Variable IsWaiting auf true setzen darf und auf andere Agenten warten kann.
% if there are only connected Komponentes. Also all edges have to be directed.
TimeEdgeIncomingReady(NameIn,TypeNameIn,WeightMin,Type):-#count{X:TimeEdge(X,_,StationNameIn,StationTypeNameIn,_,true,false,_)}=0,TimeEdgeAllLogicalIncomingReady(NameIn,TypeNameIn,WeightMin,Type).
% This triggers if there are only noconnected Komponentes. Also all edges have to be directed.
TimeEdgeIncomingReady(NameIn,TypeNameIn,WeightMin,Type):-#count{X:TimeEdge(X,_,NameIn,TypeNameIn,_,true,true,_)}=0,#count{Y:TimeEdge(Name1,TypeName1,NameIn,TypeNameIn,Y,true,false,Weight),TimeEdgeStatus(Name1,TypeName1,_,_,_,EchtZeit,false,true,_),EchtZeit>=Weight}>0,TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).
% This triggers if there are connected and noconnected Komponentes. Also all edges have to be directed.
TimeEdgeIncomingReady(NameIn,TypeNameIn,WeightMin,Type):-TimeEdgeAllLogicalIncomingReady(NameIn,TypeNameIn,WeightMin,Type),#count{Y:TimeEdge(Name1,TypeName1,NameIn,TypeNameIn,Y,true,false,Weight),TimeEdgeStatus(Name1,TypeName1,_,_,_,EchtZeit,false,true,_),EchtZeit>=Weight}>0.


%TimeEdgeNoCondition(StationName,StationTypeName,Echtzeit,Type).  Type=0 is Station and type=1 is agent.
%Gibt alle Komponenten zurück, die keine eingehenden Kanten haben und keine logical Konnection besitzen.
TimeEdgeNoCondition(Name,TypeName,Echtzeit,Type):- TimeEdgeAll(Name,TypeName,Type),TimeEdge(Name,TypeName,_,_,_,true,_,Echtzeit),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,_,_)}=0.  %Hilfsvariable

%TimeEdgeStatus(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,Type,CountTick,IsWaiting,IsReady,IsFinish). 
%When type=0 then NameWithTimeEdge is a station, NameVisited is an agent. And when type =1 then NameWithTimeEdge is an agent and NameVisited is a station
%AgentName/AgentTypeName ist der Agent, der StationName ausgewählt hat oder nothing bei keiner Auswahl.
%CountTime wird auf eine Tick erhöht, wenn ein Agent eine Station auswählt und eine bestinnte Zeit warten muss.
%IsWaiting auf true und gibt an, ob sich ein Agent in der StationName gemeldet hat und wartet auf eine Rückmeldung von einem Agent. Es setzt IsFinish auf false. Die Veränderung benötigt die folgenden Strategien(Regeln) in Wissensbasis.
%IsReady auf true bedeutet CountTick>0 und bestätigt, dass timeEdge erfüllt ist und informiert den entsprechenden Agent.Es setzt IsWaiting auf false. Dieser Wert wird von einem oder dem Agent verändert, wenn er eine oder die Station betritt.
%IsFinish auf true bedeutet die erfüllte Bedingung wurde angewendet. Es setzt IsReady auf false und  und CountTick auf 0. Es setzt IsWaiting auf false. Dieser Wert wird von einem oder dem Agent verändert, wenn er eine oder die Station verlässt.

%TimeEdge(Name1,TypeName1,Name2,TypeName2,TimeType,IsDirected,ConnectionType,weight) for remember
%+++++++++++++++++++++ Hierunter sind alle timeEdgeGo: D.h: Die varaible IsWaiting kann auf true gesetzt werden +++++++++++++++++++++++
% <An>------&-<B>: Wenn alle An in waiting Zustand ist, dann kann B auf waiting Zustand gesetzt werden.
%+++++ Hier weiter: Als nächste create regeln: for NoDC and NoDNoC
%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Ready verfahren &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


%+++++++++++++++++++++++++++++++++++++++++++  IsReady allgemein ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type).

%Nur DirectedConnected and NoDirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and NoDirectedConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected  and DirectedNoConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur DirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected  and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur NoDirectedBothConneted and DirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur DirectedConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur NoDirectedBothConneted and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedBothConneted and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedConnected and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,true,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

% nur outgoing
TimeEdgeReady(Name,TypeName,Type):-TimeEdgeOnlyOutgoing(Name,TypeName,_,Type).


%+++++++++++++++++ NoDirectedNoConnected and NoDirectedNoConnected: IsReady auf true setzen, wenn erfüllt+++++++++++++++++++++++++++++

%Gibt alle noConnected Komponenten zurück, deren correspondings Komponent <mindesten ein IsWaiting or IsReady auf true> ist.
TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,WeightMin,Type):-#count{X:TimeEdgeStatusNoDirectedNoConnectedReady(X,TypeName1,Name,TypeName,WeightMin,Type)}>0,TimeEdgeNoDirectedNoConnectedWeightMin(Name,TypeName,WeightMin,Type).

%Gibt alle nodirected and bothConnected Komponenten zurück, die ihr corresponding Komponent ihr IsReady auf true ist.
TimeEdgeStatusNoDirectedNoConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,Echtzeit,true,false,_),TimeEdgeOutIn(Name1,TypeName1,Name2,TypeName2,_,false,false,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,Echtzeit,false,true,_),WeightMin<=Echtzeit. %Hilfsvariable
%Gibt alle nodirected and noConnected Komponenten zurück, die ihr corresponding Komponent ihr IsWaiting auf true ist.
TimeEdgeStatusNoDirectedNoConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedNoConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,Echtzeit,true,false,_),TimeEdgeOutIn(Name1,TypeName1,Name2,TypeName2,_,false,false,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,Echtzeit,true,false,_). %Hilfsvariable

%+++++++++++++++++ Outgoing and incomingNoConnected: IsReady auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++

%Gibt alle incoming Komponenten deren outgoing Komponenten ihrer Isconnected=false und mindesten ein ihrer IsReady=true zurück.
%mindesten eine <out,IsConnected=false,IsReady=true> --> <In>
TimeEdgeDirectedNoConnectedReady(NameIn,TypeNameIn,WeightMin,Type):- #count{X:TimeEdgeOutgoingReady(X,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type)}>0,TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).

%Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
TimeEdgeStatusDirectedOnConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,Echtzeit,false,true,_),WeightMin<=Echtzeit,TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,false,_). %Hilfsvariable

%+++++++++++++++++ NoDirectedBothConnected and NoDirectedBothConnected: IsReady auf true setzen, wenn erfüllt+++++++++++++++++++++++++++++

%Gibt alle Komponenten of TimeEdgeNoDirectedBothConnected(...) zurück, deren timeEdge <IsConnect=both> ist.
TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,WeightMin,Type):-#count{X:TimeEdgeNoDirectedBothConnectedNoReady(X,TypeName1,Name,TypeName,WeightMin,Type)}=0,TimeEdgeNoDirectedBothConnected(Name1,TypeName1,Name,TypeName,WeightMin,Type).

%Gibt alle coresponding Komponenten of TimeEdgeNoDirectedConnectedReady(...) zurück, deren <IsWaiting=false and IsReady=false>  timeEdge ist.
TimeEdgeNoDirectedBothConnectedNoReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):- #count{X:TimeEdgeStatusNoDirectedBothConnectedReady(X,TypeName1,_,_,_,_)}=0,TimeEdgeNoDirectedBothConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type). %Hilfsvariable

%Gibt alle nodirected and bothConnected Komponenten zurück, die ihr corresponding Komponent ihr IsReady auf true ist.
TimeEdgeStatusNoDirectedBothConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,Echtzeit,true,false,_),TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,both,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,Echtzeit,false,true,_),WeightMin<=Echtzeit. %Hilfsvariable
%Gibt alle nodirected and bothConnected Komponenten zurück, die ihr corresponding Komponent ihr IsWaiting auf true ist.
TimeEdgeStatusNoDirectedBothConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,Echtzeit,true,false,_),TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,both,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,Echtzeit,true,false,_). %Hilfsvariable

%+++++++++++++++++ NoDirectedConnected and NoDirectedNoConnected: IsReady auf true setzen, wenn erfüllt+++++++++++++++++++++++++++++

%Gibt alle Komponenten of TimeEdgeNoDirectedOneConnected(...) zurück, deren correspndings komponenten mindesten eine Iswaiting=true ist.
TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,WeightMin,Type):- #count{X:TimeEdgeNoDirectedOneConnectedNoReady(X,TypeName1,Name,TypeName,WeightMin,Type)}=0,TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name,TypeName,WeightMin,Type).

%Gibt alle coresponding Komponenten of TimeEdgeNoDirectedConnectedReady(...) zurück, deren <IsWaiting=false>  timeEdge ist.
TimeEdgeNoDirectedOneConnectedNoReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):- #count{X:TimeEdgeStatusNoDirectedWaiting(X,TypeName1,_,_,_,_)}=0,TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type). %Hilfsvariable

%TimeEdgeStatusNoDirectedWaiting(Name1,TypeName1,Name2,TypeName2,WeightMin,Type)
%Gibt alle nodirected Komponenten zurück, die ihr ISready auf true ist.
TimeEdgeStatusNoDirectedConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,Echtzeit,true,false,_),TimeEdge(Name1,TypeName1,Name2,TypeName2,_,false,true,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,Echtzeit,false,true,_),WeightMin<=Echtzeit. %Hilfsvariable

%+++++++++++++++++ Outgoing and incomingConnected: IsReady auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++
%TimeEdgeAllLogicalIncomingReady(NameIn,TypeNameIn,WeightMin,Type)
%Gibt alle incoming Komponenten deren outgoing Komponenten ihrer Isconnected=true und ihrer IsReady=true zurück.
%alle <out,IsConnected=true,IsReady=true> --> <In>
TimeEdgeDirectedConnectedReady(NameIn,TypeNameIn,WeightMin,Type):- #count{X:TimeEdgeOutgoingReady(X,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type)}=0,TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).

%TimeEdgeOutgoingReady(StationNameOut,StationTypeNameOut,StationNameIn,StationTypeNameIn,EchtzeitMin,Type). Dies ist eine Hilfsvariable.
%Gibt alle incoming StationTypen mit IsReady=false und Isconnected=true: <out> ---->  <In>
TimeEdgeOutgoingReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):-TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type), TimeEdgeAll(NameOut,TypeNameOut,_),#count{X:TimeEdge(X,TypeNameOut,NameIn,TypeNameIn,_,true,true,_)}>0,#count{Y:TimeEdgeStatusDirectedConnectedReady(Y,TypeNameOut,_,_,_,_)}=0. %Hilfsvariable
%TimeEdgeStatusDirectedConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type): Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
TimeEdgeStatusDirectedConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,Echtzeit,false,true,_),WeightMin<=Echtzeit,TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,true,_). %Hilfsvariable


%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Waiting verfahren &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

%+++++++++++++++++++++++++++++++++++++++++++  IsWaiting allgemein ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type).

%Nur DirectedConnected and NoDirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and NoDirectedConnected and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected  and DirectedNoConnected and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnectedWaiting and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur DirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected  and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur NoDirectedBothConneted and DirectedNoConnected and NoDirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur DirectedConnected and NoDirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur NoDirectedBothConneted and NoDirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedBothConneted and DirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedConnected and DirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedBothConneted
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedNoConnected
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

% nur outgoing
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeOnlyOutgoing(Name,TypeName,_,Type).




%+++++++++++++++++ Outgoing and incomingNoConnected: IsWaiting auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++

%Gibt alle Komponenten of incomingNoConnected Komponente zurück., deren correspndings komponenten mindesten eine IsConnected=false ist.
TimeEdgeDirectedNoConnectedWaiting(NameIn,TypeNameIn,WeightMin,Type):-TimeEdge(_,_,NameIn,TypeNameIn,Y,true,false,Weight),TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).


%+++++++++++++++++ Only Outgoing: IsWaiting and IsReady auf true setzen, wenn Komponent ausgewählt.+++++++++++++++++++++++++++++

%TimeEdgeOnlyOutgoing(StationName,StationTypeName,Echtzeit,Type).  Type=0 is Station and type=1 is agent.
%Gibt alle Komponenten nur mit ausgehenden Kanten zurück. Diese sind ohne Bedingung anzuschalten und Vorraussetzung für die anderen Stationen.
TimeEdgeOnlyOutgoing(Name,TypeName,Echtzeit,Type):- TimeEdgeAll(Name,TypeName,Type),TimeEdge(Name,TypeName,_,_,_,true,_,Echtzeit),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,_,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%+++++++++++++++++ NoDirectedNoConnected and NoDirectedNoConnected: IsWaiting auf true setzen, wenn erfüllt+++++++++++++++++++++++++++++

%Gibt alle Komponenten zurück, die mindesten eine <Isconnected=false and no directed>  timeEdge hat.
TimeEdgeNoDirectedNoConnectedWaiting(Name,TypeName,WeightMin,Type):- TimeEdgeNoDirectedNoConnectedWeightMin(Name,TypeName,WeightMin,Type). 

%+++++++++++++++++ NoDirectedBothConnected and NoDirectedBothConnected: IsWaiting auf true setzen, wenn erfüllt+++++++++++++++++++++++++++++

%Gibt alle Komponenten of TimeEdgeNoDirectedBothConnected(...) zurück, deren timeEdge <IsConnect=both> ist.
TimeEdgeNoDirectedBothConnectedWaiting(Name,TypeName,WeightMin,Type):-TimeEdgeNoDirectedBothConnected(_,_,Name,TypeName,WeightMin,Type). 

%Gibt alle coresponding Komponenten of TimeEdgeNoDirectedConnected(...) zurück, deren <Isconnected=both>  timeEdge ist.
TimeEdgeNoDirectedBothConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):- TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,both,_),TimeEdgeNoDirectedConnected(Name2,TypeName2,WeightMin,Type). %Hilfsvariable

%+++++++++++++++++ NoDirectedConnected and NoDirectedNoConnected: IsWaiting auf true setzen, wenn erfüllt+++++++++++++++++++++++++++++
%Gibt alle Komponenten of TimeEdgeNoDirectedOneConnected(...) zurück, deren correspndings komponenten mindesten eine Iswaiting=true ist.
TimeEdgeNoDirectedOneConnectedWaiting(Name,TypeName,WeightMin,Type):- #count{X:TimeEdgeNoDirectedOneConnectedNoWaiting(X,TypeName1,Name,TypeName,WeightMin,Type)}=0,TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name,TypeName,WeightMin,Type).

%Gibt alle coresponding Komponenten of TimeEdgeNoDirectedConnected(...) zurück, deren <IsWaiting=false>  timeEdge ist.
TimeEdgeNoDirectedOneConnectedNoWaiting(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):- #count{X:TimeEdgeStatusNoDirectedConnectedWaiting(X,TypeName1,_,_,_,_)}=0,TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type). %Hilfsvariable

%TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type). 
%Gibt alle coresponding Komponenten of TimeEdgeNoDirectedConnected(...) zurück, deren <Isconnected=true>  timeEdge ist.
TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):- TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,true,_),TimeEdgeNoDirectedConnected(Name2,TypeName2,WeightMin,Type). %Hilfsvariable

%TimeEdgeStatusNoDirectedConnectedWaiting(Name1,TypeName1,Name2,TypeName2,WeightMin,Type)
%Gibt alle nodirected Komponenten zurück, die ihr IsWaiting auf true ist.
TimeEdgeStatusNoDirectedConnectedWaiting(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name1,TypeName1,_,_,_,Echtzeit,true,false,_),TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,_,_). %Hilfsvariable

%+++++++++++++++++ Outgoing and incomingConnected: IsWaiting auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++

%TimeEdgeAllLogicalIncomingReady(NameIn,TypeNameIn,WeightMin,Type)
%Gibt alle incoming Komponenten deren outgoing Komponenten ihrer Isconnected=true und ihrer IsReady=true zurück.
%alle <out,IsConnected=true,IsReady=true> --> <In>
TimeEdgeDirectedConnectedWaiting(NameIn,TypeNameIn,WeightMin,Type):- #count{X:TimeEdgeOutgoingWaiting(X,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type)}=0,TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).

%TimeEdgeOutgoing(StationNameOut,StationTypeNameOut,StationNameIn,StationTypeNameIn,EchtzeitMin,Type). Dies ist eine Hilfsvariable.
%Gibt alle incoming StationTypen mit IsReady=false und Isconnected=true: <out> ---->  <In>
TimeEdgeOutgoingWaiting(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):-TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type), TimeEdgeAll(NameOut,TypeNameOut,_),#count{X:TimeEdge(X,TypeNameOut,NameIn,TypeNameIn,_,true,true,_)}>0,#count{Y:TimeEdgeStatusDirectedConnectedWaitingOrReady(Y,TypeNameOut,_,_,_,_)}=0. %Hilfsvariable
%TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type): Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,Echtzeit,false,true,_),WeightMin<=Echtzeit,TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,true,_). %Hilfsvariable
%TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type): Gibt alle Komponenten zurück, die ihr IsWaiting auf true ist.
TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,Echtzeit,true,false,_),TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,true,_).


%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& END Waiting verfahren &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
%+++++++++++++++++++++++++++ Alle NoDirectedConnected oder NoDriectedBothConnected Komponenten. +++++++++++++++++++++
 
%Gibt alle Komponenten zurück, die mindesten eine <Isconnected=true or both>  timeEdge hat.
TimeEdgeNoDirectedConnected(Name,TypeName,WeightMin,Type):- #count{Y:TimeEdgeOutInAnd(Y,_,Name,TypeName,_,false,_,_)}>0,TimeEdgeNoDirectedConnectedWeightMin(Name,TypeName,WeightMin,Type). %Hilfsvariable

%++++++++++++++++++++++++++++++++++ No timeedge komponent ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%NoTimeEdgeForStation(Name,TypeName) : Name und TypeName sind AgentName und AgentTypeName bzw. StationName und StationTypeName.
%Gibt  alle Stationen zurück, die keine timeEdge haben.
NoTimeEdgeForStation(StationName,StationTypeName):- NoTimeEdge(StationName,StationTypeName,0).
%Gibt  alle Agenten zurück, die keine timeEdge haben.
NoTimeEdgeForAgent(AgentName,AgentTypeName):- NoTimeEdge(AgentName,AgentTypeName,1).
%Gibt  alle Agenten und Stationen zurück, die keine timeEdge haben.
NoTimeEdge(Name,TypeName,Type):-TimeEdgeAll(Name,TypeName,Type),#count{X:TimeEdge(Name,TypeName,X,_,_,_,_,_)}=0,#count{Y:TimeEdge(Y,_,Name,TypeName,_,_,_,_)}=0.
%+++++++++++++++++++++++++++++++++ Weight Min ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
%TimeEdgeDirectedConnectedWeightMin(Name,TypeName,WeightMin,Type): Type is either station or agent
%Return all incoming komponents with its corresponding minimal weight. only connected edge are importance.
TimeEdgeDirectedConnectedWeightMin(Name,TypeName,WeightMin,Type):-#min{EchtzeitX:TimeEdge(_,_,Name,TypeName,_,true,true,EchtzeitX)}=WeightMin,TimeEdgeAll(Name,TypeName,Type).
%TimeEdgeNoDirectedConnectedWeightMin(Name,TypeName,WeightMin,Type): Type is either station or agent
%Return all komponents with its corresponding minimal weight. only connected edge are importance.
TimeEdgeNoDirectedConnectedWeightMin(Name,TypeName,WeightMin,Type):-#min{EchtzeitX:TimeEdgeOutInAnd(_,_,Name,TypeName,_,false,_,EchtzeitX)}=WeightMin,TimeEdgeAll(Name,TypeName,Type).
%TimeEdgeNoDirectedNoConnectedWeightMin(Name,TypeName,WeightMin,Type): Type is either station or agent
%Return all komponents with its corresponding weight. only noconnected edge are importance.
TimeEdgeNoDirectedNoConnectedWeightMin(Name,TypeName,WeightMin,Type):-TimeEdgeOutIn(_,_,Name,TypeName,_,false,false,Echtzeit),WeightMin=Echtzeit+0,TimeEdgeAll(Name,TypeName,Type).

%TimeEdgeDirectedNoConnectedWeightMin(Name,TypeName,WeightMin,Type): Type is either station or agent
%Return all incoming komponents with its corresponding weight. only noconnected edge are importance.
TimeEdgeDirectedNoConnectedWeightMin(Name,TypeName,WeightMin,Type):-TimeEdge(_,_,Name,TypeName,_,true,false,Echtzeit),WeightMin=Echtzeit+0,TimeEdgeAll(Name,TypeName,Type).

%++++++++++++++++++++++ timeEdge nodirected both direction ++++++++++++++++++++++++++++++

TimeEdgeOutIn(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,Y,Z):-TimeEdge(NameIn,TypeNameIn,NameOut,TypeNameOut,X,false,Y,Z),Y!=true.
TimeEdgeOutIn(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,Y,Z):-TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,Y,Z),Y!=true.

%++++++++++++++++++++++ timeEdge nodirected but with logical And. 

TimeEdgeOutInAnd(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,both,Z):-TimeEdgeOutIn(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,both,Z).
TimeEdgeOutInAnd(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,true,Z):-TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,true,Z).

%+++++++++++++++++++++++++++++++Return all stations and agents. Type=0 is Station and type=1 is agent. ++++++++++++++++++++
TimeEdgeAll(Name,TypeName,0):-Station(Name,TypeName,_,_,_).
TimeEdgeAll(Name,TypeName,1):-Agent(Name,TypeName,_,_,_).
%+++++++++++++++++++++++++++ from TimeEdgeState, TimeEdgeStatus will be genered . +++++++++++++++
%TimeEdgeStatus(Name1,TypeName1,Name2,TypeName2,Type,TimeUnit,IsWaiting,IsReady,IsFinish). convert the CountTick to TimeUnit.
TimeEdgeStatus(Name1,TypeName1,Name2,TypeName2,Type,TimeUnit,IsWaiting,IsReady,IsFinish):-TimeEdgeState(Name1,TypeName1,Name2,TypeName2,Type,CountTime,IsWaiting,IsReady,IsFinish),ZeitEinheit(Unit),TimeUnit=CountTime/Unit.
%+++++++++++++++++++++++++++ from TimeEdgeStatus, TimeEdgeAgent and TimeEdgeStation will be genered  +++++++++++++++
%++ The agent has the time edge and it is located at station.
TimeEdgeAgent(StationName,StationTypeName,AgentName,AgentTypeName,CountTime,IsWaiting,IsReady,IsFinish):-TimeEdgeStatus(AgentName,AgentTypeName,StationName,StationTypeName,1,CountTime,IsWaiting,IsReady,IsFinish).
%++ The station has the time edge and agent is visiting it. 
TimeEdgeStation(StationName,StationTypeName,AgentName,AgentTypeName,CountTime,IsWaiting,IsReady,IsFinish):-TimeEdgeStatus(StationName,StationTypeName,AgentName,AgentTypeName,0,CountTime,IsWaiting,IsReady,IsFinish).
