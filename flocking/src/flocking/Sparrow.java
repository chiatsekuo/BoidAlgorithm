package flocking;

import boid.Alignment;
import boid.BoidRule;
import boid.BoidRuleBase;
import boid.Cohesion;
import boid.Separation;
import boid.Sight;
import graphics.Colors;
import graphics.DrawingBird;
import graphics.DrawingRule;
import graphics.DrawingSight;
import vector.Vector;

public class Sparrow extends DrawingBird {

	
	private static BoidRule DrawingBasic(boolean draw){
		BoidRule end = new BoidRuleBase();
		end = new Alignment(end,1.5);
		if(draw) {
			end = new DrawingRule(end,Colors.YELLOW);
		}
		end = new Separation(end,1);
		if(draw) {
			end = new DrawingRule(end,Colors.RED);
		}
		end = new Cohesion(end,1.5);
		if(draw) {
			end = new DrawingRule(end,Colors.GREEN);
		}
		if(draw) {
			end = new DrawingSight(end);
		}else {
			end = new Sight(end);
		}
		return end;
	}
	
	public Sparrow(int x, int y, Vector vel,boolean draw) {
		super(x, y, vel, DrawingBasic(draw), Colors.GREEN);
		// TODO Auto-generated constructor stub
	}

}
