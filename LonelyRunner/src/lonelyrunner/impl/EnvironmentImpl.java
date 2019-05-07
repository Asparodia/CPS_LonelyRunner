package lonelyrunner.impl;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.SetCharItem;

public class EnvironmentImpl extends ScreenImpl implements EnvironmentService {

	SetCharItem[][] env;

	@Override
	public SetCharItem getCellContent(int x, int y) {
		return env[x][y];
	}

	@Override
	public void init(EditableScreenService ess) {
		super.init(ess.getHeight(), ess.getWidth());

		this.env = new SetCharItem[ess.getWidth()][ess.getHeight()];

		for (int i = 0; i < ess.getWidth(); i++) {
			for (int j = 0; j < ess.getHeight(); j++) {
				this.env[i][j] = new SetCharItem();
			}
		}

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				super.cells[i][j] = ess.getCellNature(i, j);
			}
		}

	}

	public void clone(EnvironmentService base) {
		this.height = base.getHeight();
		this.width = base.getWidth();
		this.cells = new Cell[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.cells[i][j] = Cell.EMP;
			}
		}

		this.env = new SetCharItem[base.getWidth()][base.getHeight()];

		for (int i = 0; i < base.getWidth(); i++) {
			for (int j = 0; j < base.getHeight(); j++) {
				this.env[i][j] = new SetCharItem();
				this.env[i][j].setItem(base.getCellContent(i, j).getItem());
				for (CharacterService c : base.getCellContent(i, j).getCar()) {
					if (c.getClass() == (GuardImpl.class)) {
						this.env[i][j].addCar((GuardService) c);
					} else {
						if (c.getClass() == (PlayerImpl.class)) {
							this.env[i][j].addCar((PlayerService) c);
						} else {
							if (c.getClass() == (CharacterImpl.class)) {
								this.env[i][j].addCar((CharacterService) c);
							}
						}
					}

				}
			}
		}

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				this.cells[i][j] = base.getCellNature(i, j);
			}
		}
	}

	public void clone2(EnvironmentService base) {
		this.height = base.getHeight();
		this.width = base.getWidth();
		this.cells = new Cell[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.cells[i][j] = Cell.EMP;
			}
		}

		this.env = new SetCharItem[base.getWidth()][base.getHeight()];

		for (int i = 0; i < base.getWidth(); i++) {
			for (int j = 0; j < base.getHeight(); j++) {
				this.env[i][j] = new SetCharItem();
				this.env[i][j].setItem(base.getCellContent(i, j).getItem());
				for (CharacterService c : base.getCellContent(i, j).getCar()) {
					if (c.getClass() == (GuardImpl.class)) {
						this.env[i][j].addCar((GuardService) c);

					}
				}
			}
		}

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				this.cells[i][j] = base.getCellNature(i, j);
			}
		}
	}

	public void clone3(EnvironmentService base) {
		this.height = base.getHeight();
		this.width = base.getWidth();
		this.cells = new Cell[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.cells[i][j] = Cell.EMP;
			}
		}

		this.env = new SetCharItem[base.getWidth()][base.getHeight()];

		for (int i = 0; i < base.getWidth(); i++) {
			for (int j = 0; j < base.getHeight(); j++) {
				this.env[i][j] = new SetCharItem();
				this.env[i][j].setItem(base.getCellContent(i, j).getItem());
				for (CharacterService c : base.getCellContent(i, j).getCar()) {
					if (c.getClass() == (PlayerImpl.class)) {
						this.env[i][j].addCar((PlayerService) c);

					}
					if (c.getClass() == (GuardImpl.class)) {
						this.env[i][j].addCar((GuardService) c);

					}
				}
			}
		}

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				this.cells[i][j] = base.getCellNature(i, j);
			}
		}
	}

}
