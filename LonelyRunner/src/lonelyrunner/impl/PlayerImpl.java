package lonelyrunner.impl;

import lonelyrunner.service.EngineService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Move;

public class PlayerImpl extends CharacterImpl implements PlayerService {

	private EngineService engine;
	
	@Override
	public EngineService getEngine() {
		return engine;
	}

	@Override
	public void init(ScreenService s, int x, int y, EngineService engine) {
		super.init(s, x, y);
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
			digL();
			break;
		case DigR:
			digR();
			break;
		default:
			break;
		}
	}

	@Override
	public void digL() {
		
		Cell cell_down = getEnvi().getCellNature(getWdt(), getHgt()-1);
		Cell cell_leftdown = getEnvi().getCellNature(getWdt()-1, getHgt()-1);
		
		if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (getEnvi().getCellContent(getWdt(), getHgt()-1).getCar() != null)) {
			if(cell_leftdown == Cell.PLT) {
				if(getEnvi().getCellContent(getWdt()-1, getHgt()-1).getCar() == null) {
					engine.getEnvironment().dig(getWdt()-1, getHgt()-1);
				}
			}
		}
	}

	@Override
	public void digR() {
		Cell cell_down = getEnvi().getCellNature(getWdt(), getHgt()-1);
		Cell cell_rightdown = getEnvi().getCellNature(getWdt()+1, getHgt()-1);
		
		if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (getEnvi().getCellContent(getWdt(), getHgt()-1).getCar() != null)) {
			if(cell_rightdown == Cell.PLT) {
				if(getEnvi().getCellContent(getWdt()+1, getHgt()-1).getCar() == null) {
					engine.getEnvironment().dig(getWdt()+1, getHgt()-1);
				}
			}
		}
	}

}
