package bugDector;

import soot.DexClassProvider;
import soot.Scene;
import soot.jimple.toolkits.callgraph.CallGraph;

public class ApkInfo {
	private static ApkInfo apkInfo;
	FlowdroidProxy flowdroid = null;
	String apkPath;
	
	public ApkInfo(){
		
	}
	
	public static ApkInfo getInstance(){
		if(apkInfo==null){
			apkInfo = new ApkInfo();
		}
		return apkInfo;
	}
	
	public void setInfo(String apkPath){
		this.apkPath = apkPath;
		this.flowdroid = FlowdroidProxy.getInstance();
		this.flowdroid.setInfo(apkPath);
		//获取组件信息等
		ManifestInfo.getInstance().setInfo(flowdroid.getMainfestInfo());
		
		//获取certificate信息
		CertificateInfo.getInstance().setInfo(apkPath);
	}
	
	public String getApplicationName(){
		return ManifestInfo.getInstance().getApplicationName();
	}
	
	public String getVersion(){
		return ManifestInfo.getInstance().getVersionName();
	}
	
	public String getPackageName(){
		return ManifestInfo.getInstance().getPackageName();
	}
	
	public int getActivityCount(){
		return ManifestInfo.getInstance().getActivities().size();
	}
	
	public int getProviderCount(){
		return ManifestInfo.getInstance().getProviders().size();
	}
	
	public int getReceiverCount(){
		return ManifestInfo.getInstance().getReceivers().size();
	}
	
	public int getServiceCount(){
		return ManifestInfo.getInstance().getServices().size();
	}
	
	public int getUsesPermisionCount(){
		return ManifestInfo.getInstance().getPermissions().size();
	}
	
	public void get(){
		DexClassProvider.getDexFile(apkPath);
	}
	
	/**
	 private static void visit(CallGraph cg,SootMethod m){
	        //在soot中，函数的signature就是由该函数的类名，函数名，参数类型，以及返回值类型组成的字符串
	        String identifier = m.getSignature();
	        //记录是否已经处理过该点
	        visited.put(m.getSignature(), true);
	        //以函数的signature为label在图中添加该节点
	        cge.createNode(m.getSignature());
	        //获取调用该函数的函数
	        Iterator<MethodOrMethodContext> ptargets = new Targets(cg.edgesInto(m));
	        if(ptargets != null){
	            while(ptargets.hasNext())
	            {
	                SootMethod p = (SootMethod) ptargets.next();
	                if(p == null){
	                    System.out.println("p is null");
	                }
	                if(!visited.containsKey(p.getSignature())){
	                    visit(cg,p);
	                }
	            }
	        }
	        //获取该函数调用的函数
	        Iterator<MethodOrMethodContext> ctargets = new Targets(cg.edgesOutOf(m));
	        if(ctargets != null){
	            while(ctargets.hasNext())
	            {
	                SootMethod c = (SootMethod) ctargets.next();
	                if(c == null){
	                    System.out.println("c is null");
	                }
	                //将被调用的函数加入图中
	                cge.createNode(c.getSignature());
	                //添加一条指向该被调函数的边
	                cge.linkNodeByID(identifier, c.getSignature());
	                if(!visited.containsKey(c.getSignature())){
	                    //递归
	                    visit(cg,c);
	                }
	            }
	        }
	    }
	    ***/
}
