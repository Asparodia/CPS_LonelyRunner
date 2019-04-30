package lonelyrunner.impl;

import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Move;

public class GuardImpl extends CharacterImpl implements GuardService{
	
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
	public void climbLeft() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void climbRight() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doNeutral() {
		Cell pos = env.getCellNature(width, height);
		Cell down = env.getCellNature(width, height-1);

		//chute libre
		if( pos != Cell.LAD && pos != Cell.HDR && pos != Cell.HOL) {
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

	@Override
	public void step() {
		doNeutral();
		
	}

	

}
