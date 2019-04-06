package bridge.contracts;

import bridge.services.BridgeService;

public class BridgeContract extends LimitedRoadContract implements BridgeService {
	
	private String serviceName="BridgeService";
	public BridgeContract(BridgeService delegate) {
		super(delegate);
	}

	@Override
	protected BridgeService getDelegate() {
		return (BridgeService) super.getDelegate();
	}
	
	@Override
	public int getNbIn() {
		return getDelegate().getNbIn();
	}

	@Override
	public int getNbOut() {
		return getDelegate().getNbOut();
	}
	
	
	public void checkInvariant() {
		// raffinement donc
		//meme invariant
		if (getNbCars() != (getNbIn() + getNbOut())){
			Contractor.defaultContractor().invariantError(serviceName, "The number of Cars in the bridge is not equals to the add of the number of car in the both ways");
		}
		if (getNbIn()<0){
			Contractor.defaultContractor().invariantError(serviceName, "The number of Cars in the In way should be positive");
		}	
		if (getNbOut()<0){
			Contractor.defaultContractor().invariantError(serviceName, "The number of Cars out the Out way should be positive");
		}
		super.checkInvariant();
		
	}
	

	@Override
	public void init() {
		
		super.init();
	}

	@Override
	public void init(int lim) {
		super.init(lim);
	}

	
	@Override
	public void enter() {
		int NbIn_at_pre=getNbIn();
		int NbOut_at_pre=getNbOut();
		
		checkInvariant();
		
		super.enter();
		
		if (NbIn_at_pre<NbOut_at_pre){
			if (getNbIn()!=(NbIn_at_pre+1)){
				Contractor.defaultContractor().postconditionError(serviceName, "enter", "The number in the in way shoud be incremented");
			}
			if (getNbOut()!=(NbOut_at_pre)){
				Contractor.defaultContractor().postconditionError(serviceName, "enter", "The number out the out way shoud be modified");
			}
		}
		else{
			if (getNbIn()!=(NbIn_at_pre)){
				Contractor.defaultContractor().postconditionError(serviceName, "enter", "The number in the in way shoudn't be modified");
			}
			if (getNbOut()!=(NbOut_at_pre+1)){
				Contractor.defaultContractor().postconditionError(serviceName, "enter", "The number out the out way shoud be incremented");
			}
		}
		checkInvariant();
	}
	@Override
	public void enterIn() {
		//Capture
		int NbIn_at_pre=getNbIn();
		int NbOut_at_pre=getNbOut();
		
		if (isFull()){
			Contractor.defaultContractor().invariantError(serviceName, "Cannot in the road is full");
		}
		checkInvariant();
		getDelegate().enterIn();
		checkInvariant();
		
		if (getNbIn()!=(NbIn_at_pre+1)){
			Contractor.defaultContractor().postconditionError(serviceName, "enterIn", "The number in the in way shoud be incremented");
		}
		if (getNbOut()!=(NbOut_at_pre)){
			Contractor.defaultContractor().postconditionError(serviceName, "enterIn", "The number out the in way shoudn't be modified");
		}
	}

	@Override
	public void leaveIn() {
		//Capture
		int NbIn_at_pre=getNbIn();
		int NbOut_at_pre=getNbOut();
		
		if (getNbIn()==0){
			Contractor.defaultContractor().invariantError(serviceName, "Cannot leave in the road is empty");
		}
		checkInvariant();
		getDelegate().leaveIn();
		checkInvariant();
		
		if (getNbIn()!=(NbIn_at_pre-1)){
			Contractor.defaultContractor().postconditionError(serviceName, "leaveIn", "The number in the in way shoud be deincremented");
		}
		if (getNbOut()!=(NbOut_at_pre)){
			Contractor.defaultContractor().postconditionError(serviceName, "leaveIn", "The number out the in way shoudn't be modified");
		}
		
	}

	@Override
	public void enterOut() {
		int NbIn_at_pre=getNbIn();
		int NbOut_at_pre=getNbOut();

		if (isFull()){
			Contractor.defaultContractor().invariantError(serviceName, "Cannot in the road is full");
		}
		checkInvariant();
		getDelegate().enterOut();
		checkInvariant();

		if (getNbIn()!=(NbIn_at_pre)){
			Contractor.defaultContractor().postconditionError(serviceName, "enterOut", "The number in the in way shoudn't be modified");
		}
		if (getNbOut()!=(NbOut_at_pre+1)){
			Contractor.defaultContractor().postconditionError(serviceName, "enterOut", "The number out the out way shoud be incremented");
		}
	}

	@Override
	public void leaveOut() {
		int NbIn_at_pre=getNbIn();
		int NbOut_at_pre=getNbOut();
		
		if (getNbOut()==0){
			Contractor.defaultContractor().invariantError(serviceName, "Cannot leave in the road is empty");
		}
		checkInvariant();
		getDelegate().leaveOut();
		checkInvariant();
		
		if (getNbIn()!=(NbIn_at_pre)){
			Contractor.defaultContractor().postconditionError(serviceName, "leaveOut", "The number in the in way shoud be modified");
		}
		if (getNbOut()!=(NbOut_at_pre-1)){
			Contractor.defaultContractor().postconditionError(serviceName, "leaveOut", "The number out the in way shoudn't be deincremened");
		}
	}
	
	public void leave() {
		int NbIn_at_pre=getNbIn();
		int NbOut_at_pre=getNbOut();
		checkInvariant();
		super.leave();
		checkInvariant();
		if (NbIn_at_pre<NbOut_at_pre){
			if (getNbIn()!=(NbIn_at_pre-1)){
				Contractor.defaultContractor().postconditionError(serviceName, "leave", "The number in the in way shoud be incremented");
			}
			if (getNbOut()!=(NbOut_at_pre)){
				Contractor.defaultContractor().postconditionError(serviceName, "leave", "The number out the out way shoud be modified");
			}
		}
		else{
			if (getNbIn()!=(NbIn_at_pre)){
				Contractor.defaultContractor().postconditionError(serviceName, "leave", "The number in the in way shoudn't be modified");
			}
			if (getNbOut()!=(NbOut_at_pre-1)){
				Contractor.defaultContractor().postconditionError(serviceName, "leave", "The number out the out way shoud be incremented");
			}
		}
		
	}
	
}