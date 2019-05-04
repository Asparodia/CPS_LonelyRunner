package lonelyrunner.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.EngineContract;
import lonelyrunner.contract.EnvironmentContract;
import lonelyrunner.contract.PlayerContract;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.impl.EngineImpl;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.impl.PlayerImpl;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.ItemType;
import lonelyrunner.service.utils.Status;

public class PlayerTest {

	private PlayerService player;
	private EnvironmentService env;
	private EditableScreenService editscreen;
	private EngineService engine;

	@Before
	public void beforeTests() {
		env = new EnvironmentContract(new EnvironmentImpl());
		editscreen = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		engine = new EngineContract(new EngineImpl());

	}

	@After
	public void afterTests() {
		env = null;
		editscreen = null;
		player = null;
		engine = null;

	}

	public void testInvariant() {
		Cell c = env.getCellNature(player.getWdt(), player.getHgt());
		assertTrue(c == Cell.EMP || c == Cell.HOL || c == Cell.LAD || c == Cell.HDR);
		boolean in = engine.getEnvironment().getCellContent(player.getWdt(), player.getHgt()).isInside(player);
		assertTrue(in);
	}

	@Test
	public void testInitialisationPositif() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		assertTrue(editscreen.getCellNature(0, 1) == Cell.EMP);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		assertTrue(engine.getStatus() == Status.Playing);
		player.init(env, 0, 1, engine);
		testInvariant();
		assertTrue(engine.getEnvironment().getCellContent(0, 1).isInside(player));
	}

	@Test
	public void testInitialisationNegatif() {
		System.out.println(
				"le player est init dans engine du coup c'est plutot dans les test de engine qu'on peut verifier qu'on init bien ou pas le player ");
	}

	@Test
	public void testGoLeftEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 1, engine);

		testInvariant();
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goLeft();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoLeftObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		editscreen.setNature(0, 1, Cell.MTL);
		engine.init(editscreen, new Couple<Integer, Integer>(1, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 1, 1, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goLeft();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

		editscreen.setNature(0, 1, Cell.PLT);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(1, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 1, 1, engine);

		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.goLeft();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoLeftFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 3), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 3, engine);

		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goLeft();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore - 1 == player.getHgt());
	}

	@Test
	public void testGoLeftNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 1, engine);

		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goLeft();
		testInvariant();
		assertTrue(xbefore - 1 == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoRightEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(14, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 14, 1, engine);

		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goRight();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoRightObstacle() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		editscreen.setNature(1, 1, Cell.MTL);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 1, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goRight();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

		editscreen.setNature(1, 1, Cell.PLT);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 1, engine);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.goRight();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoRightFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 3), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 3, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goRight();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore - 1 == player.getHgt());
	}

	@Test
	public void testGoRightNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(1, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 1, 1, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goRight();
		testInvariant();
		assertTrue(xbefore + 1 == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoUpEdge() {
		editscreen.init(2, 3);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		editscreen.setNature(0, 1, Cell.LAD);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(1, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 1, 1, engine);
		testInvariant();
		player.goLeft();
		testInvariant();
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goUp();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoUpObstacle() {
		editscreen.init(3, 4);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		editscreen.setNature(1, 1, Cell.LAD);
		editscreen.setNature(1, 2, Cell.MTL);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 1, engine);
		testInvariant();
		player.goRight();
		testInvariant();
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goUp();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

		editscreen.setNature(1, 2, Cell.PLT);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 1, engine);
		testInvariant();
		player.goRight();
		testInvariant();
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.goUp();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

		editscreen.setNature(1, 2, Cell.HDR);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 1, engine);
		testInvariant();
		player.goRight();
		testInvariant();
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.goUp();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoUpFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 3), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 3, engine);

		int xbefore = player.getWdt();
		int ybefore = player.getHgt();

		testInvariant();
		player.goUp();
		testInvariant();

		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore - 1 == player.getHgt());
	}

	@Test
	public void testGoUpNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 1; x < editscreen.getHeight(); x++) {
			editscreen.setNature(1, x, Cell.LAD);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 1, engine);
		testInvariant();
		player.goRight();
		testInvariant();
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		int j = 0;
		for (int x = 2; x < 9; x++) {
			player.goUp();
			testInvariant();
			j++;
		}
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore + j == player.getHgt());
	}

	@Test
	public void testGoDownObstacle() {
		editscreen.init(3, 4);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(1, 1, Cell.MTL);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(1, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 1, 2, engine);

		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goDown();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

		editscreen.setNature(1, 1, Cell.PLT);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(1, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 1, 2, engine);

		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.goDown();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testGoDownFalling() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 3), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 3, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goDown();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore - 1 == player.getHgt());
	}

	@Test
	public void testGoDownNormal() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		editscreen.setNature(2, 2, Cell.MTL);
		editscreen.setNature(3, 2, Cell.LAD);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 3), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 3, engine);

		player.goRight();
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.goDown();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore - 1 == player.getHgt());

		editscreen.setNature(2, 2, Cell.MTL);
		editscreen.setNature(3, 2, Cell.HDR);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 3), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 3, engine);
		testInvariant();
		player.goRight();
		testInvariant();
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.goDown();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore - 1 == player.getHgt());

		editscreen.setNature(2, 2, Cell.MTL);
		editscreen.setNature(3, 2, Cell.EMP);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 3), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 3, engine);
		testInvariant();
		player.goRight();
		testInvariant();
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.goDown();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore - 1 == player.getHgt());
	}

	@Test
	public void testDigLCase() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		Cell mtl, hdr, emp, lad, plt;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		plt = Cell.PLT;

		editscreen.setNature(0, 1, mtl);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(1, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 1, 2, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.digL();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(0, 1) == Cell.MTL);

		editscreen.setNature(1, 1, hdr);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(2, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 2, 2, engine);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digL();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(1, 1) == Cell.HDR);

		editscreen.setNature(2, 1, emp);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(3, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 3, 2, engine);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digL();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(2, 1) == Cell.EMP);

		editscreen.setNature(3, 1, lad);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(4, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 4, 2, engine);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digL();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(3, 1) == Cell.LAD);

		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				avants.add(env.getCellNature(x, y));
			}
		}

		editscreen.setNature(4, 1, plt);
		Cell beforeDig = env.getCellNature(4, 1);
		assertTrue(beforeDig == Cell.PLT);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(5, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 5, 2, engine);

		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digL();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(4, 1) == Cell.HOL);

		Cell afterDig = env.getCellNature(4, 1);
		assertTrue(afterDig == Cell.HOL);

		ArrayList<Cell> after = new ArrayList<>();
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				after.add(env.getCellNature(x, y));
			}
		}
		int v = 0;
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				if (x == 4 && y == 1) {
					v++;
					continue;
				}
				assertTrue(avants.get(v).compareTo(after.get(v)) == 0);
				v++;
			}
		}

	}

	@Test
	public void testDigRCase() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		Cell mtl, hdr, emp, lad, plt;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		plt = Cell.PLT;

		editscreen.setNature(14, 1, mtl);
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(13, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 13, 2, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(14, 1) == Cell.MTL);

		editscreen.setNature(13, 1, hdr);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(12, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 12, 2, engine);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(13, 1) == Cell.HDR);

		editscreen.setNature(12, 1, emp);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(11, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 11, 2, engine);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(12, 1) == Cell.EMP);

		editscreen.setNature(11, 1, lad);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(10, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 10, 2, engine);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(11, 1) == Cell.LAD);

		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				avants.add(env.getCellNature(x, y));
			}
		}
		editscreen.setNature(9, 1, plt);

		Cell beforeDig = env.getCellNature(9, 1);

		assertTrue(beforeDig == Cell.PLT);

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(8, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 8, 2, engine);

		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
		assertTrue(env.getCellNature(9, 1) == Cell.HOL);

		Cell afterDig = env.getCellNature(9, 1);
		assertTrue(afterDig == Cell.HOL);

		ArrayList<Cell> after = new ArrayList<>();
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				after.add(env.getCellNature(x, y));
			}
		}
		int v = 0;
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				if (x == 9 && y == 1) {
					v++;
					continue;
				}
				assertTrue(avants.get(v).compareTo(after.get(v)) == 0);
				v++;
			}
		}

	}

	@Test
	public void testDigLEdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 2, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.digL();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

	}

	@Test
	public void testDigREdge() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(14, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 14, 2, engine);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testDigLUnderItemOrCharacter() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(14, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 14, 2, engine);
		PlayerContract p2 = new PlayerContract(new PlayerImpl());

		env.getCellContent(13, 2).setItem(new Item(0, ItemType.Treasure, 13, 2));
		Cell beforeDig = env.getCellNature(13, 1);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		Cell afterDig = env.getCellNature(13, 1);
		assertTrue(afterDig == beforeDig);
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 2, engine);
		p2.init(env, 13, 2);
		env.getCellContent(13, 2).addCar(p2);
		beforeDig = env.getCellNature(13, 1);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digL();
		testInvariant();
		afterDig = env.getCellNature(13, 1);
		assertTrue(afterDig == beforeDig);
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

	@Test
	public void testDigRUnderItemOrCharacter() {
		editscreen.init(10, 15);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 1, Cell.PLT);
		}
		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 2, engine);
		PlayerContract p2 = new PlayerContract(new PlayerImpl());

		env.getCellContent(1, 2).setItem(new Item(0, ItemType.Treasure, 1, 2));
		Cell beforeDig = env.getCellNature(1, 1);
		int xbefore = player.getWdt();
		int ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		Cell afterDig = env.getCellNature(1, 1);
		assertTrue(afterDig == beforeDig);
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());

		env.init(editscreen);
		engine.init(editscreen, new Couple<Integer, Integer>(0, 2), new ArrayList<Couple<Integer, Integer>>(),
				new ArrayList<Couple<Integer, Integer>>());
		env = engine.getEnvironment();
		player.init(env, 0, 2, engine);
		p2.init(env, 1, 2);
		env.getCellContent(1, 2).addCar(p2);
		beforeDig = env.getCellNature(1, 1);
		xbefore = player.getWdt();
		ybefore = player.getHgt();
		testInvariant();
		player.digR();
		testInvariant();
		afterDig = env.getCellNature(1, 1);
		assertTrue(afterDig == beforeDig);
		assertTrue(xbefore == player.getWdt());
		assertTrue(ybefore == player.getHgt());
	}

}