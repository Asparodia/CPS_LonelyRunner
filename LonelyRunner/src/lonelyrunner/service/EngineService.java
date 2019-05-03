package lonelyrunner.service;

import java.util.ArrayList;
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
	
	// Constructor
	
	//\pre init(ES,posChar,posGuards,posItems) \req EditableScreenService::isPlayable(ES) == True 
		//\and getCellNature(ES,posChar.x,posChar.y) == EMP
		//\and \forall pos:Couple<Int,Int> in posGuards ((ScreenService::getCellNature(ES,pos.x,pos.y) = EMP) \and (pos.x != posChar.x \or pos.y != posChar.y))
		//\and \forall posI:Couple<Int,Int> in posItems ((ScreenService::getCellNature(ES,posI.x,posI.y) = EMP) \and ((posI.x != posChar.x \or posI.y != posChar.y \and ( \forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \or  posI.y!=pos.y ))) )
			//\and getCellNature(ES,posI.x,posI.y-1) == {PLT,MTL}
	public void init(EditableScreenService es,Couple<Integer,Integer> posChar, ArrayList<Couple<Integer,Integer>> posGuards, ArrayList<Couple<Integer,Integer>> posItems );
	
	// Operators
	//\post:  \forall T:Item \in getTreasures()
		// T \in EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
			//\implies T not in getTreasures() \and getScore() == getScore()@pre + 1
	//\post: \forall G:Guard \in getGuards() 
		// G \in EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
			//\implies  getNbLives() == getNbLives()@pre - 1
	//\post: \forall G:Guard \in getGuards()@pre
		// t:Item \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)) \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G)) == HOL
			//\implies t \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)-1)
		// t:Item \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)) \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G)) != HOL
				//\implies t \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G))
	//\post: \forall H:Hole \in getHoles()
		//H.time == 15 \and PlayerService p in EnvironmentService::getCellContent(H.x,H.y)
			//\implies getNbLives() == getNbLives()@pre - 1
		//H.time == 15 \and GuardService g in EnvironmentService::getCellContent(H.x,H.y)
			//\implies getHoles() == getHoles()@pre \minus H \and g.getHdt() == g.initPos.x \and  g.getWdt() == g.initPos.y
		//H.time == 15 \implies getHoles() == getHoles()@pre \minus H
	//\post: getTreasures() == empty \implies getStatus() == Win
	//\post: getNbLives() <= 0 \implies getStatus() == Loss
	public void step();
	
	public void setCommand(Move c);
	
	public void setNbLives(int l);
	
	// Invariants
	//getPlayer() \in {EnvironmentService::getCellContent(getEnvironment(),getWdt(getPlayer()),getHgt(getPlayer()))}
	//\forall G:Guard \in getGuards() 
		// G \in {EnvironmentService::getCellContent(getEnvironment(),getWdt(G),getHgt(G))}
	//\forall T:Item \in getTreasures() 
		// T \in {EnvironmentService::getCellContent(getEnvironment(),getWdt(T),getHgt(T))}
	//\forall H:Hole \in getHoles()
		// EnvironmentService::getCellNature(getEnvironment(),H.x,H.y) == HOL

	
}
