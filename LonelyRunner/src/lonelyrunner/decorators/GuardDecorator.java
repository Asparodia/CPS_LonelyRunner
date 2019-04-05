package lonelyrunner.decorators;

import lonelyrunner.service.GuardService;

public class GuardDecorator implements GuardService{
	private final GuardService delegate;

	

	public GuardDecorator(GuardService delegate) {
		this.delegate = delegate;
	}
	
	public GuardService getDelegate() {
		return delegate;
	}

}
