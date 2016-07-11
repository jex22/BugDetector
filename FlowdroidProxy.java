package bugDector;


import soot.PackManager;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.io.IOException;
import java.util.Collections;

import org.xmlpull.v1.XmlPullParserException;

import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;

public class FlowdroidProxy {
    public static String apkPath = null;
    private static FlowdroidProxy proxy = null;
   
	public final String sourceAndSinksTxt = "sourcesAndSinks.txt";
	public FlowdroidProxy() {
		// TODO Auto-generated constructor stub
	}


	public static FlowdroidProxy getInstance(){
		if(proxy==null){
			proxy = new FlowdroidProxy();
		}
		return proxy;
	}
	
	public void setInfo(String apkPath){
		this.apkPath = apkPath;
		String androidJars = System.getenv("ANDROID_JARS");
		if (androidJars == null)
			androidJars = System.getProperty("ANDROID_JARS");
		if (androidJars == null)
			throw new RuntimeException("Android JAR dir not set");
		
		SetupApplication setupApplication = new SetupApplication(androidJars,apkPath);
		try{
            //计算APK的入口点，这一步导入的文件是Flowdroid进行污点分析的时候需要的，这里直接新建一个空文件即可
			setupApplication.calculateSourcesSinksEntrypoints(sourceAndSinksTxt);
        }catch(Exception e){
            e.printStackTrace();
        }
		
		Options.v().set_src_prec(Options.src_prec_apk);
        Options.v().set_process_dir(Collections.singletonList(apkPath));
        Options.v().set_force_android_jar(androidJars);
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
        Options.v().setPhaseOption("cg.spark verbose:true", "on");
        Scene.v().loadNecessaryClasses();

        SootMethod entryPoint = setupApplication.getEntryPointCreator().createDummyMain();
        Options.v().set_main_class(entryPoint.getSignature());
        Scene.v().setEntryPoints(Collections.singletonList(entryPoint));
        PackManager.v().runPacks();
	}
	
	
	
	public ProcessManifest getMainfestInfo(){
		ProcessManifest processMan = null;
		try {
			processMan = new ProcessManifest(apkPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processMan;
	}
	
	
}
