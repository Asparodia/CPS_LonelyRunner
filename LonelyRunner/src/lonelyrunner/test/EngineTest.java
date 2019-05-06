package lonelyrunner.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.EngineContract;
import lonelyrunner.contract.contracterr.ContractError;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.impl.EngineImpl;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineTest {
	
	private EngineService engine;
	private EditableScreenService editscreen;
	
	@Before
	public void beforeTests() {
		editscreen = new EditableScreenContract(new EditableScreenImpl());
		engine = new EngineContract(new EngineImpl());

	}

	@After
	public void afterTests() {
		engine = null;
		editscreen = null;
	}
	
	public void testInvariant() {
		EnvironmentService env = engine.getEnvironment();
		PlayerService player = engine.getPlayer();
		ArrayList<GuardService> guards = engine.getGuards();
		ArrayList<Hole> holes = engine.getHoles();
		ArrayList<Item> items = engine.getTreasures();

		assertTrue((env.getCellContent(player.getWdt(), player.getHgt()).isInside(player)));
	
	
		for (GuardService g : guards) {
			assertTrue(env.getCellContent(g.getWdt(), g.getHgt()).isInside(g));
		}
		for (Item i : items) {
			assertTrue(env.getCellContent(i.getCol(), i.getHgt()).getItem().getId() == i.getId());
		}
		for (Hole h : holes) {
			assertTrue(env.getCellNature(h.getX(), h.getY()) == Cell.HOL);
		}
		
		if (engine.getNbLives() < 0) {
			assertTrue(engine.getStatus() == Status.Loss);
		}
		if (engine.getTreasures().isEmpty()) {
			assertTrue(engine.getStatus() == Status.Win);
		}
	}
	
	@Test
	public void testInitialisationPositif() {
		editscreen.init(10, 10);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		assertTrue(editscreen.isPlayable());
		assertTrue(editscreen.getCellNature(0, 1) == Cell.EMP);
		
		ArrayList<Couple<Integer, Integer>> treasures = new ArrayList<Couple<Integer, Integer>>();
		treasures.add(new Couple<Integer,Integer>(9,1));
		ArrayList<Couple<Integer, Integer>> guards = new ArrayList<Couple<Integer, Integer>>();
		guards.add(new Couple<Integer,Integer>(5,1));
		assertTrue(!treasures.isEmpty());
		for(Couple<Integer, Integer> c : guards) {
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
		}
		for(Couple<Integer, Integer> c : treasures) {
			for(Couple<Integer, Integer> g : guards) {
				assertTrue(c.getElem1() != g.getElem1() || c.getElem2() != g.getElem2());
			}
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.PLT || editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.MTL);
		}
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), 
				guards, treasures);
		
		testInvariant();
		
		assertTrue(engine.getStatus()==Status.Playing);
		assertTrue(engine.getNbLives()==2);
		assertTrue(engine.getScore()==0);
		assertTrue(engine.getEnvironment().getCellContent(0, 1).isInside((engine.getPlayer())));
		for (Vector<Integer> v : engine.guardInitPos()) {
			for (GuardService g : engine.getGuards()) {
				if (g.getId() == v.get(0)) {
					assertTrue(g.getHgt() == v.get(2) && g.getWdt() == v.get(1));
				}
			}
		}
		for (GuardService gs : engine.getGuards()) {
			assertTrue(engine.getEnvironment().getCellContent(gs.getWdt(), gs.getHgt()).isInside(gs));
		}
		for (Item i : engine.getTreasures()) {
			assertTrue(engine.getEnvironment().getCellContent(i.getCol(), i.getHgt()).getItem().getId() == i.getId());
		}
	}

	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif() {
		editscreen.init(10, 10);
		for (int x = 1; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		ArrayList<Couple<Integer, Integer>> treasures = new ArrayList<Couple<Integer, Integer>>();
		treasures.add(new Couple<Integer,Integer>(9,1));
		ArrayList<Couple<Integer, Integer>> guards = new ArrayList<Couple<Integer, Integer>>();
		guards.add(new Couple<Integer,Integer>(5,1));
		assertTrue(!treasures.isEmpty());
		for(Couple<Integer, Integer> c : guards) {
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
		}
		for(Couple<Integer, Integer> c : treasures) {
			for(Couple<Integer, Integer> g : guards) {
				assertTrue(c.getElem1() != g.getElem1() || c.getElem2() != g.getElem2());
			}
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.PLT || editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.MTL);
		}
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), 
				guards, treasures);
		// non plablescreen
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif2() {
		editscreen.init(10, 10);
		for (int x = 0; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		ArrayList<Couple<Integer, Integer>> treasures = new ArrayList<Couple<Integer, Integer>>();
		ArrayList<Couple<Integer, Integer>> guards = new ArrayList<Couple<Integer, Integer>>();
		guards.add(new Couple<Integer,Integer>(5,1));
		for(Couple<Integer, Integer> c : guards) {
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
		}
		for(Couple<Integer, Integer> c : treasures) {
			for(Couple<Integer, Integer> g : guards) {
				assertTrue(c.getElem1() != g.getElem1() || c.getElem2() != g.getElem2());
			}
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.PLT || editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.MTL);
		}
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), 
				guards, treasures);
		// no treasure
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif3() {
		editscreen.init(10, 10);
		for (int x = 1; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		
		editscreen.setNature(9, 1, Cell.MTL);
		ArrayList<Couple<Integer, Integer>> treasures = new ArrayList<Couple<Integer, Integer>>();
		treasures.add(new Couple<Integer,Integer>(9,1));
		ArrayList<Couple<Integer, Integer>> guards = new ArrayList<Couple<Integer, Integer>>();
		guards.add(new Couple<Integer,Integer>(5,1));
		assertTrue(!treasures.isEmpty());
		for(Couple<Integer, Integer> c : guards) {
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
		}
		for(Couple<Integer, Integer> c : treasures) {
			for(Couple<Integer, Integer> g : guards) {
				assertTrue(c.getElem1() != g.getElem1() || c.getElem2() != g.getElem2());
			}
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.PLT || editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.MTL);
		}
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), 
				guards, treasures);
		// non empty for guard
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif4() {
		editscreen.init(10, 10);
		for (int x = 1; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		
		editscreen.setNature(0, 1, Cell.MTL);
		ArrayList<Couple<Integer, Integer>> treasures = new ArrayList<Couple<Integer, Integer>>();
		treasures.add(new Couple<Integer,Integer>(9,1));
		ArrayList<Couple<Integer, Integer>> guards = new ArrayList<Couple<Integer, Integer>>();
		guards.add(new Couple<Integer,Integer>(5,1));
		assertTrue(!treasures.isEmpty());
		for(Couple<Integer, Integer> c : guards) {
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
		}
		for(Couple<Integer, Integer> c : treasures) {
			for(Couple<Integer, Integer> g : guards) {
				assertTrue(c.getElem1() != g.getElem1() || c.getElem2() != g.getElem2());
			}
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.PLT || editscreen.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.MTL);
		}
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), 
				guards, treasures);
		// non empty for player
	}
	
	@Test(expected=ContractError.class)
	public void testInitialisationNegatif5() {
		editscreen.init(10, 10);
		for (int x = 1; x < editscreen.getWidth(); x++) {
			editscreen.setNature(x, 0, Cell.MTL);
		}
		
		editscreen.setNature(9, 1, Cell.MTL);
		ArrayList<Couple<Integer, Integer>> treasures = new ArrayList<Couple<Integer, Integer>>();
		treasures.add(new Couple<Integer,Integer>(9,1));
		ArrayList<Couple<Integer, Integer>> guards = new ArrayList<Couple<Integer, Integer>>();
		guards.add(new Couple<Integer,Integer>(5,1));
		assertTrue(!treasures.isEmpty());
		for(Couple<Integer, Integer> c : guards) {
			assertTrue(editscreen.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP);
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
		}
		for(Couple<Integer, Integer> c : treasures) {
			for(Couple<Integer, Integer> g : guards) {
				assertTrue(c.getElem1() != g.getElem1() || c.getElem2() != g.getElem2());
			}
			assertTrue(c.getElem1() != 0 || c.getElem2() != 1);
			assertTrue(c.getElem1() >= 0 || c.getElem1() < editscreen.getWidth()-1);
			assertTrue(c.getElem2() >= 0 || c.getElem2() < editscreen.getHeight()-1);
		}
		engine.init(editscreen, new Couple<Integer, Integer>(0, 1), 
				guards, treasures);
		// non empty for item
	}
	
	@Test
	public void testSetCommande() {
		testInitialisationPositif();
		engine.setCommand(Move.DigL);
		testInvariant();
		assertTrue(engine.getNextCommand()== Move.DigL);
		engine.setCommand(Move.DigR);
		testInvariant();
		assertTrue(engine.getNextCommand()== Move.DigR);
		engine.setCommand(Move.NEUTRAL);
		testInvariant();
		assertTrue(engine.getNextCommand()== Move.NEUTRAL);
		engine.setCommand(Move.RIGHT);
		testInvariant();
		assertTrue(engine.getNextCommand()== Move.RIGHT);
		engine.setCommand(Move.LEFT);
		testInvariant();
		assertTrue(engine.getNextCommand()== Move.LEFT);
		engine.setCommand(Move.UP);
		testInvariant();
		assertTrue(engine.getNextCommand()== Move.UP);
		engine.setCommand(Move.DOWN);
		testInvariant();
		assertTrue(engine.getNextCommand()== Move.DOWN);
		
	}
	
	
	
	@Test
	public void testSetNblives() {
		testInitialisationPositif();
		testInvariant();
		engine.setNbLives(10);
		testInvariant();
		assertTrue(engine.getNbLives()==10);
		engine.setNbLives(100);
		testInvariant();
		assertTrue(engine.getNbLives()==100);
	}
	
	@Test(expected=ContractError.class)
	public void testSetNblivesCrash() {
		testInitialisationPositif();
		engine.setNbLives(-11);
		// car pas de step qui s'execute
	}
	
	@Test
	public void testStep() {
		
		testInitialisationPositif();
		while(engine.getStatus() == Status.Playing) {
			engine.step();
		}
		assertTrue(engine.getStatus()==Status.Loss);
		
	}
	

}
