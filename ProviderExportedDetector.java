package bugDector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bugDector.ManifestInfo.ContentProvider;

/**
 * �˽ű������������exported���Դ���©��
 * a) ���������Ϊtrue�� "android:targetSdkVersion" < 17ʱû������exported���ԣ�����exportedΪtrue
 * b) ���������Ϊfalse: ʹ�õ�Ȩ�޵ȼ�Ϊnormal��dangerousʱ������exportedΪ����ʱ������app��Ȼ�޷���ȡ������
 */
public class ProviderExportedDetector implements DetectorInterface {
	//��һ��bug�� ���������Ϊtrue�� "android:targetSdkVersion" < 17ʱû������exported���ԣ�����exportedΪtrue
	//����������
	private String detectItem1="�����¶-ContentProvider";
	//©������
	private String description1="�����¶���ܵ��¸������������app������ã������������й¶���ܾ�����ȷ���";
	//����������
	private String advice1 = "��targetSdkVersion<17�İ汾�ϣ�exported����Ĭ��Ϊtrue.���齫ContentProvider�ġ�exported������ֵΪ��false��";
	//©������λ��
	private List<String> postions1 = null;
	//�ڶ���bug�� ʹ�õ�Ȩ�޵ȼ�Ϊnormal��dangerousʱ������exportedΪ����ʱ������app��Ȼ�޷���ȡ������
	//����������
	private String detectItem2="exported�������ô���-ContentProvider";
	//©������
	private String description2="��targetSdkVersion>=17�İ汾�ϣ�exported����Ĭ��Ϊfalse.";
	//����������
	private String advice2 = "��ȷ���Ƿ�Ҫ��¶���������ǣ��뽫ContentProvider�ġ�exported������ֵ����Ϊ��true��";
	//©������λ��
	private List<String> postions2 = null;
	
	public ProviderExportedDetector(){
		
	}
	
	/**
	 * ����unsafe��exported contentProvider
	 * ��Android "android:targetSdkVersion" < 17��contentProvider ��exportedĬ��Ϊtrue�����ڴ�֮��Ĭ��Ϊfalse
	 * 1. ���ҵ�����exporedΪtrue �Ϳյ�contentProvider
	 * 2. ����1�����ҵõ���ContentProvider����ȡ��Ҫ���Ȩ�ޣ�����permission,writePermission��readPermission
	 *    �ж�
	 * 3. ������������´�contentProvider�������ǲ��Ե�
	 *    a) ���������Ϊtrue�� "android:targetSdkVersion" < 17ʱû������exported���ԣ�����exportedΪtrue
	 *    b) ���������Ϊfalse: ʹ�õ�Ȩ�޵ȼ�Ϊnormal��dangerousʱ������exportedΪ����ʱ������app��Ȼ�޷���ȡ������
	 */   
	public void exec(){
		Set<ContentProvider> providers = ManifestInfo.getInstance().getExportedProvider();
		int tagetSdkVersion =  ManifestInfo.getInstance().targetSdkVersion();
		int count = 0;
		//result1Ϊa����ʾ��bug
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
