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
		engine.getEnvironment().getCellContent(x, y).addCar(this);
		this.engine = engine;
		
	}

	@Override
	public void step() {
		Move next = engine.getNextCommand();
		switch (next) {
		case RIGHT:
			super.goRight();
			engine.setCommand(Move.NEUTRAL);
			break;
		case LEFT:
			super.goLeft();
			engine.setCommand(Move.NEUTRAL);
			break;
		case DOWN:
			super.goDown();
			engine.setCommand(Move.NEUTRAL);
			break;
		case UP:
			super.goUp();
			engine.setCommand(Move.NEUTRAL);
			break;
		case DigL:
			digL();
			engine.setCommand(Move.NEUTRAL);
			break;
		case DigR:
			digR();
			engine.setCommand(Move.NEUTRAL);
			break;
		case NEUTRAL:
			doNeutral();
			engine.setCommand(Move.NEUTRAL);
			break;
		default:
			break;
		}
	}

	@Override
	public void digL() {
		
		Cell cell_down = getEnvi().getCellNature(getWdt(), getHgt()-1);
		if( this.getWdt() != 0) {
			Cell cell_leftdown = getEnvi().getCellNature(getWdt()-1, getHgt()-1);
			if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (!(getEnvi().getCellContent(getWdt(), getHgt()-1).getCar().isEmpty()) )) {
				if(cell_leftdown == Cell.PLT) {
					if(getEnvi().getCellContent(getWdt()-1, getHgt()-1).getCar().isEmpty()){
						engine.getEnvironment().dig(getWdt()-1, getHgt()-1);
						System.out.println(super.env.getCellNature(getWdt()-1, getHgt()-1));
					}
				}
			}
		}
	}

	@Override
	public void digR() {
		Cell cell_down = getEnvi().getCellNature(getWdt(), getHgt()-1);
		
		
		if( this.getWdt() != env.getWidth() - 1 ) {
			Cell cell_rightdown = getEnvi().getCellNature(getWdt()+1, getHgt()-1);
			if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (!(getEnvi().getCellContent(getWdt(), getHgt()-1).getCar().isEmpty()))) {
				if(cell_rightdown == Cell.PLT) {
					if(getEnvi().getCellContent(getWdt()+1, getHgt()-1).getCar().isEmpty()) {
						engine.getEnvironment().dig(getWdt()+1, getHgt()-1);
					}
				}
			}
		}
	}

	@Override
	public void doNeutral() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height-1);

		//chute libre
		if( pos != Cell.LAD && pos != Cell.HDR) {
			if(down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) {
				if(env.getCellContent(width, height-1).getCar().isEmpty()) {
					env.getCellContent(width, height).removeCharacter(this);
					height-=1;
					env.getCellContent(width, height).addCar(this);
					return;
				}
			}
		}
		
	}

}
