package lonelyrunner.impl;

import lonelyrunner.service.CharacterService;
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
		super.init(s, x, y);
		id = cpt;
		cpt++;
		target = t;
		timeInHole = 0;

		int hgtTarger = target.getHgt();
		int wdtTarger = target.getWdt();

		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		boolean set = false;

		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height).getCar()) {
				if (cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}

		// if (pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT) ||
		// containGuarddown)) {
		// if (Math.abs(width - wdtTarger) > Math.abs(height - hgtTarger)) {
		// if (hgtTarger > height) {
		// behaviour = Move.UP;
		// set = true;
		// }
		//
		// }
		// }
		// if (pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT) ||
		// containGuarddown) && !set) {
		// if (Math.abs(width - wdtTarger) < Math.abs(height - hgtTarger)) {
		// if (wdtTarger > width) {
		// behaviour = Move.RIGHT;
		// set = true;
		// }
		//
		// }
		// }
		// if (pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT) ||
		// containGuarddown) && !set) {
		// if (Math.abs(width - wdtTarger) < Math.abs(height - hgtTarger)) {
		// if (wdtTarger < width) {
		// behaviour = Move.LEFT;
		// set = true;
		// }
		//
		// }
		// }
		if (pos == Cell.LAD && (hgtTarger > height) && !set) {
			behaviour = Move.UP;
			set = true;
		}
		if (pos == Cell.LAD && (hgtTarger < height) && !set) {
			behaviour = Move.DOWN;
			set = true;
		}

		if ((pos == Cell.HOL || pos == Cell.HDR)
				|| ((down == Cell.MTL || down == Cell.PLT) || containGuarddown) && !set) {
			if (width > wdtTarger) {
				behaviour = Move.LEFT;
				set = true;
			}
		}
		if ((pos == Cell.HOL || pos == Cell.HDR)
				|| ((down == Cell.MTL || down == Cell.PLT) || containGuarddown) && !set) {
			if (width < wdtTarger) {
				behaviour = Move.RIGHT;
				set = true;

			}
		}
		// if ((pos == Cell.HOL || pos == Cell.HDR)
		// || ((down == Cell.MTL || down == Cell.PLT) || containGuarddown) && !set) {
		// if (width == wdtTarger) {
		// behaviour = Move.NEUTRAL;
		// set = true;
		//
		// }
		// }
		// if (pos == Cell.LAD && (hgtTarger == height) && !set) {
		// behaviour = Move.NEUTRAL;
		// set = true;
		// }

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
			if (timeInHole == 5) {
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

		pos = env.getCellNature(width, height);
		int hgtTarger = target.getHgt();
		int wdtTarger = target.getWdt();
		Cell down = env.getCellNature(width, height - 1);
		boolean set = false;

		boolean containGuarddown = false;
		if (!env.getCellContent(width, height - 1).getCar().isEmpty()) {
			for (CharacterService cs : env.getCellContent(width, height).getCar()) {
				if (cs.getClass().isInstance(GuardService.class)) {
					containGuarddown = true;
				}
			}
		}
		// if (pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT) ||
		// containGuarddown)) {
		// if (Math.abs(width - wdtTarger) > Math.abs(height - hgtTarger)) {
		// if (hgtTarger > height) {
		// behaviour = Move.UP;
		// set = true;
		// }
		//
		// }
		// }
		// if (pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT) ||
		// containGuarddown) && !set) {
		// if (Math.abs(width - wdtTarger) < Math.abs(height - hgtTarger)) {
		// if (wdtTarger > width) {
		// behaviour = Move.RIGHT;
		// set = true;
		// }
		//
		// }
		// }
		// if (pos == Cell.LAD && ((down == Cell.MTL || down == Cell.PLT) ||
		// containGuarddown) && !set) {
		// if (Math.abs(width - wdtTarger) < Math.abs(height - hgtTarger)) {
		// if (wdtTarger < width) {
		// behaviour = Move.LEFT;
		// set = true;
		// }
		//
		// }
		// }
		if (pos == Cell.LAD && (hgtTarger > height) && !set) {
			behaviour = Move.UP;
			set = true;
		}
		if (pos == Cell.LAD && (hgtTarger < height) && !set) {
			behaviour = Move.DOWN;
			set = true;
		}
		// if (pos == Cell.LAD && (hgtTarger == height) && !set) {
		// behaviour = Move.NEUTRAL;
		// set = true;
		// }
		if ((pos == Cell.HOL || pos == Cell.HDR)
				|| ((down == Cell.MTL || down == Cell.PLT) || containGuarddown) && !set) {
			if (width > wdtTarger) {
				behaviour = Move.LEFT;
				set = true;
			}
		}
		if ((pos == Cell.HOL || pos == Cell.HDR)
				|| ((down == Cell.MTL || down == Cell.PLT) || containGuarddown) && !set) {
			if (width < wdtTarger) {
				behaviour = Move.RIGHT;
				set = true;

			}
		}
		// if ((pos == Cell.HOL || pos == Cell.HDR)
		// || ((down == Cell.MTL || down == Cell.PLT) || containGuarddown) && !set) {
		// if (width == wdtTarger) {
		// behaviour = Move.NEUTRAL;
		// set = true;
		//
		// }
		// }

		set = false;

	}

	@Override
	public void climbLeft() {

		if (width > 0 && height < env.getHeight() - 1) {
			boolean containGuardleft = false;
			if (!env.getCellContent(width - 1, height).getCar().isEmpty()) {
				for (CharacterService cs : env.getCellContent(width - 1, height).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
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
							if (cs.getClass().isInstance(GuardService.class)) {
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
		if (width < env.getWidth() - 1 && height < env.getHeight() - 1) {
			Cell upright = env.getCellNature(width + 1, height + 1);
			Cell right = env.getCellNature(width + 1, height);
			boolean containGuardright = false;
			if (!env.getCellContent(width - 1, height).getCar().isEmpty()) {
				for (CharacterService cs : env.getCellContent(width + 1, height).getCar()) {
					if (cs.getClass().isInstance(GuardService.class)) {
						containGuardright = true;
					}
				}
			}
			if (right == Cell.MTL || right == Cell.PLT || containGuardright) {
				if (upright == Cell.EMP || upright == Cell.HOL || upright == Cell.LAD || upright == Cell.HDR) {
					boolean containGuardupright = false;
					if (!env.getCellContent(width + 1, height + 1).getCar().isEmpty()) {
						for (CharacterService cs : env.getCellContent(width + 1, height + 1).getCar()) {
							if (cs.getClass().isInstance(GuardService.class)) {
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

		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
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

	@Override
	public void goLeft() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height - 1);
		// chute libre
		if (pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
			if (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if (env.getCellContent(width, height - 1).getCar().isEmpty()) {

					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
					return;
				}

			}
		}

		if (width != 0) {
			Cell left = env.getCellNature(width - 1, height);
			if (left != Cell.PLT && left != Cell.MTL) {
				if ((pos == Cell.LAD || pos == Cell.HDR) || (down == Cell.PLT || down == Cell.MTL || down == Cell.LAD
						|| !(env.getCellContent(width, height - 1).getCar().isEmpty()))) {
					env.getCellContent(width, height).removeCharacter(this);
					width -= 1;
					env.getCellContent(width, height).addCar(this);
				}
			}
		}
	}

}
