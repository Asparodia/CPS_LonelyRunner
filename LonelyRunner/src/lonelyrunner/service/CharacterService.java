package lonelyrunner.service;

public interface CharacterService {
	// Operators
	
	public EnvironmentService getEnvi();
	public int getHgt();
	public int getWdt();
	
	//Constructors
	//\pre: init(S,x,y) req EnvironmentService::getCellNature(S,x,y) = EMP
	public void init(ScreenService s, int x,int y);
	
	//Operators
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == 0 \implies getWdt() == getWdt()@pre 
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \in {MTL,PLT,LAD} \implies
		// getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL}
		//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
		//\implies getWdt() == getWdt()@pre
	//\post: \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \implies getWdt() == getWdt()@pre
	//\post: getWdt() != 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \not in {MTL,PLT}
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\and \not (\exist Character c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre)) ) \implies getWdt() == getWdt()@pre - 1
	public void goLeft();
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == EnvironmentService::getWidth()-1 \implies getWdt() == getWdt()@pre 
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \in {MTL,PLT,LAD} \implies
		// getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \not in {PLT,MTL}
		//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
		//\implies getWdt() == getWdt()@pre
	//\post: \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \implies getWdt() == getWdt()@pre
	//\post: getWdt()@pre != 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \not in {MTL,PLT}
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\and \not (\exist Character c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre+1,getHgt()@pre)) ) \implies getWdt() == getWdt()@pre +1
	public void goRight();
	
	//\post: getWdt() == getWdt()@pre
	//\post: getHgt()@pre == EnvironmentService::getHeight()-1 \implies getHgt() == getHgt()@pre 
	//################################### NOT DONE YET ##############################################
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \in {MTL,PLT,LAD} \implies
		// getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \not in {PLT,MTL}
		//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
		//\implies getWdt() == getWdt()@pre
	//\post: \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \implies getWdt() == getWdt()@pre
	//\post: getWdt()@pre != 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \not in {MTL,PLT}
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\and \not (\exist Character c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre+1,getHgt()@pre)) ) \implies getWdt() == getWdt()@pre +1
	public void goUp();
	public void goDown();
	
}
