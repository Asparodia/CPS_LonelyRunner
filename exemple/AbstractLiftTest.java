package lift.test;

import lift.contracts.ContractError;
import lift.services.CommandsService;
import lift.services.DoorStatus;
import lift.services.LiftService;
import lift.services.LiftStatus;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractLiftTest {
	private LiftService lift;
	private CommandsService commands;

	protected AbstractLiftTest() {
		lift = null;
		commands = null;
	}

	protected final LiftService getLift() {
		return lift;
	}

	protected final CommandsService getCommands() {
		return commands;
	}

	protected final void setLift(LiftService lift) {
		this.lift = lift;
	}

	protected final void setCommands(CommandsService commands) {
		this.commands = commands;
	}

	@Before
	public abstract void beforeTests(); 

	@After
	public final void afterTests() {
		lift = null;
		commands = null;
	}

	public void initialisation(){
		commands.init();
		lift.init(0,5);
	}
	
	public void testinvariant(){
		assertTrue(lift.getMaxLevel()>=lift.getLevel());
		assertTrue(lift.getMinLevel()<=lift.getLevel());
		if ( (LiftStatus.MOVING_DOWN==lift.getLiftStatus()) || (LiftStatus.MOVING_UP==lift.getLiftStatus())){
			assertEquals(DoorStatus.CLOSED,lift.getDoorStatus());
		}
		if (LiftStatus.IDLE==lift.getLiftStatus()){
			if (!(lift.getDoorStatus()==DoorStatus.OPENED ||lift.getDoorStatus()==DoorStatus.CLOSING )){
			assertEquals(DoorStatus.OPENED,lift.getDoorStatus());
			assertEquals(DoorStatus.CLOSING,lift.getDoorStatus());
			}
		}
		
		
	}

	
	@Test
	public void preInitPositif(){
		commands.init();
		lift.init(2,5);
		testinvariant();
		assertEquals(5,(int)lift.getMaxLevel());
		assertEquals(2,(int)lift.getMinLevel());
		assertEquals(2,(int)lift.getLevel());
		assertEquals( LiftStatus.IDLE,lift.getLiftStatus());
		assertEquals( DoorStatus.OPENED,lift.getDoorStatus());
		assertEquals(0,commands.getNbUpCommands());
		assertEquals(0,commands.getNbDownCommands());
	}

	@Test
	public void preInitNegatif1(){
		commands.init();
		try {
			lift.init(-2,5);
			fail();
		}
		catch (ContractError e) {	
		}

	}

	@Test
	public void preInitNegatif2(){
		commands.init();

		try {
			lift.init(2,2);
			fail();
		}
		catch (ContractError e) {	
		}
	}

	
	@Test
	public void preCloseDoorPositif(){
		initialisation();
		lift.closeDoor();
		testinvariant();
		assertEquals(DoorStatus.CLOSING,lift.getDoorStatus());
		 
	}

	@Test
	public void preCloseDoorNegatif(){
		initialisation();
		try {
			lift.openDoor();
			fail();
		}
		catch (ContractError e) {	
		}

	}


	@Test 
	public void preDoorAckPositif() {
		initialisation();
		lift.selectLevel(2);
		lift.closeDoor();
		testinvariant();
		lift.doorAck();
		assertEquals(DoorStatus.CLOSED,lift.getDoorStatus());
		
		
	}
	
	@Test
	public void preDoorAckNegatif1() {
		initialisation();
		try {
			lift.doorAck();
			fail();
		}
		catch (ContractError e) {	
		}


	}

	@Test
	public void preDoorAckNegatif2() {
		initialisation();
		lift.selectLevel(2);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveUp();
		try {
			lift.doorAck();
			fail();
		}
		catch (ContractError e) {	
		}
	}

	
	@Test
	public void preOpenDoorPositif(){
		initialisation();
		lift.selectLevel(2);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		lift.openDoor();
		testinvariant();
		assertEquals(DoorStatus.OPENING,lift.getDoorStatus());
		
	}

	@Test
	public void preOpenDoorNegatif1(){
		initialisation();
		try {
			lift.openDoor();
			fail();
		}
		catch (ContractError e) {	
		}

	}

	@Test
	public void preOpenDoorNegatif2(){
		initialisation();
		lift.selectLevel(2);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		try {
			lift.openDoor();
			fail();
		}
		catch (ContractError e) {	
		}

	}


	@Test
	public void preBeginMoveUpPositif(){
		initialisation();
		lift.selectLevel(2);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveUp();
		testinvariant();
		assertEquals(LiftStatus.MOVING_UP,lift.getLiftStatus());
		
	}

	@Test
	public void preBeginMoveUpNegatif1(){
		initialisation();
		try {
			lift.beginMoveUp();
			fail();
		}
		catch (ContractError e) {	
		}
	}

	@Test
	public void preBeginMoveUpNegatif2(){
		initialisation();
		lift.selectLevel(2);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		lift.openDoor();
		lift.doorAck();
		lift.selectLevel(0);
		lift.closeDoor();
		lift.doorAck();
		try {
			lift.beginMoveUp();
			fail();
		}
		catch (ContractError e) {	
		}

	}
	
	
	@Test
	public void preEndMoveUpPositif() {
		initialisation();
		lift.selectLevel(3);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.stepMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		testinvariant();
		assertEquals(LiftStatus.STOP_UP,lift.getLiftStatus());
		assertEquals(0,commands.getNbUpCommands());
	}
	
	@Test
	public void preEndMoveUpNegatif(){
		initialisation();
		try {
			lift.endMoveUp();
			fail();
		}
		catch (ContractError e) {	
		}

	}
	
	
	@Test
	public void preBeginMoveDownPositif() {
		preEndMoveUpPositif();
		lift.openDoor();
		lift.doorAck();

		lift.selectLevel(0);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveDown();
		testinvariant();
		assertEquals(LiftStatus.MOVING_DOWN,lift.getLiftStatus());
		
	}
	
	@Test
	public void preBeginMoveDownNegatif1(){
		initialisation();
		try {
			lift.beginMoveDown();
			fail();
		}
		catch (ContractError e) {	
		}

	}

	@Test
	public void preBeginMoveDownNegatif2() {
		initialisation();

		lift.selectLevel(3);
		lift.closeDoor();
		lift.doorAck();
		try {
		lift.beginMoveDown();fail();
		}catch(ContractError e) {}
	}

	
	@Test
	public void preEndMoveDownPositif(){
		preBeginMoveDownPositif();//Ascenseur au 3 voulant aller au 0
		lift.stepMoveDown();
		lift.stepMoveDown();
		lift.stepMoveDown();
		lift.endMoveDown();

		testinvariant();
		assertEquals(LiftStatus.STOP_DOWN,lift.getLiftStatus());
		assertEquals(0,commands.getNbDownCommands());
		
	}

	@Test(expected=ContractError.class)
	public void preEndMoveDownNegatif(){
		initialisation();
		lift.endMoveDown();
	}

	
	@Test
	public void preStepMoveUpPositif() {
		preBeginMoveUpPositif();
		lift.stepMoveUp();

		testinvariant();
		assertEquals(LiftStatus.MOVING_UP,lift.getLiftStatus());
		
	}
	
	@Test
	public void preStepMoveUpNegatif1() {
		initialisation();
		try{
			lift.stepMoveUp();
		}catch(ContractError e) {}
		
	}
	
	@Test
	public void preStepMoveUpNegatif2() {
		preBeginMoveUpPositif();
		lift.stepMoveUp();
		lift.stepMoveUp();
		try{
			lift.stepMoveUp();
		}catch(ContractError e) {}
	}
	
	@Test
	public void preStepMoveUpNegatif3() {
		preBeginMoveDownPositif();
		try{
			lift.stepMoveUp();
		}catch(ContractError e) {}
	}

	
	@Test
	public void preStepMoveDownPositif() {
		preBeginMoveDownPositif();
		lift.stepMoveDown();
	}
	
	@Test
	public void preStepMoveDownNegatif1() {
		initialisation();
		try{
			lift.stepMoveDown();
			fail();
		}catch(ContractError e) {}
		
	}
	
	@Test
	public void preStepMoveDownNegatif2() {
		preBeginMoveDownPositif();
		lift.stepMoveDown();
		lift.stepMoveDown();
		lift.stepMoveDown();
		try{
			lift.stepMoveDown();
			fail();
		}catch(ContractError e) {}
	}
	
	@Test
	public void preStepMoveDownNegatif3() {
		preBeginMoveUpPositif();
		try{
			lift.stepMoveDown();
			fail();
		}catch(ContractError e) {}
	}
	
	
	@Test
	public void preSelectLevelPositif1() {
		initialisation();
		lift.selectLevel(2);

		testinvariant();
		assertEquals(1,commands.getNbUpCommands());
		assertEquals(0,commands.getNbDownCommands());
	}
	
	@Test
	public void preSelectLevelPositif2() {
		initialisation();
		lift.selectLevel(0);

		testinvariant();
		assertEquals(0,commands.getNbUpCommands());
		assertEquals(0,commands.getNbDownCommands());
	}
	
	@Test
	public void preSelectLevelPositif3() {
		initialisation();
		lift.selectLevel(2);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		lift.openDoor();
		lift.doorAck();
		lift.selectLevel(1);
		testinvariant();
		assertEquals(0,commands.getNbUpCommands());
		assertEquals(1,commands.getNbDownCommands());
		lift.selectLevel(0);
		testinvariant();
		assertEquals(0,commands.getNbUpCommands());
		assertEquals(2,commands.getNbDownCommands());
		lift.selectLevel(4);
		testinvariant();
		assertEquals(1,commands.getNbUpCommands());
		assertEquals(2,commands.getNbDownCommands());
	}
	
	@Test
	public void preSelectLevelNegatif1() {
		initialisation();
		try {
			lift.selectLevel(-5);
			fail();
		}catch(ContractError e) {}
	}
	
	@Test
	public void preSelectLevelNegatif2() {
		initialisation();
		try {
			lift.selectLevel(18);
			fail();
		}catch(ContractError e) {}
	}
	
	@Test
	public void preSelectLevelNegatif3() {
		preEndMoveUpPositif();
		try {
			lift.selectLevel(3);
			fail();
		}catch(ContractError e) {}
		
	}

	
	
}
