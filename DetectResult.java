package bugDector;

import java.util.List;

public class DetectResult{
	//����������
	public String detectItem;
	//©������
	public String description;
	//����������
	public String advice;
	//©������λ��
	public List<String> positions = null;
	//apk���Ƿ���ڴ�©��
	public boolean isBug = false;
	
	public DetectResult(){
		
	}
}
