package lonelyrunner.service;

public interface PlayerService extends/*include*/ CharacterService{
	
	// Observators 
	
	public EngineService getEngine();
	
	// Constructors
	
	//\pre: init(S,x,y) \req EnvironmentService::getCellNature(S,x,y) = EMP
	//\post : this in getEngine().getEnvironment().getCellContent(x,y)
	public void init(ScreenService s, int x,int y,EngineService engine);
	
	// Operators
	
	public void step();
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
			//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
				//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: getWdt()@pre > 0 \and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT} \or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == PLT 
			//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre 
			//\and not exist i:Item in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre))
				//\implies  EnvironmentService::getCellNature(getEnvi(),getWdt()-1,getHgt()-1) == HOL
					//\and getEngine().getHoles() ==  getEngine()@pre::getHoles() Union Hole(getWdt()@pre-1,getHgt()@pre-1,0)
					//\and \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight()  (x!=u or y!=v) EnvironmentService::getCellNature()@pre = getCellNature()
	
	public void digL();
	
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
			//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
				//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: getWdt()@pre != EnvironmentService::getWidth()-1 \and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT} \or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == PLT 
			//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) 
			//\and not exist i:Item in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre))
				//\implies  EnvironmentService::getCellNature(getEnvi(),getWdt()+1,getHgt()-1) == HOL 
					//\and getEngine()::getHoles() ==  getEngine()@pre::getHoles() Union Hole(getWdt()@pre+1,getHgt()@pre-1,0)
					//\and \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight()  (x!=u or y!=v) EnvironmentService::getCellNature()@pre = getCellNature()
	public void digR();
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
		//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	public void doNeutral();
	
	// Invariants 
	
	// EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) \in {EMP,HOL,LAD,HDR}
	//\exist Player p in EnvironmentService::getCellContent(getEnvi(),getWdt(),getHgt()) \implies p = this
}
