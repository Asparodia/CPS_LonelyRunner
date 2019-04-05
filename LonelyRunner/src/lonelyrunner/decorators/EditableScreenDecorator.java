package lonelyrunner.decorators;

import lonelyrunner.service.EditableScreenService;

public class EditableScreenDecorator implements EditableScreenService{
	private final EditableScreenService delegate;
	
	public EditableScreenDecorator(EditableScreenService ess) {
		this.delegate = ess;
	}
	
	public EditableScreenService getDelegate() {
		return delegate;
	}
	

}
