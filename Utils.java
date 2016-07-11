package bugDector;

public class Utils {
	public static boolean isNullOrEmptyString(String str){
		if(str==null||str.length()==0){
			return true;
		}
		return false;
	}
}
