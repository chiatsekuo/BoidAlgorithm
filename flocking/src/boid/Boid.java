package boid;

/*
interface Boid{
ok	public boolean movement();//calulates the boids movment to the next frame
		in Bird
ok	public boolean preBehaviour();
		in Bird
ok	public boolean behaviour();			//caluates the acceleration in the 
		in Bird
ok	public void seeBoid(Boid other);		//when two boids can see each other each one needs to see each other
		in Bird
ok	public Vector getVelocityVector();		//get the boids velocity vector
		in Bird
ok	public Vector getPositionVector();		//get the boids position vector
		in Bird
ok	public double sightRange();				//get sight range
		in Bird
ok	public static double distance(Boid a,Boid b) // return the distance between two boids
er	public static boolean sight(Boid a, Boid b) // detect if two boids 
ok	public default Vector getCenter() // same as return position
ok	public default boolean prey() //detect if someone is a hostile
ok	getColor set a default color

}
 */


import vector.Vector;


import graphics.Colors;
import graphics.Drawable;
import utils.Utils;
public interface Boid {
	
	public boolean movement();//calulates the boids movment to the next frame
	public boolean preBehaviour();
	public boolean behaviour();			//caluates the acceleration in the 
	public void seeBoid(Boid other);		//when two boids can see each other each one needs to see each other
	public Vector getVelocityVector();		//get the boids velocity vector
	public Vector getPositionVector();		//get the boids position vector
	public double sightRange();				//get sight range
	public static double distance(Boid a,Boid b) {	
		return utils.Utils.distance(a.getPositionVector(),b.getPositionVector());
	}
	public static boolean sight(Boid a, Boid b) {//need to include the edges
		boolean inSightRange = false;
		
		double x = 0;
		double y = 0;
		//if the distance is over the edge
		if(a.getPositionVector().getxComponent() - b.getPositionVector().getxComponent() >Utils.SCREEN_WIDTH/2) {
			x+=Utils.SCREEN_WIDTH;
		}else if(a.getPositionVector().getxComponent() - b.getPositionVector().getxComponent() < -Utils.SCREEN_WIDTH/2) {
			x-=Utils.SCREEN_WIDTH;
		}
		
		if(a.getPositionVector().getyComponent() - b.getPositionVector().getyComponent() >Utils.SCREEN_HIEGHT/2) {
			y+=Utils.SCREEN_HIEGHT;
		}else if(a.getPositionVector().getyComponent() - b.getPositionVector().getyComponent() < -Utils.SCREEN_HIEGHT/2) {
			y-=Utils.SCREEN_HIEGHT;
		}
		//shift the bird
		b.getPositionVector().setxComponent(b.getPositionVector().getxComponent()+x);
		b.getPositionVector().setyComponent(b.getPositionVector().getyComponent()+y);
		
		double d = Boid.distance(a, b);
		if(d<a.sightRange()) {
			a.seeBoid(b);
			inSightRange = true;
		}
		//shift the bird back
		b.getPositionVector().setxComponent(b.getPositionVector().getxComponent()-x);
		b.getPositionVector().setyComponent(b.getPositionVector().getyComponent()-y);
		
		//shift the other bird
		a.getPositionVector().setxComponent(a.getPositionVector().getxComponent()-x);
		a.getPositionVector().setyComponent(a.getPositionVector().getyComponent()-y);
		d = Boid.distance(a, b);
		if(d<b.sightRange()) {
			b.seeBoid(a);
			inSightRange = true;
		}
		a.getPositionVector().setxComponent(a.getPositionVector().getxComponent()+x);
		a.getPositionVector().setyComponent(a.getPositionVector().getyComponent()+y);
		return inSightRange;
//		if(!a.prey()||!b.prey()) {//if exist
//			if(d<a.sightRange()*2) {
//				a.seeBoid(b);
//			}
//			if(d<b.sightRange()*2) {
//				b.seeBoid(a);
//			}
//		}
	}

}
