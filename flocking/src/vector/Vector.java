package vector;
import java.lang.Math;

import utils.Log;
import utils.Utils;
/* vector.h

add some public static final vector for north south west east up down left right ...

class vector{
	ok 	public void setComponents(double x, double y);
	ok	public double getLength();
	ok  public void setLength(double l);
	ok 	public void setZero();
	ok	public Vector(double x,double y);
	ok	public Vector(double x1, double y1,double x2, double y2);
	ok	public Vector(double radians, double length, boolean type);
	ok	public boolean add(Vector other); 								change to no new
	ok	public boolean subtract(Vector other); 							change to no new
	ok	public static Vector add(Vector a, Vector b);  		this will create a new vector
	ok	public static Vector subtract(Vector a, Vector b);	this will create a new vector
	ok 	public void multiply(double m);
	ok	public void divide(double d);  new divide function
	ok	public normalize();
	ok	public void scale(double newlength);
	ok	public void invert();
	ok	public bool limit(double min, double max); // adjust length so it is within the range //if it hit a boundy return true
	ok	public rotate(double radians);
	ok 	public void setAngle(double) // rotates the vector to a certain angle in radians
	ok 	public double getAngle();
	ok  public boolean isEqual(Vector other);
	ok	public void copy(Vector other); //copies the x and y of one vector to the other
}
 */

public class Vector {
	
	private double xComponent;
	
	private double yComponent;
	
	protected static Log log = Log.getLog();
	protected static int ERROR = Log.VECTOR+Log.ERROR+log.DEBUG;

	public double getxComponent() {
		return xComponent;
	}

	public void setxComponent(double xComponent) {
		this.xComponent = xComponent;
	}

	public double getyComponent() {
		return yComponent;
	}

	public void setyComponent(double yComponent) {
		this.yComponent = yComponent;
	}
	
	public void setComponents(double x, double y) {
		this.setxComponent(x);
		this.setyComponent(y);
	}
	
	public double getLength() {
		double length = Math.sqrt(Math.pow(this.xComponent, 2) + Math.pow(this.yComponent, 2));
		return length;
	}

	public void setLength(double l) {
		double oldLength = this.getLength();
		double scale = l/oldLength;
		this.setComponents(this.getxComponent()*scale, this.getyComponent()*scale);
	}
	
	public void setZero() {
		this.setComponents(0, 0);
	}
	
	public void copy(Vector other) {
		this.setComponents(other.getxComponent(), other.getyComponent());
	}
	
	public Vector(double x,double y) {
		this.setComponents(x, y);
	}
	
	public Vector(double x1, double y1,double x2, double y2) {
		this(x2-x1,y2-y1);
	}
	
	public Vector(double radians, double length, boolean type) {
		this.setComponents(length*Math.cos(radians), length*Math.sin(radians));
	}
	
	public boolean add(Vector other) {
		this.setComponents(this.getxComponent()+other.getxComponent(), this.getyComponent()+other.getyComponent());
		return true;
	}
	
	public boolean subtract(Vector other) {
		other.invert();
		this.setComponents(this.getxComponent()+other.getxComponent(), this.getyComponent()+other.getyComponent());
		other.invert();
		return true;
	}
	
	public static Vector add(Vector a, Vector b) {
		Vector newVector = new Vector(0,0);
		boolean addA = newVector.add(a);
		boolean addB = newVector.add(b);
		return newVector;
	}
	
	public static Vector subtract(Vector a, Vector b) {
		Vector newVector = new Vector(0,0);
		boolean addA = newVector.add(a);
		boolean addB = newVector.subtract(b);
		return newVector;
	}
	
	public void multiply(double m) {
		this.setComponents(this.getxComponent()*m, this.getyComponent()*m);
	}
	
	public void divide(double d) {
		if(d==0) {
			log.println("divide by zero: ", ERROR);
			this.setComponents(0, 0);
		}else {
			this.setComponents(this.getxComponent()/d, this.getyComponent()/d);
		}
		
	}
	
	public void normalize() {
		if(this.getxComponent()!=0&&this.getyComponent()!=0) {
			this.scale(1);
		}
	} 
	
	public void scale(double newlength) {
		if(this.getxComponent()==0&&this.getyComponent()==0) {
			return; 
		}
		double scale = newlength/this.getLength();
		this.setComponents(this.getxComponent()*scale, this.getyComponent()*scale);
	}
	
	public void invert() {
		this.setComponents(-this.getxComponent(), -this.getyComponent());
	}
	
	public boolean limit(double min, double max) {
		double oldLength = this.getLength();
		double scale;
		if (oldLength<min) {
			scale = min/oldLength;
			this.setComponents(this.getxComponent()*scale, this.getyComponent()*scale);
			return true;
		}else if (oldLength>max) {
			scale = max/oldLength;
			this.setComponents(this.getxComponent()*scale, this.getyComponent()*scale);
			return true;
		}else {
			return false;
		}
	}
	
	public void rotate(double radians) {
		this.setComponents(this.xComponent*Math.cos(radians)-this.yComponent*Math.sin(radians), 
				this.xComponent*Math.sin(radians)+this.yComponent*Math.cos(radians));
	}
	
	public void setAngle(double newAngle) {
		Vector newVector = new Vector(newAngle, this.getLength(), true);
		this.setComponents(newVector.getxComponent(), newVector.getyComponent());
	}
	
	public double getAngle() {
		double angle;
		if(this.getxComponent()>0) {
			angle = Math.atan(this.getyComponent()/this.getxComponent());
		}else if(this.getxComponent()<0) {
			angle = Math.atan(this.getyComponent()/this.getxComponent())+Math.PI;
		}else if (this.getyComponent()>0) {
			angle = Math.PI/2;
		}else if (this.getyComponent()<0) {
			angle = -Math.PI/2;
		}else {
			angle = 0;
		}
		return angle;
	}
	
	public boolean isEqual(Vector other) {
		if (utils.Utils.round(this.getxComponent())==utils.Utils.round(other.getxComponent()) && utils.Utils.round(this.getyComponent())==utils.Utils.round(other.getyComponent()))
			return true;
		return false;
	}
	
	public Vector perpendicular() {
		Vector pVector = new Vector(-this.getyComponent(), this.getxComponent());
		return pVector;
	}
	
	public double dotProduct(Vector other) {
		return this.getxComponent()*other.getxComponent()+this.getyComponent()*other.getyComponent();
	}
	
	@Override
	public String toString() {
		return "Vector [xComponent=" + xComponent + ", yComponent=" + yComponent + "]";
	}
}
