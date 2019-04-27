package lonelyrunner.service.utils;

public class Item {

    private int id;
    private ItemType nature;
    private int hgt;
    private int col;
    
	public Item(int id, ItemType nature, int col, int hgt) {
		this.id = id;
		this.nature = nature;
		this.hgt = hgt;
		this.col = col;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemType getNature() {
		return nature;
	}

	public void setNature(ItemType nature) {
		this.nature = nature;
	}

	public int getHgt() {
		return hgt;
	}

	public void setHgt(int hgt) {
		this.hgt = hgt;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}