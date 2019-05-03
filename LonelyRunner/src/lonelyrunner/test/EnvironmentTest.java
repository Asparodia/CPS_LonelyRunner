package lonelyrunner.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.EnvironmentContract;
import lonelyrunner.contract.contracterr.ContractError;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.ItemType;

public class EnvironmentTest {

	private EnvironmentService env;
	private EditableScreenService editscreen;
	
	@Before
	public void beforeTests() {
		env = new EnvironmentContract(new EnvironmentImpl());
		editscreen =  new EditableScreenContract(new EditableScreenImpl());
	}

	@After
	public void afterTests() {
		env = null;
		editscreen = null;
	}

	public void initialisation() {
		editscreen.init(10, 15);
		env.init(editscreen);
	}
	
	public void testInvariant() {
		for(int x=0;x<env.getWidth();x++) {
			for(int y=0;y<env.getHeight();y++) {
				if(env.getCellNature(x, y) == Cell.MTL || env.getCellNature(x, y) == Cell.PLT) {
					assertTrue(env.getCellContent(x, y).getCar().isEmpty());
					assertTrue(env.getCellContent(x, y).getItem() == null );
				}
			}
		}
		for(int x=0;x<env.getWidth();x++) {
			for(int y=1;y<env.getHeight();y++) {
				if(env.getCellContent(x, y).getCar().isEmpty() && env.getCellContent(x, y).getItem() == null)
					continue;
				if(env.getCellContent(x, y).getItem() != null) {
					if(env.getCellContent(x, y).getItem().getNature() == ItemType.Treasure) {
						assertTrue(env.getCellNature(x, y) == Cell.EMP);
						assertTrue((env.getCellNature(x,y-1) == Cell.PLT || env.getCellNature(x,y-1) == Cell.MTL));
						assertTrue(!env.getCellContent(x, y-1).getCar().isEmpty());
					}
				}
			}
		}
	}
	
	@Test
	public void testInitialistionPositif() {
		initialisation();
		this.testInvariant();
		for(int x=0;x<env.getWidth();x++) {
			for(int y=0;y<env.getHeight();y++) {
				assertTrue(env.getCellNature(x, y) == editscreen.getCellNature(x, y) );
			}
		}
	}
	
	@Test(expected=ContractError.class)
	public void testInitialistionNegatif() {
		editscreen.init(-5, 15);
		env.init(editscreen);
	}
	
	@Test
	public void testGetCellContentPositif() {
		initialisation();
		testInvariant();
		env.getCellContent(0, 0);
	}
	
	@Test(expected=ContractError.class)
	public void testGetCellContentPositif2() {
		initialisation();
		testInvariant();
		env.getCellContent(-1, 0);
	}
	
	@Test(expected=ContractError.class)
	public void testGetCellContentPositif3() {
		initialisation();
		testInvariant();
		env.getCellContent(10000, 0);
	}
	
	@Test
	public void testDigPositif() {
		editscreen.init(10, 15);
		Cell changeTo = Cell.PLT;
		editscreen.setNature(1, 1, changeTo);
		env.init(editscreen);
		
		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				avants.add(env.getCellNature(x, y));
			}
		}
		
		Cell beforeDig = env.getCellNature(1, 1);
		assertTrue(beforeDig == Cell.PLT);
		this.testInvariant();
		env.dig(1, 1);
		this.testInvariant();
		Cell afterDig = env.getCellNature(1, 1);
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
				if(x==1 && y== 1) {
					v++;
					continue;
				}
				assertTrue(avants.get(v).compareTo(after.get(v))==0);
				v++;
			}
		}
	}

	@Test(expected=ContractError.class)
	public void testDigNegatif() {
		this.initialisation();
		testInvariant();
		env.dig(1, 1);
	}
	
	@Test
	public void testDigNegatif2() {
		Cell mtl,hdr,emp,lad,hol;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		hol = Cell.HOL;
		editscreen.init(10, 15);
		editscreen.setNature(0, 1, mtl);
		editscreen.setNature(1, 1, hdr);
		editscreen.setNature(2, 1, emp);
		editscreen.setNature(3, 1, lad);
		editscreen.setNature(4, 1, hol);
		env.init(editscreen);
		testInvariant();
		try {
			
			env.dig(0, 1);
		}catch(ContractError e) {
			}
		try {
			env.dig(1, 1);
		}catch(ContractError e) {
			}
		try {
			env.dig(2, 1);
		}catch(ContractError e) {
			}
		try {
			env.dig(3, 1);
		}catch(ContractError e) {
			}
		try {
			env.dig(4, 1);
		}catch(ContractError e) {
			}
	}

	@Test
	public void testFillPositif() {
		editscreen.init(10, 15);
		Cell changeTo = Cell.HOL;
		editscreen.setNature(1, 1, changeTo);
		env.init(editscreen);
		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				avants.add(env.getCellNature(x, y));
			}
		}
		Cell beforeDig = env.getCellNature(1, 1);
		assertTrue(beforeDig == Cell.HOL);
		testInvariant();
		env.fill(1, 1);
		testInvariant();
		Cell afterDig = env.getCellNature(1, 1);
		assertTrue(afterDig == Cell.PLT);
		ArrayList<Cell> after = new ArrayList<>();
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				after.add(env.getCellNature(x, y));
			}
		}
		int v = 0;
		for (int x = 0; x < env.getWidth(); x++) {
			for (int y = 0; y < env.getHeight(); y++) {
				if(x==1 && y== 1) {
					v++;
					continue;
				}
				assertTrue(avants.get(v).compareTo(after.get(v))==0);
				v++;
			}
		}
	}

	@Test(expected=ContractError.class)
	public void testFillNegatif() {
		this.initialisation();
		testInvariant();
		env.fill(1, 1);
	}
	
	@Test
	public void testFillNegatif2() {
		Cell mtl,hdr,emp,lad,plt;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		plt = Cell.PLT;
		editscreen.init(10, 15);
		editscreen.setNature(0, 1, mtl);
		editscreen.setNature(1, 1, hdr);
		editscreen.setNature(2, 1, emp);
		editscreen.setNature(3, 1, lad);
		editscreen.setNature(4, 1, plt);
		env.init(editscreen);
		testInvariant();
		try {
			env.fill(0, 1);
		}catch(ContractError e) {
			}
		try {
			env.fill(1, 1);
		}catch(ContractError e) {
			}
		try {
			env.fill(2, 1);
		}catch(ContractError e) {
			}
		try {
			env.fill(3, 1);
		}catch(ContractError e) {
			}
		try {
			env.fill(4, 1);
		}catch(ContractError e) {
			}
	}
	
}
