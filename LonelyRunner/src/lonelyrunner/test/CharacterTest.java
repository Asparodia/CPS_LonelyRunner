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
		editscreen = new EditableScreenContract(new EditableScreenImpl());
		character = new CharacterContract(new CharacterImpl());

	}

	@After
	public void afterTests() {
		env = null;
		editscreen = null;
		character = null;
	}

	public void testInvariant() {
		Cell c = env.getCellNature(character.getWdt(), character.getHgt());
		assertTrue(c == Cell.EMP || c== Cell.HOL || c== Cell.LAD || c== Cell.HDR) ;
		boolean in =env.getCellContent(character.getWdt(), character.getHgt()).isInside(character);
		assertTrue(in);
	}

	public void initialisation() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 3, 2);
	}

	@Test
	public void testInitialisationPositif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		assertTrue(env.getCellNature(3, 2) == Cell.EMP);
		character.init(env, 3, 2);
		testInvariant();
	}

	@Test(expected = ContractError.class)
	public void testInitialisationNegatif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 3, 1);
	}

	@Test(expected = ContractError.class)
	public void testInitialisationNegatif3() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, -1, 1);
	}

	@Test(expected = ContractError.class)
	public void testInitialisationNegatif4() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 1, 1000);
	}

	@Test
	public void testInitialisationNegatif2() {
		Cell mtl, hdr, lad, hol, plt;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		lad = Cell.LAD;
		hol = Cell.HOL;
		plt = Cell.PLT;

		editscreen.init(10, 15);
		editscreen.setNature(1, 1, mtl);
		editscreen.setNature(2, 1, hdr);
		editscreen.setNature(4, 1, lad);
		editscreen.setNature(5, 1, hol);
		editscreen.setNature(6, 1, plt);
		env.init(editscreen);

		boolean error = false;
		try {
			character.init(env, 1, 1);
		} catch (ContractError e) {
			error = true;
		}
		assertTrue(error);
		error = false;
		try {
			character.init(env, 2, 1);
		} catch (ContractError e) {
			error = true;
		}
		assertTrue(error);
		error = false;
		try {
			character.init(env, 4, 1);
		} catch (ContractError e) {
			error = true;
		}
		assertTrue(error);
		error = false;
		try {
			character.init(env, 5, 1);
		} catch (ContractError e) {
			error = true;
		}
		assertTrue(error);
		error = false;
		try {
			character.init(env, 6, 1);
		} catch (ContractError e) {
			error = true;
		}
		assertTrue(error);
	}

	@Test
	public void testGoLeftEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 0, 2);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goLeft();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoLeftObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(0, 2, Cell.MTL);
		env.init(editscreen);
		character.init(env, 1, 2);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goLeft();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());

		editscreen.setNature(0, 2, Cell.PLT);
		env.init(editscreen);
		character.init(env, 1, 2);
		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goLeft();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoLeftFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 2, 3);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goLeft();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());
	}

	@Test
	public void testGoLeftNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 2, 2);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goLeft();
		testInvariant();
		assertTrue(xbefore - 1 == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoRightEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 14, 2);

		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goRight();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoRightObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 2, Cell.MTL);
		env.init(editscreen);
		character.init(env, 1, 2);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goRight();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());

		editscreen.setNature(2, 2, Cell.PLT);
		env.init(editscreen);
		character.init(env, 1, 2);
		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goRight();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoRightFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 2, 3);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goRight();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());
	}

	@Test
	public void testGoRightNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 2, 2);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goRight();
		testInvariant();
		assertTrue(xbefore + 1 == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoUpEdge() {
		editscreen.init(3, 3);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(0, 2, Cell.LAD);
		env.init(editscreen);
		character.init(env, 1, 2);
		testInvariant();
		character.goLeft();
		testInvariant();
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goUp();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoUpObstacle() {
		editscreen.init(4, 4);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(1, 2, Cell.LAD);
		editscreen.setNature(1, 3, Cell.MTL);
		env.init(editscreen);
		character.init(env, 0, 2);
		testInvariant();
		character.goRight();
		testInvariant();
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goUp();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());

		editscreen.setNature(1, 3, Cell.PLT);
		env.init(editscreen);
		character.init(env, 0, 2);
		testInvariant();
		character.goRight();
		testInvariant();
		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goUp();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());

		editscreen.setNature(1, 3, Cell.HDR);
		env.init(editscreen);
		character.init(env, 0, 2);
		testInvariant();
		character.goRight();
		testInvariant();
		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goUp();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoUpFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 2, 3);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goUp();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());
	}

	@Test
	public void testGoUpNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		for (int x = 2; x < editscreen.getHeight(); x++) {
			editscreen.setNature(2, x, Cell.LAD);
		}
		env.init(editscreen);
		character.init(env, 1, 2);
		testInvariant();
		character.goRight();
		testInvariant();
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		int j = 0;
		for (int x = 2; x < 9; x++) {
			character.goUp();
			testInvariant();
			j++;
		}
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore + j == character.getHgt());
	}

	@Test
	public void testGoDownObstacle() {
		editscreen.init(3, 4);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(1, 1, Cell.MTL);
		env.init(editscreen);
		character.init(env, 1, 2);

		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goDown();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());

		editscreen.setNature(1, 1, Cell.PLT);
		env.init(editscreen);
		character.init(env, 1, 2);

		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goDown();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore == character.getHgt());
	}

	@Test
	public void testGoDownFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		character.init(env, 2, 3);
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goDown();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());
	}

	@Test
	public void testGoDownNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 2, Cell.MTL);
		editscreen.setNature(3, 2, Cell.LAD);
		env.init(editscreen);
		character.init(env, 2, 3);

		character.goRight();
		int xbefore = character.getWdt();
		int ybefore = character.getHgt();
		testInvariant();
		character.goDown();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());

		editscreen.setNature(2, 2, Cell.PLT);
		editscreen.setNature(3, 2, Cell.HOL);
		env.init(editscreen);
		character.init(env, 2, 3);
		testInvariant();
		character.goRight();
		testInvariant();
		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goDown();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());

		editscreen.setNature(2, 2, Cell.PLT);
		editscreen.setNature(3, 2, Cell.HDR);
		env.init(editscreen);
		character.init(env, 2, 3);
		testInvariant();
		character.goRight();
		testInvariant();
		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goDown();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());

		editscreen.setNature(2, 2, Cell.PLT);
		editscreen.setNature(3, 2, Cell.EMP);
		env.init(editscreen);
		character.init(env, 2, 3);
		testInvariant();
		character.goRight();
		testInvariant();
		xbefore = character.getWdt();
		ybefore = character.getHgt();
		testInvariant();
		character.goDown();
		testInvariant();
		assertTrue(xbefore == character.getWdt());
		assertTrue(ybefore - 1 == character.getHgt());
	}
	@Test
	public void testManyCharacterOnTheSameCase() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		
		character.init(env, 1, 2);
		CharacterContract c2 = new CharacterContract(new CharacterImpl());
		c2.init(env, 3, 2);
		
		testInvariant();
		character.goRight();
		testInvariant();
		testInvariant();
		c2.goLeft();
		testInvariant();
		
		assertTrue(c2.getWdt() == character.getWdt());
		assertTrue(c2.getHgt() == character.getHgt());
	}

	@Test
	public void testCharacterOnCharacter() {
		editscreen.init(10, 15);
		
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(1, 2, Cell.PLT);
		editscreen.setNature(3, 2, Cell.PLT);
		
		env.init(editscreen);
		CharacterContract c2 = new CharacterContract(new CharacterImpl());
		c2.init(env, 2, 2);
		character.init(env, 1, 3);
		testInvariant();
		character.goRight();
		
		testInvariant();
		assertTrue(c2.getWdt() == character.getWdt());
		assertTrue(c2.getHgt()+1 == character.getHgt());
		
		testInvariant();
		character.goRight();
		testInvariant();
		assertTrue(character.getWdt() == 3);
		assertTrue(character.getHgt() == 3);
		
		
	}

}
