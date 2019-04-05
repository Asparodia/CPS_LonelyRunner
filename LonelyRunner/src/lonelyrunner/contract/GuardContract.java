package lonelyrunner.contract;

import lonelyrunner.decorators.GuardDecorator;
import lonelyrunner.service.GuardService;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
	
	}

}
