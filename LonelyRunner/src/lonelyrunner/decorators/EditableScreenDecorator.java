package lonelyrunner.decorators;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.utils.Cell;

public class EditableScreenDecorator extends ScreenDecorator implements EditableScreenService {

	public EditableScreenDecorator(EditableScreenService ess) {
		super(ess);
	}

	public EditableScreenService getDelegate() {
		return (EditableScreenService) super.getDelegate();
	}

	@Override
	public boolean isPlayable() {
		return getDelegate().isPlayable();
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		getDelegate().setNature(x, y, c);

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
