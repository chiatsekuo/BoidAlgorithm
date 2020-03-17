package utilsTesting;


import utils.*;


public class UtilTest {

	public static void main(String[] args) {
		System.out.println("Start Util Test");
		
		
		System.out.println("Start Log Test");
		Log testing = utils.Log.getLog();
		
		
		//single item perfect filter
		testing.setPerfectFilter(true);
		testing.setFilter(Log.DEBUG);
		testing.print("Driften in the --- ");
		testing.print("FAIL",Log.NONE);
		testing.print("FAIL",Log.ERROR);
		
		//two item perfect filter
		testing.setFilter(Log.DEBUG+Log.UTIL);
		testing.print("FAIL");
		testing.print("FAIL",0);
		testing.println("FAIL",Log.UTIL);
		testing.print("FAIL",Log.DEBUG);
		testing.println("Open waaater.",Log.DEBUG+Log.UTIL);

		
		
		//single item or filter
		testing.setPerfectFilter(false);
		testing.setFilter(Log.ERROR);
		testing.print("FAIL");
		testing.println("FAIL",Log.NONE);
		testing.print("Biger than a --- ",Log.ERROR+Log.VECTOR);
		testing.print("Man ",Log.ERROR);
		testing.print("Hole ",Log.ERROR+Log.DEBUG);
		testing.println("Cover.",Log.ERROR+Log.UTIL);
		
		//two item or filter
		
		testing.setFilter(Log.ERROR+Log.DEBUG);
		testing.print("Gentle giant ");
		testing.println("Fail",Log.NONE);
		testing.print("Of the ",Log.ERROR);
		testing.println("Seeeeaaaa --",Log.ERROR+Log.DEBUG);
		testing.print("How did you ",Log.ERROR+Log.UTIL);
		testing.print("Come to ",Log.DEBUG);
		testing.print("Fail",Log.UTIL);
		
		testing.setPerfectFilter(true);
		testing.setFilter(Log.NONE);
		testing.println("Be?",Log.NONE);
	}

}
