package lonelyrunner.contract;

import lonelyrunner.decorators.GuardDecorator;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Move;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
	
	}
	
	public void init(ScreenService s, int x, int y, PlayerService t) {
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
	public PlayerService getTarget() {
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
		getDelegate().step();
	}
	
	@Override
	public void doNeutral() {
		getDelegate().doNeutral();
	}

}
