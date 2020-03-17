package ray;

import java.util.ArrayList;

import vector.Vector;
public interface RayDetectable {
	public abstract double distanceToPoint(Vector point);//the length of the shortest path from the point to the edge of the shape
	public abstract double distanceToPointCircle(Vector point);//the length of the shortest path from the point to the edge of the shape if it were a circle
	public abstract ArrayList<Vector> getPoints(Vector projection);// the points of the shape
	public abstract Vector getCenter();
}
