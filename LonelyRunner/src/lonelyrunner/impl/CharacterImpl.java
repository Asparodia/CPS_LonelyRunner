package lonelyrunner.impl;

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
		Cell left = env.getCellNature(width-1, height);
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height-1);
		if( width != 0 && left != Cell.PLT &&  left != Cell.MTL ) {
			if( (pos == Cell.LAD && pos == Cell.HDR) || (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) || (env.getCellContent(width, height-1).getCar() != null) ){
				if( (env.getCellContent(width-1, height).getCar() == null) ) {
					env.getCellContent(width, height).removeCharacter();
					width-=1;
					env.getCellContent(width, height).setCar(this);
				}
			}
		}
		
	}

	@Override
	public void goRight() {
		Cell right = env.getCellNature(width+1, height);
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height-1);
		if( width != 0 && right != Cell.PLT &&  right != Cell.MTL ) {
			if( (pos == Cell.LAD && pos == Cell.HDR) || (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) || (env.getCellContent(width, height-1).getCar() != null) ){
				if( (env.getCellContent(width+1, height).getCar() == null) ) {
					env.getCellContent(width, height).removeCharacter();
					width+=1;
					env.getCellContent(width, height).setCar(this);
				}
			}
		}
	}

	@Override
	public void goUp() {
		Cell up = env.getCellNature(width, height+1);
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height-1);
		
		if( width != env.getWidth()-1) {
			if(pos == Cell.EMP) {
				if(up == Cell.LAD) {
					if((down == Cell.MTL || down == Cell.PLT) || (env.getCellContent(width, height-1).getCar() != null)) {
						env.getCellContent(width, height).removeCharacter();
						height+=1;
						env.getCellContent(width, height).setCar(this);
					}
						
				}
			}
			else {
				if(pos == Cell.LAD) {
					if(up == Cell.LAD || up == Cell.EMP || (env.getCellContent(width, height-1).getCar() != null)) {
						env.getCellContent(width, height).removeCharacter();
						height+=1;
						env.getCellContent(width, height).setCar(this);
					}
				}
			}
			if( (pos == Cell.LAD && pos == Cell.HDR) || (down != Cell.PLT && down != Cell.MTL && down != Cell.LAD) || (env.getCellContent(width, height-1).getCar() != null) ){
				if( (env.getCellContent(width-1, height).getCar() == null) ) {
					env.getCellContent(width, height).removeCharacter();
					height-=1;
					env.getCellContent(width, height).setCar(this);;
				}
			}
		}
		
	}

	@Override
	public void goDown() {
		Cell up = env.getCellNature(width, height+1);
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height-1);
		
		
	}

}
