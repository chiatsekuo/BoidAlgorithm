package xmlReader;

public class BoidStructure {
	
	
	String type;
	int x;
	int y;
	int vx;
	int vy;
	
	public BoidStructure(String type, int x, int y,int vx,int vy) {
		this.type=type;
		this.x=x;
		this.y=y;
		this.vx=vx;
		this.vy=vy;
	}

	public String getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}

}