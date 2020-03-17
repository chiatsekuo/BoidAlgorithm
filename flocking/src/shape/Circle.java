package shape;

import java.util.ArrayList;
import vector.Vector;
import utils.Utils;

/* 
class circle{
	double radius
	get and set
}
*/

public class Circle extends Shape{
	private int radius;
	
	public Circle(Vector center, int r) {
		super(center);
		this.radius = r;
	}
	
	public double getRadius() {
		return this.radius;
	}

	@Override
	public double distanceToPoint(Vector point) {
		double tempDistance = Utils.distance(point, this.getCenter());
		double resultDistance = tempDistance-this.getRadius();
		return resultDistance;
	}

	@Override
	public double distanceToPointCircle(Vector point) {
		return distanceToPoint(point);
	}
	
	@Override
	public ArrayList<Vector> getPoints(Vector projection){
		
		Vector direction = new Vector((this.getCenter().getxComponent()-projection.getxComponent()), this.getCenter().getyComponent()-projection.getyComponent());
		direction.scale(this.getRadius());
		
		Vector lu = new Vector(this.getCenter().getxComponent()-direction.getyComponent(), this.getCenter().getyComponent()+direction.getxComponent());
		
		Vector ru = new Vector(this.getCenter().getxComponent()+direction.getxComponent(), this.getCenter().getyComponent()+direction.getyComponent());
		
		Vector ld = new Vector(this.getCenter().getxComponent()-direction.getxComponent(), this.getCenter().getyComponent()-direction.getyComponent());
		
		Vector rd = new Vector(this.getCenter().getxComponent()+direction.getyComponent(), this.getCenter().getyComponent()-direction.getxComponent());
		
		ArrayList<Vector> pointsOfCircle = new ArrayList<Vector>();
		pointsOfCircle.add(lu);
		pointsOfCircle.add(ru);
		pointsOfCircle.add(ld);
		pointsOfCircle.add(rd);
		return pointsOfCircle;
	}
	
}
