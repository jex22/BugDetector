package bugDector;

import java.util.List;

public class DetectResult{
	//检测项的名称
	public String detectItem;
	//漏洞描述
	public String description;
	//建议解决方法
	public String advice;
	//漏洞所处位置
	public List<String> positions = null;
	//apk中是否存在此漏洞
	public boolean isBug = false;
	
	public DetectResult(){
		
	}
}
