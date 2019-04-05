package lonelyrunner.decorators;

import lonelyrunner.service.ScreenService;

public class ScreenDecorator implements ScreenService{
	private final ScreenService delegate;

	public ScreenDecorator(ScreenService delegate) {
		this.delegate = delegate;
	}

	public ScreenService getDelegate() {
		return delegate;
	}
	
	
}
