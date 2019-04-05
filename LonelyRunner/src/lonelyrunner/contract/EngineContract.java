package lonelyrunner.contract;

import lonelyrunner.decorators.EngineDecorator;
import lonelyrunner.service.EngineService;

public class EngineContract extends EngineDecorator{

	public EngineContract(EngineService delegate) {
		super(delegate);
	}

}
