package lonelyrunner.service;

public interface PlayerService extends/*include*/ CharacterService{
	
	// Observators 
	
	public EngineService getEngine();
	
	// Operators
	
	public void step();
	
}
