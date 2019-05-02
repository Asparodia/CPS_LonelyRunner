package lonelyrunner.impl;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;

public class CharacterImpl implements CharacterService {

	protected EnvironmentService env;
	protected int height;
	protected int width;

	@Override
	public void init(ScreenService s, int x, int y) {
		width = x;
		height = y;
		env = (EnvironmentService) s; // hum
	}

	@Override
	public EnvironmentService getEnvi() {
		return env;
	}

	@Override
	public int getHgt() {
		return height;
	}

	@Override
	public int getWdt() {
		return width;
	}

	@Override
	public void goLeft() {

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

		if (width != 0) {
			Cell left = env.getCellNature(width - 1, height);
			if (left != Cell.PLT && left != Cell.MTL) {
				if ( (pos == Cell.LAD || pos == Cell.HDR) || (down == Cell.PLT || down == Cell.MTL || down == Cell.LAD
						|| !(env.getCellContent(width, height - 1).getCar().isEmpty()))) {
					env.getCellContent(width, height).removeCharacter(this);
					width -= 1;
					env.getCellContent(width, height).addCar(this);
				}
			}
		}
	}

	@Override
	public void goRight() {

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

		if (width != env.getWidth() - 1) {
			Cell right = env.getCellNature(width + 1, height);
			if (right != Cell.PLT && right != Cell.MTL) {
				if ((pos == Cell.LAD || pos == Cell.HDR) || (down == Cell.PLT || down == Cell.MTL || down == Cell.LAD
						|| !(env.getCellContent(width, height - 1).getCar().isEmpty()))) {
					env.getCellContent(width, height).removeCharacter(this);
					width += 1;
					env.getCellContent(width, height).addCar(this);
				}
			}
		}
	}

	@Override
	public void goUp() {

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
		if (height != env.getHeight() - 1) {
			Cell up = env.getCellNature(width, height + 1);
			if (pos == Cell.EMP) {
				if (up == Cell.LAD) {
					env.getCellContent(width, height).removeCharacter(this);
					height += 1;
					env.getCellContent(width, height).addCar(this);
				}
			}
			if (pos == Cell.LAD && (up == Cell.EMP || up == Cell.LAD)) {
				env.getCellContent(width, height).removeCharacter(this);
				height += 1;
				env.getCellContent(width, height).addCar(this);
			}
		}

	}

	@Override
	public void goDown() {
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

		if (height != 0) {
			if (pos == Cell.EMP || pos == Cell.LAD || pos == Cell.HDR) {
				if (down == Cell.EMP || down == Cell.LAD || down == Cell.EMP || down == Cell.HOL) {
					env.getCellContent(width, height).removeCharacter(this);
					height -= 1;
					env.getCellContent(width, height).addCar(this);
				}
			}
		}

	}

}
