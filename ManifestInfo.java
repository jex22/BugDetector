package bugDector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import soot.jimple.infoflow.android.manifest.ProcessManifest;
import soot.jimple.infoflow.android.axml.AXmlAttribute;
import soot.jimple.infoflow.android.axml.AXmlHandler;
import soot.jimple.infoflow.android.axml.AXmlNode;
import soot.jimple.infoflow.android.axml.ApkHandler;

public class ManifestInfo {
	private static ManifestInfo manifestInfo;
	//从flowdroid中获取到的
	private ProcessManifest manFInfo = null;
	public ManifestInfo(){
		
	}
	
	public class ContentProvider{
		AXmlNode node;
		String name;
		String isExported;
		String permission;
		String readPermission;
		String writePermission;
		ContentProvider(){
			
		}
	}
	
	public static ManifestInfo getInstance(){
		if(manifestInfo==null){
			manifestInfo = new ManifestInfo();
		}
		return manifestInfo;
	}
	
	public void setInfo(ProcessManifest info){
		manFInfo = info;
	}
	
	public String getApplicationName() {
		return manFInfo.getApplicationName();
	}
	
	public Set<String> getPermission(){
		return manFInfo.getPermissions();
	}
	
	public int getMinSdkVersion() {
		return manFInfo.getMinSdkVersion();
	}
	//apk没有设置，则默认为-1
	public int targetSdkVersion() {
		return manFInfo.targetSdkVersion();
	}
	
	public String getVersionName() {
		return manFInfo.getVersionName();
	}
	
	public String getPackageName() {
		return manFInfo.getPackageName();
	}
	
	public ArrayList<AXmlNode> getActivities(){
		return manFInfo.getActivities();
	}
	
	public ArrayList<AXmlNode> getReceivers() {
		return manFInfo.getReceivers();
	}
	
	public ArrayList<AXmlNode> getProviders() {
		return manFInfo.getProviders();
	}
	
	public ArrayList<AXmlNode> getServices() {
		return manFInfo.getServices();
	}
	
	public Set<String> getPermissions() {
		return manFInfo.getPermissions();
	}
	
	public Set<String> getActivityNames(){
		Set<String> actyNames = new HashSet<String>();
		for (AXmlNode node : getActivities())
			manFInfo.checkAndAddComponent(actyNames, node);
	
		return actyNames;
	}
	
//获取exported为true的contentProvider组件
//@return set of ContentProvider
	public Set<ContentProvider> getExportedProvider(){
		Set<ContentProvider> providers = new HashSet<ContentProvider>();
		for(AXmlNode node:getProviders()){
			String name = getNodeValue(node,"name");
			//没有执行heuristic
			AXmlAttribute<?> exportedAttr = node.getAttribute("exported");
			String exported = exportedAttr==null?"":((Boolean)exportedAttr.getValue()?"true":"false");
			if(Utils.isNullOrEmptyString(name)){
				if(exported.toLowerCase()!="false"){
					String permission = getNodeValue(node,"permission");
					String readPermission = getNodeValue(node,"readPermission");
					String writePermission = getNodeValue(node,"writePermission");
					
					ContentProvider provider = new ContentProvider();
					provider.node = node;
					provider.name = name;
					provider.isExported = "true";
					provider.permission = permission;
					provider.readPermission = readPermission;
					provider.writePermission = writePermission;
					providers.add(provider);
				}
			}
				
		}
		return providers;
	}
	//@return key在获取不到时返回""空字符串
	private String getNodeValue(AXmlNode node, String key){
		AXmlAttribute<?> nameAttr = node.getAttribute(key);
		String name = nameAttr==null?"":(String)nameAttr.getValue();
		return name;
	}
}
