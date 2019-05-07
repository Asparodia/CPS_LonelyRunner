package lonelyrunner.contract;

import java.util.HashMap;

import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.ScreenDecorator;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;

public class ScreenContract extends ScreenDecorator {

	public ScreenContract(ScreenService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		// rien a verif
	}

	@Override
	public Cell getCellNature(int i, int j) {
		// pre
		if (!(i >= 0 && i < super.getWidth())) {
			throw new PreconditionError("getCellNature(" + i + ", " + j + " )",
					"i must be between 0 and strictly inf to getWidth");
		}
		if (!(j >= 0 && j < super.getHeight())) {
			throw new PreconditionError("getCellNature(" + i + ", " + j + " )",
					"j must be between 0 and strictly inf to getHeight");
		}
		return super.getCellNature(i, j);
	}

	@Override
	public void init(int h, int w) {
		// pre
		if (!(h > 0)) {
			throw new PreconditionError("init(" + h + ", " + w + " )", "height must be strictly positive");
		}
		if (!(w > 0)) {
			throw new PreconditionError("init(" + h + ", " + w + " )", "width must be strictly positive");
		}

		// Call
		super.init(h, w);
		checkInvariant();

		// post
		if (!(h == super.getHeight())) {
			throw new PostconditionError("init(" + h + ", " + w + " )", "h is not equal to getHeight after init");
		}
		if (!(w == super.getWidth())) {
			throw new PostconditionError("init(" + h + ", " + w + " )", "w is not equal to getWidth after init");
		}
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				if (!(super.getCellNature(i, j) == Cell.EMP)) {
					throw new PostconditionError("init(" + h + ", " + w + " )",
							"cell (" + i + ", " + j + ") is not EMP");
				}
			}

		}

	}

	@Override
	public void dig(int u, int v) {
		// pre
		if (!(super.getCellNature(u, v) == Cell.PLT)) {
			throw new PreconditionError("dig(" + u + ", " + v + " )", "Before dig the cell must be a PLT");
		}

		// captures
		HashMap<Couple<Integer, Integer>, Cell> getCellNature_atpre = new HashMap<>();
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				if (i == u && j == v) {
					continue;
				}
				Couple<Integer, Integer> c = new Couple<Integer, Integer>(i, j);
				Cell nc = super.getCellNature(i, j);
				getCellNature_atpre.put(c, nc);
			}
		}

		// Call
		checkInvariant();
		super.dig(u, v);
		checkInvariant();

		// post
		if (!(super.getCellNature(u, v) == Cell.HOL)) {
			throw new PostconditionError("dig(" + u + ", " + v + " )", "cell (" + u + ", " + v + ") is not HOL");
		}
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				Cell nc = null;
				for (Couple<Integer, Integer> cp : getCellNature_atpre.keySet()) {
					if (cp.getElem1() == i && cp.getElem2() == j) {
						nc = getCellNature_atpre.get(cp);
					}
				}

				if (nc != null) {
					if (!(super.getCellNature(i, j) == nc)) {
						throw new PostconditionError("dig(" + u + ", " + v + " )",
								"cell (" + i + ", " + j + ") changed ");
					}
				}

			}

		}
	}

	@Override
	public void fill(int u, int v) {
		// pre
		if (!(super.getCellNature(u, v) == Cell.HOL)) {
			throw new PreconditionError("fill(" + u + ", " + v + " )", "Before fill the cell must be a HOL");
		}

		// captures
		HashMap<Couple<Integer, Integer>, Cell> getCellNature_atpre = new HashMap<>();
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				if (i == u && j == v) {
					continue;
				}
				Couple<Integer, Integer> c = new Couple<Integer, Integer>(i, j);
				Cell nc = super.getCellNature(i, j);
				getCellNature_atpre.put(c, nc);
			}
		}

		// Call
		checkInvariant();
		super.fill(u, v);
		checkInvariant();

		// post
		if (!(super.getCellNature(u, v) == Cell.PLT)) {
			throw new PostconditionError("fill(" + u + ", " + v + " )", "cell (" + u + ", " + v + ") is not PLT");
		}
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				Cell nc = null;
				for (Couple<Integer, Integer> cp : getCellNature_atpre.keySet()) {
					if (cp.getElem1() == i && cp.getElem2() == j) {
						nc = getCellNature_atpre.get(cp);
					}
				}
				if (nc != null) {
					if (!(super.getCellNature(i, j) == nc)) {
						throw new PostconditionError("fill(" + u + ", " + v + " )",
								"cell (" + i + ", " + j + ") changed ");
					}
				}

			}

		}

	}

}
