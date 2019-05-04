package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.GuardDecorator;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.impl.GuardImpl;
import lonelyrunner.service.CharacterService;
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
		Cell c = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		if(!(c == Cell.EMP || c== Cell.HOL || c== Cell.LAD || c== Cell.HDR)) {
			throw new InvariantError("EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) not in {EMP,HOL,LAD,HDR}");	
		}
		boolean chara = (getDelegate().getEnvi().getCellContent(getDelegate().getWdt(), getDelegate().getHgt())).isInside(getDelegate());
		if(!(chara)) {
			throw new InvariantError("exist GuardService P in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) should implie p = this");	
		}
		
//		int hgt = getDelegate().getHgt();
//		int wdt = getDelegate().getWdt();
//		int hgtTarger = getDelegate().getTarget().getHgt();
//		int wdtTarger = getDelegate().getTarget().getWdt();
//		Move behaviour = getDelegate().getBehaviour();
//		Cell pos = getDelegate().getEnvi().getCellNature(wdt, hgt);
//		Cell down = getDelegate().getEnvi().getCellNature(wdt, hgt - 1);
//		boolean containGuarddown = false;
//		
//		if (!getDelegate().getEnvi().getCellContent(wdt, hgt - 1).getCar().isEmpty()) {
//			for (CharacterService cs : getDelegate().getEnvi().getCellContent(wdt, hgt).getCar()) {
//				if (cs.getClass().isInstance(GuardService.class)) {
//					containGuarddown = true;
//				}
//			}
//		}
		
//		if (pos == Cell.LAD && (hgtTarger > hgt)) {
//			if (!(behaviour == Move.UP)) {
//				throw new InvariantError("/EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD\r\n" + 
//						"		//\\and getTarget().getHgt() > getHgt() \r\n" + 
//						"			//\\implies getBehaviour() == UP");
//			}
//		}
//		if (pos == Cell.LAD && (hgtTarger < hgt)) {
//			if (!(behaviour == Move.DOWN)) {
//				throw new InvariantError("EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD\r\n" + 
//						"		//\\and getTarget()getHgt() < getHgt()\r\n" + 
//						"			//\\implies getBehaviour() == DOWN");
//			}
//		}
//		if (pos == Cell.LAD && (hgtTarger == hgt)) {
//			if (!(behaviour == Move.NEUTRAL)) {
//				throw new InvariantError("//EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) == LAD\n"
//						+ "			//\\and getHgt(getTarget()) == getHgt()\n"
//						+ "			//\\implies getBehaviour() == NEUTRAL");
//			}
//		}
		
//		if ((pos == Cell.HOL || pos == Cell.HDR) || ((down == Cell.MTL || down == Cell.PLT) || containGuarddown)) {
//			if (wdt < wdtTarger) {
//				if (!(behaviour == Move.RIGHT)) {
//					throw new InvariantError(
//							"EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \\or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n"
//									+ "			//\\and getWdt() < getWdt(getTarget())\n"
//									+ "			//\\implies getBehaviour() == RIGHT");
//				}
//
//			}
//			if (wdt > wdtTarger) {
//				if (!(behaviour == Move.LEFT)) {
//					throw new InvariantError(
//							"EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \\or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n"
//									+ "			//\\and getWdt() > getWdt(getTarget())\n"
//									+ "			//\\implies getBehaviour() == LEFT");
//				}
//
//			}
//			if (wdt == wdtTarger) {
//				if (!(behaviour == Move.NEUTRAL)) {
//					throw new InvariantError(
//							"EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()) in {HOL,HDR} \\or( EnvironmentService::getCellNature(getEnvi(),getWdt(),getHgt()-1) in {MTL,PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi(), getWdt(), getHdt() - 1) )\n"
//									+ "			//\\and getWdt() == getWdt(getTarget())\n"
//									+ "			//\\implies getBehaviour() == NEUTRAL");
//				}
//
//			}
//		}
		

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
		


	}

	@Override
	public void init(ScreenService s, int x, int y) {
		if (!(getDelegate().getEnvi().getCellNature(x, y) == Cell.EMP)) {
			throw new PreconditionError("init( s, " + x + ", " + y + " )", "Cell is not empty");
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

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());

		boolean containGuardleft = false;

		if (!(cell_atpre == Cell.HOL)) {
			throw new PreconditionError("climbLeft()", "guard pos must be a HOL to call climb left");
		}

		checkInvariant();
		getDelegate().climbLeft();
		checkInvariant();

		if (getWdt_atpre > 0 && getHgt_atpre < getEnvi_atpre.getHeight() - 1) {
			Cell cell_leftup = getEnvi_atpre.getCellNature(getDelegate().getWdt() - 1, getDelegate().getHgt() + 1);
			Cell cell_left = getEnvi_atpre.getCellNature(getDelegate().getWdt() - 1, getDelegate().getHgt());
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardleft = true;
					}
				}
			}
			if (cell_left == Cell.MTL || cell_left == Cell.PLT || containGuardleft) {

				if (cell_leftup == Cell.EMP || cell_leftup == Cell.HOL || cell_leftup == Cell.LAD
						|| cell_leftup == Cell.HDR) {
					boolean containGuardleftUp = false;
					if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre + 1).getCar().isEmpty()) {
						for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre + 1)
								.getCar()) {
							if (cs.getClass().isInstance(GuardService.class)) {
								containGuardleftUp = true;
							}
						}
					}
					if (!containGuardleftUp) {
						if (!(getWdt() == getWdt_atpre - 1 && getHgt() == getHgt_atpre + 1)) {
							throw new PostconditionError("climbLeft()",
									"The cell you are trying to reach is not free or you are trying to climb on a unfree case adn your height and width should have change");
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

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());

		if (!(cell_atpre == Cell.HOL)) {
			throw new PreconditionError("climbRight()", "guard pos must be a HOL to call climb right");
		}

		boolean containGuardright = false;

		checkInvariant();
		getDelegate().climbRight();
		checkInvariant();

		if (getWdt_atpre < getEnvi_atpre.getWidth() - 1 && getHgt_atpre < getEnvi_atpre.getHeight() - 1) {
			Cell cell_rightup = getEnvi_atpre.getCellNature(getDelegate().getWdt() + 1, getDelegate().getHgt() + 1);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardright = true;
					}
				}
			}
			Cell cell_right = getEnvi_atpre.getCellNature(getDelegate().getWdt() + 1, getDelegate().getHgt());
			if (cell_right == Cell.MTL || cell_right == Cell.PLT || containGuardright) {
				if (cell_rightup == Cell.EMP || cell_rightup == Cell.HOL || cell_rightup == Cell.LAD
						|| cell_rightup == Cell.HDR) {
					boolean containGuardrightUp = false;
					if (!getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre + 1).getCar().isEmpty()) {
						for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre + 1)
								.getCar()) {
							if (cs.getClass().isInstance(GuardService.class)) {
								containGuardrightUp = true;
							}
						}
					}
					if (!containGuardrightUp) {
						if (!(getWdt() == getWdt_atpre + 1 && getHgt() == getHgt_atpre + 1)) {
							throw new PostconditionError("climbRight()",
									"The cell you are trying to reach is not free or you are trying to climb on a unfree case adn your height and width should have change");
						}
					}
				}
			}
		}

	}

	@Override
	public void goLeft() {
		System.out.println("hey");
		// Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		boolean containGuardleft = false;

		boolean containGuarddown = false;

		if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
			for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar()) {
				
				if (cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}

		checkInvariant();
		getDelegate().goLeft();
		checkInvariant();

		// Post
		if (getWdt_atpre == 0) {
			if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goLeft()",
						"getWdt()@pre == 0 should implies  getWdt() == getWdt()@pre and getHgt()@pre == getHgt()");
		}
		
		if (getWdt_atpre > 0) {
			Cell cell_left = getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar()) {
					System.out.println(cs.getClass());
					if (cs.getClass().isInstance(GuardService.class)) {
						
						containGuardleft = true;
					}
				}
			}
			if (cell_left == Cell.MTL || cell_left == Cell.PLT || containGuardleft) {
				if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
					throw new PostconditionError("goLeft()",
							"getWdt()@pre > 0 and  (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT} or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre)) \r\n"
									+ "		should implies getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");
			}
		}
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!containGuarddown) {
						if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
							throw new PostconditionError("goLeft()", "impossible while falling");
					}
				}
			}
		}

		if (getDelegate().getWdt() > 0) {
			Cell cell_left = getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardleft = true;
					}
				}
			}
			if (cell_left != Cell.MTL && cell_left != Cell.PLT) {
				if ((cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) || (cell_down == Cell.PLT
						|| cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown)) {
					if (!containGuardleft) {
						if (!(getWdt_atpre - 1 == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
							throw new PostconditionError("goLeft()",
									"(getWdt()@pre > 0 \\and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \\not in {MTL,PLT} )\r\n"
											+ "		//\\and \\not \\exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre))\r\n"
											+ "		//\\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\in {LAD,HDR}\r\n"
											+ "			//\\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\r\n"
											+ "			//\\or \\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\r\n"
											+ "			//\\implies getWdt() == getWdt()@pre - 1 \\and getHgt()@pre == getHgt()");
					}
				}
			}
		}
	}

	@Override
	public void goRight() {
		System.out.println("hoy");
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		boolean containGuardright = false;

		boolean containGuarddown = false;

		if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
			for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar()) {
				if (cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}

		checkInvariant();
		getDelegate().goRight();
		checkInvariant();

		// Post
		if (getWdt_atpre == getEnvi_atpre.getWidth() - 1) {
			if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goRight()",
						"getWdt()@pre == EnvironmentService::getWidth()-1 should implies  getWdt() == getWdt()@pre and getHgt()@pre == getHgt()");
		}
		if (getWdt_atpre < getEnvi_atpre.getWidth() - 1) {
			Cell cell_right = getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardright = true;
					}
				}
			}
			if (cell_right == Cell.MTL || cell_right == Cell.PLT || containGuardright) {
				if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
					throw new PostconditionError("goRight()",
							"getWdt()@pre < EnvironmentService::getWidth()-1 and  (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) in {MTL,PLT} or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre)) \r\n"
									+ "		should implies getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");
			}
		}
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!containGuarddown) {
						if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
							throw new PostconditionError("goLeft()", "impossible while falling");
					}
				}
			}
		}

		if (getDelegate().getWdt() > 0) {
			Cell cell_right = getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardright = true;
					}
				}
			}
			if (cell_right != Cell.MTL && cell_right != Cell.PLT) {
				if ((cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) || (cell_down == Cell.PLT
						|| cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown)) {
					if (!containGuardright) {
						if (!(getWdt_atpre - 1 == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
							throw new PostconditionError("goRight()",
									"(getWdt()@pre != EnvironmentService::getWidth()-1 \\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \\not in {MTL,PLT} )\r\n"
											+ "		//\\and \\not \\exist GuardService c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre+1,getHgt()@pre)) )\r\n"
											+ "		//\\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) \\in {LAD,HDR}\r\n"
											+ "			//\\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\r\n"
											+ "			//\\or \\exist GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\r\n"
											+ "			//\\implies getWdt() == getWdt()@pre +1 \\and getHgt()@pre == getHgt()");
					}
				}
			}
		}

	}

	@Override
	public void goUp() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		boolean containGuardup = false;

		boolean containGuarddown = false;

		if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
			for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar()) {
				if (cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}

		checkInvariant();
		getDelegate().goUp();
		checkInvariant();

		// Post
		if (getHgt_atpre == getEnvi_atpre.getHeight() - 1) {
			if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goUp()",
						"getHgt()@pre == EnvironmentService::getHeight()-1 should implies getHgt() == getHgt()@pre and getWdt() == getWdt()@pre ");
		}
		if (getHgt_atpre < getEnvi_atpre.getHeight() - 1) {
			Cell cell_up = getEnvi_atpre.getCellNature(getWdt_atpre, getHgt_atpre + 1);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardup = true;
					}
				}
			}
			if (cell_up == Cell.MTL || cell_up == Cell.PLT || cell_up == Cell.HDR || containGuardup) {
				if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
					throw new PostconditionError("goUp()",
							" getHgt()@pre < EnvironmentService::getHeight()-1 and (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT, HDR} or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1)\r\n"
									+ "		//\\implies getWdt()@pre == getWdt() \\and getHgt()@pre == getHgt()");
			}
		}

		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!containGuarddown) {
						if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
							throw new PostconditionError("goUp()", "goUp() is impossible while falling");

					}
				}

			}
		}
		if (getHgt_atpre < getEnvi_atpre.getHeight() - 1) {
			Cell cell_up = getEnvi_atpre.getCellNature(getWdt_atpre, getHgt_atpre + 1);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardup = true;
					}
				}
			}
			if (cell_atpre == Cell.LAD && (cell_up == Cell.LAD || cell_up == Cell.EMP)) {
				if(!containGuardup) {
					if (!(getHgt_atpre + 1 == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt())) {
						throw new PostconditionError("goUp()", "(getHgt()@pre != EnvironmentService::getHeight() -  1)\r\n" + 
								"		//\\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD\r\n" + 
								"		//\\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} \r\n" + 
								"		//\\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre +1)\r\n" + 
								"			//\\implies getHgt() = getHgt()@pre + 1 and getWdt()@pre == getWdt()");
					}
				}
			}
		}
	}

	@Override
	public void goDown() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		
		boolean containGuarddown = false;

		if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
			for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar()) {
				if (cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		
		checkInvariant();
		getDelegate().goDown();
		checkInvariant();
		
		// Post
		
		if(getHgt_atpre ==0) {
			if(!(getHgt_atpre == getDelegate().getHgt()  && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()" , "getHgt()@pre == 0 should implies getHgt()==getHgt()@pre and getWdt() == getWdt()@pre");
		}
		if(getHgt_atpre != 0 && (cell_down == Cell.MTL || cell_down ==Cell.PLT || containGuarddown)) {
			if(!(getHgt_atpre == getDelegate().getHgt()  && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()" , "getHgt()@pre > 0 \\and (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \\in {MTL, PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1))\r\n" + 
						"		//\\implies getWdt()@pre == getWdt() \\and getHgt()@pre == getHgt()");
		}
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!containGuarddown) {
						if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
							throw new PostconditionError("goLeft()", "impossible while falling");
					}
				}
			}
		}
		if(getHgt_atpre > 0) {
			if(cell_atpre == Cell.EMP || cell_atpre == Cell.LAD || cell_atpre == Cell.HDR ) {
				if(cell_down == Cell.EMP || cell_down == Cell.LAD  || cell_down == Cell.HOL || cell_down == Cell.HDR) {
					if(!containGuarddown) {
						if(! ( getHgt_atpre-1 == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()) ) {
							throw new PostconditionError("goDown()" , " getHgt()@pre > 0 and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) in {LAD,HDR,EMP,HOL}\r\n" + 
									"		and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) in {EMP,LAD,HDR,HOL}\r\n" + 
									"			should implie getHgt()==getHgt()@pre-1 and getWdt() == getWdt()@pre");
						}
					}	
				}
			}
		}
	}

	@Override
	public void doNeutral() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);
		
		boolean containGuarddown = false;
		if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
			for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar()) {
				if (cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}

		checkInvariant();
		getDelegate().doNeutral();
		checkInvariant();

		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (!containGuarddown) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("doNeutral()",
								"you should be falling there");

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
		cloneR.clone(getDelegate());
		cloneR.goRight();
		GuardImpl cloneL = new GuardImpl();
		cloneL.clone(getDelegate());
		cloneL.goLeft();
		GuardImpl cloneU = new GuardImpl();
		cloneU.clone(getDelegate());
		cloneU.goUp();
		GuardImpl cloneD = new GuardImpl();
		cloneD.clone(getDelegate());
//		.init(getDelegate().getEnvi(), getDelegate().getWdt(), getDelegate().getHgt());
		cloneD.goDown();
		GuardImpl cloneN = new GuardImpl();
		cloneN.clone(getDelegate());
		cloneN.doNeutral();
		GuardImpl cloneCL = new GuardImpl();
		cloneCL.clone(getDelegate());
		cloneCL.climbLeft();
		GuardImpl cloneCR = new GuardImpl();
		cloneCR.clone(getDelegate());
		cloneCR.climbRight();

		checkInvariant();
		getDelegate().step();
		checkInvariant();

		// Post

		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre < 5) {
				if (!(getDelegate().getTimeInHole() == getTimeInHole_atpre + 1 && getWdt_atpre == getDelegate().getWdt()
						&& getHgt_atpre == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n" + 
							"		//\\and getTimeInHole()@pre<5 \r\n" + 
							"		//\\implies getTimeInHole() == getTimeInHole()@pre + 1 \\and getWdt()@pre == getWdt() \\and getHgt()@pre = getHgt()");
				}
			}
		}
		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre >= 5 && getBehaviour_atpre == Move.LEFT) {
				if (!(cloneCL.getWdt() == getDelegate().getWdt() && cloneCL.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n" + 
							"		//\\and getTimeInHole()@pre>=5 \r\n" + 
							"		//\\and getBehaviour()@pre == LEFT\r\n" + 
							"		//\\and clone@pre:GuardService \r\n" + 
							"			//\\implies getWdt() == clone@pre.climbLeft().getWdt() \\and getHgt() = clone@pre.climbLeft().getHgt()");
				}
			}
		}
		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre >= 5 && getBehaviour_atpre == Move.RIGHT) {
				if (!(cloneCR.getWdt() == getDelegate().getWdt() && cloneCR.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n" + 
							"		//\\and getTimeInHole()@pre>=5 \r\n" + 
							"		//\\and getBehaviour()@pre == RIGHT \r\n" + 
							"		//\\and clone@pre:GuardService \r\n" + 
							"	//\\implies getWdt() == clone@pre.climbRight().getWdt() \\and getHgt() = clone@pre.climbRight().getHgt()");
				}
			}
		}
		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre >= 5 && getBehaviour_atpre == Move.NEUTRAL) {
				if (!(cloneN.getWdt() == getDelegate().getWdt() && cloneN.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n" + 
							"		//\\and getTimeInHole()@pre>=5 \r\n" + 
							"		//\\and getBehaviour()@pre == NEUTRAL \r\n" + 
							"		//\\and clone@pre:GuardService \r\n" + 
							"			//\\implies getWdt() == clone@pre.doNeutral().getWdt() \\and getHgt() = clone@pre.doNeutral().getHgt()");
				}
			}
		}

		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.LEFT) {
			goLeft();
			
			if (!(cloneL.getWdt() == getDelegate().getWdt() && cloneL.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n" + 
						"		//\\and getBehaviour()@pre == LEFT\r\n" + 
						"		//\\and clone@pre:GuardService \r\n" + 
						"			//\\implies getWdt() == clone@pre.goLeft().getWdt() \\and getHgt() = clone@pre.goLeft().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.RIGHT) {
			if (!(cloneR.getWdt() == getDelegate().getWdt() && cloneR.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						":EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n" + 
						"		//\\and getBehaviour()@pre == RIGHT\r\n" + 
						"		//\\and clone@pre:GuardService \r\n" + 
						"			//\\implies getWdt() == clone@pre.goRight().getWdt() = clone@pre.goRight().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.UP) {
			if (!(cloneU.getWdt() == getDelegate().getWdt() && cloneU.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						":EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n" + 
						"		//\\and getBehaviour()@pre == UP\r\n" + 
						"		//\\and clone@pre:GuardService \r\n" + 
						"			//\\implies getWdt() == clone@pre.goUp().getWdt() = clone@pre.goUp().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.DOWN) {
			if (!(cloneD.getWdt() == getDelegate().getWdt() && cloneD.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n" + 
						"		//\\and getBehaviour()@pre == DOWN\r\n" + 
						"		//\\and clone@pre:GuardService \r\n" + 
						"			//\\implies getWdt() == clone@pre.goDown().getWdt() = clone@pre.goDown().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.NEUTRAL) {
			if (!(cloneN.getWdt() == getDelegate().getWdt() && cloneN.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n" + 
						"		//\\and getBehaviour()@pre == NEUTRAL\r\n" + 
						"		//\\and clone@pre:GuardService \r\n" + 
						"			//\\implies getWdt() == clone@pre.doNeutral().getWdt() = clone@pre.doNeutral().getHgt()");
			}
		}
	}

}
