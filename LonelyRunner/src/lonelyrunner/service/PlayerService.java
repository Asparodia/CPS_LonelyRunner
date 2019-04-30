package lonelyrunner.service;

public interface PlayerService extends/*include*/ CharacterService{
	
	// Observators 
	
	public EngineService getEngine();
	
	// Constructors
	
	public void init(ScreenService s, int x,int y,EngineService engine);
	
	// Operators
	
	public void step();
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == getWdt()
	//\post: (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT} \or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == PLT \and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1)
				//\implies  EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == HOL
					////\and getEngine()::getHoles() ==  getEngine()@pre::getHoles() Union Hole(getWdt()@pre-1,getHgt()@pre-1,0)
	public void digL();
	
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == getWdt()
	//\post: (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT} \or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == PLT \and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1)
				//\implies  EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == HOL 
					//\and getEngine()::getHoles() ==  getEngine()@pre::getHoles() Union Hole(getWdt()@pre+1,getHgt()@pre-1,0)
	public void digR();
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
		//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	public void doNeutral();
}
