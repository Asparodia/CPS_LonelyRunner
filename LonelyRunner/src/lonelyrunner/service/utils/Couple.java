package lonelyrunner.service.utils;

public class Couple<T, V> {

	private T elem1;
	private V elem2;

	public Couple(T elem1, V elem2) {
		this.elem1 = elem1;
		this.elem2 = elem2;
	}

	public T getElem1() {
		return elem1;
	}

	public void setElem1(T elem1) {
		this.elem1 = elem1;
	}

	public V getElem2() {
		return elem2;
	}

	public void setElem2(V elem2) {
		this.elem2 = elem2;
	}

	public boolean equals(Couple<T, V> c) {
		if (c.getElem1().equals(getElem1()) && c.getElem2().equals(getElem2())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Couple<T, V> other = (Couple<T, V>) obj;
		if (other.getElem1().equals(getElem1()) && other.getElem2().equals(getElem2())) {
			return true;
		}
		return false;
	}

}
