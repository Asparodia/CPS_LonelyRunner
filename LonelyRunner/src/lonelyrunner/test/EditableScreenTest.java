package lonelyrunner.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.contracterr.ContractError;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.utils.Cell;

public class EditableScreenTest {

	private EditableScreenService editscreen;

	@Before
	public void beforeTests() {
		editscreen = new EditableScreenContract(new EditableScreenImpl());
	}

	@After
	public void afterTests() {
		editscreen = null;
	}

	public void initialisation() {
		editscreen.init(10, 15);
	}

	public void testInvariant() {
		boolean isPlay = editscreen.isPlayable();

		if (isPlay) {
			Boolean t = true;
			for (int x = 0; x < editscreen.getWidth(); x++) {
				for (int y = 0; y < editscreen.getHeight(); y++) {
					if (!(editscreen.getCellNature(x, y) != Cell.HOL)) {
						t = false;
					}
				}
			}
			for (int x = 0; x < editscreen.getWidth(); x++) {
				if (!(editscreen.getCellNature(x, 0) == Cell.MTL)) {
					t = false;
				}
			}
			assertTrue(isPlay == t);

		} else {
			Boolean t = true;
			for (int x = 0; x < editscreen.getWidth(); x++) {
				for (int y = 0; y < editscreen.getHeight(); y++) {
					if (!(editscreen.getCellNature(x, y) != Cell.HOL)) {
						t = false;
					}
				}
			}
			for (int x = 0; x < editscreen.getWidth(); x++) {
				if (!(editscreen.getCellNature(x, 0) == Cell.MTL)) {
					t = false;
				}
			}
			assertTrue(isPlay == t);
		}

	}

	public void setNaturePos(Cell c, int a, int b) {
		Cell changeTo = c;
		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				avants.add(editscreen.getCellNature(x, y));
			}
		}
		Cell avant = editscreen.getCellNature(a, b);
		testInvariant();
		editscreen.setNature(a, b, changeTo);
		testInvariant();
		Cell apres = editscreen.getCellNature(a, b);
		ArrayList<Cell> after = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				after.add(editscreen.getCellNature(x, y));
			}
		}
		int v = 0;
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				if(x==a && y==b) {
					if (avant == changeTo) {
						assertTrue(apres == avant);
						assertTrue(apres == changeTo);
					} else {
						assertTrue(avant != apres);
						assertTrue(apres == changeTo);
					}
					v++;
					continue;
				}
				assertTrue(avants.get(v).compareTo(after.get(v))==0);
				v++;
			}
		}
	}
	@Test
	public void testSetNaturePositif() {
		Cell changeTo = Cell.PLT;
		this.initialisation();
		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				avants.add(editscreen.getCellNature(x, y));
			}
		}
		Cell avant = editscreen.getCellNature(1, 1);
		testInvariant();
		editscreen.setNature(1, 1, changeTo);
		testInvariant();
		Cell apres = editscreen.getCellNature(1, 1);
		ArrayList<Cell> after = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				after.add(editscreen.getCellNature(x, y));
			}
		}
		int v = 0;
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				if(x==1 && y== 1) {
					if (avant == changeTo) {
						assertTrue(apres == avant);
						assertTrue(apres == changeTo);
					} else {
						assertTrue(avant != apres);
						assertTrue(apres == changeTo);
					}
					v++;
					continue;
				}
				assertTrue(avants.get(v).compareTo(after.get(v))==0);
				v++;
			}
		}
	}
	
	@Test
	public void testSetNaturePositif2() {
		this.initialisation();
		Cell mtl,hdr,emp,lad,hol,plt;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		hol = Cell.HOL;
		plt = Cell.PLT;
		setNaturePos(mtl,1,1);
		assertTrue(editscreen.getCellNature(1,1) == mtl);
		setNaturePos(hdr,1,1);
		assertTrue(editscreen.getCellNature(1,1) == hdr);
		setNaturePos(emp,1,1);
		assertTrue(editscreen.getCellNature(1,1) == emp);
		setNaturePos(lad,1,1);
		assertTrue(editscreen.getCellNature(1,1) == lad);
		setNaturePos(hol,1,1);
		assertTrue(editscreen.getCellNature(1,1) == hol);
		setNaturePos(plt,1,1);
		assertTrue(editscreen.getCellNature(1,1) == plt);
	}

	@Test(expected = ContractError.class)
	public void testSetNatureNegatif() {
		Cell changeTo = Cell.PLT;
		
		this.initialisation();
		testInvariant();
		editscreen.setNature(-1, 1, changeTo);
	}

	@Test
	public void testDigPositif() {
		this.initialisation();
		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				avants.add(editscreen.getCellNature(x, y));
			}
		}
		Cell changeTo = Cell.PLT;
		editscreen.setNature(1, 1, changeTo);
		Cell beforeDig = editscreen.getCellNature(1, 1);
		assertTrue(beforeDig == Cell.PLT);
		testInvariant();
		editscreen.dig(1, 1);
		testInvariant();
		Cell afterDig = editscreen.getCellNature(1, 1);
		assertTrue(afterDig == Cell.HOL);
		ArrayList<Cell> after = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				after.add(editscreen.getCellNature(x, y));
			}
		}
		int v = 0;
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
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
		editscreen.dig(1, 1);
		
	}
	
	@Test
	public void testDigNegatif2() {
		Cell mtl,hdr,emp,lad,hol;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		hol = Cell.HOL;
		this.initialisation();
		editscreen.setNature(0, 1, mtl);
		editscreen.setNature(1, 1, hdr);
		editscreen.setNature(2, 1, emp);
		editscreen.setNature(3, 1, lad);
		editscreen.setNature(4, 1, hol);
		try {
			testInvariant();
			editscreen.dig(0, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.dig(1, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.dig(2, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.dig(3, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.dig(4, 1);
		}catch(ContractError e) {
			}
	}

	@Test
	public void testFillPositif() {
		this.initialisation();
		ArrayList<Cell> avants = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				avants.add(editscreen.getCellNature(x, y));
			}
		}
		Cell changeTo = Cell.HOL;
		editscreen.setNature(1, 1, changeTo);
		Cell beforeDig = editscreen.getCellNature(1, 1);
		assertTrue(beforeDig == Cell.HOL);
		testInvariant();
		editscreen.fill(1, 1);
		testInvariant();
		Cell afterDig = editscreen.getCellNature(1, 1);
		assertTrue(afterDig == Cell.PLT);
		ArrayList<Cell> after = new ArrayList<>();
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
				after.add(editscreen.getCellNature(x, y));
			}
		}
		int v = 0;
		for (int x = 0; x < editscreen.getWidth(); x++) {
			for (int y = 0; y < editscreen.getHeight(); y++) {
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
		editscreen.fill(1, 1);
	}
	
	@Test
	public void testFillNegatif2() {
		Cell mtl,hdr,emp,lad,plt;
		mtl = Cell.MTL;
		hdr = Cell.HDR;
		emp = Cell.EMP;
		lad = Cell.LAD;
		plt = Cell.PLT;
		this.initialisation();
		editscreen.setNature(0, 1, mtl);
		editscreen.setNature(1, 1, hdr);
		editscreen.setNature(2, 1, emp);
		editscreen.setNature(3, 1, lad);
		editscreen.setNature(4, 1, plt);
		try {
			testInvariant();
			editscreen.fill(0, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.fill(1, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.fill(2, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.fill(3, 1);
		}catch(ContractError e) {
			}
		try {
			testInvariant();
			editscreen.fill(4, 1);
		}catch(ContractError e) {
			}
	}
}
