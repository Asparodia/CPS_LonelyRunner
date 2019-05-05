package lonelyrunner.decorators;

import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Move;

public class GuardDecorator extends CharacterDecorator implements GuardService {

	public GuardDecorator(GuardService delegate) {
		super(delegate);
	}

	public GuardService getDelegate() {
		return (GuardService) super.getDelegate();
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
	public int getTimeInHole() {
		return getDelegate().getTimeInHole();
	}

	@Override
	public PlayerService getTarget() {
		return getDelegate().getTarget();
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

	public void init(ScreenService s, int x, int y, PlayerService t) {
		getDelegate().init(s, x, y, t);

	}

	@Override
	public void doNeutral() {
		getDelegate().doNeutral();

	}

	@Override
	public void goLeft() {
		getDelegate().goLeft();

	}

	@Override
	public void goRight() {
		getDelegate().goRight();

	}

	@Override
	public void goUp() {
		getDelegate().goUp();

	}

	@Override
	public void goDown() {
		getDelegate().goDown();

	}

}
