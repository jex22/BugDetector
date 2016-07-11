package bugDector;

import java.util.ArrayList;
import java.util.List;

import soot.jimple.infoflow.android.manifest.ProcessManifest;

public class DetectFactory {
	private static DetectFactory factory = null;
	private List<DetectorInterface> detectors = null;

	public DetectFactory(){
		
	}
	
	public static DetectFactory getInstance(){
		if(factory==null){
			factory = new DetectFactory();
		}
		return factory;
	}
	
	public void exec(){
		detectors = new ArrayList<DetectorInterface>();
		
		if(DetectConfiguartion.providerExportedDetect){
			detectors.add(new ProviderExportedDetector());
		}
		
		for(DetectorInterface detector: detectors){
			detector.exec();
		}
	}
	
	public void printResults(){
		Log.info("漏洞信息");
		Log.info("漏洞分布");

		Log.info("漏洞详情");
/*		for(DetectorInterface detector: detectors){
			Log.info("检测项--"+detector.getDetectItem());
			Log.info("漏洞描述--"+detector.getDescription());
			Log.info("修复建议--"+detector.getBugAdvice());
			if(detector.getBugPosition()==null){
				continue;
			}
			Log.info("漏洞所处位置--");
			for(String position:detector.getBugPosition()){
				Log.info(position);
			}
		}*/
		
		for(DetectResult result:DetectResults.getInstance().getResults()){
			Log.info("检测项--"+result.detectItem);
			Log.info("漏洞描述--"+result.description);
			Log.info("修复建议--"+result.advice);
			if(result.positions==null){
				continue;
			}
			Log.info("漏洞所处位置--");
			for(String position:result.positions){
				Log.info(position);
			}
		}
	}
}
