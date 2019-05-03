package lonelyrunner.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lonelyrunner.contract.CharacterContract;
import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.EnvironmentContract;
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
	public void test() {
		initialisation();	
	}
}
