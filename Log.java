package bugDector;

public class Log {
	final static boolean info = true;
	final static boolean bug = true;
	final static boolean error = true;
	
	public static void info(String str){
		if(info){
			System.out.println("bugDetector----"+str);
		}
	}
	
	public static void bug(String str){
		if(info){
			System.out.println("bugDetector----"+str);
		}
	}
	
	public static void error(String str){
		if(info){
			System.out.println("bugDetector----"+str);
		}
	}
	
}