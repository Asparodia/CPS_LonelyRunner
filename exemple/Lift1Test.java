package lift.test;

import org.junit.*;

import lift.contracts.*;
import lift.services.LiftService;
import liftimpl1.Lift1;

public class Lift1Test extends AbstractLiftTest {

	
	public Lift1Test(){
		super();
	}


	@Override
	public void beforeTests() {
		LiftService ls=new  LiftContract(new Lift1());
		setLift(ls);
		setCommands(ls.getCommands());
	}
	
	
}
