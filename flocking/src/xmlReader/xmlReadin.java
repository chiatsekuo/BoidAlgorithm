package xmlReader;

import shape.*;
import utils.Utils;
import vector.Vector;

import org.w3c.dom.*;

import graphics.Screen;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

/*
class xmlReadin{

	public xmlReadin(String fileLocation) //inialize the read in
	
	public ArrayList<Shape> getEnviroment() get a list of all shape to be draw and used as obstacles
	
	public ArrayList<BoidStructure> getAllboids() //get a list of shapes
	
	public int getWidth();					//to be used in the screensize
	public int getHeight();
	
	private BoidStructure mk_boid (xml element); interal seperation
	private shape mk_rect (xml element);
	private shape mk_polygon (xml element);
	private shape mk_circle (xml element);




}
 */


public class xmlReadin {
	private ArrayList<Shape> listofshapes = new ArrayList<Shape>();
	private ArrayList<BoidStructure> listofboids = new ArrayList<BoidStructure>();
	private int width;
	private int height;
	
	public static void main(String args[]) {
		System.out.println("xml");
		xmlReadin xml1 = new xmlReadin("CS3343.tmx");
		Utils.SCREEN_WIDTH = 750;
		Utils.SCREEN_HIEGHT = 500;
		Screen Window = Screen.initScreen(Utils.SCREEN_WIDTH,Utils.SCREEN_HIEGHT);
		
		for(int i = 0; i< xml1.listofshapes.size(); i++) {
			Window.getToDraw().add(xml1.listofshapes.get(i));
		}
		Window.updateFrameBuffer();
		Window.repaint();
		
	}
	
	public xmlReadin(String fileLocation) {
		try {
			File inputFile = new File(fileLocation);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        
	        NodeList screen = doc.getElementsByTagName("map");
	        Element e = (Element) screen.item(0);
	        int w = Integer.parseInt(e.getAttribute("width"));
	        int h = Integer.parseInt(e.getAttribute("height"));
	        int tilew = Integer.parseInt(e.getAttribute("tilewidth"));
	        int tileh = Integer.parseInt(e.getAttribute("tileheight"));
	        this.width = w*tilew;
	        this.height = h*tileh;
	        // should read width and height times them by 50 to get the width and height pixel
	        
	        NodeList nList = doc.getElementsByTagName("object");
	        for (int temp = 0; temp < nList.getLength(); temp++) { 
	        	Node nNode = nList.item(temp);
	        	//add all the element in the object group to the listofshapes
	        	if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	        		Element eElement = (Element) nNode;
	        		if(eElement.getAttribute("name").equals("rectangle")) {
	        			double x = Double.parseDouble(eElement.getAttribute("x")); 
	        			double y = Double.parseDouble(eElement.getAttribute("y"));
	        			int width = Integer.parseInt(eElement.getAttribute("width"));
	        			int height = Integer.parseInt(eElement.getAttribute("height"));
	        			Vector point = new Vector(x,y);
	        			point.add(new Vector(width/2, height/2));
	        			Rectangle rect = new Rectangle(point, width, height);
	        			listofshapes.add(rect);
	        			
	        		} else if(eElement.getAttribute("name").equals("circle")) {
	        			double x = Double.parseDouble(eElement.getAttribute("x")); 
	        			double y = Double.parseDouble(eElement.getAttribute("y"));
	        			Vector point = new Vector(x,y);
	        			int radius = Integer.parseInt(eElement.getAttribute("height"))/2;
	        			point.add(new Vector(radius, radius));
	        			Circle cir = new Circle(point, radius);
	        			listofshapes.add(cir);
	        			
	        		} else if(eElement.getAttribute("name").equals("polygon")) {
	        			NodeList PolyList = eElement.getElementsByTagName("polygon");
	        			Node node = PolyList.item(0);
	        			Element ele = (Element) node;
	        			String templist = ele.getAttribute("points");
	        			String[] pointsList = templist.split(" ");
	        			ArrayList<Vector> listofvec = new ArrayList<Vector>();
	        			double x = Double.parseDouble(eElement.getAttribute("x")); 
	        			double y = Double.parseDouble(eElement.getAttribute("y"));
	        			double totalX = 0, totalY = 0;
	        			
	        			for(int i = 0; i <pointsList.length; i++) {
	        				String[] xandy = pointsList[i].split(",");
	        				double processedX = x + Double.parseDouble(xandy[0]);
	        				double processedY = y + Double.parseDouble(xandy[1]);
	        				Vector actualpoints = new Vector(processedX, processedY);
	        				totalX += processedX;
	        				totalY += processedY;
	        				listofvec.add(actualpoints);
	        			}
	        			Vector center = new Vector(totalX/5, totalY/5);
	        			for(int j = 0; j < listofvec.size(); j++) {
	        				listofvec.get(j).subtract(center);
	        			}
	        			Polygon poly = new Polygon(center, listofvec);
	        			listofshapes.add(poly);
	        		}else if(eElement.getAttribute("name").equals("boid")) {
	        			NodeList PolyList = eElement.getElementsByTagName("polyline");
	        			Node node = PolyList.item(0);
	        			String templist = ((Element)node).getAttribute("points");
	        			NodeList props = eElement.getElementsByTagName("property");
	        			String type="Bird";
	        			for(int i=0;i<props.getLength();i++) {
	        				if(((Element)props.item(i)).getAttribute("name").equals("type")) {
	        					type=((Element)props.item(i)).getAttribute("value");
	        				}
	        			}
	        			Element ele = (Element) node;
	        			String[] pointsList = templist.split(" ");
	        			int x = Integer.parseInt(eElement.getAttribute("x")); 
	        			int y = Integer.parseInt(eElement.getAttribute("y"));
	        				String[] xandy = pointsList[1].split(",");
	        				int vx =  Integer.parseInt(xandy[0]);
	        				int vy =  Integer.parseInt(xandy[1]);
	        			listofboids.add(new BoidStructure(type, x, y, vx, vy));
	        			
	        		}
	        		
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Shape> getEnvironment() {
		return this.listofshapes;
		
	}
	
	public ArrayList<BoidStructure> getAllboids() {
		return this.listofboids;
		
	}
	
	public int getWidth() {
		return this.width;
		
	}
	
	public int getHeight() {
		return this.height;
		
	}
}
