package bugDector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bugDector.ManifestInfo.ContentProvider;

/**
 * 此脚本检测两个设置exported属性错误漏洞
 * a) 错误的设置为true： "android:targetSdkVersion" < 17时没有设置exported属性，设置exported为true
 * b) 错误的设置为false: 使用的权限等级为normal和dangerous时，但是exported为“”时，其它app仍然无法获取到属性
 */
public class ProviderExportedDetector implements DetectorInterface {
	//第一个bug： 错误的设置为true： "android:targetSdkVersion" < 17时没有设置exported属性，设置exported为true
	//检测项的名称
	private String detectItem1="组件暴露-ContentProvider";
	//漏洞描述
	private String description1="组件暴露可能导致该组件被第三方app恶意调用，造成敏感数据泄露、拒绝服务等风险";
	//建议解决方法
	private String advice1 = "在targetSdkVersion<17的版本上，exported属性默认为true.建议将ContentProvider的“exported”属性值为“false”";
	//漏洞所处位置
	private List<String> postions1 = null;
	//第二个bug： 使用的权限等级为normal和dangerous时，但是exported为“”时，其它app仍然无法获取到属性
	//检测项的名称
	private String detectItem2="exported属性设置错误-ContentProvider";
	//漏洞描述
	private String description2="在targetSdkVersion>=17的版本上，exported属性默认为false.";
	//建议解决方法
	private String advice2 = "请确认是否要暴露组件，如果是，请将ContentProvider的“exported”属性值设置为“true”";
	//漏洞所处位置
	private List<String> postions2 = null;
	
	public ProviderExportedDetector(){
		
	}
	
	/**
	 * 查找unsafe的exported contentProvider
	 * 在Android "android:targetSdkVersion" < 17，contentProvider 的exported默认为true，而在此之后默认为false
	 * 1. 查找到所有expored为true 和空的contentProvider
	 * 2. 对于1中所找得到的ContentProvider，获取其要求的权限，包括permission,writePermission和readPermission
	 *    判断
	 * 3. 以下两种情况下此contentProvider的设置是不对的
	 *    a) 错误的设置为true： "android:targetSdkVersion" < 17时没有设置exported属性，设置exported为true
	 *    b) 错误的设置为false: 使用的权限等级为normal和dangerous时，但是exported为“”时，其它app仍然无法获取到属性
	 */   
	public void exec(){
		Set<ContentProvider> providers = ManifestInfo.getInstance().getExportedProvider();
		int tagetSdkVersion =  ManifestInfo.getInstance().targetSdkVersion();
		int count = 0;
		//result1为a所表示的bug
		DetectResult result1 =null;
		for(ContentProvider provider:providers){
			if(provider.isExported=="true"||(provider.isExported==""&&tagetSdkVersion<17)){
				count++;
				if(count==1){
					result1 = new DetectResult();
					result1.detectItem = detectItem1;
					result1.description = description1;
					result1.advice = advice1;
					List<String> postions = new ArrayList<String>();
					postions.add(provider.name);
					result1.positions = postions;
					continue;
				}
				result1.positions.add(provider.name);
			}
		}
		
		DetectResults.getInstance().addResult(result1);
	}

}
