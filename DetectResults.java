package bugDector;

import java.util.ArrayList;
import java.util.List;

public class DetectResults {
	private static DetectResults detectResults;
	private List<DetectResult> results;
	public DetectResults(){
		results = new ArrayList<DetectResult>();
	}
	
	public static DetectResults getInstance(){
		if(detectResults==null){
			detectResults = new DetectResults();
		}
		return detectResults;
	}
	
	public void setResult(DetectResult result){
		results.add(result);
	}
	
	public List<DetectResult> getResults(){
		return results;
	}
	
	public void addResult(DetectResult result){
		if(result!=null){
			results.add(result);
		}
	}
	
}
