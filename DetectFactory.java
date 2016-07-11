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
		Log.info("©����Ϣ");
		Log.info("©���ֲ�");

		Log.info("©������");
/*		for(DetectorInterface detector: detectors){
			Log.info("�����--"+detector.getDetectItem());
			Log.info("©������--"+detector.getDescription());
			Log.info("�޸�����--"+detector.getBugAdvice());
			if(detector.getBugPosition()==null){
				continue;
			}
			Log.info("©������λ��--");
			for(String position:detector.getBugPosition()){
				Log.info(position);
			}
		}*/
		
		for(DetectResult result:DetectResults.getInstance().getResults()){
			Log.info("�����--"+result.detectItem);
			Log.info("©������--"+result.description);
			Log.info("�޸�����--"+result.advice);
			if(result.positions==null){
				continue;
			}
			Log.info("©������λ��--");
			for(String position:result.positions){
				Log.info(position);
			}
		}
	}
}
