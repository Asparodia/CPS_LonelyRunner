package lonelyrunner.contract;

import lonelyrunner.decorators.EditableScreenDecorator;
import lonelyrunner.service.EditableScreenService;

public class EditableScreenContract extends EditableScreenDecorator {

	
	public EditableScreenContract(EditableScreenService ess) {
			super(ess);
		}
	
	public void checkInvariant() {
			
		}
}
