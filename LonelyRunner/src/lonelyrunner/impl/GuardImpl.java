package lonelyrunner.impl;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Move;

public class GuardImpl extends CharacterImpl implements GuardService {

	private static int cpt = 0;
	private int id;

	private Move behaviour = Move.NEUTRAL;
	private PlayerService target;
	private int timeInHole;

	public void init(ScreenService s, int x, int y, PlayerService t) {
		width = x;
		height = y;
		id = cpt;
		cpt++;
		env = (EnvironmentService) s;
		env.getCellContent(x, y).addCar(this);
		target = t;
		timeInHole = 0;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTime(int t) {
		this.timeInHole = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Move getBehaviour() {
		return behaviour;
	}

	@Override
	public int getTimeInHole() {
		return timeInHole;
	}

	@Override
	public PlayerService getTarget() {
		return target;
	}

	@Override
	public void step() {
		Cell pos = env.getCellNature(width, height);
		if (pos == Cell.HOL) {
			if (timeInHole < 5) {
				timeInHole++;
				return;
			}
			if (timeInHole >= 5) {
				if (behaviour == Move.LEFT) {
					climbLeft();
				}
				if (behaviour == Move.RIGHT) {
					climbRight();
				}
			}
		} else {
			if (behaviour == Move.LEFT) {
				goLeft();
			}
			if (behaviour == Move.RIGHT) {
				goRight();
			}
			if (behaviour == Move.UP) {
				goUp();
			}
			if (behaviour == Move.DOWN) {
				goDown();
			}
			if (behaviour == Move.NEUTRAL) {
				doNeutral();
			}
		}

		nextBehaviour();

	}

	public void nextBehaviour() {
		Cell pos = env.getCellNature(width, height);
		int hgtTarger = target.getHgt();
		int wdtTarger = target.getWdt();
		Cell down = env.getCellNature(width, height - 1);
		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		boolean canR = true;
		boolean canL = true;
		boolean canU = true;
		boolean canD = true;

		if (width > 0) {
			for (CharacterService cs : env.getCellContent(width - 1, height).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					canL = false;
				}
			}
			Cell left = env.getCellNature(width - 1, height);
			if (left == Cell.MTL || left == Cell.PLT) {
				canL = false;
			}
			if (down != Cell.MTL || down != Cell.PLT || !containGuarddown) {
				canL = false;
			}
		}
		if (width < env.getWidth() - 1) {
			for (CharacterService cs : env.getCellContent(width + 1, height).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					canR = false;
				}
			}
			Cell right = env.getCellNature(width + 1, height);
			if (right == Cell.MTL || right == Cell.PLT) {
				canR = false;
			}
			if (down != Cell.MTL || down != Cell.PLT || !containGuarddown) {
				canR = false;
			}
		}

		if (height < env.getHeight() - 1) {
			for (CharacterService cs : env.getCellContent(width, height + 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					canU = false;
				}
			}
			Cell up = env.getCellNature(width, height + 1);
			if (up == Cell.MTL || up == Cell.PLT) {
				canU = false;
			}
			if (down != Cell.MTL || down != Cell.PLT || !containGuarddown) {
				canU = false;
			}
		}

		if (height > 0) {
			if (down == Cell.MTL || down == Cell.PLT || containGuarddown) {
				canD = false;
			}
		}

		if ((pos == Cell.HOL)) {
			if (width > wdtTarger) {
				behaviour = Move.LEFT;
			}
			if (width < wdtTarger) {
				behaviour = Move.RIGHT;
			}
			if (width == wdtTarger) {
				behaviour = Move.NEUTRAL;
			}
		}

		if (pos == Cell.LAD && !canD) {
			if (Math.abs(width - wdtTarger) <= Math.abs(height - hgtTarger)) {
				if (hgtTarger > height) {
					behaviour = Move.UP;
				}
			}
		}

		if ((pos == Cell.LAD) && (down != Cell.MTL && down != Cell.PLT && !containGuarddown)) {
			if (Math.abs(width - wdtTarger) <= Math.abs(height - hgtTarger)) {
				if ((hgtTarger < height)) {
					behaviour = Move.DOWN;
				}
			}
		}

		if (pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT) || containGuarddown)) {
			if (Math.abs(width - wdtTarger) > Math.abs(height - hgtTarger)) {
				if (wdtTarger > width) {
					if (canR) {
						behaviour = Move.RIGHT;
					} else {
						behaviour = Move.UP;
					}

				}
				if (wdtTarger < width) {
					behaviour = Move.LEFT;
				}
				if (wdtTarger == width) {
					behaviour = Move.NEUTRAL;
				}
			}
		}
		if ((pos == Cell.LAD) && (down != Cell.MTL && down != Cell.PLT && !containGuarddown)) {
			if (Math.abs(width - wdtTarger) > Math.abs(height - hgtTarger)) {
				if (wdtTarger > width) {
					behaviour = Move.RIGHT;
				}
				if (wdtTarger < width) {
					behaviour = Move.LEFT;
				}
				if (wdtTarger == width) {
					behaviour = Move.NEUTRAL;
				}
			}
		}
		if ((pos == Cell.EMP || pos == Cell.HDR) && (down != Cell.MTL && down != Cell.PLT && !containGuarddown)) {
			if (Math.abs(width - wdtTarger) <= Math.abs(height - hgtTarger)) {
				if ((hgtTarger < height)) {
					behaviour = Move.DOWN;
				}
			}
			if (Math.abs(width - wdtTarger) > Math.abs(height - hgtTarger)) {
				if (wdtTarger > width) {
					behaviour = Move.RIGHT;
				}
				if (wdtTarger < width) {
					behaviour = Move.LEFT;
				}
				if (wdtTarger == width) {
					behaviour = Move.NEUTRAL;
				}
			}
		}
		if ((pos == Cell.EMP || pos == Cell.HDR) && (down == Cell.MTL || down == Cell.PLT || containGuarddown)) {
			if (wdtTarger > width) {
				behaviour = Move.RIGHT;
			}
			if (wdtTarger < width) {
				behaviour = Move.LEFT;
			}
			if (wdtTarger == width) {
				behaviour = Move.NEUTRAL;
			}
		}
	}

	@Override
	public void climbLeft() {

		if (env.getCellNature(width, height) == Cell.HOL && width > 0 && height < env.getHeight() - 1) {
			boolean containGuardleft = false;
			if (!env.getCellContent(width - 1, height).getCar().isEmpty()) {
				for (CharacterService cs : env.getCellContent(width - 1, height).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardleft = true;
					}
				}
			}
			Cell upleft = env.getCellNature(width - 1, height + 1);
			Cell left = env.getCellNature(width - 1, height);

			if (left == Cell.MTL || left == Cell.PLT || containGuardleft) {
				if (upleft == Cell.EMP || upleft == Cell.HOL || upleft == Cell.LAD || upleft == Cell.HDR) {
					boolean containGuardupleft = false;
					if (!env.getCellContent(width - 1, height + 1).getCar().isEmpty()) {
						for (CharacterService cs : env.getCellContent(width - 1, height + 1).getCar()) {
							if (cs.getClass() == (GuardImpl.class)) {
								containGuardupleft = true;
							}
						}
					}
					if (!containGuardupleft) {
						env.getCellContent(width, height).removeCharacter(this);
						width--;
						height++;
						env.getCellContent(width, height).addCar(this);
						timeInHole = 0;
					}
				}
			}
		}

	}

	@Override
	public void climbRight() {
		if (env.getCellNature(width, height) == Cell.HOL && width < env.getWidth() - 1
				&& height < env.getHeight() - 1) {
			Cell upright = env.getCellNature(width + 1, height + 1);
			Cell right = env.getCellNature(width + 1, height);
			boolean containGuardright = false;
			if (!env.getCellContent(width + 1, height).getCar().isEmpty()) {
				for (CharacterService cs : env.getCellContent(width + 1, height).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardright = true;
					}
				}
			}
			if (right == Cell.MTL || right == Cell.PLT || containGuardright) {
				if (upright == Cell.EMP || upright == Cell.HOL || upright == Cell.LAD || upright == Cell.HDR) {
					boolean containGuardupright = false;
					if (!env.getCellContent(width + 1, height + 1).getCar().isEmpty()) {
						for (CharacterService cs : env.getCellContent(width + 1, height + 1).getCar()) {
							if (cs.getClass() == (GuardImpl.class)) {
								containGuardupright = true;
							}
						}
					}
					if (!containGuardupright) {
						env.getCellContent(width, height).removeCharacter(this);
						width++;
						height++;
						env.getCellContent(width, height).addCar(this);
						timeInHole = 0;
					}
				}
			}
		}

	}

	@Override
	public void doNeutral() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (!containGuarddown) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}
			}
		}

	}

	@Override
	public void goLeft() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (!containGuarddown) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}

			}
		}
		if (width > 0) {
			Cell left = env.getCellNature(width - 1, height);
			boolean containGuardleft = false;
			if (!env.getCellContent(width - 1, height).getCar().isEmpty()) {
				for (CharacterService cs : env.getCellContent(width - 1, height).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardleft = true;
					}
				}
			}
			if (left != Cell.PLT && left != Cell.MTL) {
				if ((pos == Cell.LAD || pos == Cell.HDR)
						|| (down == Cell.PLT || down == Cell.MTL || down == Cell.LAD || containGuarddown)) {
					if (!containGuardleft) {
						env.getCellContent(width, height).removeCharacter(this);
						width -= 1;
						env.getCellContent(width, height).addCar(this);
					}
				}
			}
		}
	}

	@Override
	public void goRight() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (!containGuarddown) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}

			}
		}
		if (width < env.getWidth() - 1) {
			Cell right = env.getCellNature(width + 1, height);
			boolean containGuardright = false;
			if (!env.getCellContent(width + 1, height).getCar().isEmpty()) {
				for (CharacterService cs : env.getCellContent(width + 1, height).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardright = true;
					}
				}
			}
			if (right != Cell.PLT && right != Cell.MTL) {
				if ((pos == Cell.LAD || pos == Cell.HDR)
						|| (down == Cell.PLT || down == Cell.MTL || down == Cell.LAD || containGuarddown)) {
					if (!containGuardright) {
						env.getCellContent(width, height).removeCharacter(this);
						width += 1;
						env.getCellContent(width, height).addCar(this);
					}
				}
			}
		}
	}

	@Override
	public void goUp() {

		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (!containGuarddown) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}

			}
		}
		if (height < env.getHeight() - 1) {
			Cell cell_up = env.getCellNature(width, height + 1);
			boolean containGuardup = false;
			if (!env.getCellContent(width, height + 1).getCar().isEmpty()) {
				for (CharacterService cs : env.getCellContent(width, height + 1).getCar()) {
					if (cs.getClass() == (GuardImpl.class)) {
						containGuardup = true;
					}
				}
			}
			if (pos == Cell.LAD && (cell_up == Cell.LAD || cell_up == Cell.EMP)) {
				if (!containGuardup) {
					env.getCellContent(width, height).removeCharacter(this);
					height += 1;
					env.getCellContent(width, height).addCar(this);
				}
			}
		}
	}

	@Override
	public void goDown() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height - 1).getCar()) {
				if (cs.getClass() == (GuardImpl.class)) {
					containGuarddown = true;
				}
			}
		}
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (!containGuarddown) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}

			}
		}
		if (height > 0) {
			if ((pos == Cell.LAD || pos == Cell.EMP || pos == Cell.HDR)
					&& (down == Cell.LAD || down == Cell.EMP || down == Cell.HOL || down == Cell.HDR)) {
				if (!containGuarddown) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
				}
			}
		}
	}

	public void clone(GuardService ps) {
		this.id = ps.getId();
		this.height = ps.getHgt();
		this.width = ps.getWdt();
		this.target = ps.getTarget();
		this.timeInHole = ps.getTimeInHole();
		EnvironmentImpl envi = new EnvironmentImpl();
		envi.clone2(ps.getEnvi());
		this.env = envi;
	}
}