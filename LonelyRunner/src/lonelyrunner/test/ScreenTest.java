package lonelyrunner.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import lonelyrunner.contract.ScreenContract;
import lonelyrunner.contract.contracterr.ContractError;
import lonelyrunner.impl.ScreenImpl;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;

public class ScreenTest {

	private ScreenService screen;

	@Before
	public void beforeTests() {
		screen = new ScreenContract(new ScreenImpl());

	}

	@After
	public void afterTests() {
		screen = null;
	}

	public void initialisation() {
		screen.init(10, 115);
	}

	public void testInvariant() {
		// pas d'invariant
		return;
	}

	/*
	 * Test d'initialisation de screen positive avec les parametres bien superieur a
	 * zero
	 */
	@Test
	public void testInitPositif() {
		screen.init(10, 15);
		this.testInvariant();
		assertTrue(screen.getWidth() > 0);
		assertTrue(screen.getHeight() > 0);
		assertTrue(screen.getHeight() == 10);
		assertTrue(screen.getWidth() == 15);
		for (int i = 0; i < screen.getWidth(); i++) {
			for (int j = 0; j < screen.getHeight(); j++) {
				assertTrue(screen.getCellNature(i, j) == Cell.EMP);
			}
		}
	}

	/*
	 * Test d'initialisation de screen negatif avec hauteur nulle
	 */
	@Test(expected = ContractError.class)
	public void testInitNegatif() {
		screen.init(0, 15);
	}

	/*
	 * Test d'initialisation de screen negatif avec largeur negative
	 */
	@Test(expected = ContractError.class)
	public void testInitNegatif2() {
		screen.init(10, -1);
	}

	/*
	 * Test d'initialisation de screen negatif avec hauteur et largeur negatif
	 */
	@Test(expected = ContractError.class)
	public void testInitNegatif3() {
		screen.init(-1000, -1);
	}

	@Test
	public void nonAtteignable() {
		System.out.println(
				"les operators dig et fill ne sont pas testable ici car il faut setNature et setNature n'est pas un operateur de ScreenService\n dig et fill seront tester dans EnvironmentTest et EditableScreenTest");

	}
}
