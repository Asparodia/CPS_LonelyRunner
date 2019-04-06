package bridge.contracts;

import bridge.decorators.LimitedRoadDecorator;
import bridge.services.LimitedRoadService;

public class LimitedRoadContract extends LimitedRoadDecorator {

	public LimitedRoadContract(LimitedRoadService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		// remarque : include et non refine donc on n'hérite
		// pas des invariants de RoadSectionService, il faut refaire des tests.
				
		if(!(getNbCars() >= 0)) {
			Contractor.defaultContractor().invariantError("LimitedRoadService","The number of cars should be positive");
		}
		if(isFull()) {
			if(!(getNbCars()==getLimit())) {
				Contractor.defaultContractor().invariantError("LimitedRoadService","isFull but the number of cars is not equals to the limit");
			}
		}
		if(!(getNbCars()<=getLimit())) {
			Contractor.defaultContractor().invariantError("LimitedRoadService","There are more cars than the limit of cars");
		}
 	}
	
	@Override
	public void enter() {
		// captures
		int getNbCars_atPre = getNbCars();
		// pre
		if(!(!isFull())) {
			Contractor.defaultContractor().preconditionError("LimitedRoadService", "enter", "its Full !");
			
		}
		// inv pre
		checkInvariant();
		// run
		super.enter();
		// int post
		checkInvariant();
		// post: getNbCars() == getNbCars()@pre + 1 
		if(!(getNbCars() == getNbCars_atPre + 1)) {
			Contractor.defaultContractor().postconditionError("LimitedRoadService", "enter", "The cars count did not increase");
		}
	}
	
	public void init(int lim) {
		// pre
		if(!(lim>0)) {
			Contractor.defaultContractor().preconditionError("LimitedRoadService", "init", "The limit is not positive");
		}
		getDelegate().init();
		checkInvariant();
		
		if(!(getNbCars() == 0)) {
			Contractor.defaultContractor().postconditionError("LimitedRoadService", "init", "The number of cars is not equals to 0 after init");
		}
		if(!(getLimit()==lim)) {
			Contractor.defaultContractor().postconditionError("LimitedRoadService", "init", "limit is not equals to the limit passed as parameter after init");
		}
	}
	
	@Override
	public void leave() {
		// pre: getNbCars() > 0
		if(!(getNbCars() > 0)) {
			Contractor.defaultContractor().preconditionError("RoadSectionService", "leave", "The number of cars is not strictly positive");
		}
		// captures
		int getNbCars_atPre = getNbCars();
		// inv pre
		checkInvariant();
		// run
		super.leave();
		// int post
		checkInvariant();
		// post: getNbCars() == getNbCars()@pre - 1 
		if(!(getNbCars() == getNbCars_atPre - 1)) {
			Contractor.defaultContractor().postconditionError("RoadSectionService", "leave", "The cars count did not decrease");
		}
	}
	
	
}
