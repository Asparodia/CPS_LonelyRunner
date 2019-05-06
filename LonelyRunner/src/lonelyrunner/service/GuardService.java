package lonelyrunner.service;

import lonelyrunner.service.utils.Move;

public interface GuardService extends /*include*/ CharacterService {
	
	// Observators
	
	public int getId();
	
	public Move getBehaviour();
	
	public PlayerService getTarget();
	
	public int getTimeInHole();
	
	// Constructors
	
	//\pre: init(S,x,y) \req EnvironmentService::getCellNature(S,x,y) = EMP
	public void init(ScreenService s, int x,int y,PlayerService t);
	
	// Operators
	
	//\pre : EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == HOL 
	//\post : getWdt()@pre > 0 \and getHgt()@pre < EnvironmentService::getHeight()-1 
		//\and getTimeInHoles()@pre >= 5
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT} 
			//\or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre))
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1) \in {EMP,HOL,LAD,HDR}
		//\and not exist GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1)
			//implies getWdt() == getWdt()@pre-1 \and getHgt() = getHgt(C)@pre+1 \and getTimeInHole() == 0
	public void climbLeft();
	
	//\pre : EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == HOL
	//\post : getWdt()@pre < EnvironmentService::getWidth()-1 \and getHgt()@pre < EnvironmentService::getHeight()-1
		// \and getTimeInHoles()@pre >= 5
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) in {MTL,PLT} 
			//\or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre)
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre+1) \in {EMP,HOL,LAD,HDR}
		//\and not exist GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1)
			//implies getWdt() == getWdt()@pre+1 \and getHgt() = getHgt(C)@pre+1 \and getTimeInHole() == 0
	public void climbRight();
	
	//\post: getWdt()@pre == 0 \implies getWdt() == getWdt()@pre \and getHgt()@pre == getHgt()
	//\post: getWdt()@pre > 0 \and  (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \in {MTL,PLT} \or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre)) 
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} \or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR,HOL} 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
			//\and not exists GuardService g in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
				//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: (getWdt()@pre > 0 \and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \not in {MTL,PLT} )
		//\and \not \exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre))
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
			//\implies getWdt() == getWdt()@pre - 1 \and getHgt()@pre == getHgt()
	public void goLeft();
	
	//\post: getWdt()@pre == EnvironmentService::getWidth()-1 \implies getWdt() == getWdt()@pre \and getHgt()@pre == getHgt()
	//\post: getWdt()@pre < EnvironmentService::getWidth()-1 \and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \in {MTL,PLT} \or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre)) 
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} \or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR,HOL} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \not in {PLT,MTL,LAD}
		//\and not exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: (getWdt()@pre != EnvironmentService::getWidth()-1 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \not in {MTL,PLT} )
		//\and \not \exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre+1,getHgt()@pre)) )
		//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) \in {LAD,HDR}
			//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
			//\or \exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
			//\implies getWdt() == getWdt()@pre +1 \and getHgt()@pre == getHgt()
	public void goRight();
	
	//\post: getHgt()@pre == EnvironmentService::getHeight()-1 \implies getHgt() == getHgt()@pre \and getWdt() == getWdt()@pre
	//\post:  getHgt()@pre < EnvironmentService::getHeight()-1 \and (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT,HDR} \or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1)
		//\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} \or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))
			//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) not in {LAD,HDR,HOL}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1) not in {MTL, PLT, LAD} 
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	//\post: (getHgt()@pre != EnvironmentService::getHeight() -  1)
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD
		//\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} 
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre +1)
			//\implies getHgt() = getHgt()@pre + 1 \and getWdt()@pre == getWdt()
	public void goUp();
	

	//\post: getHgt()@pre == 0 \implies getHgt() == getHgt()@pre \and getWdt() == getWdt()@pre
	//\post: getHgt()@pre > 0 \and (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {MTL, PLT} \or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1))
		//\implies getWdt()@pre == getWdt() \and getHgt()@pre == getHgt()
	//\post: (Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \not \in {LAD,HDR,HOL}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \not \in {PLT,MTL,LAD}
		//\and not exists GuardService c \in EnvironmentService::getCellContent(getEnvi()@, getWdt()@pre, getHgt()@pre-1))
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1)
	//\post: getHgt()@pre != 0 
		//\and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \in {LAD,HDR,EMP}
		//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \in {EMP,LAD,HDR,HOL}
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1)
			//\implies getHgt()==getHgt()@pre-1 \and getWdt()@pre == getWdt()
	public void goDown();
	
	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR,HOL} 
		//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
		//\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre \and getHgt() == getHgt()@pre - 1
	public void doNeutral();
	
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL
		//\and getTimeInHole()@pre<5 
		//\implies getTimeInHole() == getTimeInHole()@pre + 1 \and getWdt()@pre == getWdt() \and getHgt()@pre = getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL
		//\and getTimeInHole()@pre>=5 
		//\and getBehaviour()@pre == LEFT
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.climbLeft().getWdt() \and getHgt() = clone@pre.climbLeft().getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL
		//\and getTimeInHole()@pre>=5 
		//\and getBehaviour()@pre == RIGHT 
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.climbRight().getWdt() \and getHgt() = clone@pre.climbRight().getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL
		//\and getTimeInHole()@pre>=5 
		//\and getBehaviour()@pre == NEUTRAL 
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.doNeutral().getWdt() \and getHgt() = clone@pre.doNeutral().getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == LEFT
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.goLeft().getWdt() \and getHgt() = clone@pre.goLeft().getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == RIGHT
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.goRight().getWdt() = clone@pre.goRight().getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == UP
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.goUp().getWdt() = clone@pre.goUp().getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == DOWN
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.goDown().getWdt() = clone@pre.goDown().getHgt()
	//\post:EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL
		//\and getBehaviour()@pre == NEUTRAL
		//\and clone@pre:GuardService 
			//\implies getWdt() == clone@pre.doNeutral().getWdt() = clone@pre.doNeutral().getHgt()	
	public void step();
	
	
	// Invariants
	
	// EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) \in {EMP,HOL,LAD,HDR}
	//\exist GuardService X in EnvironmentService::getCellContent(getEnvi(),getWdt(),getHgt()) \implies X = this
	
}

//\post:EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == HOL
		//\and getTarget().getWdt() > getWdt()
			//\implies getBehaviour() == RIGHT
	//\post:EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == HOL
		//\and getTarget().getWdt() < getWdt()
			//\implies getBehaviour() == LEFT
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD 
		//\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
			//\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getTarget().getWdt()| < |getTarget().getHgt()-getHgt()|
		//\and getTarget().getHgt() > getHgt() 
			//\implies getBehaviour() == UP
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD 
		//\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) not in {MTL,PLT} 
			//\or not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getTarget().getWdt()| < |getTarget().getHgt()-getHgt()|
		//\and getTarget().getHgt() < getHgt() 
			//\implies getBehaviour() == DOWN


//(EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} 
//\or  EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
//\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
//\and getWdt() > getTarget().getWdt()
//\implies getBehaviour() == LEFT
//(EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} 
//\or EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
//\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
//\and getWdt() < getTarget().getWdt()
//\implies getBehaviour() == RIGHT
//(EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} 
//\or EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
//\or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) 
//\and getWdt() == getTarget().getWdt())
//\implies getBehaviour() == NEUTRAL

//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD 
		//\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
			//\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getTarget().getWdt()| > |getTarget().getHgt()-getHgt()|
		//\and getTarget().getHgt() > getHgt() 
			//\implies getBehaviour() == UP
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD 
		//\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) not in {MTL,PLT} 
			//\or  not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getTarget().getWdt()| > |getTarget().getHgt()-getHgt()|
		//\and getTarget().getHgt() < getHgt() 
			//\implies getBehaviour() == DOWN
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD
		//\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
			//\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getTarget().getWdt()| < |getTarget().getHgt()-getHgt()|
		//\and getTarget().getWdt() > getWdt() 
			//\implies getBehaviour() == RIGHT
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD 
		//\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
			//\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
		//\and |getWdt()-getTarget().getWdt()| < |getTarget().getHgt()-getHgt()|
		//\and getTarget().getHgt() < getHgt() 
			//\implies getBehaviour() == LEFT
	//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD 
			//\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} 
				//\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1) )
			//\and |getWdt()-getTarget().getWdt()| < |getTarget().getHgt()-getHgt()|
			//\and getTarget().getHgt() == getHgt() 
				//\implies getBehaviour() == NEUTRAL
