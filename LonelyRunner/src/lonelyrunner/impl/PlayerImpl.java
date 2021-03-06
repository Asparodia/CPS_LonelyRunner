package lonelyrunner.impl;

import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Move;

public class PlayerImpl extends CharacterImpl implements PlayerService {

	private static int id = 1111;

	private EngineService engine;

	@Override
	public EngineService getEngine() {
		return engine;
	}

	@Override
	public void init(ScreenService s, int x, int y, EngineService engine) {
		width = x;
		height = y;
		env = (EnvironmentService) s;
		engine.getEnvironment().getCellContent(x, y).addCar(this);
		this.engine = engine;
	}

	@Override
	public void step() {
		Move next = engine.getNextCommand();
		switch (next) {
		case RIGHT:
			super.goRight();
			break;
		case LEFT:
			super.goLeft();
			break;
		case DOWN:
			super.goDown();
			break;
		case UP:
			super.goUp();
			break;
		case DigL:
			this.digL();
			break;
		case DigR:
			this.digR();
			break;
		case NEUTRAL:
			doNeutral();
			break;
		default:
			break;
		}
	}

	@Override
	public void digL() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (env.getCellContent(width, height - 1).getCar().isEmpty()) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}
			}
		}

		Cell cell_down = getEnvi().getCellNature(getWdt(), getHgt() - 1);
		if (this.getWdt() > 0) {
			Cell cell_leftdown = getEnvi().getCellNature(getWdt() - 1, getHgt() - 1);
			Cell cell_left = getEnvi().getCellNature(getWdt() - 1, getHgt());
			if ((cell_down == Cell.MTL || cell_down == Cell.PLT || cell_down == Cell.LAD)
					|| (!(getEnvi().getCellContent(getWdt(), getHgt() - 1).getCar().isEmpty()))) {
				if (cell_leftdown == Cell.PLT) {
					if (getEnvi().getCellContent(getWdt() - 1, getHgt()).getCar().isEmpty()
							&& getEnvi().getCellContent(getWdt() - 1, getHgt()).getItem() == null
							&& (cell_left != Cell.MTL && cell_left != Cell.PLT)) {
						engine.getEnvironment().dig(getWdt() - 1, getHgt() - 1);
						engine.getHoles().add(new Hole(getWdt() - 1, getHgt() - 1, 0));
					}
				}
			}
		}
	}

	@Override
	public void digR() {

		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (env.getCellContent(width, height - 1).getCar().isEmpty()) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}
			}
		}

		Cell cell_down = getEnvi().getCellNature(getWdt(), getHgt() - 1);
		if (this.getWdt() < env.getWidth() - 1) {
			Cell cell_rightdown = getEnvi().getCellNature(getWdt() + 1, getHgt() - 1);
			Cell cell_right = getEnvi().getCellNature(getWdt() + 1, getHgt());
			if ((cell_down == Cell.MTL || cell_down == Cell.PLT || cell_down == Cell.LAD)
					|| (!(getEnvi().getCellContent(getWdt(), getHgt() - 1).getCar().isEmpty()))) {
				if (cell_rightdown == Cell.PLT) {
					if (getEnvi().getCellContent(getWdt() + 1, getHgt()).getCar().isEmpty()
							&& getEnvi().getCellContent(getWdt() + 1, getHgt()).getItem() == null
							&& (cell_right != Cell.MTL && cell_right != Cell.PLT)) {
						engine.getEnvironment().dig(getWdt() + 1, getHgt() - 1);
						engine.getHoles().add(new Hole(getWdt() + 1, getHgt() - 1, 0));
					}
				}
			}
		}
	}

	@Override
	public void doNeutral() {

		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (env.getCellContent(width, height - 1).getCar().isEmpty()) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}
			}
		}

	}

	public void clone(PlayerService ps, EngineService eng) {
		EngineImpl ena = new EngineImpl();
		ena.clone(eng);

		this.engine = ena;
		this.env = this.engine.getEnvironment();
		this.height = ps.getHgt();
		this.width = ps.getWdt();

	}

	public int getId() {
		int i = PlayerImpl.id;
		return i;
	}

}
