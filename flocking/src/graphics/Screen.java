package graphics;
/*
 * 
 * unimplemented
 * class Screen{
 * 		
 * 		
 * 		public Screen(int width, int height); // on defult constructor just pick a size
 * 		public list toDraw(); 					// get list for you draw to
 *      public updateFrameBuffer(): 			// draws objects to the frame buffer
 *      private drawDrawable(Drawable);			// draw a single shape
 * 		public getWidth();
 * 		public getHeight();
 * 		draw_center(vector);						//draw a dot at the defined center
 * 		draw_polygon(list vector,vector)			//draw the vector relative to center
 * 		draw_circle(vector center, double radius)	//draw the circle at the center
 * 		draw_rectangle(vector center, int width,int height) // draw a rectangle a center point
 *		setColor(enm color)							//decode enm to actual color 
 *		getviewpoint()						//give a vector that can be changed to adust the view
 * 		setZoom/getZoom 					//set the zoom used in drawing to scale the images
 * 		paint								//overide the jframe to draw the frame buffer  --Alert! call with repaint()
 * }
 * 
 * 
 * 
 * 
 * 
 * */
import javax.swing.*;
import shape.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import vector.Vector;
import utils.Log;
import utils.Utils;

public class Screen extends JFrame implements ComponentListener{

	//add width and height
	private BufferedImage FrameBuffer;

	private static Screen instance;
	
	public static Screen initScreen(int width, int height) {
		instance = new Screen(width, height);
		return getScreen();
	}
	
	public static Screen getScreen() {
		return instance;
	}
	
	private ArrayList<Drawable> toDraw = new ArrayList<Drawable>();
	private double zoom;
	private Vector viewPoint;
	private static Log log = utils.Log.getLog();
	private static int DEBUG = Log.GRAPHICS+log.DEBUG;
	private boolean clearall = true;
	public void componentResized(ComponentEvent ce) {
	    int height = this.getHeight();
	    int width = this.getWidth();
	    log.println("Screen: ["+width+","+height+"]",DEBUG);
	    clearall=true;
	  };
	
	private Screen(int width, int height){
		viewPoint=new Vector(Utils.SCREEN_WIDTH/2,Utils.SCREEN_HIEGHT/2);
		setVisible(true);
		setResizable(true);
		setSize(width,height);
		getContentPane().addComponentListener(this);
		FrameBuffer =  new BufferedImage(Utils.SCREEN_WIDTH, Utils.SCREEN_WIDTH, BufferedImage.TYPE_INT_ARGB);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		zoom=1;
		validate();
	}
	
	//it will go through arraylist and draw each
	public void updateFrameBuffer() {
		long timestamp = System.currentTimeMillis();
		//Graphics g = RotationBuffer.createGraphics();
		Graphics g = FrameBuffer.createGraphics();
		g.clearRect(0,0,Utils.SCREEN_WIDTH,Utils.SCREEN_HIEGHT);
		//rotationG.clearRect(0,0,Utils.SCREEN_WIDTH,Utils.SCREEN_HIEGHT);
		g.setColor(Color.BLUE);
		for(int i=0;i<toDraw.size();i++) {
			vector.Vector c = new Vector(Utils.SCREEN_WIDTH/2,Utils.SCREEN_HIEGHT/2);
			c.subtract(viewPoint);
			draw(toDraw.get(i),g,c);
		}
		timestamp = System.currentTimeMillis() - timestamp;
		log.println("updateFrame: " + timestamp, DEBUG);
		//shift image by view point
		
		//rotationG.drawImage(RotationBuffer, Utils.SCREEN_WIDTH/2 -(int)Math.round(viewPoint.getxComponent()), Utils.SCREEN_HIEGHT/2 -(int)Math.round(viewPoint.getyComponent()), this);
		
		//draw a part to the left or the right

		

		
		
		
		
		//rotationG.drawImage(RotationBuffer, this.getWidth()/2 -(int)Math.round(viewPoint.getxComponent()), -this.getHeight()/2 -(int)Math.round(viewPoint.getyComponent()), this);
		
		
	}
	public void paint(Graphics g) {
		long timestamp = System.currentTimeMillis();
		if(clearall) {
			clearall=false;
			g.clearRect(0,0,this.getWidth(),this.getHeight());
		}
		//g.setColor(Color.gray);
		int heightOffset = 0;
		int widthOffset = 0;
		widthOffset=(this.getWidth() - Utils.SCREEN_WIDTH)/2;
		heightOffset=(this.getHeight() - Utils.SCREEN_HIEGHT)/2;
		//g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(FrameBuffer, widthOffset + (int)(Utils.SCREEN_WIDTH*(1-zoom)), heightOffset + (int)(Utils.SCREEN_HIEGHT*(1-zoom)),
			widthOffset+(int)(Utils.SCREEN_WIDTH*zoom),  heightOffset+(int)(Utils.SCREEN_HIEGHT*zoom),
			0,0,
			(int) (Utils.SCREEN_WIDTH), (int) (Utils.SCREEN_HIEGHT), this);
		//g.drawImage(FrameBuffer, 0, 0, this);
		
		timestamp = System.currentTimeMillis() - timestamp;
		log.println("printTime: " + timestamp, DEBUG);
	}
	
	//draw a shape in arraylist
	public void draw(Drawable shape, Graphics g, Vector center) {
		if(shape.getColor()!= Colors.NOCOLOR){
			setColor(g,shape.getColor());
		}
		center.add(shape.getCenter());
		
		//adjust center for the edge of screen
		if(center.getxComponent()<0) {
			center.setxComponent(center.getxComponent()+utils.Utils.SCREEN_WIDTH);
		}else if(center.getxComponent()>utils.Utils.SCREEN_WIDTH) {
			center.setxComponent(center.getxComponent()-utils.Utils.SCREEN_WIDTH);
		}
		if(center.getyComponent()<0) {
			center.setyComponent(center.getyComponent()+utils.Utils.SCREEN_HIEGHT);
		}else if(center.getyComponent()>utils.Utils.SCREEN_HIEGHT) {
			center.setyComponent(center.getyComponent()-utils.Utils.SCREEN_HIEGHT);
		}
		
		if(shape.getRadius()>0) {
			this.draw_circle(center, shape.getRadius(), g);
		}
		if(shape.getWidth()>0 && shape.getHeight()>0) {
			this.draw_rectangle(center, shape.getWidth(), shape.getHeight(), g);
		}
		if(shape.getlines()!=null) {
			this.draw_polygon(center, shape.getlines(), g);
		}
		
		//draw_center(center, g);
		
		if(shape.getDrawables()!=null) {
			for(int i=0;i<shape.getDrawables().size();i++) {
				Vector subCenter = new Vector(0,0);
				subCenter.copy(center);
				draw(shape.getDrawables().get(i),g,subCenter);
			}
		}
	}

	public void setColor(Graphics g, Colors color) {
		
		switch(color) {
			case RED:
				g.setColor(Color.RED);
				break;
			case BLUE:
				g.setColor(Color.BLUE);
				break;
			case YELLOW:
				g.setColor(Color.YELLOW);
				break;
			case GREEN:
				g.setColor(Color.GREEN);
				break;
			case ORANGE:
				g.setColor(Color.ORANGE);
				break;
			case PURPLE:
				g.setColor(Color.MAGENTA);
				break;
			case GRAY:
				g.setColor(Color.GRAY);
				break;
			case WHITE:
				g.setColor(Color.WHITE);
				break;
			case BLACK:
				g.setColor(Color.BLACK);
				break;
			case CYAN:
				g.setColor(Color.CYAN);
				break;
			case DARK_GRAY:
				g.setColor(Color.DARK_GRAY);
				break;
			case PINK:
				g.setColor(Color.PINK);
				break;
			default:
				g.setColor(Color.BLACK);
		}
	}
	public ArrayList<Drawable> getToDraw() {
		return toDraw;
	}

	protected void setToDraw(ArrayList<Drawable> toDraw) {
		this.toDraw = toDraw;
	}
	
	public void draw_circle(Vector center, double radius, Graphics g) {
		int x = (int) center.getxComponent();
		int y = (int) center.getyComponent();
		x -= radius;
		y -= radius;
		g.drawOval(x, y, (int) radius*2, (int) radius*2);
	}
	
	public void draw_center(Vector center, Graphics g) {
		int x = (int) center.getxComponent();
		int y = (int) center.getyComponent();
		x -= 5;
		y -= 5;
		g.drawOval(x, y, 10, 10);
	}
	
	public void draw_rectangle(Vector center, double width, double height, Graphics g) {
		int x = (int) center.getxComponent();
		int y = (int) center.getyComponent();
		x -= width/2;
		y -= height/2;
		g.drawRect(x, y, (int) width, (int) height);
	}
	
	public void draw_polygon(Vector center, ArrayList<Vector> list, Graphics g) {
		for(int i = 0; i < list.size(); i++) {
			Vector point1, point2;
			point1 = new Vector(list.get(i).getxComponent() ,list.get(i).getyComponent());
			point1 = Vector.add(point1, center);
			if (i == list.size()-1) {
				point2 = new Vector(list.get(0).getxComponent() ,list.get(0).getyComponent());
				point2 = Vector.add(point2, center);
			} else {
				point2 = new Vector(list.get(i+1).getxComponent() ,list.get(i+1).getyComponent());
				point2 = Vector.add(point2, center);
			}
			g.drawLine((int) point1.getxComponent(),(int) point1.getyComponent(),(int) point2.getxComponent(),(int) point2.getyComponent());
		}
	}

	public Vector getViewPoint() {
		return viewPoint;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
