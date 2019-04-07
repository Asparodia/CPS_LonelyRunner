package lonelyrunner.decorators;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.utils.Cell;

public class EditableScreenDecorator implements EditableScreenService{
	private final EditableScreenService delegate;
	
	public EditableScreenDecorator(EditableScreenService ess) {
		this.delegate = ess;
	}
	
	public EditableScreenService getDelegate() {
		return delegate;
	}
	
	@Override
	public boolean isPlayable() {
		return delegate.isPlayable();
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		delegate.setNature(x, y, c);
		
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
