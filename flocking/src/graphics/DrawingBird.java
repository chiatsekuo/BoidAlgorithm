package graphics;

import java.util.ArrayList;

import boid.Bird;
import boid.BoidRule;
import vector.Vector;

public class DrawingBird extends Bird implements Drawable {

	private ArrayList<Vector> drawLines = new ArrayList<Vector>();
	private ArrayList<Drawable> Drawings = new ArrayList<Drawable>();
	private Colors boidcolor;
	public DrawingBird(int x, int y, Vector vel, BoidRule r,Colors c) {
		super(x, y, vel, r);
		boidcolor=c;
		for(int i=0;i<4;i++) {
			drawLines.add(new Vector(0,0));
		}
		rules.drawingComponents(Drawings);
	}
	
	
	
	public ArrayList<Vector> getlines(){
		return drawLines;
	}
	public ArrayList<Drawable> getDrawables(){
		return Drawings;
	}

	public void updateDrawLines() {
		drawLines.get(0).copy(velocity);
		drawLines.get(0).scale(10);
		
		drawLines.get(1).copy(velocity);
		drawLines.get(1).scale(-10);
		drawLines.get(1).rotate(.5);
		
		drawLines.get(2).setZero();
		
		drawLines.get(3).copy(velocity);
		drawLines.get(3).scale(-10);
		drawLines.get(3).rotate(-.5);
		
		
	}
	public Colors getColor() {
		return boidcolor;
	}
	public boolean movement() {
		super.movement();
		updateDrawLines();
		return false;
	}
	@Override
	public Vector getCenter() {
		return super.getPositionVector();
	}

}
