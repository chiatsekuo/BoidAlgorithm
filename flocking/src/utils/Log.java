package utils;
/*
 * 
 * 
 * this class replaces the system out
 * you can add flags and then filter msgs by flag
 * examples in utilsTesting
 * 
 * class log{
 * 
 * 		type can by char int double object string
 * 		flag list:
 * 	ok		DEBUG
 * 	ok		ERROR
 * 	ok		EVENT
 * 	ok		GRAPHICS
 * 	ok		BOIDS
 * 	ok		RAY
 * 	ok		SHAPE
 * 	ok		UTIL
 * 	ok		VECTOR
 * 	ok		ALL
 * 	ok		NONE
 * 		add flags in any order to set them
 * 
 * 	ok	perfectFilter
 * 			set and get
 * 		when true items must perfectly match the filter normally they need to match one
 * 
 * 	ok	filter
 * 			set and get
 * 		the filter flags are compared to
 * 
 * 	ok	print(type); 				// will assume debugflag
 * 	ok	print(type, int flag) 		// will assume flags
 * 			//adds a new line
 * 	ok	println(type); 				// will assume debugflag
 * 	ok	println(type, int flag) 	//will assume flags
 * }
 * 
 * 
 * */

public class Log {
	
	public static final int DEBUG = 1;//not show to user at out put
	public static final int ERROR = 2;//being related to an error
	public static final int EVENT = 4;//being related to an event
	public static final int GRAPHICS = 8; // related to grapics
	public static final int BOIDS = 16; // related to boids
	public static final int RAY = 32; // related to ray
	public static final int SHAPE = 64; // related to shape
	public static final int UTIL = 128; // related to util
	public static final int VECTOR = 256; // related to util
	public static final int TIMER = 512; // related to util
	public static final int ALL = DEBUG+ERROR+EVENT+GRAPHICS+BOIDS+RAY+SHAPE+UTIL+VECTOR;
	public static final int NONE = 0;
	
	
	private static final Log instance = new Log();
	
	private int filter = DEBUG+ERROR;
	public int getFilter() {
		return filter;
	}
	public void setFilter(int oFilter) {
		this.filter = oFilter;
	}



	boolean perfectFilter = false;
	public boolean isPerfectFilter() {
		return perfectFilter;
	}

	public void setPerfectFilter(boolean perfectFilter) {
		this.perfectFilter = perfectFilter;
	}

	private Log() {}
	
	public static Log getLog() {
		return instance;
	}
	private boolean valid(int flags) {
		if(perfectFilter) {
			if((filter^flags) == 0) {
				return true;
			}
			return false;
		}else {
			if((filter&flags)!=0) {
				return true;
			}
			return false;
		}
	}
	public boolean print(String s,int flags) {
		if(valid(flags)) {
			System.out.print(s);
			return true;
		}
		else {
			return false;
		}
	}
	public boolean println(String s,int flags) {
		if(valid(flags)) {
			System.out.println(s);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean print(String s) {
		return print(s,DEBUG);
	}
	public boolean println(String s) {
		return println(s,DEBUG);
	}
	
	
	public boolean print(boolean b) {
		return print(String.valueOf(b),DEBUG);
	}
	public boolean print(int b) {
		return print(String.valueOf(b),DEBUG);
	}
	public boolean print(char b) {
		return print(String.valueOf(b),DEBUG);
	}
	public boolean print(double b) {
		return print(String.valueOf(b),DEBUG);
	}
	public boolean print(Object b) {
		return print(String.valueOf(b),DEBUG);
	}
	public boolean print(boolean b,int flags) {
		return print(String.valueOf(b),flags);
	}
	public boolean print(int b,int flags) {
		return print(String.valueOf(b),flags);
	}
	public boolean print(double b,int flags) {
		return print(String.valueOf(b),flags);
	}
	public boolean print(char b,int flags) {
		return print(String.valueOf(b),flags);
	}
	public boolean print(Object b,int flags) {
		return print(String.valueOf(b),flags);
	}
	public boolean println(boolean b) {
		return println(String.valueOf(b),DEBUG);
	}
	public boolean println(int b) {
		return println(String.valueOf(b),DEBUG);
	}
	public boolean println(char b) {
		return println(String.valueOf(b),DEBUG);
	}
	public boolean println(double b) {
		return println(String.valueOf(b),DEBUG);
	}
	public boolean println(Object b) {
		return println(String.valueOf(b),DEBUG);
	}
	public boolean println(boolean b,int flags) {
		return println(String.valueOf(b),flags);
	}
	public boolean println(int b,int flags) {
		return println(String.valueOf(b),flags);
	}
	public boolean println(double b,int flags) {
		return println(String.valueOf(b),flags);
	}
	public boolean println(char b,int flags) {
		return println(String.valueOf(b),flags);
	}
	public boolean println(Object b,int flags) {
		return println(String.valueOf(b),flags);
	}
	
	
}
