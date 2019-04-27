package lonelyrunner.impl;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Move;

public class GuardImpl extends CharacterImpl implements GuardService{
	
	private static int cpt = 0;
	private int id;
	private Move behaviour;
	private CharacterService target;
	private int timeInHole;
	
	public void init(ScreenService s, int x, int y, CharacterService t) {
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
	public CharacterService getTarget() {
		return target;
	}

	@Override
	public int getTimeInHole() {
		return timeInHole;
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void climbRight() {
		// TODO Auto-generated method stub
		
	}

}
