package lonelyrunner.contract;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.PlayerDecorator;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.impl.PlayerImpl;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class PlayerContract extends PlayerDecorator {

	public PlayerContract(PlayerService c) {
		super(c);
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
			if (cs.getClass() == (PlayerImpl.class)) {
				if (((PlayerImpl) cs).getId() == 1111) {
					chara = true;
				}
			}
		}
		if (!(chara)) {
			throw new InvariantError(
					"exist Player P in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) should implie p = this");
		}
	}

	@Override
	public void init(ScreenService s, int x, int y, EngineService engine) {
		if (!(s.getCellNature(x, y) == Cell.EMP)) {
			throw new PreconditionError("init( s, " + x + ", " + y + " )", "Cell is not empty");
		}
		if (!(engine.getStatus() == Status.Playing)) {
			throw new PreconditionError("init( s, " + x + ", " + y + " )", "engine is not on playing mode");
		}
		getDelegate().init(s, x, y, engine);
		checkInvariant();
		if (!(getDelegate().getEngine().getEnvironment().getCellContent(x, y).isInside(getDelegate()))) {
			throw new PreconditionError("init( s, " + x + ", " + y + " )", "player not set in engine environment");
		}
	}

	@Override
	public void step() {

		Move com = getDelegate().getEngine().getNextCommand();
		
		PlayerImpl cloneR = new PlayerImpl();
		cloneR.clone(getDelegate() , getDelegate().getEngine());
		cloneR.goRight();

		PlayerImpl cloneL = new PlayerImpl();
		cloneL.clone(getDelegate() , getDelegate().getEngine());
		cloneL.goLeft();

		PlayerImpl cloneU = new PlayerImpl();
		cloneU.clone(getDelegate() , getDelegate().getEngine());
		cloneU.goUp();

		PlayerImpl cloneD = new PlayerImpl();
		cloneD.clone(getDelegate() , getDelegate().getEngine());
		cloneD.goDown();

		PlayerImpl cloneN = new PlayerImpl();
		cloneN.clone(getDelegate() , getDelegate().getEngine());
		cloneN.doNeutral();

		checkInvariant();
		getDelegate().step();
		checkInvariant();

		if (com == Move.LEFT) {
			if (!(cloneL.getWdt() == getDelegate().getWdt() && cloneL.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()", "should have made a goLeft");
			}
		}
		if (com == Move.RIGHT) {
			if (!(cloneR.getWdt() == getDelegate().getWdt() && cloneR.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()", "should have made a goRight");
			}
		}
		if (com == Move.UP) {
			if (!(cloneU.getWdt() == getDelegate().getWdt() && cloneU.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()", "should have made a goUp");
			}
		}
		if (com == Move.DOWN) {
			if (!(cloneD.getWdt() == getDelegate().getWdt() && cloneD.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()", "should have made a goDown");
			}
		}

		if (com == Move.NEUTRAL) {
			if (!(cloneN.getWdt() == getDelegate().getWdt() && cloneN.getHgt() == getDelegate().getHgt())) {
				throw new PostconditionError("step()", "should have made a doNeutral");
			}
		}
		

	}

	@Override
	public void digL() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		HashMap<Couple<Integer, Integer>, Cell> getCellNature_atpre = new HashMap<>();
		for (int i = 0; i < getEnvi_atpre.getWidth(); i++) {
			for (int j = 0; j < getEnvi_atpre.getHeight(); j++) {
				if (i == getWdt_atpre - 1 && j == getHgt_atpre - 1) {
					continue;
				}
				Couple<Integer, Integer> c = new Couple<Integer, Integer>(i, j);
				Cell nc = getEnvi_atpre.getCellNature(i, j);
				getCellNature_atpre.put(c, nc);
			}
		}

		ArrayList<Hole> getHoles_atpre = new ArrayList<>();

		for (Hole h : getDelegate().getEngine().getHoles()) {
			getHoles_atpre.add(new Hole(h.getX(), h.getY(), h.getTime()));
		}

		checkInvariant();
		getDelegate().digL();
		checkInvariant();

		// Post
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("digL()", "digL() is impossible while falling");
				}
			}
		}
		if (getWdt_atpre > 0) {
			if ((cell_down == Cell.MTL || cell_down == Cell.PLT || cell_down == Cell.LAD)
					|| (!(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()))) {
				Cell cell_leftdown = getEnvi_atpre.getCellNature(getDelegate().getWdt() - 1,
						getDelegate().getHgt() - 1);
				Cell cell_left = getEnvi_atpre.getCellNature(getDelegate().getWdt() - 1, getDelegate().getHgt());
				if (cell_leftdown == Cell.PLT) {
					if (getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getCar().isEmpty()
							&& getEnvi_atpre.getCellContent(getWdt_atpre - 1, getHgt_atpre).getItem() == null
							&& (cell_left != Cell.MTL && cell_left != Cell.PLT)) {
						if (!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt() - 1,
								getDelegate().getHgt() - 1) == Cell.HOL)) {
							throw new PostconditionError("digL()", "the cell should have been hol");
						}
						if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
							throw new PostconditionError("digL()()", "moving impossible while digging");
						if ((getHoles_atpre.size() + 1 == getDelegate().getEngine().getHoles().size())) {
							for (Hole h : getDelegate().getEngine().getHoles()) {
								if (h.getX() == getDelegate().getWdt() - 1 && h.getY() == getDelegate().getHgt() - 1
										&& h.getTime() == 0) {
									continue;
								}
								if (!getHoles_atpre.contains(h)) {
									throw new PostconditionError("digL()",
											"Must not touch the other holes in getHoles()");
								}
							}
						}
						for (int i = 0; i < getEnvi_atpre.getWidth(); i++) {
							for (int j = 0; j < getEnvi_atpre.getHeight(); j++) {
								Cell nc = null;
								for (Couple<Integer, Integer> cp : getCellNature_atpre.keySet()) {
									if (cp.getElem1() == i && cp.getElem2() == j) {
										nc = getCellNature_atpre.get(cp);
									}
								}

								if (nc != null) {
									if (!(getEnvi_atpre.getCellNature(i, j) == nc)) {
										throw new PostconditionError(
												"dig(" + (getWdt_atpre - 1) + ", " + (getHgt_atpre - 1) + " )",
												"cell (" + i + ", " + j + ") changed ");
									}
								}

							}

						}

					}
				}
			}
		}
	}

	@Override
	public void digR() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());

		HashMap<Couple<Integer, Integer>, Cell> getCellNature_atpre = new HashMap<>();
		for (int i = 0; i < getEnvi_atpre.getWidth(); i++) {
			for (int j = 0; j < getEnvi_atpre.getHeight(); j++) {
				if (i == getWdt_atpre + 1 && j == getHgt_atpre - 1) {
					continue;
				}
				Couple<Integer, Integer> c = new Couple<Integer, Integer>(i, j);
				Cell nc = getEnvi_atpre.getCellNature(i, j);
				getCellNature_atpre.put(c, nc);
			}
		}

		ArrayList<Hole> getHoles_atpre = new ArrayList<>();

		for (Hole h : getDelegate().getEngine().getHoles()) {
			getHoles_atpre.add(new Hole(h.getX(), h.getY(), h.getTime()));
		}

		checkInvariant();
		getDelegate().digR();
		checkInvariant();

		// Post

		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("digR()", "digR() is impossible while falling");
				}
			}
		}
		if (getWdt_atpre < getEnvi_atpre.getHeight() - 1) {
			if ((cell_down == Cell.MTL || cell_down == Cell.PLT)
					|| (!(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()))) {
				Cell cell_rightdown = getDelegate().getEnvi().getCellNature(getDelegate().getWdt() + 1,
						getDelegate().getHgt() - 1);
				Cell cell_right = getDelegate().getEnvi().getCellNature(getDelegate().getWdt() + 1,
						getDelegate().getHgt());
				if (cell_rightdown == Cell.PLT) {
					if (getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getCar().isEmpty()
							&& getEnvi_atpre.getCellContent(getWdt_atpre + 1, getHgt_atpre).getItem() == null
							&& (cell_right != Cell.MTL && cell_right != Cell.PLT)) {
						if (!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt() - 1,
								getDelegate().getHgt() - 1) == Cell.HOL)) {
							throw new PostconditionError("digR()", "the cell should have been hol");
						}
						if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
							throw new PostconditionError("digL()()", "moving impossible while digging");
						if ((getHoles_atpre.size() + 1 == getDelegate().getEngine().getHoles().size())) {
							for (Hole h : getDelegate().getEngine().getHoles()) {
								if (h.getX() == getDelegate().getWdt() + 1 && h.getY() == getDelegate().getHgt() - 1
										&& h.getTime() == 0) {
									continue;
								}
								if (!getHoles_atpre.contains(h)) {
									throw new PostconditionError("digR()",
											"Must not touch the other holes in getHoles()");
								}
							}
						}
						for (int i = 0; i < getEnvi_atpre.getWidth(); i++) {
							for (int j = 0; j < getEnvi_atpre.getHeight(); j++) {
								Cell nc = null;
								for (Couple<Integer, Integer> cp : getCellNature_atpre.keySet()) {
									if (cp.getElem1() == i && cp.getElem2() == j) {
										nc = getCellNature_atpre.get(cp);
									}
								}

								if (nc != null) {
									if (!(getEnvi_atpre.getCellNature(i, j) == nc)) {
										throw new PostconditionError(
												"dig(" + (getWdt_atpre + 1) + ", " + (getHgt_atpre - 1) + " )",
												"cell (" + i + ", " + j + ") changed ");
									}
								}

							}

						}

					}
				}
			}
		}
	}

	@Override
	public EngineService getEngine() {
		return getDelegate().getEngine(); // bizarre
	}

	@Override
	public void doNeutral() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		checkInvariant();
		getDelegate().doNeutral();
		checkInvariant();

		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("doNeutral()",
								"EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) not in {LAD,HDR} \n"
										+ "			and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) not in {PLT,MTL,LAD}\n"
										+ "			and not exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n"
										+ "				should implie getWdt() == getWdt()@pre \\and getHgt() == getHgt()@pre - 1");

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

		checkInvariant();
		getDelegate().goLeft();
		checkInvariant();

		// Post
		if (getWdt_atpre == 0) {
			if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goLeft()",
						"getWdt()@pre == 0 implies getWdt() == getWdt()@pre and getHgt()@pre == getHgt()");
		}
		if (getWdt_atpre > 0) {
			if (getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre) == Cell.MTL
					|| getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre) == Cell.PLT) {
				if (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
						throw new PostconditionError("goLeft()",
								"getWdt()@pre > 0 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT} and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} implies getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");
				}
			}
		}
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("goLeft()", "goLeft() is impossible while falling");
				}
			}
		}
		if (getWdt_atpre > 0 && getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre) != Cell.MTL
				&& getEnvi_atpre.getCellNature(getWdt_atpre - 1, getHgt_atpre) != Cell.PLT) {
			if ((cell_atpre == Cell.LAD || cell_atpre == Cell.HDR)
					|| (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD
							|| !(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()))) {
				if (!(getWdt_atpre - 1 == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
					throw new PostconditionError("goLeft()",
							"(getWdt()@pre > 0 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) not in {MTL,PLT} )\n"
									+ "		and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) in {LAD,HDR}\n"
									+ "			or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n"
									+ "			or exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n"
									+ "		should implie getWdt() == getWdt()@pre - 1 and getHgt()@pre == getHgt()");
			}
		}
	}

	@Override
	public void goRight() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();

		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());

		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		checkInvariant();
		getDelegate().goRight();
		checkInvariant();

		// Post

		if (getWdt_atpre == getEnvi_atpre.getWidth() - 1) {
			if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goRight()",
						"getWdt()@pre == EnvironmentService::getWidth()-1 implies getWdt() == getWdt()@pre and getHgt()@pre == getHgt()");
		}
		if (getWdt_atpre < getDelegate().getWdt() - 1) {
			if ((getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre) == Cell.MTL
					|| getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre) == Cell.PLT)) {
				if (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
						throw new PostconditionError("goRight()",
								"getWdt()@pre < EnvironmentService::getWidth()-1 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) in {MTL,PLT} and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} should implie"
										+ "getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");
				}

			}
		}
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("goRight()", "goRight() is impossible while falling");
				}
			}
		}
		if (getWdt_atpre < getEnvi_atpre.getWidth() - 1
				&& getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre) != Cell.MTL
				&& getEnvi_atpre.getCellNature(getWdt_atpre + 1, getHgt_atpre) != Cell.PLT) {
			if ((cell_atpre == Cell.LAD || cell_atpre == Cell.HDR)
					|| (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD
							|| !(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()))) {
				if (!(getWdt_atpre + 1 == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
					throw new PostconditionError("goRight()",
							"( getWdt()@pre < EnvironmentService::getWidth()-1 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) in {LAD,HDR}\n"
									+ "			or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n"
									+ "			or exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n"
									+ "		should implie getWdt() == getWdt()@pre +1 and getHgt()@pre == getHgt()");
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
			if (cell_up == Cell.MTL || cell_up == Cell.PLT || cell_up == Cell.HDR) {
				if (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD) {
					if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
						throw new PostconditionError("goUp()",
								"EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT, HDR} and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD} should implies getHgt() = getHgt()@pre and getWdt() == getWdt()@pre");
				}
			}
		}

		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("goUp()", "goUp() is impossible while falling");

				}
			}
		}
		if (getHgt_atpre < getEnvi_atpre.getHeight() - 1) {
			Cell cell_up = getEnvi_atpre.getCellNature(getWdt_atpre, getHgt_atpre + 1);
			if (cell_atpre == Cell.LAD && (cell_up == Cell.LAD || cell_up == Cell.EMP)) {
				if (!(getHgt_atpre + 1 == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt())) {
					throw new PostconditionError("goUp()", "(getHgt()@pre <EnvironmentService::getHeight() -  1)\n"
							+ "		and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD\n"
							+ "		and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} \n"
							+ "		should implie getHgt() = getHgt()@pre + 1 and getWdt() == getWdt()@pre");
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
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt() - 1);

		checkInvariant();
		getDelegate().goDown();
		checkInvariant();

		// Post

		if (getHgt_atpre == 0) {
			if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()",
						"getHgt()@pre == 0 should implies getHgt()==getHgt()@pre and getWdt() == getWdt()@pre");
		}
		if (getHgt_atpre > 0 && (cell_down == Cell.MTL || cell_down == Cell.PLT)) {
			if (!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()",
						"(EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) in {MTL, PLT} should implie getHgt()==getHgt()@pre and getWdt() == getWdt()@pre");
		}
		if (cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre - 1).getCar().isEmpty()) {
					if (!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre - 1 == getDelegate().getHgt()))
						throw new PostconditionError("goDown()", "goDown() impossible while falling");
				}
			}
		}
		if (getHgt_atpre > 0) {
			if (cell_atpre == Cell.EMP || cell_atpre == Cell.LAD || cell_atpre == Cell.HDR || cell_atpre == Cell.HOL) {
				if (cell_down == Cell.EMP || cell_down == Cell.LAD || cell_down == Cell.HOL || cell_down == Cell.HDR) {
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
