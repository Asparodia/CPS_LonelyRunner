package lonelyrunner.decorators;

import lonelyrunner.service.EngineService;

public class EngineDecorator implements EngineService{
	
	private final EngineService delegate;

	public EngineDecorator(EngineService delegate) {
		this.delegate = delegate;
	}

	public EngineService getDelegate() {
		return delegate;
	}

}
