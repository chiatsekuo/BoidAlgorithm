package flocking;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import boid.Bird;
import boid.Boid;
import graphics.Drawable;
import graphics.Screen;
import graphics.Timer;
import ray.Ray;
import utils.Log;
import utils.Utils;
import vector.Vector;
import xmlReader.BoidStructure;
import xmlReader.xmlReadin;
public class Main {
	public static Screen Window;
	static int SPEED_RANGE = 5;
	public static boolean drawingDataLines=false;
	public static String mapName = "";
	public static int pigeonCt = 0;
	public static int sparrowCt = 0;
	public static int hawkCt = 0;
	public static boolean track = false;
	public static ArrayList<Drawable> mapobjects =   new ArrayList<Drawable>();
	
	//args
		//-map	filename		//set the file name to load if no map it loads blank
		//-track				//follow the first bird in the list
		//-draw					//draw data lines
		//-sparrow	number		//
		//-pigeon	number		//
		//-hawk		number		//

	public static void main(String[] args) {
		
		mapName = "";
		track = false;
		drawingDataLines = false;
		Log.getLog().setFilter(Log.NONE);
		int argindex = 	0;
		while(argindex<args.length) {
			String arg = args[argindex];
			if(arg.compareTo("-map")==0) {
				mapName = args[argindex+1];
				argindex+=2;
			}
			else if(arg.compareTo("-pigeon")==0) {
				try {
					pigeonCt = Integer.parseInt(args[argindex+1]);
				}catch (NumberFormatException e) {
					System.out.println("Illegal argument: '"+args[argindex+1]+"'");
					System.exit(1);
				}
				argindex+=2;
			}
			else if(arg.compareTo("-sparrow")==0) {
				try {
					sparrowCt = Integer.parseInt(args[argindex+1]);
				}catch (NumberFormatException e) {
					System.out.println("Illegal argument: '"+args[argindex+1]+"'");
					System.exit(1);
				}
				argindex+=2;
			}
			else if(arg.compareTo("-hawk")==0) {
				try {
					hawkCt = Integer.parseInt(args[argindex+1]);
				}catch (NumberFormatException e) {
					System.out.println("Illegal argument: '"+args[argindex+1]+"'");
					System.exit(1);
				}
				argindex+=2;
			}
			else if(arg.compareTo("-track")==0) {
				track=true;
				argindex+=1;
			}
			else if(arg.compareTo("-draw")==0) {
				drawingDataLines=true;
				argindex+=1;
			}
			else {
				System.out.println("Unknown Argument: '"+arg+"'");
				System.exit(1);
			}
		}
		
		if(mapName.compareTo("")!=0) {
			try {
				loadmap(mapName);
			}
			catch(Exception e){
				System.out.println("Could not load: '"+mapName+"'");
			}
		}
		
		RandomBoids(pigeonCt,sparrowCt,hawkCt);
		
		System.out.println("Welcome to Boids!");
		Window = graphics.Screen.initScreen(1000,800); 
		Timer clock = new Timer("Clock",20);
		
		
		
		//add birds to screen
		for(int i=0;i<Bird.getAllBirds().size();i++) {
			Window.getToDraw().add((Drawable)Bird.getAllBirds().get(i));
		}
		//add map objecst
		for(int i=0;i<mapobjects.size();i++) {
			Window.getToDraw().add(mapobjects.get(i));
		}
		
		//main loop
		boolean done = false;
		while(!done) {
			
			//clear for calculations
			for(int i=0;i<Bird.getAllBirds().size();i++) {
				Bird.getAllBirds().get(i).preBehaviour();
			}
			//see each bird
			for(int i=0;i<Bird.getAllBirds().size()-1;i++) {
				for(int ii=i+1;ii<Bird.getAllBirds().size();ii++) {
					Boid.sight(Bird.getAllBirds().get(i), Bird.getAllBirds().get(ii));
				}
			}
			//run formula
			for(int i=0;i<Bird.getAllBirds().size();i++) {
				Bird.getAllBirds().get(i).behaviour();
			}
			//move birds
			for(int i=0;i<Bird.getAllBirds().size();i++) {
				Bird.getAllBirds().get(i).movement();
			}
			
			//myLog.println(a, DEBUG_CODE);
			if(track) {
				Window.getViewPoint().copy(Bird.getAllBirds().get(0).getPositionVector());
			}
			Window.updateFrameBuffer();
			Window.repaint();
			try {
				clock.sleep();
			} catch (InterruptedException e) {
				e.printStackTrace();
				done = true;
			}
		}

	}
	public static void RandomBoids(int pigeonCt,int sparrowCt,int hawkCt) {
		Random rand = new Random();
		for(int i=0;i<pigeonCt;i++) {
			int xpos = rand.nextInt(utils.Utils.SCREEN_WIDTH);
			int ypos = rand.nextInt(utils.Utils.SCREEN_HIEGHT);
			double xcomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			double ycomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			new Pigeon(xpos,ypos,new Vector(xcomp,ycomp),drawingDataLines);
		}
		for(int i=0;i<sparrowCt;i++) {
			int xpos = rand.nextInt(utils.Utils.SCREEN_WIDTH);
			int ypos = rand.nextInt(utils.Utils.SCREEN_HIEGHT);
			double xcomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			double ycomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			new Sparrow(xpos,ypos,new Vector(xcomp,ycomp),drawingDataLines);
		}
		for(int i=0;i<hawkCt;i++) {
			int xpos = rand.nextInt(utils.Utils.SCREEN_WIDTH);
			int ypos = rand.nextInt(utils.Utils.SCREEN_HIEGHT);
			double xcomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			double ycomp = (SPEED_RANGE*2*rand.nextDouble())-SPEED_RANGE;
			new Hawk(xpos,ypos,new Vector(xcomp,ycomp),drawingDataLines);
		}
	}
	public static void loadmap(String mapName) {
		xmlReadin world = new xmlReadin(mapName);
		Utils.SCREEN_WIDTH = world.getWidth();
		Utils.SCREEN_HIEGHT = world.getHeight();
		
		//create boids
		for(int i=0;i<world.getAllboids().size();i++) {
			makeBoid(world.getAllboids().get(i));
		}
		
		// add shapes to ray and world
		for(int i=0;i<world.getEnvironment().size();i++) {
			mapobjects.add(world.getEnvironment().get(i));
			Ray.getRaydetectable().add(world.getEnvironment().get(i));
		}
	}
	public static void makeBoid(BoidStructure bird) {
		if(bird.getType().equals("pigeon")) {
			new Pigeon(bird.getX(),bird.getY(),new Vector(bird.getVx(),bird.getVy()),drawingDataLines);
		}
		if(bird.getType().equals("hawk")) {
			new Hawk(bird.getX(),bird.getY(),new Vector(bird.getVx(),bird.getVy()),drawingDataLines);
		}
		if(bird.getType().equals("sparrow")) {
			new Sparrow(bird.getX(),bird.getY(),new Vector(bird.getVx(),bird.getVy()),drawingDataLines);
		}
		
		
		
	}

}
