package shape;

import java.util.ArrayList;
import vector.Vector;
import utils.Utils;

public class Rectangle extends Shape{
	private int width;
	private int height;
	public Rectangle(Vector center, int width, int height) {
		super(center);
		this.width = width;
		this.height = height;
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}

	@Override
	public double distanceToPoint(Vector point) {
		Vector lu = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		lu.setComponents(lu.getxComponent()-(this.getWidth()/2), lu.getyComponent()+(this.getHeight()/2));
		
		Vector ru = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		ru.setComponents(ru.getxComponent()+(this.getWidth()/2), ru.getyComponent()+(this.getHeight()/2));
		
		Vector ld = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		ld.setComponents(ld.getxComponent()-(this.getWidth()/2), ld.getyComponent()-(this.getHeight()/2));
		
		Vector rd = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		rd.setComponents(rd.getxComponent()+(this.getWidth()/2), rd.getyComponent()-(this.getHeight()/2));
		
		if(point.getxComponent()<lu.getxComponent()&&point.getyComponent()>lu.getyComponent()) {
			//lu
			return Utils.distance(point, lu);
		}else if(point.getxComponent()<ld.getxComponent()&&point.getyComponent()<ld.getyComponent()) {
			//ld
			return Utils.distance(point, ld);
		}else if(point.getxComponent()>ru.getxComponent()&&point.getyComponent()>ru.getyComponent()) {
			//ru
			return Utils.distance(point, ru);
		}else if(point.getxComponent()>rd.getxComponent()&&point.getyComponent()<rd.getyComponent()) {
			//rd
			return Utils.distance(point, rd);
		}else if(point.getxComponent()<lu.getxComponent()){
			//l
			Vector l = new Vector(lu.getxComponent(), point.getyComponent());
			return Utils.distance(point, l);
		}else if(point.getxComponent()>ru.getxComponent()) {
			//r
			Vector r = new Vector(ru.getxComponent(), point.getyComponent());
			return Utils.distance(point, r);
		}else if(point.getyComponent()>lu.getyComponent()) {
			//u
			Vector u = new Vector(point.getxComponent(), lu.getyComponent());
			return Utils.distance(point, u);
		}else if(point.getyComponent()<rd.getyComponent()) {
			//d
			Vector d = new Vector(point.getxComponent(), rd.getyComponent());
			return Utils.distance(point, d);
		}else {
			//point inside the rectangle
			return -1;
		}
	}

	@Override
	public double distanceToPointCircle(Vector point) {
		// TODO Auto-generated method stub
		double radius = Math.sqrt((this.getHeight()/2)*(this.getHeight()/2)+(this.getWidth()/2)*(this.getWidth()/2));
		double tempDistance = Utils.distance(point, this.getCenter());
		if(tempDistance>radius) {
			double resultDistance = tempDistance-radius;
			return resultDistance;
		}
		return -1;
	}
	
	@Override
	public ArrayList<Vector> getPoints(Vector projection){
		Vector lu = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		lu.setComponents(lu.getxComponent()-(this.getWidth()/2), lu.getyComponent()+(this.getHeight()/2));
		
		Vector ru = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		ru.setComponents(ru.getxComponent()+(this.getWidth()/2), ru.getyComponent()+(this.getHeight()/2));
		
		Vector ld = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		ld.setComponents(ld.getxComponent()-(this.getWidth()/2), ld.getyComponent()-(this.getHeight()/2));
		
		Vector rd = new Vector(this.getCenter().getxComponent(), this.getCenter().getyComponent());
		rd.setComponents(rd.getxComponent()+(this.getWidth()/2), rd.getyComponent()-(this.getHeight()/2));
		
		ArrayList<Vector> pointsOfRec = new ArrayList<Vector>();
		pointsOfRec.add(lu);
		pointsOfRec.add(ru);
		pointsOfRec.add(ld);
		pointsOfRec.add(rd);
		return pointsOfRec;
	}
}
