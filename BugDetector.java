package bugDector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BugDetector {
 //����Ҫ������APK�ļ�
    public String apkPath = "D:\\ʵ�����ļ�\\ʵ���ұ�����Ϣ\\googleTop200-160503\\iMeeting.apk";
	//Ӧ���ύʱ��
    public String submitTimeStamp;
    //������ʱ��
    public String detectCompleteTimeStamp;
    public void setApkPath(String path){
    	apkPath = path;
    }
    
	public void exec(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		submitTimeStamp =df.format(new Date());
		
		//��ȡfile����Ϣ
		FileInfo.getInstance().setInfo(apkPath);
		
		//��ȡapk����Ϣ,��ʱ���ǵ���Maneifes�е�����
		ApkInfo.getInstance().setInfo(apkPath);
		
		
		//������©��
		DetectFactory.getInstance().exec();
		
		detectCompleteTimeStamp =df.format(new Date());	
		printResults();
	}
	
	public void printResults(){
		Log.info("�������");
		Log.info("Ӧ���ύʱ��--" + submitTimeStamp);
		Log.info("������ʱ��--" + detectCompleteTimeStamp);
		
		
		Log.info("������Ϣ");
		Log.info("�ļ���Ϣ");
		Log.info("�ļ�����---" + FileInfo.getInstance().getName());
		Log.info("�ļ���С---" + FileInfo.getInstance().getSize());
		
		Log.info("Ӧ����Ϣ");
		Log.info("Ӧ������---" + ApkInfo.getInstance().getApplicationName());
		Log.info("�汾��Ϣ---" + ApkInfo.getInstance().getVersion());
		Log.info("����---" + ApkInfo.getInstance().getPackageName());
		Log.info("Activity����---" + ApkInfo.getInstance().getActivityCount());
		Log.info("Provider����---" + ApkInfo.getInstance().getProviderCount());
		Log.info("Receiver����---" + ApkInfo.getInstance().getReceiverCount());
		Log.info("Service����---" + ApkInfo.getInstance().getServiceCount());
		Log.info("����Ȩ�޸���---" + ApkInfo.getInstance().getUsesPermisionCount());
		
		Log.info("Ӧ����Ϣ");
		Log.info("IssuerDN---" + CertificateInfo.getInstance().getIssuerDN());
		Log.info("SubjectDN---" + CertificateInfo.getInstance().getSubjectDN());
		Log.info("PublicKey---" + CertificateInfo.getInstance().getPublicKey());
		
		DetectFactory.getInstance().printResults();
	}
	
	public static void main(String[] args){
		BugDetector bugDetector = new BugDetector();
		bugDetector.exec();
		
	}
	
	
}
