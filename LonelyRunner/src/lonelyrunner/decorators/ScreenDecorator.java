package lonelyrunner.decorators;

import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.ScreenService;

public class ScreenDecorator implements ScreenService{
	private final ScreenService delegate;

	public ScreenDecorator(ScreenService delegate) {
		this.delegate = delegate;
	}

	public ScreenService getDelegate() {
		return delegate;
	}

	@Override
	public int getHeight() {
		return delegate.getHeight();
	}

	@Override
	public int getWidth() {
		return delegate.getWidth();
	}

	@Override
	public Cell getCellNature(int i, int j) {
		return delegate.getCellNature(i, j);
	}

	@Override
	public void init(int h, int w) {
		delegate.init(h, w);
		
	}

	@Override
	public void dig(int u, int v) {
		delegate.dig(u, v);
	}

	@Override
	public void fill(int x, int y) {
		delegate.fill(x, y);
	}
	
	
}
