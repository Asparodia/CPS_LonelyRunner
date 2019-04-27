package lonelyrunner.service;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public interface EngineService {
	
	// Observators
	
	public EnvironmentService getEnvironment();
	
	public PlayerService getPlayer();
	
	public ArrayList<GuardService> getGuards();
	
	public ArrayList<Item> getTreasures();
	
	public Status getStatus();
	
	public Move getNextCommand();
	
	public HashMap<Couple<Integer,Integer>,Integer> getHoles();
	
	// Constructor
	
	//\pre init(ES,posChar,posGuards,posItems) \req EditableScreenService::isPlayable(ES) == True 
		//\and getCellNature(ES,posChar.x,posChar.y) == EMP
		//\and \forall pos:Couple<Int,Int> in posGuards ((ScreenService::getCellNature(ES,pos.x,pos.y) = EMP) \and (pos.x != posChar.x \and pos.y != posChar.y))
		//\and \forall posI:Couple<Int,Int> in posItems ((ScreenService::getCellNature(ES,posI.x,posI.y) = EMP) \and ((posI.x != posChar.x \and posI.y != posChar.y \and ( \forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \and  posI.y!=pos.y ))) )
			//\and getCellNature(ES,posI.x,posI.y-1) == {PLT,MTL}
	public void init(EditableScreenService es,Couple<Integer,Integer> posChar, ArrayList<Couple<Integer,Integer>> posGuards, ArrayList<Couple<Integer,Integer>> posItems );
	
	// Operators
	
	// \post:  \forall T:Item \in getTreasures()@pre
		// T \in EnvironmentService::getCellContent(getEnvironment(getPlayer())@pre,getHgt(getPlayer())@pre,getWdt(getPlayer())@pre)
			//\implies T not in getTreasures()
	// \post: \forall G:Guard \in getGuards() 
			// G \in EnvironmentService::getCellContent(getEnvironment(getPlayer()),getHgt(getPlayer()),getWdt(getPlayer()))
				//\implies  getStatus() == Loss
	// \post: getTreasures() == empty \implies getStatus() == Win
	public void step();
	
	// Invariants

	// getPlayer() \in {EnvironmentService::getCellContent(getEnvironment(),getHgt(getPlayer()),getWdt(getPlayer()))}
	//\forall G:Guard \in getGuards() 
		// G \in {EnvironmentService::getCellContent(getEnvironment(),getHgt(G),getWdt(G))}
	//\forall T:Item \in getTreasures() 
			// T \in {EnvironmentService::getCellContent(getEnvironment(),getHgt(T),getWdt(T))}
	
	public void setCommand(Move c);
}
