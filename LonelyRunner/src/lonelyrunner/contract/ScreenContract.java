package lonelyrunner.contract;

import lonelyrunner.decorators.ScreenDecorator;
import lonelyrunner.service.ScreenService;

public class ScreenContract extends ScreenDecorator {

	public ScreenContract(ScreenService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
		
	}

}
