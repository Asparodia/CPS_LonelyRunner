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
		if (!(c == Cell.EMP || c == Cell.HOL || c == Cell.LAD || c == Cell.HDR)) {
			throw new InvariantError(
					"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) not in {EMP,HOL,LAD,HDR}");
		}
		boolean chara = false;
		for (CharacterService cs : getDelegate().getEnvi()
				.getCellContent(getDelegate().getWdt(), getDelegate().getHgt()).getCar()) {
			if (cs.getClass() == (GuardImpl.class)) {
				if (((GuardService) cs).getId() == getDelegate().getId()) {
					chara = true;
				}
			}
		}
		if (!(chara)) {
			throw new InvariantError(
					"exist GuardService P in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) should implie p = this");
		}

	}

	@Override
	public void init(ScreenService s, int x, int y, PlayerService p) {
		// pre
		if (!(s.getCellNature(x, y) == Cell.EMP)) {
			throw new PreconditionError("init( s, " + x + ", " + y + " )", "Cell is not empty");
		}

		// Call
		getDelegate().init(s, x, y, p);
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
		// Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		int time = getDelegate().getTimeInHole();
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());

		boolean containGuardleft = false;
		// pre
		if (!(cell_atpre == Cell.HOL)) {
			throw new PreconditionError("climbLeft()", "must be on a hole to climb");
		}

		// Call
		checkInvariant();
		getDelegate().climbLeft();
		checkInvariant();

		// Post
		if (cell_atpre == Cell.HOL) {
			if (getWdt_atpre > 0 && getHgt_atpre < getEnvi_atpre.getHeight() - 1 && time >= 5) {
				Cell cell_leftup = getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre + 1);
				Cell cell_left = getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre);
				if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
					for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar()) {
						if (cs.getClass() == (GuardImpl.class)) {
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
								if (cs.getClass() == (GuardImpl.class)) {
									containGuardleftUp = true;
								}
							}
						}
						if (!containGuardleftUp) {
							if (!(getDelegate().getWdt() == getWdt_atpre - 1
									&& getDelegate().getHgt() == getHgt_atpre + 1
									&& getDelegate().getTimeInHole() == 0)) {
								throw new PostconditionError("climbLeft()",
										"The cell you are trying to reach is not free or you are trying to climb on a unfree case adn your height and width should have change");
							}
						}
					}
				}
			}
		}

	}

	@Override
	public void climbRight() {
		// Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		int time = getDelegate().getTimeInHole();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());

		// pre
		if (!(cell_atpre == Cell.HOL)) {
			throw new PreconditionError("climbRight()", "must be on a hole to climb");
		}

		boolean containGuardright = false;

		// Call
		checkInvariant();
		getDelegate().climbRight();
		checkInvariant();

		// Post
		if (cell_atpre == Cell.HOL) {
			if (getWdt_atpre < getEnvi_atpre.getWidth() - 1 && getHgt_atpre < getEnvi_atpre.getHeight() - 1
					&& time >= 5) {
				Cell cell_rightup = getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre + 1);
				if (!getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar().isEmpty()) {
					for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
						if (cs.getClass() == (GuardImpl.class)) {
							containGuardright = true;
						}
					}
				}
				Cell cell_right = getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre);
				if (cell_right == Cell.MTL || cell_right == Cell.PLT || containGuardright) {
					if (cell_rightup == Cell.EMP || cell_rightup == Cell.HOL || cell_rightup == Cell.LAD
							|| cell_rightup == Cell.HDR) {
						boolean containGuardrightUp = false;
						if (!getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre + 1).getCar().isEmpty()) {
							for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre + 1)
									.getCar()) {
								if (cs.getClass() == (GuardImpl.class)) {
									containGuardrightUp = true;
								}
							}
						}
						if (!containGuardrightUp) {
							if (!(getDelegate().getWdt() == getWdt_atpre + 1
									&& getDelegate().getHgt() == getHgt_atpre + 1
									&& getDelegate().getTimeInHole() == 0)) {
								throw new PostconditionError("climbRight()",
										"The cell you are trying to reach is not free or you are trying to climb on a unfree case adn your height and width should have change");
							}
						}
					}
				}
			}

		}

	}

	@Override
	public void goLeft() {
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

				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		// Call

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
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardleft = true;
					}
				}
			}
			if (cell_left == Cell.MTL || cell_left == Cell.PLT || containGuardleft) {
				if (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
						throw new PostconditionError("goLeft()",
								"getWdt()@pre > 0 and  (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT} or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre)) \r\n"
										+ "and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))"
										+ "		should implies getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");
				}
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

		if (getWdt_atpre > 0) {
			Cell cell_left = getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {

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
		// Captures
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
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}

		// Call

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
			if (!getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardright = true;
					}
				}
			}
			if (cell_right == Cell.MTL || cell_right == Cell.PLT || containGuardright) {
				if (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
						throw new PostconditionError("goRight()",
								"getWdt()@pre < EnvironmentService::getWidth()-1 and  (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) in {MTL,PLT} or GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre)) \r\n"
										+ "and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))"
										+ "		should implies getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");

				}
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

		if (getWdt_atpre < getEnvi_atpre.getWidth() - 1) {
			Cell cell_right = getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre);
			if (!getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardright = true;
					}
				}
			}
			if (cell_right != Cell.MTL && cell_right != Cell.PLT) {
				if ((cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) || (cell_down == Cell.PLT
						|| cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown)) {
					if (!containGuardright) {
						if (!(getWdt_atpre + 1 == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
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

		// Captures
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
				if (cs.getClass() == (GuardImpl.class)) {
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
			if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre + 1).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre + 1).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardup = true;
					}
				}
			}
			if (cell_up == Cell.MTL || cell_up == Cell.PLT || cell_up == Cell.HDR || containGuardup) {
				if (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD || containGuarddown) {
					if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
						throw new PostconditionError("goUp()",
								" getHgt()@pre < EnvironmentService::getHeight()-1 and (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT, HDR} or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1)\r\n"
										+ "and (EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} or exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))"
										+ "		//\\implies getWdt()@pre == getWdt() \\and getHgt()@pre == getHgt()");

				}
			}
		}

		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
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
			if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre + 1).getCar().isEmpty()) {
				for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre + 1).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardup = true;
					}
				}
			}
			if (cell_atpre == Cell.LAD && (cell_up == Cell.LAD || cell_up == Cell.EMP)) {
				if (!containGuardup) {
					if (!(getHgt_atpre + 1 == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt())) {
						throw new PostconditionError("goUp()",
								"(getHgt()@pre != EnvironmentService::getHeight() -  1)\r\n"
										+ "		//\\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD\r\n"
										+ "		//\\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} \r\n"
										+ "		//\\and not exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre +1)\r\n"
										+ "			//\\implies getHgt() = getHgt()@pre + 1 and getWdt()@pre == getWdt()");
					}
				}
			}
		}
	}

	@Override
	public void goDown() {
		// Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		boolean containGuarddown = false;

		if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
			for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}

		// Call
		checkInvariant();
		getDelegate().goDown();
		checkInvariant();

		// Post

		if (getHgt_atpre == 0) {
			if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()",
						"getHgt()@pre == 0 should implies getHgt()==getHgt()@pre and getWdt() == getWdt()@pre");
		}
		if (getHgt_atpre > 0 && (cell_down == Cell.MTL || cell_down == Cell.PLT || containGuarddown)) {
			if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()",
						"getHgt()@pre > 0 \\and (EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) \\in {MTL, PLT} \\or  exists GuardService c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHdt()@pre - 1))\r\n"
								+ "		//\\implies getWdt()@pre == getWdt() \\and getHgt()@pre == getHgt()");
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
		if (getHgt_atpre > 0) {
			if (cell_atpre == Cell.EMP || cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) {
				if (cell_down == Cell.EMP || cell_down == Cell.LAD || cell_down == Cell.HOL || cell_down == Cell.HDR) {
					if (!containGuarddown) {
						if (!(getHgt_atpre - 1 == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt())) {
							throw new PostconditionError("goDown()",
									" getHgt()@pre > 0 and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) in {LAD,HDR,EMP,HOL}\r\n"
											+ "		and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) in {EMP,LAD,HDR,HOL}\r\n"
											+ "			should implie getHgt()==getHgt()@pre-1 and getWdt() == getWdt()@pre");
						}
					}
				}
			}
		}
	}

	@Override
	public void doNeutral() {
		// Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		boolean containGuarddown = false;
		if (!getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
			for (CharacterService cs : getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		// Call

		checkInvariant();
		getDelegate().doNeutral();
		checkInvariant();

		// Post
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR && cell_atpre != Cell.HOL) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (!containGuarddown) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("doNeutral()", "you should be falling there");

				}
			}
		}
	}

	@Override
	public void step() {
		// Captures

		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());

		Move getBehaviour_atpre = getDelegate().getBehaviour();
		int getTimeInHole_atpre = getDelegate().getTimeInHole();

		GuardImpl clone = new GuardImpl();
		clone.clone(getDelegate());
		GuardContract cloneR = new GuardContract(clone);
		cloneR.goRight();

		GuardImpl clone2 = new GuardImpl();
		clone2.clone(getDelegate());
		GuardContract cloneL = new GuardContract(clone2);
		cloneL.goLeft();

		GuardImpl clone3 = new GuardImpl();
		clone3.clone(getDelegate());
		GuardContract cloneU = new GuardContract(clone3);
		cloneU.goUp();

		GuardImpl clone4 = new GuardImpl();
		clone4.clone(getDelegate());
		GuardContract cloneD = new GuardContract(clone4);
		cloneD.goDown();

		GuardImpl clone5 = new GuardImpl();
		clone5.clone(getDelegate());
		GuardContract cloneN = new GuardContract(clone5);
		cloneN.doNeutral();

		GuardImpl clone6 = null;
		GuardContract cloneCL = null;
		GuardImpl clone7 = null;
		GuardContract cloneCR = null;
		if (cell_atpre == Cell.HOL && getTimeInHole_atpre >= 5) {
			clone6 = new GuardImpl();
			clone6.clone(getDelegate());
			cloneCL = new GuardContract(clone6);
			cloneCL.climbLeft();

			clone7 = new GuardImpl();
			clone7.clone(getDelegate());
			cloneCR = new GuardContract(clone7);
			cloneCR.climbRight();
		}
		
		// Call

		checkInvariant();
		getDelegate().step();
		checkInvariant();

		// Post

		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre < 5) {
				if (!(getDelegate().getTimeInHole() == getTimeInHole_atpre + 1 && getWdt_atpre == getDelegate().getWdt()
						&& getHgt_atpre == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n"
									+ "		//\\and getTimeInHole()@pre<5 \r\n"
									+ "		//\\implies getTimeInHole() == getTimeInHole()@pre + 1 \\and getWdt()@pre == getWdt() \\and getHgt()@pre = getHgt()");
				}
			}
		}
		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre >= 5 && getBehaviour_atpre == Move.LEFT) {
				if (!(cloneCL.getWdt() == getDelegate().getWdt() && cloneCL.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n"
									+ "		//\\and getTimeInHole()@pre>=5 \r\n"
									+ "		//\\and getBehaviour()@pre == LEFT\r\n"
									+ "		//\\and clone@pre:GuardService \r\n"
									+ "			//\\implies getWdt() == clone@pre.climbLeft().getWdt() \\and getHgt() = clone@pre.climbLeft().getHgt()");
				}
			}
		}
		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre >= 5 && getBehaviour_atpre == Move.RIGHT) {
				if (!(cloneCR.getWdt() == getDelegate().getWdt() && cloneCR.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n"
									+ "		//\\and getTimeInHole()@pre>=5 \r\n"
									+ "		//\\and getBehaviour()@pre == RIGHT \r\n"
									+ "		//\\and clone@pre:GuardService \r\n"
									+ "	//\\implies getWdt() == clone@pre.climbRight().getWdt() \\and getHgt() = clone@pre.climbRight().getHgt()");
				}
			}
		}
		if (cell_atpre == Cell.HOL) {
			if (getTimeInHole_atpre >= 5 && getBehaviour_atpre == Move.NEUTRAL) {
				if (!(cloneN.getWdt() == getDelegate().getWdt() && cloneN.getHgt() == getDelegate().getHgt())) {
					throw new PostconditionError("step()",
							"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL\r\n"
									+ "		//\\and getTimeInHole()@pre>=5 \r\n"
									+ "		//\\and getBehaviour()@pre == NEUTRAL \r\n"
									+ "		//\\and clone@pre:GuardService \r\n"
									+ "			//\\implies getWdt() == clone@pre.doNeutral().getWdt() \\and getHgt() = clone@pre.doNeutral().getHgt()");
				}
			}
		}

		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.LEFT) {
			if (!(cloneL.getWdt() == getDelegate().getWdt() && cloneL.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n"
								+ "		//\\and getBehaviour()@pre == LEFT\r\n"
								+ "		//\\and clone@pre:GuardService \r\n"
								+ "			//\\implies getWdt() == clone@pre.goLeft().getWdt() \\and getHgt() = clone@pre.goLeft().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.RIGHT) {
			if (!(cloneR.getWdt() == getDelegate().getWdt() && cloneR.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						":EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n"
								+ "		//\\and getBehaviour()@pre == RIGHT\r\n"
								+ "		//\\and clone@pre:GuardService \r\n"
								+ "			//\\implies getWdt() == clone@pre.goRight().getWdt() = clone@pre.goRight().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.UP) {
			if (!(cloneU.getWdt() == getDelegate().getWdt() && cloneU.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						":EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n"
								+ "		//\\and getBehaviour()@pre == UP\r\n"
								+ "		//\\and clone@pre:GuardService \r\n"
								+ "			//\\implies getWdt() == clone@pre.goUp().getWdt() = clone@pre.goUp().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.DOWN) {
			if (!(cloneD.getWdt() == getDelegate().getWdt() && cloneD.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n"
								+ "		//\\and getBehaviour()@pre == DOWN\r\n"
								+ "		//\\and clone@pre:GuardService \r\n"
								+ "			//\\implies getWdt() == clone@pre.goDown().getWdt() = clone@pre.goDown().getHgt()");
			}
		}
		if (cell_atpre != Cell.HOL && getBehaviour_atpre == Move.NEUTRAL) {
			if (!(cloneN.getWdt() == getDelegate().getWdt() && cloneN.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()",
						"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) != HOL\r\n"
								+ "		//\\and getBehaviour()@pre == NEUTRAL\r\n"
								+ "		//\\and clone@pre:GuardService \r\n"
								+ "			//\\implies getWdt() == clone@pre.doNeutral().getWdt() = clone@pre.doNeutral().getHgt()");
			}
		}
	}

}
