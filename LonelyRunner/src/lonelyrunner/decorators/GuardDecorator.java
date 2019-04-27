package lonelyrunner.decorators;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Move;

public class GuardDecorator extends CharacterDecorator implements GuardService{

	

	public GuardDecorator(GuardService delegate) {
		super(delegate);
	}
	
	public GuardService getDelegate() {
		return (GuardDecorator) super.getDelegate();
	}

	@Override
	public int getId() {
		return getDelegate().getId();
	}

	@Override
	public Move getBehaviour() {
		return getDelegate().getBehaviour();
	}

	@Override
	public CharacterService getTarget() {
		return getDelegate().getTarget();
	}

	@Override
	public int getTimeInHole() {
		return getDelegate().getTimeInHole();
	}

	@Override
	public void climbLeft() {
		getDelegate().climbLeft();
		
	}

	@Override
	public void step() {
		getDelegate().step();
		
	}

	@Override
	public void climbRight() {
		getDelegate().climbRight();
		
	}
	
	public void init(ScreenService s, int x, int y, CharacterService t) {
		getDelegate().init(s, x, y,t);
		
	}

}
