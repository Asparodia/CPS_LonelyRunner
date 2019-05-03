package lonelyrunner.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lonelyrunner.contract.CharacterContract;
import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.EnvironmentContract;
import lonelyrunner.contract.contracterr.ContractError;
import lonelyrunner.impl.CharacterImpl;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.utils.Cell;

public class CharacterTest {

	private CharacterService character;
	private EnvironmentService env;
	private EditableScreenService editscreen;
	
	@Before
	public void beforeTests() {
		env = new EnvironmentContract(new EnvironmentImpl());
		editscreen =  new EditableScreenContract(new EditableScreenImpl());
		character = new CharacterContract(new CharacterImpl());
		
	}
	
	@After
	public void afterTests() {
		env = null;
		editscreen = null;
		character = null;
	}
	
	public void testInvariant() {
		
	}
	
	public void initialisation() {
		System.out.println("charac est sur des plt");
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env,3,2);
	}
	
	@Test
	public void testInitialisationPositif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		assertTrue(env.getCellNature(3, 2)==Cell.EMP);
		character.init(env,3,2);
		testInvariant();
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env,3,1);
	}
	
	@Test
	public void testInitialisationNegatif2() {
		Cell mtl,hdr,emp,lad,hol,plt;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		hol = Cell.HOL;
		plt = Cell.PLT;
		
		editscreen.init(10, 15);
		editscreen.setNature(1, 1,mtl);
		editscreen.setNature(2, 1,hdr);
		editscreen.setNature(3, 1,emp);
		editscreen.setNature(4, 1,lad);
		editscreen.setNature(5, 1,hol);
		editscreen.setNature(6, 1,plt);
		env.init(editscreen);
		testInvariant();
		try {
			character.init(env,1,1);
		}
		catch(ContractError e) {
		}
		try {
			character.init(env,2,1);
		}
		catch(ContractError e) {
		}
		try {
			character.init(env,3,1);
		}
		catch(ContractError e) {
		}
		try {
			character.init(env,4,1);
		}
		catch(ContractError e) {
		}
		try {
			character.init(env,5,1);
		}
		catch(ContractError e) {
		}
		try {
			character.init(env,6,1);
		}
		catch(ContractError e) {
		}		
	}
	
	@Test
	public void testGoLeftEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env,0,2);
		int xbefore = character.getWdt();
		character.goLeft();
		assertTrue(xbefore == character.getWdt());
		
	}
	
	@Test
	public void testGoLeftObstacle() {
		
	}
	
	@Test
	public void testGoLeftFalling() {
		
	}
	
}
