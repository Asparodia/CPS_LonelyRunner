package lonelyrunner.service;

public interface CharacterService {
	// Operators
	
	public EnvironmentService getEnvi();
	public int getHgt();
	public int getWdt();
	
	//Constructors
	//\pre: init(S,x,y) \req EnvironmentService::getCellNature(S,x,y) = EMP
	public void init(ScreenService s, int x,int y);
	
	//Operators
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == 0 \implies getWdt() == getWdt()@pre 
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \in {MTL,PLT,HDR} \implies
		//getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
		//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre)
		//\implies getWdt() == getWdt()@pre
	//\post: \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \implies getWdt() == getWdt()@pre
	//\post: getWdt()@pre != 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \not in {MTL,PLT}
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\and \not (\exist Character c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre)) ) \implies getWdt() == getWdt()@pre - 1
	public void goLeft();
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == EnvironmentService::getWidth()-1 \implies getWdt() == getWdt()@pre 
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \in {MTL,PLT,HDR} \implies
		// getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \not in {PLT,MTL,LAD}
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
	//\post: EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT, HDR} \or exists Character c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt(), getHgt() + 1)
		//\implies getHgt() = getHgt()@pre
	//\post: EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) not in {LAD,HDR}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1) not in {MTL, PLT, LAD} 
		//\and not exists Character c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1)
			//\implies getHgt() = getHgt(C)@pre
	//\post: (getHgt()@pre != EnvironmentService::getHeight() -  1)
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == EMP
		//\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) == LAD
			//\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1) in {MTL, PLT} \or exists Character c in EnvironmentService::CellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1)
			//\implies getHgt() = getHgt()@pre + 1
		//\or EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) = LAD
			//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {EMP, LAD}
				//\implies getHgt() = getHgt()@pre + 1
	public void goUp();
	

	// \post: getWdt(C)==getWdt()@pre
	// \post: getHgt(C) == 0 \implies (getHgt()==getHgt()@pre)
	// \post: (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {MTL, PLT, HDR}
	//		\or \exists Character c \in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre, getHdt(C)@pre-1)) => (getHgt()==getHgt()@pre)
	// \post: ((Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \not \in {LAD,HDR}
	// 			\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \not \in {PLT,MTL,LAD}
	//			\and \not \exists Character c \in EnvironmentService::getCellContent(getEnvi()@, getWdt()@pre, getHgt()@pre-1))
	//			\implies getHgt()==getHgt()@pre-1)
	// \post: getHgt()@pre != 0 \and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \in {LAD,HDR, EMP}
	//			\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {EMP,LAD}
	//			\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {MTL, PLT}
	//			\or \exists Character c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1)
	//				=> getHgt()==getHgt()@pre-1
	//			\or EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre)==LAD \and Environment::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
	//				=> getWdt()==getWdt()@pre-1

	public void goDown();
	
	//Invariants 
	// EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) \in {EMP,HOL,LAD,HDR}
	//\exist Character X in EnvironmentService::getCellContent(getEnvi(),getWdt(),getHgt()) \implies c = this
}
