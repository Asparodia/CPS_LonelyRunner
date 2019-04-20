package lonelyrunner.service;

import lonelyrunner.service.utils.Move;

public interface GuardService extends CharacterService {
	
	// Observators
	
	public int getId();
	public Move getBehaviour();
	public CharacterService getTarget(); // a changer maybe par player
	public int getTimeInHole();
	
	// Operators
	
	//\pre climbLeft() \req EnvironmentService::getCellNature(getEnvi()@pre,getHgt()@pre,getWdt()@pre) == HOL
	public void climbLeft();
	
//	public void climbRight();
	
	public void step();
	
	// Invariants
}
