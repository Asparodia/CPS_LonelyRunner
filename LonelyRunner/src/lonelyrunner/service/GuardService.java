package lonelyrunner.service;

import lonelyrunner.service.utils.Move;

public interface GuardService extends CharacterService {
	
	// Observators
	
	public int getId();
	
	public Move getBehaviour();
	
	public PlayerService getTarget();
	
	public int getTimeInHole();
	
	// Constructors
	
	//\pre: init(S,x,y) \req EnvironmentService::getCellNature(S,x,y) = EMP
	public void init(ScreenService s, int x,int y,PlayerService t);
	
	// Operators
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL 
		//\and  getWdt()@pre != 0 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,LAD,PLT,HDR}
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1) \in {EMP,HOL,LAD,HDR}
		//\and not exist GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1)
			//implies getWdt() == getWdt()@pre-1 \and getHgt() = getHgt(C)@pre+1
	public void climbLeft();
	
	//\post EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL 
		//\and  getWdt()@pre != EnvironmentService::getWidth()-1
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) in {MTL,LAD,PLT,HDR}
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre+1) \in {EMP,HOL,LAD,HDR}
		//\and not exist GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1)
			//implies getWdt() == getWdt()@pre+1 \and getHgt() = getHgt(C)@pre+1
	public void climbRight();
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == 0 \implies getWdt() == getWdt()@pre
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \in {MTL,PLT} \implies getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR,HOL} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() = getHgt(C)@pre-1
	//\post: \exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \implies getWdt() == getWdt()@pre
	//\post: (getWdt()@pre != 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \not in {MTL,PLT} )
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\and \not (\exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre)) )
			//\implies getWdt() == getWdt()@pre - 1
	public void goLeft();
	
	//\post: getHgt() == getHgt()@pre
	//\post: getWdt()@pre == EnvironmentService::getWidth()-1 \implies getWdt() == getWdt()@pre 
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \in {MTL,PLT} \implies getWdt()@pre == getWdt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR,HOL} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \not in {PLT,MTL,LAD}
		//\and not exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: \exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \implies getWdt() == getWdt()@pre
	//\post: getWdt()@pre != EnvironmentService::getWidth()-1 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \not in {MTL,PLT}
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
		//\and \not (\exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre+1,getHgt()@pre)) ) 
			//\implies getWdt() == getWdt()@pre +1
	public void goRight();
	
	//\post: getWdt() == getWdt()@pre
	//\post: getHgt()@pre == EnvironmentService::getHeight()-1 \implies getHgt() == getHgt()@pre
	//\post: EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT} \implies getHgt() = getHgt()@pre
	//\post: EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) not in {LAD,HDR,HOL}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1) not in {MTL, PLT, LAD} 
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) \implies getHgt() = getHgt()@pre
	//\post: (getHgt()@pre != EnvironmentService::getHeight() -  1)
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD
		//\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} 
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre +1)
			//\implies getHgt() = getHgt()@pre + 1
	public void goUp();
	

	//\post: getWdt()==getWdt()@pre
	//\post: getHgt()@pre == 0 \implies getHgt()==getHgt()@pre
	//\post: (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {MTL, PLT} \implies getHgt()==getHgt()@pre
	//\post: (Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \not \in {LAD,HDR,HOL}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \not \in {PLT,MTL,LAD}
		//\and not exists GuardService c \in EnvironmentService::getCellContent(getEnvi()@, getWdt()@pre, getHgt()@pre-1))
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1)
	//\post: exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) \implies getHgt() = getHgt()@pre 
	//\post: getHgt()@pre != 0 \and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \in {LAD,HDR,EMP}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {EMP,LAD,HDR}
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1)
			//\implies getHgt()==getHgt()@pre-1
	public void goDown();
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD,HOL}
		//\and not exists Guard c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	public void doNeutral();
	
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL
		//\and getTimeInHole()@pre<5 
		//\implies getTimeInHole() == getTimeInHole()@pre + 1 \and getWdt()@pre == getWdt() \and getHgt()@pre = getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL
			//\and getTimeInHole()@pre==5 
			//\and getBehaviour()@pre == LEFT 
			//\implies getWdt() == getWdt(climbLeft())@pre \and getHgt() = getHgt(climbeLeft())@pre
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL
		//\and getTimeInHole()@pre==5 
		//\and getBehaviour()@pre == RIGHT 
		//\implies getWdt() == getWdt(climbRight())@pre \and getHgt() = getHgt(climbeRight())@pre
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == LEFT
		//\implies getWdt() == getWdt(goLeft())@pre \and getHgt() = getHgt(goLeft())@pre
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == RIGHT
		//\implies getWdt() == getWdt(goRight())@pre \and getHgt() = getHgt(goRight())@pre
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == UP
		//\implies getWdt() == getWdt(goUp())@pre \and getHgt() = getHgt(goUp())@pre
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == DOWN
		//\implies getWdt() == getWdt(goDown())@pre \and getHgt() = getHgt(goDown())@pre
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
			//\and getBehaviour()@pre == NEUTRAL
			//\implies getWdt() == getWdt(doNeutral())@pre \and getHgt() = getHgt(doNeutral())@pre
	public void step();
	
	// Invariants
	
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD
		//\and getHgt(getTarget()) > getHgt() 
		//\implies getBehaviour() == UP
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD
		//\and getHgt(getTarget()) < getHgt()
		//\implies getBehaviour() == DOWN	
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD
		//\and getHgt(getTarget()) == getHgt()
		//\implies getBehaviour() == NEUTRAL
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and getWdt() > getWdt(getTarget())
		//\implies getBehaviour() == LEFT
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and getWdt() < getWdt(getTarget())
		//\implies getBehaviour() == RIGHT
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and getWdt() == getWdt(getTarget())
		//\implies getBehaviour() == NEUTRAL
	
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD \and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getWdt(getTarget())| > |getHgt(getTarget())-getHgt()|
		//\and getHgt(getTarget()) > getHgt() 
		//\implies getBehaviour() == UP
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD \and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getWdt(getTarget())| < |getHgt(getTarget())-getHgt()|
		//\and getWdt(getTarget()) > getWdt() 
		//\implies getBehaviour() == RIGHT
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD \and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getWdt(getTarget())| < |getHgt(getTarget())-getHgt()|
		//\and getHgt(getTarget()) < getHgt() 
		//\implies getBehaviour() == LEFT
	
	
	
	
	

	
}
