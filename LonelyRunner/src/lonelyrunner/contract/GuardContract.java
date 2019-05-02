package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.GuardDecorator;
import lonelyrunner.impl.GuardImpl;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Move;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
	}
	
	
	public void checkInvariant() {
		int hgt = getDelegate().getHgt();
		int wdt = getDelegate().getWdt();
		int hgtTarger = getDelegate().getTarget().getHgt();
		int wdtTarger = getDelegate().getTarget().getWdt();
		Move behaviour = getDelegate().getBehaviour();
		boolean containGuarddown = false;
		Cell pos = getDelegate().getEnvi().getCellNature(wdt,hgt);
		Cell down = getDelegate().getEnvi().getCellNature(wdt,hgt-1);
		if(!getDelegate().getEnvi().getCellContent(wdt, hgt-1).getCar().isEmpty()) {
			for(CharacterService cs : getDelegate().getEnvi().getCellContent(wdt, hgt).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		
//		if(pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT )||containGuarddown)) {
//			if(Math.abs(wdt-wdtTarger)>Math.abs(hgt-hgtTarger)) {
//				if(hgtTarger>hgt) {
//					if(!(behaviour == Move.UP)) {
//						throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD \\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n" + 
//								"			//\\and |getWdt()-getWdt(getTarget())| > |getHgt(getTarget())-getHgt()|\n" + 
//								"			//\\and getHgt(getTarget()) > getHgt() \n" + 
//								"			//\\implies getBehaviour() == UP");	
//					}
//				}
//				
//			}
//		}
//		
//		if(pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT )||containGuarddown)) {
//			if(Math.abs(wdt-wdtTarger)<Math.abs(hgt-hgtTarger)) {
//				if(wdtTarger>wdt) {
//					if(!(behaviour == Move.RIGHT)) {
//						throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD \\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n" + 
//								"			//\\and |getWdt()-getWdt(getTarget())| < |getHgt(getTarget())-getHgt()|\n" + 
//								"			//\\and getWdt(getTarget()) > getWdt() \n" + 
//								"			//\\implies getBehaviour() == RIGHT");	
//					}
//				}
//				
//			}
//		}
//		if(pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT )||containGuarddown)) {
//			if(Math.abs(wdt-wdtTarger)<Math.abs(hgt-hgtTarger)) {
//				if(wdtTarger<wdt) {
//					if(!(behaviour == Move.LEFT)) {
//						throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD \\and ( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n" + 
//								"			//\\and |getWdt()-getWdt(getTarget())| < |getHgt(getTarget())-getHgt()|\n" + 
//								"			//\\and getHgt(getTarget()) < getHgt() \n" + 
//								"			//\\implies getBehaviour() == LEFT");	
//					}
//				}
//				
//			}
//		}
//		
//		if(pos == Cell.LAD && (hgtTarger > hgt)) {
//			if(!(behaviour == Move.UP)) {
//				throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD\n" + 
//						"			//\\and getHgt(getTarget()) > getHgt() \n" + 
//						"			//\\implies getBehaviour() == UP");	
//			}
//		}
//		if(pos == Cell.LAD && (hgtTarger < hgt)) {
//			if(!(behaviour == Move.DOWN)) {
//				throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD\n" + 
//						"			//\\and getHgt(getTarget()) < getHgt()\n" + 
//						"			//\\implies getBehaviour() == DOWN");	
//			}
//		}
//		if(pos == Cell.LAD && (hgtTarger == hgt)) {
//			if(!(behaviour == Move.NEUTRAL)) {
//				throw new InvariantError("//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD\n" + 
//						"			//\\and getHgt(getTarget()) == getHgt()\n" + 
//						"			//\\implies getBehaviour() == NEUTRAL");	
//			}
//		}
//		if((pos == Cell.HOL || pos == Cell.HDR ) ||((down == Cell.MTL || down == Cell.PLT )||containGuarddown)) {
//			if(wdt > wdtTarger) {
//					if(!(behaviour == Move.LEFT)) {
//						throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \\or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n" + 
//								"			//\\and getWdt() > getWdt(getTarget())\n" + 
//								"			//\\implies getBehaviour() == LEFT");	
//				}
//				
//			}
//		}
//		if((pos == Cell.HOL || pos == Cell.HDR ) ||((down == Cell.MTL || down == Cell.PLT )||containGuarddown)) {
//			if(wdt < wdtTarger) {
//					if(!(behaviour == Move.RIGHT)) {
//						throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \\or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n" + 
//								"			//\\and getWdt() < getWdt(getTarget())\n" + 
//								"			//\\implies getBehaviour() == RIGHT");	
//				}
//				
//			}
//		}
//		if((pos == Cell.HOL || pos == Cell.HDR ) ||((down == Cell.MTL || down == Cell.PLT )||containGuarddown)) {
//			if(wdt == wdtTarger) {
//					if(!(behaviour == Move.NEUTRAL)) {
//						throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \\or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n" + 
//								"			//\\and getWdt() == getWdt(getTarget())\n" + 
//								"			//\\implies getBehaviour() == NEUTRAL");	
//				}
//				
//			}
//		}
		
	}
	
	@Override
	public void init(ScreenService s, int x, int y) {
		if(!(getDelegate().getEnvi().getCellNature(x, y)==Cell.EMP)) {
			throw new PreconditionError("init( s, "+x+", "+y+" )" , "Cell is not empty");
		}
		getDelegate().init(s, x, y);
		checkInvariant();
	}

	@Override
	public int getId() {
		return getDelegate().getId();
	}

	@Override
	public Move getBehaviour() {
		return getDelegate().getBehaviour();
	}

	@Override
	public PlayerService getTarget() {
		return getDelegate().getTarget();
	}

	@Override
	public int getTimeInHole() {
		return getDelegate().getTimeInHole();
	}

	@Override
	public void climbLeft() {
		
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		
		checkInvariant();
		getDelegate().climbLeft();
		checkInvariant();
		
		if(cell_atpre == Cell.HOL) {
			if(getWdt_atpre != 0 ) {
				Cell cell_leftup = getEnvi_atpre.getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()+1);
				Cell cell_left = getEnvi_atpre.getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt());
				if(cell_left == Cell.MTL || cell_left == Cell.PLT ) {
					
					if( cell_leftup == Cell.EMP || cell_leftup == Cell.HOL || cell_leftup == Cell.LAD || cell_leftup == Cell.HDR ) {
						boolean containGuardleftUp = false;
						if(!getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar().isEmpty()) {
							for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre+1).getCar() ) {
								if(cs.getClass().isInstance(GuardService.class)) {
									containGuardleftUp = true;
								}
							}
						}
						if(!containGuardleftUp) {
							if( !(getWdt() == getWdt_atpre-1 && getHgt()== getHgt_atpre+1) ) {
								throw new PostconditionError("climbLeft()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL \\n\" + \n" + 
										"//\\\\and  getWdt()@pre != 0 \\n\" + \n" + 
										"//\\\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT}\\n\" + \n" + 
										"//\\\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1) \\\\in {EMP,HOL,LAD,HDR}\\n\" + \n" + 
										"//\\\\and not exist GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1)\\n\" + \n" + 
										"//implies getWdt() == getWdt()@pre-1 \\\\and getHgt() = getHgt(C)@pre+1");
							}
						}
					}
				}
			}
			
		}
		
	}
	
	@Override
	public void climbRight() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		
		checkInvariant();
		getDelegate().climbRight();
		checkInvariant();
		
		if(cell_atpre == Cell.HOL) {
			if(getWdt_atpre != 0 ) {
				Cell cell_rightup = getEnvi_atpre.getCellNature(getDelegate().getWdt()+1, getDelegate().getHgt()+1);
				Cell cell_right = getEnvi_atpre.getCellNature(getDelegate().getWdt()+1, getDelegate().getHgt());
				if(cell_right == Cell.MTL || cell_right == Cell.PLT ) {
					if( cell_rightup == Cell.EMP || cell_rightup == Cell.HOL || cell_rightup == Cell.LAD || cell_rightup == Cell.HDR ) {
						boolean containGuardrightUp = false;
						if(!getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar().isEmpty()) {
							for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre+1).getCar() ) {
								if(cs.getClass().isInstance(GuardService.class)) {
									containGuardrightUp = true;
								}
							}
						}
						if(!containGuardrightUp) {
							if( !(getWdt() == getWdt_atpre+1 && getHgt()== getHgt_atpre+1) ) {
								throw new PostconditionError("climbRight()","EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL \n" + 
										"			//\\and  getWdt()@pre != EnvironmentService::getWidth()-1\n" + 
										"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) in {MTL,LAD,PLT,HDR}\n" + 
										"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre+1) \\in {EMP,HOL,LAD,HDR}\n" + 
										"			//\\and not exist GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1)\n" + 
										"				//implies getWdt() == getWdt()@pre+1 \\and getHgt() = getHgt(C)@pre+1");	
							}
						}
					}
				}
			}
			
		}
		
	}
	

	@Override
	public void goLeft() {
		//Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		Cell cell_left = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt());
		boolean containGuardleft = false;
		
		if(!getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuardleft = true;
				}
			}
		}
		boolean containGuarddown = false;
		
		if(!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		
		
		checkInvariant();
		getDelegate().goLeft();
		checkInvariant();
		
		// Post
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("goLeft()" , "getHgt() should be equals to getHgt()@pre");
		}
		if(getWdt_atpre ==0) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goLeft()" , "getWdt()@pre == 0 implies getWdt() == getWdt()@pre");
		}
		if( cell_left == Cell.MTL || cell_left == Cell.PLT) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goLeft()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT} implies getWdt()@pre == getWdt()");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(containGuarddown) {
						if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
							throw new PostconditionError("goLeft()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\not in {LAD,HDR} \n" + 
									"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \\not in {PLT,MTL,LAD}\n" + 
									"			//\\and not exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
									"				//\\implies getWdt() == getWdt()@pre \\and getHgt() == getHgt()@pre - 1");
					}
					
			
				}
			}
		}
		if(containGuardleft) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goLeft()" , "\\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \\implies getWdt() == getWdt()@pre");
		}
		
		if(getDelegate().getWdt() != 0 && cell_left != Cell.MTL && cell_left != Cell.PLT) {
			if( (cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) || (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown )){
				if(!containGuardleft) {
					if(!(getWdt_atpre-1 == getDelegate().getWdt()))
						throw new PostconditionError("goLeft()" , "(getWdt()@pre != 0 \\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \\not in {MTL,PLT} )\n" + 
								"				//\\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\in {LAD,HDR}\n" + 
								"					//\\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n" + 
								"					//\\or \\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n" + 
								"				//\\and \\not (\\exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre)) )\n" + 
								"					//\\implies getWdt() == getWdt()@pre - 1");
				}
				
			}
		}
	}
	
	
	@Override
	public void goRight() {
		//Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		Cell cell_right = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()+1, getDelegate().getHgt());
		boolean containGuardright = false;
		
		if(!getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre+1, getHgt_atpre).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuardright = true;
				}
			}
		}
		boolean containGuarddown = false;
		
		if(!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		
		checkInvariant();
		getDelegate().goRight();
		checkInvariant();
		
		// Post
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("goRight()" , "getHgt() should be equals to getHgt()@pre");
		}
		if(getWdt_atpre == getEnvi_atpre.getWidth()-1) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goRight()" , "getWdt()@pre == EnvironmentService::getWidth()-1 implies getWdt() == getWdt()@pre");
		}
		if( cell_right == Cell.MTL || cell_right == Cell.PLT) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goRight()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT} implies getWdt()@pre == getWdt()");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(containGuarddown) {
						if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
							throw new PostconditionError("goRight()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\not in {LAD,HDR} \n" + 
									"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \\not in {PLT,MTL,LAD}\n" + 
									"			//\\and not exist GuardSerice c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
									"				//\\implies getWdt() == getWdt()@pre \\and getHgt() == getHgt()@pre - 1");
					}
				}
			}
		}
		if(containGuardright) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goRight()" , "\\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \\implies getWdt() == getWdt()@pre");
		}
		
		if(getDelegate().getWdt() != getEnvi_atpre.getWidth()-1 && cell_right != Cell.MTL && cell_right != Cell.PLT) {
			if( (cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) || (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown )){
				if(!containGuardright) {
					if(!(getWdt_atpre-1 == getDelegate().getWdt()))
						throw new PostconditionError("goRight()" , "(getWdt()@pre != 0 \\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \\not in {MTL,PLT} )\n" + 
								"				//\\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\in {LAD,HDR}\n" + 
								"					//\\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n" + 
								"					//\\or \\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n" + 
								"				//\\and \\not (\\exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre+1,getHgt()@pre)) )\n" + 
								"					//\\implies getWdt() == getWdt()@pre + 1");
				}
				
			}
		}
		
	}
	
	
	@Override
	public void goUp() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_up = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()+1);
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		boolean containGuardup = false;
		if(!getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre+1).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuardup = true;
				}
			}
		}
		boolean containGuarddown = false;
		if(!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		
		checkInvariant();
		getDelegate().goUp();
		checkInvariant();
		
		// Post
		if(!(getWdt_atpre == getDelegate().getWdt())) {
			throw new PostconditionError("goUp()" , "getWdt() should be equals to getWdt()@pre");
		}
		if(getHgt_atpre ==getEnvi_atpre.getHeight()-1) {
			if(!(getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goUp()" , "getHgt()@pre == EnvironmentService::getHeight()-1 \\implies getHgt() == getHgt()@pre ");
		}
		if( (cell_up == Cell.MTL || cell_up ==Cell.PLT || cell_up ==Cell.HDR) ) {
			if(!(getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goUp()" , "EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT, HDR} \\implies getHgt() = getHgt()@pre");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(containGuarddown) {
						if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
							throw new PostconditionError("goUp()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\not in {LAD,HDR} \n" + 
									"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \\not in {PLT,MTL,LAD}\n" + 
									"			//\\and not exist GuardSerice c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
									"				//\\implies getWdt() == getWdt()@pre \\and getHgt() == getHgt()@pre - 1");
					}
				}
			}
		}
		if(containGuardup) {
			if(!(getHgt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goUp()" , "\\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre+1) \\implies getHgt() == getHgt()@pre");
		}		
		if(getHgt_atpre != getEnvi_atpre.getHeight()-1) {
		
			if(cell_atpre == Cell.LAD && (cell_up == Cell.LAD || cell_up == Cell.EMP)) {
				if(!containGuardup) {
					if(!(getHgt_atpre+1 == getDelegate().getHgt())) {
						throw new PostconditionError("goUp()" , "(getHgt()@pre != EnvironmentService::getHeight() -  1)\n" + 
								"				//\\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD\n" + 
								"				//\\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} \n" + 
								"				//\\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre +1)\n" + 
								"					//\\implies getHgt() = getHgt()@pre + 1");
				}
				
				
			}
			}
		}
	}
	
	@Override
	public void goDown() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		boolean containGuarddown = false;
		if(!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		
		checkInvariant();
		getDelegate().goUp();
		checkInvariant();
		
		// Post
		
		if(!(getWdt_atpre == getDelegate().getWdt())) {
			throw new PostconditionError("goDown()" , "getWdt() == getWdt()@pre");
		}
		if(getHgt_atpre ==0) {
			if(!(getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goDown()" , "getHgt()@pre == 0 \\implies getHgt()==getHgt()@pre ");
		}
		if( (cell_down == Cell.MTL || cell_down ==Cell.PLT)) {
			if(!(getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goDown()" , "(EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \\in {MTL, PLT} \\implies getHgt()==getHgt()@pre");
		}

		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(containGuarddown) {
						if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
							throw new PostconditionError("goDown()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\not in {LAD,HDR} \n" + 
									"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \\not in {PLT,MTL,LAD}\n" + 
									"			//\\and not exist GuardSerice c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
									"				//\\implies getWdt() == getWdt()@pre \\and getHgt() == getHgt()@pre - 1");
					}
				}
			}
		}
		if(containGuarddown) {
			if(!(getHgt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goUp()" , "\\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre+1) \\implies getHgt() == getHgt()@pre");
		}
		if(getHgt_atpre != 0) {
			if(cell_atpre == Cell.EMP || cell_atpre == Cell.LAD || cell_atpre == Cell.HDR ) {
				if(cell_down == Cell.EMP || cell_down == Cell.LAD || cell_down == Cell.EMP || cell_down == Cell.HOL) {
					if(!containGuarddown) {
						if(!(getHgt_atpre-1 == getDelegate().getHgt()))
							throw new PostconditionError("goDown()" , "getHgt()@pre != 0 \\and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) \\in {LAD,HDR,EMP}\n" + 
									"			//\\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \\in {EMP,LAD,HDR,HOL}\n" + 
									"			//\\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1)\n" + 
									"				//\\implies getHgt()==getHgt()@pre-1");
					}
				}
			}
		}
	}
	@Override
	public void doNeutral() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		boolean containGuarddown = false;
		if(!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
			for(CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() ) {
				if(cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		
		checkInvariant();
		getDelegate().doNeutral();
		checkInvariant();
		
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD ) {
				if(!containGuarddown) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("doNeutral()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\not in {LAD,HDR,HOL} \n" + 
								"		//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \\not in {PLT,MTL,LAD}\n" + 
								"		//\\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
								"			//\\implies getWdt() == getWdt()@pre \\and getHgt() == getHgt()@pre - 1");
			
				}
			}
		}
	}
		

	@Override
	public void step() {
		
		
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Move getBehaviour_atpre = getDelegate().getBehaviour();
		int getTimeInHole_atpre = getDelegate().getTimeInHole();
		GuardImpl cloneR = new GuardImpl();
		cloneR.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneR.goRight();
		getDelegate().getEnvi().getCellContent(cloneR.getWdt(), cloneR.getHgt()).removeCharacter(cloneR);
		GuardImpl cloneL = new GuardImpl();
		cloneL.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneL.goLeft();
		getDelegate().getEnvi().getCellContent(cloneL.getWdt(), cloneL.getHgt()).removeCharacter(cloneL);
		GuardImpl cloneU = new GuardImpl();
		cloneU.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneU.goUp();
		getDelegate().getEnvi().getCellContent(cloneU.getWdt(), cloneU.getHgt()).removeCharacter(cloneU);
		GuardImpl cloneD = new GuardImpl();
		cloneD.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneD.goDown();
		getDelegate().getEnvi().getCellContent(cloneD.getWdt(), cloneD.getHgt()).removeCharacter(cloneD);
		GuardImpl cloneN = new GuardImpl();
		cloneN.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneN.doNeutral();
		getDelegate().getEnvi().getCellContent(cloneN.getWdt(), cloneN.getHgt()).removeCharacter(cloneN);
		GuardImpl cloneCL = new GuardImpl();
		cloneCL.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneCL.climbLeft();
		getDelegate().getEnvi().getCellContent(cloneN.getWdt(), cloneCL.getHgt()).removeCharacter(cloneCL);
		GuardImpl cloneCR = new GuardImpl();
		cloneCR.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneCR.climbRight();
		getDelegate().getEnvi().getCellContent(cloneCR.getWdt(), cloneCR.getHgt()).removeCharacter(cloneCR);
		
		checkInvariant();
		getDelegate().step();
		checkInvariant();
		
		// Post
		
		if(cell_atpre == Cell.HOL) {
			if(getTimeInHole_atpre<5) {
				if(!(getDelegate().getTimeInHole() == getTimeInHole_atpre+1 && getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt())) {
					throw new PostconditionError("step()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\n" + 
							"		//\\and getTimeInHole()@pre<5 \n" + 
							"		//\\implies getTimeInHole() == getTimeInHole()@pre + 1 \\and getWdt()@pre == getWdt() \\and getHgt()@pre = getHgt()");
				}
			}
		}
		if(cell_atpre == Cell.HOL) {
			if(getTimeInHole_atpre==5 && getBehaviour_atpre == Move.LEFT) {
				if(!(cloneCL.getWdt() == getDelegate().getWdt() && cloneCL.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\n" + 
							"			//\\and getTimeInHole()@pre==5 \n" + 
							"			//\\and getBehaviour()@pre == LEFT \n" + 
							"			//\\implies getWdt() == getWdt(climbLeft())@pre \\and getHgt() = getHgt(climbeLeft())@pre");
				}
			}
		}
		if(cell_atpre == Cell.HOL) {
			if(getTimeInHole_atpre==5 && getBehaviour_atpre == Move.RIGHT) {
				if(!(cloneCR.getWdt() == getDelegate().getWdt() && cloneCR.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()" , "//EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\n" + 
							"		//\\and getTimeInHole()@pre==5 \n" + 
							"		//\\and getBehaviour()@pre == RIGHT \n" + 
							"		//\\implies getWdt() == getWdt(climbRight())@pre \\and getHgt() = getHgt(climbeRight())@pre");
				}
			}
		}
		
		
		if(cell_atpre != Cell.HOL && getBehaviour_atpre == Move.LEFT) {
			if(!(cloneL.getWdt() == getDelegate().getWdt() && cloneL.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\n" + 
						"			//\\and getBehaviour()@pre == LEFT\n" + 
						"			//\\implies getWdt() == getWdt(goLeft())@pre \\and getHgt() = getHgt(goLeft())@pre");
			}
		}
		if(cell_atpre != Cell.HOL && getBehaviour_atpre == Move.RIGHT) {
			if(!(cloneR.getWdt() == getDelegate().getWdt() && cloneR.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\n" + 
						"			//\\and getBehaviour()@pre == RIGHT\n" + 
						"			//\\implies getWdt() == getWdt(goRight())@pre \\and getHgt() = getHgt(goRight())@pre");
			}
		}
		if(cell_atpre != Cell.HOL && getBehaviour_atpre == Move.UP) {
			if(!(cloneU.getWdt() == getDelegate().getWdt() && cloneU.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\n" + 
						"			//\\and getBehaviour()@pre == UP\n" + 
						"			//\\implies getWdt() == getWdt(goUp())@pre \\and getHgt() = getHgt(goUp())@pre");
			}
		}
		if(cell_atpre != Cell.HOL && getBehaviour_atpre == Move.DOWN) {
			if(!(cloneD.getWdt() == getDelegate().getWdt() && cloneD.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\n" + 
						"			//\\and getBehaviour()@pre == DOWN\n" + 
						"			//\\implies getWdt() == getWdt(goDown())@pre \\and getHgt() = getHgt(goDown())@pre");
			}
		}
		if(cell_atpre != Cell.HOL && getBehaviour_atpre == Move.NEUTRAL) {
			if(!(cloneN.getWdt() == getDelegate().getWdt() && cloneN.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\n" + 
						"				//\\and getBehaviour()@pre == NEUTRAL\n" + 
						"				//\\implies getWdt() == getWdt(doNeutral())@pre \\and getHgt() = getHgt(doNeutral())@pre");
			}
		}
	}
	
	

}
