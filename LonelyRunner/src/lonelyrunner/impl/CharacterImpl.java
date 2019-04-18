package lonelyrunner.impl;

import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;

public class CharacterImpl implements CharacterService{
	
	protected EnvironmentService env;
	private int height;
	private int width;
	
	
	@Override
	public void init(ScreenService s, int x, int y) {
		width = x;
		height = y;
		env = (EnvironmentService) s;
		
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
		if(width == 0 ) {
			return;
		}
		Cell left = env.getCellNature(width-1, height);
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height-1);
		if( left != Cell.PLT &&  left != Cell.MTL ) {
			if( (pos == Cell.LAD && pos == Cell.HDR) || (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) || (env.getCellContent(width, height-1).getCar() != null) ){
				if( (env.getCellContent(width-1, height).getCar() == null) ) {
					width-=1;
					env.getCellContent(width-1, height).getCar()
				}
			}
		}
		
	}

	@Override
	public void goRight() {
		
	}

	@Override
	public void goUp() {
		
	}

	@Override
	public void goDown() {
		
	}

}
