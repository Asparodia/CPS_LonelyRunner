package lonelyrunner.decorators;

public abstract class Decorator {
	private final Object delegate;

	public Decorator(Object delegate) {
		this.delegate = delegate;
	}

	public Object getDelegate() {
		return delegate;
	}
}
