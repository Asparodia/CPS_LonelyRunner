package lonelyrunner.service;

import java.util.ArrayList;
import java.util.Vector;

import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public interface EngineService {
	
	// Observators
	
	public int getNbLives();
	
	public int getScore();
	
	public EnvironmentService getEnvironment();
	
	public PlayerService getPlayer();
	
	public ArrayList<GuardService> getGuards();
	
	public ArrayList<Item> getTreasures();
	
	public Status getStatus();
	
	public Move getNextCommand();
	
	public ArrayList<Hole> getHoles();
	
	public ArrayList<Vector<Integer>> guardInitPos();
	
	// Constructor
	
	
	//\pre init(ES,posChar,posGuards,posItems) \req EditableScreenService::isPlayable(ES) == True \and posItems != null
		//\and getCellNature(ES,posChar.x,posChar.y) == EMP \and 0<=posChar.x<ES.getWidth()  0<=posChar.y<ES.getHeigth()
		//\and \forall pos:Couple<Int,Int> in posGuards ((ScreenService::getCellNature(ES,pos.x,pos.y) = EMP) \and (pos.x != posChar.x \or pos.y != posChar.y)) \and 0<=pos.x<ES.getWidth()  0<=pos.y<ES.getHeigth()
		//\and \forall posI:Couple<Int,Int> in posItems ((ScreenService::getCellNature(ES,posI.x,posI.y) = EMP) \and ((posI.x != posChar.x \or posI.y != posChar.y \and ( \forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \or  posI.y!=pos.y ))) )
			//\and getCellNature(ES,posI.x,posI.y-1) == {PLT,MTL} \and 0<=posI.x<ES.getWidth()  0<=posI.y<ES.getHeigth()
	//\post : getPlayer() \in getEnvironment().getCellContent(posChar.x,posChar.y) \and \forall x:int \and y:int \with 0<=x<getEnvironment().getWdt() \and  0<=y<getEnvironment().getHgt() \and (x!= posChar.x \and y!= posChar.y)
		// getPlayer() \not in EnvironmentService::getCellContent(x,y)
	//\post :\forall G:GuardService in getGuards()
		//G \in getEnvironment().getCellContent(G.getWdt(),G.getHgt()) \and \forall x:int \and y:int \with 0<=x<getEnvironment().getWdt() \and  0<=y<getEnvironment().getHgt() \and (x!= posChar.x \and y!= posChar.y)
			// getPlayer() \not in EnvironmentService::getCellContent(x,y)
	//\post :\forall I:Items in getTreasure()
			//I \in getEnvironment().getCellContent(I.getCol(),I.getHgt()) \and \forall x:int \and y:int \with 0<=x<getEnvironment().getWdt() \and  0<=y<getEnvironment().getHgt() \and (x!= posChar.x \and y!= posChar.y)
				// getPlayer() \not in EnvironmentService::getCellContent(x,y)
	//\post : getStatus() == Playig
	//\post: getNbLives() == 2
	//\post: getScore() == 0
	//\post: getPlayer().getEngine() == this
	//\post: \forall V:Vector<Integer> in guardInitPos()
		// exist G:GuardService \in getGuards() G.id = V.id \and G.getWdt() = V.x \and G.getHgt() = V.y
	public void init(EditableScreenService es,Couple<Integer,Integer> posChar, ArrayList<Couple<Integer,Integer>> posGuards, ArrayList<Couple<Integer,Integer>> posItems );
	
	// Operators
	
	//\post getNextCommande() == c
	public void setCommand(Move c);
	
	//\post : getNbLives() == l
	public void setNbLives(int l);
	
	//\post:  \forall T:Item \in getTreasures()@pre
		// T \in EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
			//\implies T not in getTreasures() \and getScore() == getScore()@pre + 1
	//\post: \forall G:Guard \in getGuards() 
		// G \in EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
			//\implies  getNbLives() == getNbLives()@pre - 1
//	\post : getPlayer(step(E)) = Player::step(getPlayer(E))
//	\post : forall G::Guard in getGuards(step(E)) and G2 in getGuards(E)
//				G::id = G2::id implie G = Guard::step(G2)
//	\post : forall G:Guard in getGuards(E)
//	 exists t:Item in Environment::getCellContent(getEnvironment(G),Guard::getWdt(G),Guard::getHgt(G)) 
//			and forall G2:Guard in getGuards(step(E))
//				G2::id == G::id and (Environment::getCellNature(getEnvironment(step(E)),Guard::getWdt(G2),Guard::getHgt(G2)) = EMP 
//					and (Environment::getCellNature(getEnvironment(step(E)),Guard::getWdt(G2),Guard::getHgt(G2)-1) in [MTL, PLT]
//					 	or exist Character c in Environment::getCellContent(getEnvironment(step(E)),Guard::getWdt(G),Guard::getHgt(G2)-1) ) )
//			implies t in EnvironmentService::getCellContent(getEnvironment(step(E)),Guard::getWdt(G),Guard::getHgt(G))
	//\post: \forall H:Hole \in getHoles()@pre
		//H.time < 15 \implies H in getHoles() \and H.time +1 == getHoles().get(H)
		//H.time == 15 \and PlayerService p in EnvironmentService::getCellContent(H.x,H.y)
			//\implies getNbLives() == getNbLives()@pre - 1
		//H.time == 15 \implies getHoles() == getHoles()@pre \minus H
		//H.time == 15 \and \for G:Guard in getGuard()@pre G in EnvironmentService::getCellContent(H.x,H.y)
			//\implies getHoles() == getHoles()@pre \minus H \and G.getHdt() == guardInitPos().get(G).x \and  g.getWdt() == guardInitPos().get(G).y
	public void step();
	
	
	// Invariants
	//getPlayer() \in {EnvironmentService::getCellContent(getEnvironment(),getWdt(getPlayer()),getHgt(getPlayer()))}
	//\forall G:Guard \in getGuards() 
		// G \in {EnvironmentService::getCellContent(getEnvironment(),getWdt(G),getHgt(G))}
	//\forall T:Item \in getTreasures() 
		// T \in {EnvironmentService::getCellContent(getEnvironment(),getWdt(T),getHgt(T))}
	//\forall H:Hole \in getHoles()
		// EnvironmentService::getCellNature(getEnvironment(),H.x,H.y) == HOL
	//\post: getTreasures() == empty \implies getStatus() == Win
	//\post: getNbLives() < 0 \implies getStatus() == Loss

	
}
