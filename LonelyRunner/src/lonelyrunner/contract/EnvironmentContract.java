package lonelyrunner.contract;

import lonelyrunner.decorators.EnvironmentDecorator;
import lonelyrunner.service.EnvironmentService;

public class EnvironmentContract extends EnvironmentDecorator {

	public EnvironmentContract(EnvironmentService delegate) {
		super(delegate);
	}
	public void checkInvariant() {
		
	}

}
