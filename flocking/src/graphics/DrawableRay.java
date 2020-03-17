package graphics;

import java.util.ArrayList;

import ray.Ray;
import shape.Circle;
import vector.Vector;

public class DrawableRay extends Ray implements Drawable {

	private ArrayList<Drawable> arrayCircle = new  ArrayList<Drawable>();
	
	@Override
	public Vector getCenter() {
		return this.getStartPoint();
	}
	public double trace(double limitDistance) {
		arrayCircle.clear();
		return super.trace(limitDistance);
	
	}
	public ArrayList<Drawable> getDrawables(){
		return this.arrayCircle;
	}
	public void moveCurrentPoint(double distance) {
		addArrayCircle(this.getCurrentPoint(),distance);
		super.moveCurrentPoint(distance);
	}
	public void addArrayCircle(Vector currentPoint, double tempDistance){
		Vector c = new Vector(0,0);
		c.copy(currentPoint);
		c.subtract(getStartPoint());
		arrayCircle.add(new Circle(c, (int)Math.round(tempDistance)));
	}
}
