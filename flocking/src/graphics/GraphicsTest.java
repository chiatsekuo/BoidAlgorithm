package graphics;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import boid.Bird;
import boid.Boid;
import flocking.Pigeon;
import ray.Ray;
import shape.*;
import vector.Vector;
public class GraphicsTest {
	
	
	private static final int NUMBER_OF_BIRDS = 2000;
	private static final int SPEED_RANGE = 20;

	public static void main(String[] args) {
		
		System.out.println("Start Graphics Test");
		Screen test = Screen.initScreen(1000, 500);

		ArrayList<Vector> list = new ArrayList<Vector>();
		list.add(new Vector(0,30));
		list.add(new Vector(-30,-15));
		list.add(new Vector(0,0));
		list.add(new Vector(30,-15));

		test.getToDraw().add(new Circle(new Vector(100,300) , 50));
		test.getToDraw().add(new Rectangle(new Vector(300,100), 50, 70));
		test.getToDraw().add(new Polygon(new Vector(300,300), list));
		
		
		Random rand = new Random();
		Boid a = new Pigeon(0,0,new Vector(1,1),true);
		for(int i=0;i<NUMBER_OF_BIRDS;i++) {
			int xpos = rand.nextInt(utils.Utils.SCREEN_WIDTH);
			int ypos = rand.nextInt(utils.Utils.SCREEN_HIEGHT);
			double xcomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			double ycomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			new Pigeon(xpos,ypos,new Vector(xcomp,ycomp),true);
		}
		
		
		//add shapes
		ArrayList<Circle> env = new ArrayList<Circle>();
		//env.add(new Circle(new Vector(200,200),50));
		
		
		graphics.Screen window = Screen.getScreen();
		for(int i=0;i<Bird.getAllBirds().size();i++) {
			window.getToDraw().add((Drawable)Bird.getAllBirds().get(i));
		}
		for(int i=0;i<env.size();i++) {
			window.getToDraw().add(env.get(i));
			Ray.getRaydetectable().add(env.get(i));
		}
		window.setZoom(10);
		
		
		boolean done = false;
		while(!done) {
			
			
			for(int i=0;i<Bird.getAllBirds().size();i++) {
				Bird.getAllBirds().get(i).preBehaviour();
			}
			for(int i=0;i<Bird.getAllBirds().size()-1;i++) {
				for(int ii=i+1;ii<Bird.getAllBirds().size();ii++) {
					Boid.sight(Bird.getAllBirds().get(i), Bird.getAllBirds().get(ii));
				}
			}
			for(int i=0;i<Bird.getAllBirds().size();i++) {
				Bird.getAllBirds().get(i).behaviour();
			}
			for(int i=0;i<Bird.getAllBirds().size();i++) {
				Bird.getAllBirds().get(i).movement();
			}
			window.setZoom(window.getZoom()-0.001);
			//myLog.println(a, DEBUG_CODE);
			window.getViewPoint().copy(a.getPositionVector());
			window.updateFrameBuffer();
			window.repaint();
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				done = true;
			}
		}
		
	}
		

}
