package lonelyrunner.service;

public interface PlayerService extends/*include*/ CharacterService{
	
	// Observators 
	
	public EngineService getEngine();
	
	// Constructors
	
	//\pre: init(S,x,y,engine) \req EnvironmentService::getCellNature(S,x,y) = EMP
	//\post : this in getEngine().getEnvironment().getCellContent(x,y)
	public void init(ScreenService s, int x,int y,EngineService engine);
	
	// Operators
	
	//\post: getNextCommand()@pre == LEFT
		//\and clone@pre:PlayerService 
			//\implies getWdt() == clone@pre.goLeft.getWdt() \and getHgt() = clone@pre.goLeft().getHgt()
	//\post: getNextCommand()@pre == RIGHT
			//\and clone@pre:PlayerService 
				//\implies getWdt() == clone@pre.goRight.getWdt() \and getHgt() = clone@pre.goRight().getHgt()
	//\post: getNextCommand()@pre == UP
			//\and clone@pre:PlayerService 
				//\implies getWdt() == clone@pre.goUp.getWdt() \and getHgt() = clone@pre.goUp().getHgt()
	//\post: getNextCommand()@pre == DOWN
			//\and clone@pre:PlayerService 
				//\implies getWdt() == clone@pre.goDown.getWdt() \and getHgt() = clone@pre.goDown().getHgt()
	//\post: getNextCommand()@pre == Neutral
			//\and clone@pre:PlayerService 
				//\implies getWdt() == clone@pre.doNeutral.getWdt() \and getHgt() = clone@pre.doNeutral.getHgt()
	
	public void step();
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
			//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
				//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: getWdt()@pre > 0 \and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT,LAD} \or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == PLT 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) not in {PLT, MTL}
			//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre 
			//\and not exist i:Item in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre))
				//\implies  EnvironmentService::getCellNature(getEnvi(),getWdt()-1,getHgt()-1) == HOL and getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre 
					//\and getEngine().getHoles() ==  getEngine()@pre::getHoles() Union Hole(getWdt()@pre-1,getHgt()@pre-1,0)
					//\and \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight()  (x!=u or y!=v) EnvironmentService::getCellNature()@pre = getCellNature()
	
	public void digL();
	
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
			//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
				//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: getWdt()@pre != EnvironmentService::getWidth()-1 \and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT,LAD} \or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == PLT 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) not in {PLT, MTL}
			//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) 
			//\and not exist i:Item in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre))
				//\implies  EnvironmentService::getCellNature(getEnvi(),getWdt()+1,getHgt()-1) == HOL getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre 
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
