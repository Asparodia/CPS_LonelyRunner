package lonelyrunner.contract;

import lonelyrunner.decorators.GuardDecorator;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Move;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
	
	}
	
	public void init(ScreenService s, int x, int y, CharacterService t) {
		getDelegate().init(s, x, y,t);
		
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
	public void climbRight() {
		getDelegate().climbRight();
		
	}

	@Override
	public void step() {
		getDelegate().climbRight();
		
	}

}
