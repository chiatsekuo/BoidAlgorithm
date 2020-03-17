package graphics;

import java.util.ArrayList;

import vector.Vector;

class drawVector extends Vector implements Drawable{
	static Vector cen = new Vector(0,0);
	private Colors color;
	private ArrayList<Vector> lines = new ArrayList<Vector>();
	public drawVector(Colors col) {
		super(0,0);
		color = col;
		lines.add(cen);
		lines.add(this);
	}
	public drawVector(double x,double y,Colors col) {
		super(x,y);
		color = col;
		lines.add(cen);
		lines.add(this);
	}
	public Vector getCenter() {
		return cen;
	}
	public ArrayList<Vector> getlines() {
		return lines;
	}
	public Colors getColor() {
		return color;
	}
	
}
