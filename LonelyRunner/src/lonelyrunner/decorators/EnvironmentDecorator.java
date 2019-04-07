package lonelyrunner.decorators;

import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;

public class EnvironmentDecorator implements EnvironmentService{
	
	public EnvironmentService delegate;

	public EnvironmentDecorator(EnvironmentService delegate) {
		this.delegate = delegate;
	}

	public EnvironmentService getDelegate() {
		return delegate;
	}
	
	@Override
	public Couple<CharacterService, Item> getCellContent(int x, int y) {
		return delegate.getCellContent(x, y);
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
