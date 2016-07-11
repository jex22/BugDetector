package bugDector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BugDetector {
 //设置要分析的APK文件
    public String apkPath = "D:\\实验室文件\\实验室备份信息\\googleTop200-160503\\iMeeting.apk";
	//应用提交时间
    public String submitTimeStamp;
    //检测完成时间
    public String detectCompleteTimeStamp;
    public void setApkPath(String path){
    	apkPath = path;
    }
    
	public void exec(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		submitTimeStamp =df.format(new Date());
		
		//获取file的信息
		FileInfo.getInstance().setInfo(apkPath);
		
		//获取apk的信息,暂时都是调用Maneifes中的内容
		ApkInfo.getInstance().setInfo(apkPath);
		
		
		//检测各项漏洞
		DetectFactory.getInstance().exec();
		
		detectCompleteTimeStamp =df.format(new Date());	
		printResults();
	}
	
	public void printResults(){
		Log.info("报告概述");
		Log.info("应用提交时间--" + submitTimeStamp);
		Log.info("检测完成时间--" + detectCompleteTimeStamp);
		
		
		Log.info("基本信息");
		Log.info("文件信息");
		Log.info("文件名称---" + FileInfo.getInstance().getName());
		Log.info("文件大小---" + FileInfo.getInstance().getSize());
		
		Log.info("应用信息");
		Log.info("应用名称---" + ApkInfo.getInstance().getApplicationName());
		Log.info("版本信息---" + ApkInfo.getInstance().getVersion());
		Log.info("包名---" + ApkInfo.getInstance().getPackageName());
		Log.info("Activity个数---" + ApkInfo.getInstance().getActivityCount());
		Log.info("Provider个数---" + ApkInfo.getInstance().getProviderCount());
		Log.info("Receiver个数---" + ApkInfo.getInstance().getReceiverCount());
		Log.info("Service个数---" + ApkInfo.getInstance().getServiceCount());
		Log.info("申请权限个数---" + ApkInfo.getInstance().getUsesPermisionCount());
		
		Log.info("应用信息");
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
