package lonelyrunner.decorators;

import lonelyrunner.service.EnvironmentService;

public class EnvironmentDecorator implements EnvironmentService{
	
	public EnvironmentService delegate;

	public EnvironmentDecorator(EnvironmentService delegate) {
		this.delegate = delegate;
	}

	public EnvironmentService getDelegate() {
		return delegate;
	}

}
