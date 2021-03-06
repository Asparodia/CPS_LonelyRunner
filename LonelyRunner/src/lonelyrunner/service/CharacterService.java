package lonelyrunner.service;

public interface CharacterService {
	// Observators
	
	public EnvironmentService getEnvi();
	public int getHgt();
	public int getWdt();
	
	// Constructors
	
	//\pre: init(S,x,y) \req EnvironmentService::getCellNature(S,x,y) = EMP
	public void init(ScreenService s, int x,int y);
	
	// Operators
	
	//\post: getWdt()@pre == 0 \implies getWdt() == getWdt()@pre \and getHgt()@pre == getHgt()
	//\post: getWdt()@pre > 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \in {MTL,PLT}
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD})
			//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
		//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: ( getWdt()@pre > 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \not in {MTL,PLT} )
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\implies getWdt() == getWdt()@pre - 1 \and getHgt()@pre == getHgt()
	public void goLeft();
	
	//\post: getWdt()@pre == EnvironmentService::getWidth()-1 \implies getWdt() == getWdt()@pre  \and getHgt()@pre == getHgt()
	//\post: getWdt()@pre < EnvironmentService::getWidth()-1 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \in {MTL,PLT}
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD})
			//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \not in {PLT,MTL,LAD}
		//\and not exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: getWdt()@pre < EnvironmentService::getWidth()-1 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \not in {MTL,PLT}
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\implies getWdt() == getWdt()@pre +1 \and getHgt()@pre == getHgt()
	public void goRight();
	
	//\post: getHgt()@pre == EnvironmentService::getHeight()-1 \implies getHgt() == getHgt()@pre \and getWdt() == getWdt()@pre
	//\post: getHgt()@pre < EnvironmentService::getHeight()-1 \and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL,PLT,HDR} 
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD})
			//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) not in {LAD,HDR}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1) not in {MTL, PLT, LAD} 
		//\and not exists CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: getHgt()@pre < EnvironmentService::getHeight() -  1
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD
		//\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} 
			//\implies getHgt() = getHgt()@pre + 1 \and getWdt() == getWdt()@pre
	public void goUp();
	

	//\post: getHgt()@pre == 0 \implies getHgt()==getHgt()@pre \and getWdt() == getWdt()@pre
	//\post: getHgt()@pre > 0 \and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL})
			//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: (Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \not \in {LAD,HDR}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \not \in {PLT,MTL,LAD}
		//\and not exists CharacterService c \in EnvironmentService::getCellContent(getEnvi()@, getWdt()@pre, getHgt()@pre-1))
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: getHgt()@pre > 0 \and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \in {LAD,HDR,EMP,HOL}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {EMP,LAD,HDR,HOL}
			//\implies getHgt()==getHgt()@pre-1 \and getWdt() == getWdt()@pre
	public void goDown();
	
	// Invariants 
	
	// EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) \in {EMP,HOL,LAD,HDR}
	//\exist CharacterService X in EnvironmentService::getCellContent(getEnvi(),getWdt(),getHgt()) \implies X = this

}
