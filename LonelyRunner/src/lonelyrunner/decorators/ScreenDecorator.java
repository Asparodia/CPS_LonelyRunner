package lonelyrunner.decorators;

import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.ScreenService;

public class ScreenDecorator extends Decorator implements ScreenService {

	public ScreenDecorator(ScreenService delegate) {
		super(delegate);
	}

	public ScreenService getDelegate() {
		return (ScreenService) super.getDelegate();
	}

	@Override
	public int getHeight() {
		return getDelegate().getHeight();
	}

	@Override
	public int getWidth() {
		return getDelegate().getWidth();
	}

	@Override
	public Cell getCellNature(int i, int j) {
		return getDelegate().getCellNature(i, j);
	}

	@Override
	public void init(int h, int w) {
		getDelegate().init(h, w);

	}

	@Override
	public void dig(int u, int v) {
		getDelegate().dig(u, v);
	}

	@Override
	public void fill(int x, int y) {
		getDelegate().fill(x, y);
	}

}
