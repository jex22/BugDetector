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
		//��ȡ�����Ϣ��
		ManifestInfo.getInstance().setInfo(flowdroid.getMainfestInfo());
		
		//��ȡcertificate��Ϣ
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
	        //��soot�У�������signature�����ɸú��������������������������ͣ��Լ�����ֵ������ɵ��ַ���
	        String identifier = m.getSignature();
	        //��¼�Ƿ��Ѿ�������õ�
	        visited.put(m.getSignature(), true);
	        //�Ժ�����signatureΪlabel��ͼ����Ӹýڵ�
	        cge.createNode(m.getSignature());
	        //��ȡ���øú����ĺ���
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
	        //��ȡ�ú������õĺ���
	        Iterator<MethodOrMethodContext> ctargets = new Targets(cg.edgesOutOf(m));
	        if(ctargets != null){
	            while(ctargets.hasNext())
	            {
	                SootMethod c = (SootMethod) ctargets.next();
	                if(c == null){
	                    System.out.println("c is null");
	                }
	                //�������õĺ�������ͼ��
	                cge.createNode(c.getSignature());
	                //���һ��ָ��ñ��������ı�
	                cge.linkNodeByID(identifier, c.getSignature());
	                if(!visited.containsKey(c.getSignature())){
	                    //�ݹ�
	                    visit(cg,c);
	                }
	            }
	        }
	    }
	    ***/
}
