% ##################### Environment Features ##############################

 %##################### Erfüllbarkeit von Necessity ###################################
%NecAgentStation(AgentName,StationName,Nec). 
%Nec=Min(NecAgent,NecStation) zeigt, wieviel oft die Station <s> vom Agent <a> besucht wurde.

NecErfull(AgentName,StationName):-NecAgentStation(AgentName,StationName,Nec),FctSup(AgentName,StationName,MinNec) ,Nec<MinNec.
%FctSup(AgentName,StationName,min(NecStation,NecAgent))
FctSup(AgentName,StationName,NecStation):-Station(StationName,StationTypeName,_,_,_),Agent(AgentName,AgentTypeName,_,_,_),StationType(StationTypeName,_,NecStation,_,_,_,_,_,_),AgentType(AgentTypeName,_,NecAgent,_,_,_,_,_,_),VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),NecStation<NecAgent.
FctSup(AgentName,StationName,NecAgent):-Station(StationName,StationTypeName,_,_,_),Agent(AgentName,AgentTypeName,_,_,_),StationType(StationTypeName,_,NecStation,_,_,_,_,_,_),AgentType(AgentTypeName,_,NecAgent,_,_,_,_,_,_),VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),NecAgent<=NecStation.
%##################### Erfüllbarkeit von Space und Size ###################################
SpaceSizeErfull(AgentName,AgentTypeName,StationName,StationTypeName):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),AgentType(AgentTypeName,_,_,_,_,_,_,Size,_),StationType(StationTypeName,_,_,_,_,_,_,SpaceStationType,_),Station(StationName,StationTypeName,_,_,SpaceStation),NewSpace=SpaceStation+Size,SpaceStationType>=NewSpace.

%##################### Erfüllbarkeit von Priority ###################################
%Pour un agent donné (Raison pour laquelle il faut un seul agent et non tous, sinon ca ne fonctionne pas.), rechercher toutes les stations qui lui sont connectés et determinés laquelle à la plus grande priorité. 
MaxPriorityStation(AgentName,StationName,Prio):-AllFreeStation(AgentName,StationName,Prio),#max{UsePrio: AllFreeStation(AgentName1,StationName1,UsePrio)}=Prio.

%Alle Stationen, die noch freien Plätze haben und kein Agent ausgewählt hat. 
AllFreeStation(AgentName,StationName,UsePrio):-AllVisitEdgeStation(AgentName,StationName,UsePrio),VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),not  -CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,false,false).
%Pour un agent donné, rechercher toutes les stations qui lui sont connectés et dont on peut encore y acceder.
AllVisitEdgeStation(AgentName,StationName,Prio):-SpaceSizeErfull(AgentName,AgentTypeName,StationName,StationTypeName),StationType(StationTypeName,_,_,_,Prio,_,_,Space,_).

%Pour une station donné, rechercher toutes les agents qui lui sont connectés et ensuite determinés lequelle à la plus grande priorité.
%Seul qui ne sont pas à l'interieur d'une station.
MaxPriorityAgent(AgentName,StationName,Prio):- AllFreeAgent(AgentName,StationName,Prio),#max{UsePrio: AllFreeAgent(AgentName1,StationName1,UsePrio)}=Prio.


%Alle Agenten, die eine Station verfolgen.
NoFreeAgent(AgentName,AgentTypeName):- -CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,false,false).
%Alle Agenten, die keine Stationen verfolgen.
AllFreeAgent(AgentName,StationName,UsePrio):-AllVisitEdgeAgent(AgentName,StationName,UsePrio),VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),not NoFreeAgent(AgentName,AgentTypeName).

%Pour un agent donné, rechercher toutes les stations qui lui sont connectés 
AllVisitEdgeAgent(AgentName,StationName,Prio):-Agent(AgentName,AgentTypeName,_,_,_),AgentType(AgentTypeName,_,_,_,Prio,_,_,_,_),AllVisitEdgeStation(AgentName,StationName,_).

%Gibt genau visitEdge(agentType und stationType) mit höhe priority.
MaxPriority(AgentTypeName,StationTypeName):-MaxPriorityStation(AgentTypeName,StationTypeName,_),MaxPriorityAgent(AgentTypeName,StationTypeName,_).
%##################### Erfüllbarkeit von Frequency ###################################
FreqErfullStation(StationName):-Station(StationName,StationTypeName,FreqStation,_,_),StationType(StationTypeName,Freq,_,_,_,_,_,_,_),FreqStation<Freq.
FreqErfullAgent(AgentName):-Agent(AgentName,AgentTypeName,FreqAgent,_,_),AgentType(AgentTypeName,Freq,_,_,_,_,_,_,_),FreqAgent<Freq.

%################################# Erfüllbarkeit von Capacity und Item ###################################################################
%+++++++++++++++++++++++++++++++++++++++++++++++++++++Item+++++++++++++++++++++++++++++++++++++++++++
%Eine Station <s> hat das Attribut Item, wenn es eine ausgehende Kante gibt.
%Und Sie kann Items erzeugen, entwerde wenn es keine eingehende Kante gibt oder wenn sie alle Items aus allen Stationen bekommen hat.
%Eingehende ist ----><S> und Ausgehende ist <S>------>
%%%%%%%%%%%%%%%%%%%%% Alle Stationen ohne ausgehenden Kanten.
ItemErfullNoAus(StationName,StationTypeName):-Station(StationName,StationTypeName,_,_,_),#count{X:PlacedEdge(StationName,StationTypeName,_,_,X,true)}=0.
%%%%%%%%%%%%%%%%%%%%%% Alle Stationen ohne eingehenden Kanten.
ItemErfullNoEin(StationName,StationTypeName):-Station(StationName,StationTypeName,_,_,_),#count{X:PlacedEdge(_,_,StationName,StationTypeName,X,true)}=0.

%%%%%%%%%%%%%%%%%%%%%% a Station has outgoing stations, aber the agent can't visit it. %%%%%%%%%%%%%%%%%%%%%%
AgentNoAusStation(AgentName,StationTypeName):-VisitEdge(AgentName,_,StationName,StationTypeName,_),#count{X:PlacedEdge(StationName,StationTypeName,StationNameIn,StationTypeNameIn,X,true),VisitEdge(AgentName,_,StationNameIn,StationTypeNameIn,_)}=0, not ItemErfullNoAus(StationName,StationTypeName).
%%%%%%%%%%%%%%%%%%%%%% a Station has ingoing stations, aber the agent can't visit it. %%%%%%%%%%%%%%%%%%%%%%
AgentNoEinStation(AgentName,StationTypeName):-VisitEdge(AgentName,_,StationName,StationTypeName,_),#count{X:PlacedEdge(StationNameIn,StationTypeNameIn,StationName,StationTypeName,X,true),VisitEdge(AgentName,_,StationNameIn,StationTypeNameIn,_)}=0,not ItemErfullNoEin(StationName,StationTypeName).


 
%Siehe Item beschreibung.
%ItemBeladen(AgentName,StationName,true). where true means that the agent can take more items and false that agent can take one item.
%True means there are no prerequis for that agent can take items.
%False means there are prerequis for that agent can take items.
%+++Gibt zurück, alle Stationen, auf der Agenten unendliche Item beladen können.
ItemBeladen(StationName,StationTypeName):-ItemErfullNoEin(StationName,_),PlacedEdge(StationName,StationTypeName,_,_,_,true).
%+++++Dies prüft, ob ein Agent noch genug Platz hat, um Items von Station s zu nehmen.
canAgentLoadItem(AgentName,StationName):-AgentType(AgentTypeName,_,_,_,_,_,AgentCap,_,_),Agent(AgentName,AgentTypeName,_,_,Cap),StationType(StationTypeName,_,_,_,_,_,Item,_,_),Station(StationName,StationTypeName,_,_,_),Z=Cap+Item,Z<=AgentCap. 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%When there are incoming edge.
%ItemSetLoadingAgent(AgentName,StationTypeName,ItemNumber). Diese gibt an, wie viele Items von dieser StationType der Agent trägt.
%Dies ist bestandteil des Agenten's Wissens und Alle müssen am Anfang initialisiert werden.
%ItemSetLoadingStation(StationTypeNameIn,StationNameOut,ItemNumber).<s_T_in=StationTypeNameIn> -----> <s_out=StationNameOut>: Diese gibt an, wie viele Items von <s_T_in> in <s_out> beladen wurden.

%++ AllIncomingStations(StationOut,StationInc).: Sucht alle station Sn eine station St so dass,  <Sn>-----><St>.
% und berechnet das minimal Anzahl der items, deren station zu der gesuchten station gehören. Wenn der zahl > 0 ist, dann
% ist die Produktion eines items möglich.
ItemProductViaInStations(AgentName,StationName,LadeItemMin):-VisitEdge(AgentName,_,StationName,_,_),#min{ItemNumber:ItemSetLoadingStation(StationTypeNameOut,StationName,ItemCount), StationType(StationTypeNameOut,_,_,_,_,_,_,_,CountStation),PlacedEdge(_,StationTypeNameOut,StationName,_,_,true),ItemNumber=ItemCount/CountStation}=LadeItemMin,LadeItemMin>0.

%ItemPlaceInStations(AgentName,StationName,LadeItemMin). gibt an, wie viel item ein agent kann place in der Station
ItemPlaceInStations(AgentName,StationName,LadeItemMin):-VisitEdge(AgentName,_,StationName,_,_),#min{ItemNumber:ItemSetLoadingAgent(AgentName,StationTypeNameOut,ItemCount), ItemCount>0,StationType(StationTypeNameOut,_,_,_,_,_,_,_,CountStation),PlacedEdge(_,StationTypeNameOut,StationName,_,_,true),ItemNumber=ItemCount/CountStation}=LadeItemMin,LadeItemMin>0.


%Wie viele Items können in einer Station mit ausgehender Kante produktiert werden, wird in Programm berechnet.
%ItemProductInStation(StationNameOut). Gibt zurück, alle Stationen die bereit ein Item zu erzeugen sind.
ItemProductInStation(StationNameOut):-ItemSetLoadingStation(_,StationNameOut,_),#count{StationTypeNameIn:ItemSetLoadingStation(StationTypeNameIn,StationNameOut,ItemNumber),StationType(StationTypeNameIn,_,_,_,_,_,_,_,Count),Count>ItemNumber}=0. %Hilfsvariable
%+++Gibt zurück, alle Stationen die bereit ein Item zu erzeugen sind und wie viele Items in dieser Station produktiert werden können.
%NumberItemToProductInStation(StationNameOut,Number): StationNameOut= station, die Item produktieren kann und Number=Menge von produktierbaren Items.
NumberItemToProductInStation(StationNameIn,Number):-ItemProductInStation(StationNameIn),Number=#min{X:ItemSetLoadingStation(StationTypeNameOut,StationNameIn,ItemNumber),StationType(StationTypeNameOut,_,_,_,_,_,_,_,Count),X=ItemNumber/Count}.



%################################################## TimeEdge eigenschaft ############################################

%TimeEdgeComp(Name,TypeName,Type,StatusTyp,Status)
%Gibt entweder ein TimeEdgeWaiting oder ein TimeEdgeReady zück. wobei Name, TypeName und Type dieselbe bedeutung wie die Terme in TimeEdgeReady. Status deutet an: 0=TimeEdgeWaiting und 1=TimeEdgeReady
% Status means nothings when StatusTyp=0 
TimeEdgeComp(Name,TypeName,Type,1,Status):-TimeEdgeReady(Name,TypeName,Type,Status).
TimeEdgeComp(Name,TypeName,Type,0,5):-TimeEdgeWaiting(Name,TypeName,Type), not TimeEdgeCompNoStatus(Name,TypeName,Type).
TimeEdgeCompNoStatus(Name,TypeName,Type):-TimeEdgeReady(Name,TypeName,Type,_).
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
TimeEdgeIncomingReady(NameIn,TypeNameIn,WeightMin,Type):-#count{X:TimeEdge(X,_,NameIn,TypeNameIn,_,true,true,_)}=0,#count{Y:TimeEdge(Name1,TypeName1,NameIn,TypeNameIn,Y,true,false,Weight),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,EchtZeit,false,true,_),EchtZeit<Weight}>0,TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).
% This triggers if there are connected and noconnected Komponentes. Also all edges have to be directed.
TimeEdgeIncomingReady(NameIn,TypeNameIn,WeightMin,Type):-TimeEdgeAllLogicalIncomingReady(NameIn,TypeNameIn,WeightMin,Type),#count{Y:TimeEdge(Name1,TypeName1,NameIn,TypeNameIn,Y,true,false,Weight),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,EchtZeit,false,true,_),EchtZeit<Weight}>0.


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
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status).

%Nur DirectedConnected and NoDirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and NoDirectedConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected  and DirectedNoConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur DirectedConnected and NoDirectedBothConneted and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected  and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Typ,Statuse),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur NoDirectedBothConneted and DirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur DirectedConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur DirectedConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0.

%Nur NoDirectedNoConnected and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur NoDirectedBothConneted and NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedBothConneted and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedConnected and DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

%Nur NoDirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,both,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedBothConneted
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,false,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,_,true,_)}=0.

%Nur NoDirectedConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,false,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,true,true,_)}=0.

%Nur DirectedNoConnected
TimeEdgeReady(Name,TypeName,Type,Status):-TimeEdgeDirectedNoConnectedReady(Name,TypeName,_,Type,Status),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,true,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

% nur outgoing. Status=0
TimeEdgeReady(Name,TypeName,Type,0):-TimeEdgeOnlyOutgoing(Name,TypeName,_,Type).

% ########################################### TimeEdgeLock definieren. #################################################
%TimeEdgeLockGet(AgentName1,StationName1,AgentName2,StationName2,EdgeType,EchTime,Lock1,Lock2,Finish1,Finish2).
% Schreibt das Atom TimeEdgeLockStateNoDNoC um, so dass man nicht mehr weißt, wer ist der VisitComponent und wer ist an TimeEdge angeschlossen.
% Sondern man kann klar sehen, wer ist der Agent bzw Station.
% EdgeType is the type of time edge
% 0 for NoDNoC 
% 1 for DNoC 
% 2 for NoDC 
% 3 for DC 
% 4 for NoDbothC 
% TimeEdgeLockGet for each EdgeType
% 0 for NoDNoC 
TimeEdgeLockGet(AgentName1,StationName1,AgentName2,StationName2,0,EchTime,Lock1,Lock2,Finish1,Finish2):-TimeEdgeLockStatusNoDNoC(AgentName1,StationName1,AgentName2,StationName2,0,Lock1,Lock2,Finish1,Finish2,EchTime), not TimeEdgeLockStateUse(AgentName2,StationName2,0),CurrentAgent(AgentName1, _),TimeEdgeLockStateNoUse(AgentName2,StationName2).
% 1 for NoDNoC 
TimeEdgeLockGet(AgentName1,StationName1,AgentName2,StationName2,1,EchTime,Lock1,Lock2,Finish1,Finish2):-TimeEdgeLockStatusDNoC(AgentName1,StationName1,AgentName2,StationName2,1,Lock1,Lock2,Finish1,Finish2,EchTime), CurrentAgent(AgentName1,_).% not TimeEdgeLockStateUse(AgentName2,StationName2,0),TimeEdgeLockStateNoUse(AgentName2,StationName2), 

TimeEdgeLockGet(AgentName1,StationName1,AgentName2,StationName2,1,EchTime,Lock1,Lock2,Finish1,Finish2):-TimeEdgeLockStatusDNoC(AgentName1,StationName1,AgentName2,StationName2,1,Lock1,Lock2,Finish1,Finish2,EchTime), TimeEdgeLockStateUseDNoC(AgentName1,StationName1,1), CurrentAgent(AgentName2,_).



%+++++++++++++++++++++ preparation of TimeEdgeLock help Components++++++++++++++++++++++++++++++++++++++++++
%SpaceSizeErfull(AgentName,_,StationName,_)

%SpaceFreqFreeStation(StationName,SpaceFree).
%Take a station and compute the free place.
SpaceFreqFreeStation(StationName,SpaceFree,FreqFree):- Station(StationName,StationTypeName,StationFreq,_,SpaceStation), StationType(StationTypeName,StationTypeFreq,_,_,_,_,_,SpaceStationType,_),SpaceFree=SpaceStationType-SpaceStation,FreqFree=StationTypeFreq-StationFreq.

%check if there free place and if the station can be visit.
TimeEdgeLockStateNoUse(AgentName,StationName):- AgentSize(AgentName,Size),SpaceFreqFreeStation(StationName,SpaceFree,FreqFree),#sum{Xa,Xb:TimeEdgeLockState(_,_,Xb,StationName,0,_,_,true,true,false,_,_),AgentSize(Xb,Xa)}=AgentsSize,AllSize=AgentsSize+Size,AllSize<=SpaceFree,VisitEdge(AgentName, _, StationName, _, _),#count{Ag:TimeEdgeLockState(_,_,Ag,StationName,0,_,_,true,true,false,_,_)}<FreqFree.

TimeEdgeLockStateUse(AgentName2,StationName2,0):- TimeEdgeLockState(Ag,St,AgentName2,StationName2,0,_,_,true,true,_,_,_).

% all TimeEdgeLockState where the first agent is finish and there aren't second agents
TimeEdgeLockStateUseDNoC(AgentName1,StationName1,1):- TimeEdgeLockState(AgentName1,StationName1,_,_,1,_,_,true,true,false,true,_).

%&&&&&&& This define the TimeEdgeLockState such as one can know who is the visited element. 
%The connected element stay at the first position.
%Agent - agent
TimeEdgeLockStateVisit(AgentName1,StationName1,AgentName2,StationName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2):-
TimeEdgeLockState(AgentName1,StationName1,AgentName2,StationName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2),TimeEdge(AgentName1,_,AgentName2,_,_,_,_,_).
%Station - agent
TimeEdgeLockStateVisit(StationName1,AgentName1,AgentName2,StationName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2):-
TimeEdgeLockState(AgentName1,StationName1,AgentName2,StationName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2),TimeEdge(StationName1,_,AgentName2,_,_,_,_,_).
%Agent - Station
TimeEdgeLockStateVisit(AgentName1,StationName1,StationName2,AgentName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2):-
TimeEdgeLockState(AgentName1,StationName1,AgentName2,StationName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2),TimeEdge(AgentName1,_,StationName2,_,_,_,_,_).
%Station - Station
TimeEdgeLockStateVisit(StationName1,AgentName1,StationName2,AgentName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2):-
TimeEdgeLockState(AgentName1,StationName1,AgentName2,StationName2,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2),TimeEdge(StationName1,_,StationName2,_,_,_,_,_).
%When there are nothings by TimeEdgeLockState, then do following in order to create TimeEdgeLockStateVisit with nothings.
%Agent - Nothings
TimeEdgeLockStateVisit(AgentName1,StationName1,nothings,nothings,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2):-
TimeEdgeLockState(AgentName1,StationName1,nothings,nothings,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2),TimeEdge(AgentName1,_,_,_,_,true,_,_).
%Station - Nothings
TimeEdgeLockStateVisit(StationName1,AgentName1,nothings,nothings,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2):-
TimeEdgeLockState(AgentName1,StationName1,nothings,nothings,EdgeType,Tick,EchTime,Activ,Lock1,Lock2,Finish1,Finish2),TimeEdge(StationName1,_,_,_,_,true,_,_).

%AgentSize(AgentName,Size)
%return the agent size
AgentSize(AgentName,Size):- Agent(AgentName,AgentTypeName,_,_,_), AgentType(AgentTypeName,_,_,_,_,_,_,Size,_).
%+++++++++++++++++++++++++++++ NoDirectedNoConnected: TimeEdgeLock for NoDNoC  +++++++++++++++++++++++++++++++

%TimeEdgeSpaceNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Type1,Type2,EchTime).
%The is a time component between Name1 and Name2. Also, the Name1 visits VisitName1 and Name2 visits VisitName2.
%Lock1=true when Component Name1 was throught this timeEgde activated and false otherwise.
%Lock2=true when Component Name2 was throught this timeEgde activated and false otherwise.
% Type_i=0 means Name_i is station and Type_i=2 means Name_i is agent.
TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,false,false,Finish1,Finish2,Type1,Type2,EchTime):- TimeEdgeState(Name1,_,VisitName1,_,0,EchTime,Type1,_,true,false,Finish1),TimeEdgeState(Name2,_,VisitName2,_,0,EchTime,Type2,_,true,false,Finish2),TimeEdgeOutIn(Name1,_,Name2,_,_,false,false,_).

TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,false,true,Finish1,Finish2,Type1,Type2,EchTime):- TimeEdgeState(Name1,_,VisitName1,_,0,EchTime,Type1,_,true,false,Finish1),TimeEdgeState(Name2,_,VisitName2,_,0,EchTime,Type2,_,false,true,Finish2),TimeEdgeOutIn(Name1,_,Name2,_,_,false,false,_).

TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,true,false,Finish1,Finish2,Type1,Type2,EchTime):- TimeEdgeState(Name1,_,VisitName1,_,0,EchTime,Type1,_,false,true,Finish1),TimeEdgeState(Name2,_,VisitName2,_,0,EchTime,Type2,_,true,false,Finish2),TimeEdgeOutIn(Name1,_,Name2,_,_,false,false,_).

TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,true,true,Finish1,Finish2,Type1,Type2,EchTime):- TimeEdgeState(Name1,_,VisitName1,_,0,EchTime,Type1,_,false,true,Finish1),TimeEdgeState(Name2,_,VisitName2,_,0,EchTime,Type2,_,false,true,Finish2),TimeEdgeOutIn(Name1,_,Name2,_,_,false,false,_).

%TimeEdgeLockStatusNoDNoC(AgentName1,StationName1,AgentName2,StationName2,EdgeType,Lock1,Lock2,Finish1,Finish2,EchTime).
% Schreibt das Atom TimeEdgeLockStateNoDNoC um, so dass man nicht mehr weißt, wer ist der VisitComponent und wer ist an TimeEdge angeschlossen.
% Sondern man kann klar sehen, wer ist der Agent bzw Station.
% EdgeType is the type of time edge

TimeEdgeLockStatusNoDNoC(AgentName1,StationName1,AgentName2,StationName2,0,Lock1,Lock2,Finish1,Finish2,EchTime):-TimeEdgeLockStateNoDNoC(AgentName1,StationName1,AgentName2,StationName2,Lock1,Lock2,Finish1,Finish2,1,1,EchTime).

TimeEdgeLockStatusNoDNoC(AgentName1,StationName1,AgentName2,StationName2,0,Lock1,Lock2,Finish1,Finish2,EchTime):-TimeEdgeLockStateNoDNoC(AgentName1,StationName1,StationName2,AgentName2,Lock1,Lock2,Finish1,Finish2,1,0,EchTime).

TimeEdgeLockStatusNoDNoC(AgentName1,StationName1,AgentName2,StationName2,0,Lock1,Lock2,Finish1,Finish2,EchTime):-TimeEdgeLockStateNoDNoC(StationName1,AgentName1,AgentName2,StationName2,Lock1,Lock2,Finish1,Finish2,0,1,EchTime).

TimeEdgeLockStatusNoDNoC(AgentName1,StationName1,AgentName2,StationName2,0,Lock1,Lock2,Finish1,Finish2,EchTime):-TimeEdgeLockStateNoDNoC(StationName1,AgentName1,StationName2,AgentName2,Lock1,Lock2,Finish1,Finish2,0,0,EchTime).


%TimeEdgeSpaceSizeNoDNoC(AgentName1,StationName1,AgentName2,StationName2,Lock1,Lock2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2)
%SpaceFree1 is the free place of station 1 and SpaceFree2 is the free place of station 2.
%Freq1 is the rest frequency of station 1 and SpaceFree2 is the rest frequency of station 2.
%Size1 is the size of agent 1 and Size2 is the size of agent 2.

%Name1 and Name2 are stations
TimeEdgeSpaceSizeNoDNoC(VisitName1,Name1,VisitName2,Name2,Lock1,Lock2,Finish1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Finish1,Finish2,0,0,_),SpaceFreqFreeStation(Name1,SpaceFree1,Freq1),SpaceFreqFreeStation(Name2,SpaceFree2,Freq2),AgentSize(VisitName1,Size1),AgentSize(VisitName2,Size2).

%Name1 and Name2 are agents
TimeEdgeSpaceSizeNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Finish1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Finish1,Finish2,1,1,_),SpaceFreqFreeStation(VisitName1,SpaceFree1,Freq1),SpaceFreqFreeStation(VisitName2,SpaceFree2,Freq2),AgentSize(Name1,Size1),AgentSize(Name2,Size2).

%Name1 is station and Name2 is agent
TimeEdgeSpaceSizeNoDNoC(VisitName1,Name1,Name2,VisitName2,Lock1,Lock2,Finish1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Finish1,Finish2,0,1,_),SpaceFreqFreeStation(Name1,SpaceFree1,Freq1),SpaceFreqFreeStation(VisitName2,SpaceFree2,Freq2),AgentSize(VisitName1,Size1),AgentSize(Name2,Size2).

%Name1 is agent and Name2 is station
TimeEdgeSpaceSizeNoDNoC(Name1,VisitName1,VisitName2,Name2,Lock1,Lock2,Finish1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Finish1,Finish2,1,0,_),SpaceFreqFreeStation(VisitName1,SpaceFree1,Freq1),SpaceFreqFreeStation(Name2,SpaceFree2,Freq2),AgentSize(Name1,Size1),AgentSize(VisitName2,Size2).

%Define TimeEdgeCheckNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2).
%TimeEdgeCheckNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2):- TimeEdgeSpaceSizeNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2).

%Define the classic negation of TimeEdgeLockNoDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2).
%-TimeEdgeCheckNoDNoC(Name1,VisitName1,Name2,VisitName2,false,false):- TimeEdgeCheckNoDNoC(Name1,VisitName1,Name2,VisitName2,_,_), not TimeEdgeCheckNoDNoC(Name1,VisitName1,Name2,VisitName2,false, false).

%TimeEdgeLockMinSpaceNoDNoC(AgentName,StationName)
%Name is the is formation about the current agent
%define which component can visit which throught which corresponding.

%Neither the current agent nor a other correpondings agent has locked. 
TimeEdgeLockNoDNoC(AgentName1,StationName1,AgentName2,StationName2):- CurrentAgent(AgentName1,_),TimeEdgeSpaceSizeNoDNoC(AgentName1,StationName1,AgentName2,StationName2,false,false,false,false,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2),#sum{Xa,Xb:TimeEdgeSpaceSizeNoDNoC(Xb,StationName1,_,_,true,false,_,_,Xa,_,_,_,_,_),Xb!=AgentName1}=SumSpace,BusySpace=SumSpace+Size1,BusySpace<=SpaceFree1, #count{Za,Zb: TimeEdgeSpaceSizeNoDNoC(Zb,StationName1,_,_,true,false,_,_,_,_,_,_,Za,_),Zb!=AgentName1}=CFreq1,CFreq1<Freq1, #sum{Ya,Yb: TimeEdgeSpaceSizeNoDNoC(_,_,Yb,StationName2,true,false,_,_,_,Ya,_,_,_,_),TimeEdgeLockStateUse(Yb,StationName2,0),Yb!=AgentName2}=SumSpace1,BusySpace1=SumSpace1+Size2,BusySpace1<=SpaceFree2, #count{Ta,Tb: TimeEdgeSpaceSizeNoDNoC(_,_,Tb,StationName2,true,false,_,_,_,_,_,_,_,Ta),TimeEdgeLockStateUse(Tb,StationName2,0),Tb!=AgentName2}<Freq2,#sum{Wa,Wb:TimeEdgeLockState(_,_,Wb,StationName1,0,_,_,true,true,false,Finish1,Finish2),AgentSize(Wb,Wa),Wb!=AgentName1}=SumSpace3,BusySpace3=SumSpace3+Size1,BusySpace3<=SpaceFree1, #count{Qa,Qb: TimeEdgeLockState(_,_,Qb,StationName1,Qa,_,_,true,true,false,Finish1,Finish2),Qb!=AgentName2}<Freq1, not TimeEdgeLockStateUse(AgentName2,StationName2,0).

%TimeEdgeLockState(AgentName2,StationName2,AgentName1,StationName1,0,IsActiv,Lock2,Lock1,Finish2,Finish1).
P(AgentName1,StationName1,AgentName2,StationName2,Freq2,X):-#sum{Wa,Wb:TimeEdgeLockState(_,_,Wb,StationName1,0,_,_,true,true,false,Finish1,Finish2),AgentSize(Wb,Wa),Wb!=AgentName1}=X,TimeEdgeSpaceSizeNoDNoC(AgentName1,StationName1,AgentName2,StationName2,false,false,false,false,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2).

%the current agent want to check if it can lock and one other correpondings agent has locked.
TimeEdgeLockNoDNoC(AgentName1,StationName1,AgentName2,StationName2):- TimeEdgeSpaceSizeNoDNoC(AgentName1,StationName1,AgentName2,StationName2,false,true,false,_,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2),#sum{Xa,Xb: TimeEdgeSpaceSizeNoDNoC(Xb,StationName1,_,_,true,true,_,_,Xa,_,_,_,_,_),Xb!=AgentName1}=SumSpace,BusySpace=SumSpace+Size1,BusySpace<=SpaceFree1, #count{Za,Zb: TimeEdgeSpaceSizeNoDNoC(Zb,StationName1,_,_,true,true,_,_,_,_,_,_,Za,_),Zb!=AgentName1}<Freq1,TimeEdgeLockState(AgentName2,StationName2,AgentName1,StationName1,0,_,_,true,true,false,Finish2,Finish1),CurrentAgent(AgentName1, _).



%&&&&&&&&&&&&&&&&&&&&+++++++++++++++++++ DirectedNoConnected: TimeEdgeLock for DNoC  +++++++++++++++++&&&&&&&&&&&&&&&&

%TimeEdgeSpaceDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Type1,Type2).
%The is a time component between Name1 and Name2. Also, the Name1 visits VisitName1 and Name2 visits VisitName2.
%Lock1=true when Component Name1 was throught this timeEgde activated and false otherwise.
%Lock2=true when Component Name2 was throught this timeEgde activated and false otherwise.
% Type_i=0 means Name_i is station and Type_i=2 means Name_i is agent.

TimeEdgeLockStateDNoC(Name1,VisitName1,Name2,VisitName2,Ready1,Ready2,Finish1,Finish2,Type1,Type2,EchTime):- TimeEdgeState(Name1,_,VisitName1,_,1,EchTime,Type1,_,_,Ready1,Finish1),TimeEdgeState(Name2,_,VisitName2,_,1,EchTime,Type2,_,_,Ready2,Finish2),TimeEdge(Name1,_,Name2,_,_,true,false,_).

%TimeEdgeLockStatusDNoC(AgentName1,StationName1,AgentName2,StationName2,EdgeType,Ready1,Lock2,Lock1,Finish2).
% Schreibt das Atom TimeEdgeLockStateDNoC um, so dass man nicht mehr weißt, wer ist der VisitComponent und wer ist an TimeEdge angeschlossen.
% Sondern man kann klar sehen, wer ist der Agent bzw Station.
% EdgeType is the type of time edge

TimeEdgeLockStatusDNoC(AgentName1,StationName1,AgentName2,StationName2,1,Ready1,Lock2,Lock1,Finish2,EchTime):-TimeEdgeLockStateDNoC(AgentName1,StationName1,AgentName2,StationName2,Ready1,Lock2,Lock1,Finish2,1,1,EchTime).

TimeEdgeLockStatusDNoC(AgentName1,StationName1,AgentName2,StationName2,1,Ready1,Lock2,Lock1,Finish2,EchTime):-TimeEdgeLockStateDNoC(AgentName1,StationName1,StationName2,AgentName2,Ready1,Lock2,Lock1,Finish2,1,0,EchTime).

TimeEdgeLockStatusDNoC(AgentName1,StationName1,AgentName2,StationName2,1,Ready1,Lock2,Lock1,Finish2,EchTime):-TimeEdgeLockStateDNoC(StationName1,AgentName1,AgentName2,StationName2,Ready1,Lock2,Lock1,Finish2,0,1,EchTime).

TimeEdgeLockStatusDNoC(AgentName1,StationName1,AgentName2,StationName2,1,Ready1,Lock2,Lock1,Finish2,EchTime):-TimeEdgeLockStateDNoC(StationName1,AgentName1,StationName2,AgentName2,Ready1,Lock2,Lock1,Finish2,0,0,EchTime).

%TimeEdgeSpaceSizeDNoC(AgentName1,StationName1,AgentName2,StationName2,Lock1,Lock2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2)
%SpaceFree1 is the free place of station 1 and SpaceFree2 is the free place of station 2.
%Freq1 is the rest frequency of station 1 and SpaceFree2 is the rest frequency of station 2.
%Size1 is the size of agent 1 and Size2 is the size of agent 2.

%Name1 and Name2 are stations
TimeEdgeSpaceSizeDNoC(VisitName1,Name1,VisitName2,Name2,Ready1,Lock2,Lock1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateDNoC(Name1,VisitName1,Name2,VisitName2,Ready1,Lock2,Lock1,Finish2,0,0,_),SpaceFreqFreeStation(Name1,SpaceFree1,Freq1),SpaceFreqFreeStation(Name2,SpaceFree2,Freq2),AgentSize(VisitName1,Size1),AgentSize(VisitName2,Size2).

%Name1 and Name2 are agents
TimeEdgeSpaceSizeDNoC(Name1,VisitName1,Name2,VisitName2,Ready1,Lock2,Lock1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateDNoC(Name1,VisitName1,Name2,VisitName2,Ready1,Lock2,Lock1,Finish2,1,1,_),SpaceFreqFreeStation(VisitName1,SpaceFree1,Freq1),SpaceFreqFreeStation(VisitName2,SpaceFree2,Freq2),AgentSize(Name1,Size1),AgentSize(Name2,Size2).

%Name1 is station and Name2 is agent
TimeEdgeSpaceSizeDNoC(VisitName1,Name1,Name2,VisitName2,Ready1,Lock2,Lock1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateDNoC(Name1,VisitName1,Name2,VisitName2,Ready1,Lock2,Lock1,Finish2,0,1,_),SpaceFreqFreeStation(Name1,SpaceFree1,Freq1),SpaceFreqFreeStation(VisitName2,SpaceFree2,Freq2),AgentSize(VisitName1,Size1),AgentSize(Name2,Size2).

%Name1 is agent and Name2 is station
TimeEdgeSpaceSizeDNoC(Name1,VisitName1,VisitName2,Name2,Ready1,Lock2,Lock1,Finish2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2):- TimeEdgeLockStateDNoC(Name1,VisitName1,Name2,VisitName2,Ready1,Lock2,Lock1,Finish2,1,0,_),SpaceFreqFreeStation(VisitName1,SpaceFree1,Freq1),SpaceFreqFreeStation(Name2,SpaceFree2,Freq2),AgentSize(Name1,Size1),AgentSize(VisitName2,Size2).

%Define TimeEdgeCheckDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2).
%TimeEdgeCheckDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2):- TimeEdgeSpaceSizeDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2).

%Define the classic negation of TimeEdgeLockDNoC(Name1,VisitName1,Name2,VisitName2,Lock1,Lock2).
%-TimeEdgeCheckDNoC(Name1,VisitName1,Name2,VisitName2,false,false):- TimeEdgeCheckDNoC(Name1,VisitName1,Name2,VisitName2,_,_), not TimeEdgeCheckDNoC(Name1,VisitName1,Name2,VisitName2,false, false).

%TimeEdgeLockMinSpaceDNoC(AgentName,StationName)
%Name is the is formation about the current agent
%define which component can visit which throught which corresponding.

% current agent is at the start station. The waiting time is more than 0.
TimeEdgeLockDNoC(AgentName1,StationName1,AgentName2,StationName2):- CurrentAgent(AgentName1,_),TimeEdgeSpaceSizeDNoC(AgentName1,StationName1,AgentName2,StationName2,false,false,false,false,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2).
%Neither the current agent nor a other correpondings agent has locked. 
%TimeEdgeLockDNoC(AgentName1,StationName1,AgentName2,StationName2):- CurrentAgent(AgentName1,_),TimeEdgeSpaceSizeDNoC(AgentName1,StationName1,AgentName2,StationName2,false,false,false,false,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2),#sum{Xa,Xb:TimeEdgeSpaceSizeDNoC(Xb,StationName1,_,_,true,false,_,_,Xa,_,_,_,_,_),Xb!=AgentName1}=SumSpace,BusySpace=SumSpace+Size1,BusySpace<=SpaceFree1, #count{Za,Zb: TimeEdgeSpaceSizeDNoC(Zb,StationName1,_,_,true,false,_,_,_,_,_,_,Za,_),Zb!=AgentName1}=CFreq1,CFreq1<Freq1, #sum{Ya,Yb: TimeEdgeSpaceSizeDNoC(_,_,Yb,StationName2,true,false,_,_,_,Ya,_,_,_,_),TimeEdgeLockStateUse(Yb,StationName2,0),Yb!=AgentName2}=SumSpace1,BusySpace1=SumSpace1+Size2,BusySpace1<=SpaceFree2, #count{Ta,Tb: TimeEdgeSpaceSizeDNoC(_,_,Tb,StationName2,true,false,_,_,_,_,_,_,_,Ta),TimeEdgeLockStateUse(Tb,StationName2,0),Tb!=AgentName2}<Freq2,#sum{Wa,Wb:TimeEdgeLockState(_,_,Wb,StationName1,0,true,true,false,Finish1,Finish2),AgentSize(Wb,Wa),Wb!=AgentName1}=SumSpace3,BusySpace3=SumSpace3+Size1,BusySpace3<=SpaceFree1, #count{Qa,Qb: TimeEdgeLockState(_,_,Qb,StationName1,Qa,true,true,false,Finish1,Finish2),Qb!=AgentName2}<Freq1, not TimeEdgeLockStateUse(AgentName2,StationName2,0).


%the current agent want to check if it can lock and one other correpondings agent has locked.
TimeEdgeLockDNoC(AgentName1,StationName1,AgentName2,StationName2):- TimeEdgeSpaceSizeDNoC(AgentName1,StationName1,AgentName2,StationName2,false,false,true,false,Size1,Size2,SpaceFree1,SpaceFree2,Freq1,Freq2), #count{Za: TimeEdgeLockState(AgentName1,StationName1,Za,_,1,_,_,true,true,true,true,Finish2),Za!=AgentName2}=0,CurrentAgent(AgentName2, _).

%################################################ End Lock ####################################################################

%+++++++++++++++++ NoDirectedNoConnected: IsReady auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++

%Status	3=the both components are waiting without waiting time
%Status	2=the both components are reading after the waiting time 
%Status 1=one component is ready and the other can begin to count.
%Status 0=the both components are waiting

%Gibt alle noConnected Komponenten zurück, deren correspondings Komponent <mindesten ein IsWaiting or IsReady auf true> ist.
%Da wir nicht genau wissen, zwischen welchen corresponds component von Name bei the TimeEdgeLock Atom the timeedge an ihn angeschlossen wurden, werden alle vier möglichkeiten gechecket. 
%Gibt alle noConnected Komponenten zurück, deren correspondings Komponent <mindesten ein IsWaiting or IsReady auf true> ist.

TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,WeightMin,0,Status):-#count{X:TimeEdgeStatusNoDirectedNoConnectedReady(X,TypeName1,Name,TypeName,WeightMin,Type,Status)}>0,TimeEdgeStatusNoDirectedNoConnectedReady(NameCorresponds,_,Name,TypeName,WeightMin,0,Status),TimeEdgeLockNoDNoC(AgName,Name,NameCorresponds,_),CurrentAgent(AgName,_).

TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,WeightMin,0,Status):-#count{X:TimeEdgeStatusNoDirectedNoConnectedReady(X,TypeName1,Name,TypeName,WeightMin,Type,Status)}>0,TimeEdgeStatusNoDirectedNoConnectedReady(NameCorresponds,_,Name,TypeName,WeightMin,0,Status),TimeEdgeLockNoDNoC(AgName,Name,_,NameCorresponds),CurrentAgent(AgName,_).

TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,WeightMin,1,Status):-#count{X:TimeEdgeStatusNoDirectedNoConnectedReady(X,TypeName1,Name,TypeName,WeightMin,Type,Status)}>0,TimeEdgeStatusNoDirectedNoConnectedReady(NameCorresponds,_,Name,TypeName,WeightMin,1,Status),TimeEdgeLockNoDNoC(AgName,_,NameCorresponds,_),CurrentAgent(AgName,_).

TimeEdgeNoDirectedNoConnectedReady(Name,TypeName,WeightMin,1,Status):-#count{X:TimeEdgeStatusNoDirectedNoConnectedReady(X,TypeName1,Name,TypeName,WeightMin,Type,Status)}>0,TimeEdgeStatusNoDirectedNoConnectedReady(NameCorresponds,_,Name,TypeName,WeightMin,1,Status),TimeEdgeLockNoDNoC(AgName,_,_,NameCorresponds),CurrentAgent(AgName,_).



%Gibt alle nodirected and noConnected Komponenten zurück, die ihr corresponding Komponent ihr IsReady auf true ist.
%TimeEdgeStatusNoDirectedNoConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type,Status)
%	3=the both components are waiting without waiting time
TimeEdgeStatusNoDirectedNoConnectedReady(Name1,TypeName1,Name2,TypeName2,0,Type,3):-TimeEdgeNoDirectedNoConnectedWeightMin(Name2,TypeName2,0,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,_,_,0,true,false,_),TimeEdgeOutIn(Name1,TypeName1,Name2,TypeName2,_,false,false,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,0,false,true,_).
%	2=the both components are reading after the waiting time 
TimeEdgeStatusNoDirectedNoConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type,2):-TimeEdgeNoDirectedNoConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,_,_,Echtzeit,true,false,_),TimeEdgeOutIn(Name1,TypeName1,Name2,TypeName2,_,false,false,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,_,false,true,_),WeightMin<=Echtzeit,WeightMin!=0.
%	1=one component is ready and the other can begin to count.
TimeEdgeStatusNoDirectedNoConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type,1):-TimeEdgeNoDirectedNoConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,_,_,Echtzeit,true,false,_),TimeEdgeOutIn(Name1,TypeName1,Name2,TypeName2,_,false,false,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,_,false,true,_),WeightMin>Echtzeit.
%Gibt alle nodirected and noConnected Komponenten zurück, die ihr corresponding Komponent ihr IsWaiting auf true ist.
%Status: 0=the both components are waiting
TimeEdgeStatusNoDirectedNoConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type,0):-TimeEdgeNoDirectedNoConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,_,_,_,true,false,_),TimeEdgeOutIn(Name1,TypeName1,Name2,TypeName2,_,false,false,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,_,true,false,_). %Hilfsvariable

%+++++++++++++++++ Outgoing and DirectedNoConnected: IsReady auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++

%TimeEdgeDirectedNoConnectedReady(NameIn,TypeNameIn,WeightMin,Type,Status)
% Rule with the Lock information
%Da wir nicht genau wissen, zwischen welchen corresponds component von Name bei the TimeEdgeLock Atom the timeedge an ihn angeschlossen wurden, werden alle vier möglichkeiten gechecket. 
%Gibt alle directedNoConnected Komponenten zurück, deren correspondings Komponent <mindesten ein IsWaiting or IsReady auf true> ist.

%The current Agent has the TimeEdge on it.
TimeEdgeDirectedNoConnectedReady(Name,TypeName,WeightMin,Type,Status):- TimeEdgeDirectedNoConnectedReadyNoLock(Name,TypeName,WeightMin,Type,Status),TimeEdgeLockDNoC(_,_,Name,_),CurrentAgent(Name,_).

%%The visited station of the current Agent has the TimeEdge on it.
TimeEdgeDirectedNoConnectedReady(Name,TypeName,WeightMin,Type,Status):- TimeEdgeDirectedNoConnectedReadyNoLock(Name,TypeName,WeightMin,Type,Status),TimeEdgeLockDNoC(_,_,AgName,Name),CurrentAgent(AgName,_).

%Status	3=the both components are waiting without waiting time
TimeEdgeDirectedNoConnectedReadyNoLock(NameIn,TypeNameIn,0,Type,3):- TimeEdgeDirectedNoConnectedReadyIn(NameIn,TypeNameIn,0,Type),TimeEdgeStatus(NameIn,TypeNameIn,_,_,_,_,_,0,true,false,false).
%Status	1=one component is ready and the other can begin to count.
TimeEdgeDirectedNoConnectedReadyNoLock(NameIn,TypeNameIn,WeightMin,Type,1):- TimeEdgeDirectedNoConnectedReadyIn(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameIn,TypeNameIn,_,_,_,_,_,Echtzeit,true,false,false),WeightMin>Echtzeit.
%Status	2=the both components are reading after the waiting time 
TimeEdgeDirectedNoConnectedReadyNoLock(NameIn,TypeNameIn,WeightMin,Type,2):- TimeEdgeDirectedNoConnectedReadyIn(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameIn,TypeNameIn,_,_,_,_,_,Echtzeit,true,false,false),WeightMin<=Echtzeit,WeightMin!=0.

%Gibt alle incoming Komponenten deren outgoing Komponenten ihrer Isconnected=false und mindesten ein ihrer IsReady=true zurück.
%mindesten eine <out,IsConnected=false,IsReady=true> --> <In>
TimeEdgeDirectedNoConnectedReadyIn(NameIn,TypeNameIn,WeightMin,Type):- #count{X:TimeEdgeOutgoingWithNoConReady(X,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type)}>0,TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).


%TimeEdgeOutgoingWithNoConReady(StationNameOut,StationTypeNameOut,StationNameIn,StationTypeNameIn,EchtzeitMin,Type). 
TimeEdgeOutgoingWithNoConReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):-TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type), TimeEdgeAll(NameOut,TypeNameOut,_),#count{X:TimeEdge(X,TypeNameOut,NameIn,TypeNameIn,_,true,false,_)}>0,TimeEdgeStatusDirectedNoConnectedReady(NameOut,TypeNameOut,_,_,_,_). %Hilfsvariable

%Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
TimeEdgeStatusDirectedOnConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,_,_,Echtzeit,false,true,_),TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,false,_).
%+++++++++++ NoDirectedBothConnected and NoDirectedBothConnected: IsReady auf true setzen, wenn erfüllt +++++++++++++++

%Status	3=the both components are waiting without waiting time
TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,0,Type,3):-TimeEdgeNoDirectedBothConnectedReadyIn(Name,TypeName,0,Type),TimeEdgeStatus(Name,TypeName,_,_,_,_,_,0,true,false,_).
%Status	2=the both components are reading after the waiting time
TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,WeightMin,Type,2):-TimeEdgeNoDirectedBothConnectedReadyIn(Name,TypeName,WeightMin,Type),TimeEdgeStatus(Name,TypeName,_,_,_,_,_,Echtzeit,true,false,_),WeightMin<=Echtzeit,WeightMin!=0.
%Status	1=one component is ready and the other can begin to count.
TimeEdgeNoDirectedBothConnectedReady(Name,TypeName,WeightMin,Type,1):-TimeEdgeNoDirectedBothConnectedReadyIn(Name,TypeName,WeightMin,Type),TimeEdgeStatus(Name,TypeName,_,_,_,_,_,Echtzeit,true,false,_),WeightMin>Echtzeit.

%Gibt alle Komponenten of TimeEdgeNoDirectedBothConnected(...) zurück, deren timeEdge <IsConnect=both> ist.
TimeEdgeNoDirectedBothConnectedReadyIn(Name,TypeName,WeightMin,Type):-#count{X:TimeEdgeNoDirectedBothConnectedNoReady(X,TypeName1,Name,TypeName,WeightMin,Type)}=0,TimeEdgeNoDirectedBothConnected(Name1,TypeName1,Name,TypeName,WeightMin,Type).

%Gibt alle coresponding Komponenten of TimeEdgeNoDirectedOneConnectedReady(...) zurück, deren <IsWaiting=false and IsReady=false>  timeEdge ist.
TimeEdgeNoDirectedBothConnectedNoReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):- #count{X:TimeEdgeStatusNoDirectedBothConnectedReady(X,TypeName1,_,_,_,_)}=0,TimeEdgeNoDirectedBothConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type). %Hilfsvariable

%Gibt alle nodirected and bothConnected Komponenten zurück, die ihr corresponding Komponent ihr IsReady auf true ist.
TimeEdgeStatusNoDirectedBothConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedBothConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,_,_,_,true,false,_),TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,both,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,_,false,true,_). %Hilfsvariable
%Gibt alle nodirected and bothConnected Komponenten zurück, die ihr corresponding Komponent ihr IsWaiting auf true ist.
TimeEdgeStatusNoDirectedBothConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,_,_,_,true,false,_),TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,both,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,_,true,false,_). %Hilfsvariable

%+++++++++++++++++ NoDirectedConnected and NoDirectedNoConnected: IsReady auf true setzen, wenn erfüllt+++++++++++++++++++++++++++++


%Status	3=the both components are waiting without waiting time
TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,0,Type,3):-TimeEdgeNoDirectedOneConnectedReadyIn(Name,TypeName,0,Type),TimeEdgeStatus(Name,TypeName,_,_,_,_,_,0,false,true,_).
%Status	2=the both components are reading after the waiting time 
TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,WeightMin,Type,2):-TimeEdgeNoDirectedOneConnectedReadyIn(Name,TypeName,WeightMin,Type),TimeEdgeStatus(Name,TypeName,_,_,_,_,_,Echtzeit,true,false,_),WeightMin<=Echtzeit,WeightMin!=0.
%Status	1=one component is ready and the other can begin to count. 
TimeEdgeNoDirectedOneConnectedReady(Name,TypeName,WeightMin,Type,1):-TimeEdgeNoDirectedOneConnectedReadyIn(Name,TypeName,WeightMin,Type),TimeEdgeStatus(Name,TypeName,_,_,_,_,_,Echtzeit,true,false,_),WeightMin>Echtzeit.

%Gibt alle Komponenten of TimeEdgeNoDirectedOneConnected(...) zurück, deren correspndings komponenten mindesten eine Iswaiting=true ist.
TimeEdgeNoDirectedOneConnectedReadyIn(Name,TypeName,WeightMin,Type):- #count{X:TimeEdgeNoDirectedOneConnectedNoReady(X,TypeName1,Name,TypeName,WeightMin,Type)}=0,TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name,TypeName,WeightMin,Type).

%Gibt alle coresponding Komponenten of TimeEdgeNoDirectedConnectedReady(...) zurück, deren <IsWaiting=false>  timeEdge ist.
TimeEdgeNoDirectedOneConnectedNoReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):- #count{X:TimeEdgeStatusNoDirectedWaiting(X,TypeName1,_,_,_,_)}=0,TimeEdgeNoDirectedOneConnected(Name1,TypeName1,Name2,TypeName2,WeightMin,Type). %Hilfsvariable

%TimeEdgeStatusNoDirectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type,Status)
%Gibt alle nodirected Komponenten zurück, die ihr ISready auf true ist.
TimeEdgeStatusNoDirectedConnectedReady(Name1,TypeName1,Name2,TypeName2,WeightMin,Type,1):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name2,TypeName2,_,_,_,_,_,_,true,false,_),TimeEdge(Name1,TypeName1,Name2,TypeName2,_,false,true,_),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,_,false,true,_).



%+++++++++++++++++ Outgoing and incomingConnected: IsReady auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++


%TimeEdgeDirectedConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type,Status): Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
%Status	2=the both components are reading after the waiting time
TimeEdgeDirectedConnectedReady(NameIn,TypeNameIn,WeightMin,Type,2):- TimeEdgeDirectedConnectedReadyIn(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameIn,TypeNameIn,_,_,_,_,_,Echtzeit,true,false,false),WeightMin<=Echtzeit,WeightMin!=0.
%Status	1=one component is ready and the other can begin to count.
TimeEdgeDirectedConnectedReady(NameIn,TypeNameIn,WeightMin,Type,1):- TimeEdgeDirectedConnectedReadyIn(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameIn,TypeNameIn,_,_,_,_,_,Echtzeit,true,false,false),WeightMin>Echtzeit.
%Status	3=the both components are waiting without waiting time
TimeEdgeDirectedConnectedReady(NameIn,TypeNameIn,0,Type,3):- TimeEdgeDirectedConnectedReadyIn(NameIn,TypeNameIn,0,Type),TimeEdgeStatus(NameIn,TypeNameIn,_,_,_,_,_,0,true,false,false).

%TimeEdgeAllLogicalIncomingReady(NameIn,TypeNameIn,WeightMin,Type)
%Gibt alle incoming Komponenten deren outgoing Komponenten ihrer Isconnected=true und ihrer IsReady=true zurück.
%alle <out,IsConnected=true,IsReady=true> --> <In>
TimeEdgeDirectedConnectedReadyIn(NameIn,TypeNameIn,WeightMin,Type):- #count{X:TimeEdgeOutgoingReady(X,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type)}=0,TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).

%TimeEdgeOutgoingReady(StationNameOut,StationTypeNameOut,StationNameIn,StationTypeNameIn,EchtzeitMin,Type). Dies ist eine Hilfsvariable.
%Gibt alle incoming StationTypen mit IsReady=false und Isconnected=true: <out> ---->  <In>
TimeEdgeOutgoingReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):-TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type), TimeEdgeAll(NameOut,TypeNameOut,_),#count{X:TimeEdge(X,TypeNameOut,NameIn,TypeNameIn,_,true,true,_)}>0,#count{Y:TimeEdgeStatusDirectedConnectedReady(Y,TypeNameOut,_,_,_,_)}=0. %Hilfsvariable
%TimeEdgeStatusDirectedConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type,Status): Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
TimeEdgeStatusDirectedConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,_,_,Echtzeit,false,false,true),TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,true,_).

%TimeEdgeStatusDirectedNoConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type,Status): Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
%TimeEdgeStatusDirectedNoConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,_,_,Echtzeit,false,false,true),TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,false,_). this in comment is the old version.
TimeEdgeStatusDirectedNoConnectedReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedNoConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,false,_),TimeEdgeLockStateVisit(NameOut,_,_,_,1,_,_,true,true,_,true,false).



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
TimeEdgeWaiting(Name,TypeName,Type):-TimeEdgeDirectedNoConnectedWaiting(Name,TypeName,_,Type),#count{X:TimeEdgeOutIn(_,X,Name,TypeName,_,false,_,_)}=0,#count{Y:TimeEdge(_,Y,Name,TypeName,_,true,true,_)}=0,#count{Z:TimeEdge(_,Z,Name,TypeName,_,false,true,_)}=0.

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
TimeEdgeStatusNoDirectedConnectedWaiting(Name1,TypeName1,Name2,TypeName2,WeightMin,Type):-TimeEdgeNoDirectedConnectedWeightMin(Name2,TypeName2,WeightMin,Type),TimeEdgeStatus(Name1,TypeName1,_,_,_,_,_,Echtzeit,true,false,_),TimeEdgeOutInAnd(Name1,TypeName1,Name2,TypeName2,_,false,_,_). %Hilfsvariable

%+++++++++++++++++ Outgoing and incomingConnected: IsWaiting auf true setzen, wenn erfüllt +++++++++++++++++++++++++++++

%TimeEdgeAllLogicalIncomingReady(NameIn,TypeNameIn,WeightMin,Type)
%Gibt alle incoming Komponenten deren outgoing Komponenten ihrer Isconnected=true und ihrer IsReady=true zurück.
%alle <out,IsConnected=true,IsReady=true> --> <In>
TimeEdgeDirectedConnectedWaiting(NameIn,TypeNameIn,WeightMin,Type):- #count{X:TimeEdgeOutgoingWaiting(X,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type)}=0,TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type).

%TimeEdgeOutgoing(StationNameOut,StationTypeNameOut,StationNameIn,StationTypeNameIn,EchtzeitMin,Type). Dies ist eine Hilfsvariable.
%Gibt alle incoming StationTypen mit IsReady=false und Isconnected=true: <out> ---->  <In>
TimeEdgeOutgoingWaiting(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):-TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type), TimeEdgeAll(NameOut,TypeNameOut,_),#count{X:TimeEdge(X,TypeNameOut,NameIn,TypeNameIn,_,true,true,_)}>0,#count{Y:TimeEdgeStatusDirectedConnectedWaitingOrReady(Y,TypeNameOut,_,_,_,_)}=0. %Hilfsvariable
%TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type): Gibt alle Komponenten zurück, die ihr IsReady auf true ist.
TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,_,_,Echtzeit,false,true,_),TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,true,_). %Hilfsvariable
%TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type): Gibt alle Komponenten zurück, die ihr IsWaiting auf true ist.
TimeEdgeStatusDirectedConnectedWaitingOrReady(NameOut,TypeNameOut,NameIn,TypeNameIn,WeightMin,Type):- TimeEdgeDirectedConnectedWeightMin(NameIn,TypeNameIn,WeightMin,Type),TimeEdgeStatus(NameOut,TypeNameOut,_,_,_,_,_,Echtzeit,true,false,_),TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,_,true,true,_).


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

%TimeEdgeNoDirectedBothConnectedWeightMin(Name2,TypeName2,WeightMin,Type): Type is either station or agent
%Return all komponents with its corresponding weight. only both connected edge are importance.
TimeEdgeNoDirectedBothConnectedWeightMin(Name,TypeName,WeightMin,Type):-#min{EchtzeitX:TimeEdgeOutIn(_,_,Name,TypeName,_,false,false,EchtzeitX)}=WeightMin,TimeEdgeAll(Name,TypeName,Type).

%++++++++++++++++++++++ timeEdge nodirected both conected ++++++++++++++++++++++++++++++

TimeEdgeOutIn(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,Y,Z):-TimeEdge(NameIn,TypeNameIn,NameOut,TypeNameOut,X,false,Y,Z),Y!=true.
TimeEdgeOutIn(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,Y,Z):-TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,Y,Z),Y!=true.

%++++++++++++++++++++++ timeEdge nodirected but with logical And. 

TimeEdgeOutInAnd(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,both,Z):-TimeEdgeOutIn(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,both,Z).
TimeEdgeOutInAnd(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,true,Z):-TimeEdge(NameOut,TypeNameOut,NameIn,TypeNameIn,X,false,true,Z).

%+++++++++++++++++++++++++++++++Return all stations and agents. Type=0 is Station and type=1 is agent. ++++++++++++++++++++
TimeEdgeAll(Name,TypeName,0):-Station(Name,TypeName,_,_,_).
TimeEdgeAll(Name,TypeName,1):-Agent(Name,TypeName,_,_,_).
%+++++++++++++++++++++++++++ from TimeEdgeState, TimeEdgeStatus will be genered . +++++++++++++++
%%TimeEdgeStatus(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,EdgeType,EchTime,Type,CountTick,IsWaiting,IsReady,IsFinish). convert the CountTick to TimeUnit.
TimeEdgeStatus(Name1,TypeName1,Name2,TypeName2,EdgeType,EchTime,Type,TimeUnit,IsWaiting,IsReady,IsFinish):-TimeEdgeState(Name1,TypeName1,Name2,TypeName2,EdgeType,EchTime,Type,CountTime,IsWaiting,IsReady,IsFinish),ZeitEinheit(Unit),TimeUnit=CountTime/Unit.
%+++++++++++++++++++++++++++ from TimeEdgeStatus, TimeEdgeAgent and TimeEdgeStation will be genered  +++++++++++++++
%++ The agent has the time edge and it is located at station.
TimeEdgeAgent(StationName,StationTypeName,AgentName,AgentTypeName,EdgeType,EchTime,CountTime,IsWaiting,IsReady,IsFinish):-TimeEdgeStatus(AgentName,AgentTypeName,StationName,StationTypeName,EdgeType,EchTime,1,CountTime,IsWaiting,IsReady,IsFinish).
%++ The station has the time edge and agent is visiting it. 
TimeEdgeStation(StationName,StationTypeName,AgentName,AgentTypeName,EdgeType,EchTime,CountTime,IsWaiting,IsReady,IsFinish):-TimeEdgeStatus(StationName,StationTypeName,AgentName,AgentTypeName,EdgeType,EchTime,0,CountTime,IsWaiting,IsReady,IsFinish).


%###################################### Definition of Desires ######################################################
%Diese sind Hilfslitterale
%CurrentAgent(AgentName,AgentTypeName).
%CurrentAgent(de1,de).
%CurrentAgent(usa1,usa). % Der Agentbesitzer des Beliefbases.
%Diese Prädikat gibt die Hinweise über die Station, wo sich der Agent gerade befindet oder wohin er geht.
%CurrentStation(AgentName,AgetnTypeName,StationName,StationTypeName,IsInStation,HasChoice). 
%hasChoice is true when a agent has choosen a station and false otherwise.
%++time:
%	wenn isMove=true: gibt an, wie lang der Weg eines agenten ist.
%	wenn isMove=false: gibt an, wie lang agent genau in der station bleiben muss.
%++IsInStation: 
%	true= if der Agent in station ist and 
%	false otherwise: agent is moving or waiting at the station.
%Must be add to the desires at the first time.
%CurrentStation(de1,de,werk1,werk,false,true).
%CurrentStation(usa1,usa,other1,other,false,false). % will be added, deleted or updated by agent.
%Diese ist vorhanden, wenn der Agent unterwegs ist.
%MoveToStation(AgentName,CurentStationName,TargetStationName,Distance,Pos) 
%%% MoveToStation muss momentant warten.
%MoveToStation(cl1,li3,ma2,5,4). % will be added, deleted or updated by agent.

-CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,false,false):- 
CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,_,_), not CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,false,false).

%Agent(AgentName,AgentTypeName,_,_,_),Station(StationName,StationTypeName,_,_,_)










%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Information about the station for plan &&&&&&&&&&&&&&&&&&&&&&&&&&&
%StationInfo(StationName,StationTypeName,Time,ItemMotiv).
%ItemMotiv	=0, : Agent can only take item, because there are no ingoing stations.
%	=1, : Agent can only place item, because there are no outgoing stations.
%	=2, : Agent can only place item, because there are outgoing stations, but agent cannot visit it.
%	=3, : Agent can only take item, because there are ingoing stations, but agent cannot visit it.
%	=4, : Agent can take and place item, because there are ingoing and outgoing stations, and the agent can visit it.
%	=5, : Agent can place and cannot take item, because there are ingoing and outgoing stations, and the condition to take is not fullfilly.
%	=6, Agent cannot take and not place item, because there are neither ingoing and or no outgoing stations.

StationInfo(StationName,StationTypeName,Time,0):-Station(StationName,StattionType,_,_,_),StationType(StationTypeName,_,_,Time,_,_,_,_,_),ItemErfullNoAus(StationName,StationTypeName),not ItemErfullNoEin(StationName,StationTypeName).
StationInfo(StationName,StationTypeName,Time,1):-Station(StationName,StattionType,_,_,_),StationType(StationTypeName,_,_,Time,_,_,_,_,_),not ItemErfullNoAus(StationName,StationTypeName),ItemErfullNoEin(StationName,StationTypeName).

StationInfo(StationName,StationTypeName,Time,2):-Station(StationName,StattionType,_,_,_),StationType(StationTypeName,_,_,Time,_,_,_,_,_),not ItemErfullNoEin(StationName,StationTypeName),AgentNoAusStation(AgentName,StationTypeName).
StationInfo(StationName,StationTypeName,Time,3):-Station(StationName,StattionType,_,_,_),StationType(StationTypeName,_,_,Time,_,_,_,_,_),not ItemErfullNoAus(StationName,StationTypeName),AgentNoEinStation(AgentName,StationTypeName).

StationInfo(StationName,StationTypeName,Time,4):-Station(StationName,StattionType,_,_,_),StationType(StationTypeName,_,_,Time,_,_,_,_,_),not ItemErfullNoAus(StationName,StationTypeName),not ItemErfullNoEin(StationName,StationTypeName).

StationInfo(StationName,StationTypeName,Time,5):-Station(StationName,StattionType,_,_,_),StationType(StationTypeName,_,_,Time,_,_,_,_,_),ItemErfullNoAus(StationName,StationTypeName),ItemErfullNoEin(StationName,StationTypeName).

%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Engeischaft von ChoiceStation &&&&&&&&&&&&&&&&&&&&&&&&&&&
%HasChoiceStation(AgentName,AgentTypeName). The Agent has choosen a station
HasChoiceStation(AgentName,AgentTypeName):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,_,_,false,false).
%AllConditionErfullChoiceStation(AgentName,StationName)
%Check if frequency and necessity are fillfully .
AllConditionErfullChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Status):- CurrentAgent(AgentName,_),VisitEdge(AgentName,_,StationName,_,_), NecErfull(AgentName,StationName),FreqErfullStation(StationName), FreqErfullAgent(AgentName),TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Status).

%ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Time,ItemMotiv).
%motiv: 0=agent and station no time edge;
%	1=agent has time edge and station no;
%	2=agent hasn't time edge and station has;
%	3=agent and station haven time edge.
%

%This is use when the agent has choice a station and cannot enter it 
%because the conditions isn't satisfied.
ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,10,Time,ItemMotiv):-CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,false,true),CurrentAgent(AgentName,AgentTypeName),StationInfo(StationName,StationTypeName,Time,ItemMotiv).
%tihs is use when the agent hasn't already choose a station.
ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Time,ItemMotiv):-HasChoiceStation(AgentName,AgentTypeName),AllConditionErfullChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,_),CurrentAgent(AgentName,AgentTypeName),StationInfo(StationName,StationTypeName,Time,ItemMotiv).


%###################################### Definition of atomic Intention ######################################################
%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Engeischaft von EnterStation &&&&&&&&&&&&&&&&&&&&&&&&&&&
%EnterStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv).
%motiv: 0=agent and station no time edge;
%	1=agent has time edge and station no;
%	2=agent hasn't time edge and station has;
%	3=agent and station haven time edge.
%Wenn agent und station keine TimeEdge haben.
%EnterStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,false,_),AllConditionErfullEnterStation(AgentName,StationName),TimeEdgeEnterStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv).

EnterStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Status):-CurrentAgent(AgentName,AgentTypeName),ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,_,_,_),AllConditionErfullEnterStation(AgentName,StationName,Motiv,Status).


%AllConditionErfullEnterStation(AgentName,StationName)
%Check if frequency and necessity are fillfully .
AllConditionErfullEnterStation(AgentName,StationName,Motiv,Status):- CurrentAgent(AgentName,_),VisitEdge(AgentName,_,StationName,_,_),SpaceSizeErfull(AgentName,_,StationName,_),MaxPriorityGet(AgentName,StationName),TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Status).
%This use to the get the components with the best priority.
MaxPriorityGet(AgentName,StationName):- MaxPriority(AgentName,StationName).
%This use to alows the agent to enter a station because it has already choose it.
MaxPriorityGet(AgentName,StationName):- CurrentStation(AgentName,_,StationName,_,false,true).

%Status	3=the both components are waiting without waiting time
%Status	2=the both components are reading after the waiting time 
%Status 1=one component is ready and the other can begin to count.
%Status 0=the both components are waiting

%When agent and station haven't timeEdge
TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,0,0):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),NoTimeEdge(StationName,StationTypeName,_),NoTimeEdge(AgentName,AgentTypeName,_).

%When agent and station haven timeEdge and are waiting
TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,6,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),TimeEdgeComp(AgentName,AgentTypeName,_,StatusTyp1,Status1),TimeEdgeComp(StationName,StationTypeName,_,StatusTyp,Status),StatusTyp<=StatusTyp1,StatusTyp!=1.

TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,6,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),TimeEdgeComp(AgentName,AgentTypeName,_,StatusTyp,Status),TimeEdgeComp(StationName,StationTypeName,_,StatusTyp1,Status1),StatusTyp<=StatusTyp1,StatusTyp!=1.
%When agent hasn't timeEdge and station is waiting
TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,5,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),NoTimeEdge(AgentName,AgentTypeName,_),TimeEdgeComp(StationName,StationTypeName,_,0,Status).
%When station hasn't timeEdge and agent is waiting
TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,4,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),NoTimeEdge(StationName,StationTypeName,_),TimeEdgeComp(AgentName,AgentTypeName,_,0,Status).

%When agent and station haven timeEdge and are ready
TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,3,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),TimeEdgeComp(AgentName,AgentTypeName,_,1,Status1),TimeEdgeComp(StationName,StationTypeName,_,1,Status),Status<=Status1.

TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,3,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),TimeEdgeComp(AgentName,AgentTypeName,_,1,Status),TimeEdgeComp(StationName,StationTypeName,_,1,Status1),Status<Status1.

%When agent hasn't timeEdge and station is ready
TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,2,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),NoTimeEdge(AgentName,AgentTypeName,_),TimeEdgeComp(StationName,StationTypeName,_,1,Status).
%When station hasn't timeEdge and agent is ready
TimeEdgeGet(AgentName,AgentTypeName,StationName,StationTypeName,1,Status):-VisitEdge(AgentName,AgentTypeName,StationName,StationTypeName,_),NoTimeEdge(StationName,StationTypeName,_),TimeEdgeComp(AgentName,AgentTypeName,_,1,Status).
%TimeEdgeComp(Name,TypeName,Type,StatusTyp,Status)


%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Engeischaft von ProdcutConsumItem &&&&&&&&&&&&&&&&&&&&&&&&&&&
%ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,ItemNumber,Motiv). This bedeutet: ein agent bekommt ein item and liefert ein anderen 
%Motiv	=0, : Agent can only take item, because there are no ingoing stations.
%	=1, : Agent can only place item, because there are no outgoing stations.
%	=2, : Agent can only place item, because there are outgoing stations, but agent cannot visit it.
%	=3, : Agent can only take item, because there are ingoing stations, but agent cannot visit it.
%	=4, : Agent can take and place item, because there are ingoing and outgoing stations, and the agent can visit it.
%	=5, : Agent can place and cannot take item, because there are ingoing and outgoing stations, and the condition to take is not fullfilly.
%ItemNumber =0, infinitly item can be take.
%	    =n, n items can be take or place depend of motiv.
%Agent can only take item, because there are no ingoing stations.
ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,0,0):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,true,true),canAgentLoadItem(AgentName,StationName),ItemBeladen(StationName,StationTypeName),ItemErfullNoEin(StationName,StationTypeName), not ItemErfullNoAus(StationName,StationTypeName).

%Agent can only place item, because there are no outgoing stations.
ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,LadeItemMin,1):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,true,true),ItemPlaceInStations(AgentName,StationName,LadeItemMin),ItemErfullNoAus(StationName,StationTypeName), not ItemErfullNoEin(StationName,StationTypeName).

%Agent can only place item, because there are outgoing stations, but agent cannot visit it.
ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,LadeItemMin,2):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,true,true),ItemPlaceInStations(AgentName,StationName,LadeItemMin),AgentNoAusStation(AgentName,StationTypeName).

%Agent can only take item, because there are ingoing stations, but agent cannot visit it.
ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,LadeItemMin,3):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,true,true),canAgentLoadItem(AgentName,StationName),ItemBeladen(StationName,StationTypeName),ItemProductViaInStations(AgentName,StationName,LadeItemMin),AgentNoAusStation(AgentName,StationTypeName).

%Agent can take and place item, because there are ingoing and outgoing stations, and the agent can visit it.
ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,LadeItemMin,4):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,true,true),ItemProductViaInStations(AgentName,StationName,LadeItemMin),not ItemErfullNoEin(StationName,StationTypeName),not ItemErfullNoAus(StationName,StationTypeName).

%Agent can place and cannot take item, because there are ingoing and outgoing stations, and the condition to take is not fullfilly.
ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,LadeItemMin,5):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,true,true),ItemPlaceInStations(AgentName,StationName,LadeItemMin),not ItemErfullNoEin(StationName,StationTypeName),not ItemErfullNoAus(StationName,StationTypeName).


%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Engeischaft von StationTyp into whom the agent has a item   &&&&&&&&&&&&&&&&&&&&&&&&&&&
%StationTypItem(AgentName,StationNameIn,StationTypNameIn,StationTypeNameOut,item). Out------>In
%Item is the dimension of item of the StationTypeNameOut.
StationTypItem(AgentName,StationNameIn,StationTypNameIn,StationTypeNameOut,item):-StationType(StationTypeNameOut,_,_,_,_,_,Item,_,_),CurrentStation(AgentName,_,StationNameIn,StationTypNameIn,false,false),CurrentAgent(AgentName,_),PlacedEdge(_,StationTypeNameOut,StationNameIn,StationTypNameIn,_,true).

%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Engeischaft von LeaveStation &&&&&&&&&&&&&&&&&&&&&&&&&&&
%LeaveStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv).
LeaveStation(AgentName,AgentTypeName,StationName,StationTypeName,0):-CurrentAgent(AgentName,AgentTypeName),CurrentStation(AgentName,AgentTypeName,StationName,StationTypeName,true,true),Station(StationName,StattionType,_,_,_).


%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Engeischaft von VisitStation &&&&&&&&&&&&&&&&&&&&&&&&&&&
%VisitStation(AgentName,AgentTypeName,StationName,StationTypeName).


%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Engeischaft von Know-How &&&&&&&&&&&&&&&&&&&&&&&&&&&
%KnowHow(AgentName,AgentTypeName,StationName,StationTypeName,maxFreeSpace,Value).

KnowHow(AgentName,AgentTypeName,StationName,StationTypeName,maxFreeSpace,Value):-ChoiceAgentStationFreePlace(AgentName,AgentTypeName,StationName,StationTypeName,FreePlace),Value=#max{X:ChoiceAgentStationFreePlace(AgentName,AgentTypeName,_,_,X)},Value==FreePlace.

%Gibt einen Agenten mit einer ausgewählten Station und ihre entsprechenden freien Plätze.
ChoiceAgentStationFreePlace(AgentName,AgentTypeName,StationName,StationTypeName,FreePlace):-ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,_,_,_),Station(StationName,StationType,_,_,Space),StationType(StationTypeName,_,_,_,_,_,_,StationSpace,_),FreePlace=StationSpace-Space.

%Gibt die Station, die am wenigstens besucht wird.
KnowHow(AgentName,AgentTypeName,StationName,StationTypeName,minVisited,Value):-ChoiceAgentStationFreq(AgentName,AgentTypeName,StationName,StationTypeName,Freq),Value=#min{X:ChoiceAgentStationFreq(AgentName,AgentTypeName,_,_,X)},Freq==Value.


%Gibt einen Agenten mit einer ausgewählten Station und ihre entsprechende Besuchshäufigkeit.
ChoiceAgentStationFreq(AgentName,AgentTypeName,StationName,StationTypeName,Freq):-ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,_,_,_),Station(StationName,StationType,Freq,_,_).
