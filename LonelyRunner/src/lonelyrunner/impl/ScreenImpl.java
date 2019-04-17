package lonelyrunner.impl;

import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;

public class ScreenImpl implements ScreenService {
	
	private int height;
	private int width;
	protected Cell[][] cells;
	
	@Override
	public void init(int h, int w) {
		height = h;
		width = w;
		cells = new Cell[width][height];
		
		for(int i = 0;i<width;i++) {
			for(int j=0;j<height;j++) {
				cells[i][j] = Cell.EMP;
			}
		}
		
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Cell getCellNature(int i, int j) {
		return cells[i][j];
	}

	

	@Override
	public void dig(int u, int v) {
		cells[u][v] = Cell.HOL;
		
	}

	@Override
	public void fill(int x, int y) {
		cells[x][y] = Cell.PLT;
	}

}
