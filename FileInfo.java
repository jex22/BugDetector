package bugDector;

import java.io.File;
import java.math.BigDecimal;

public class FileInfo {
	private static FileInfo fileInfo;
	private String apkPath;
	private String fileName;
	private String fileSize;
	
	public FileInfo(){
		
	}
	
	public static FileInfo getInstance(){
		if(fileInfo==null){
			fileInfo = new FileInfo();
		}
		return fileInfo;
	}
	
	public void setInfo(String apkPath){
		this.apkPath = apkPath;
		File file = new File(apkPath);
		fileName = file.getName();
		Log.info(fileName);
		if(file.exists()&&file.isFile()){
			fileSize=getFormatSize(file.length());
			Log.info(fileSize);
		}
		
	}
	/**
	 * 
	 * @return the file name
	 */
	public String getName(){
		return fileName;
	}
	/**
	 * 
	 * @return the file size
	 */
	public String getSize(){
		return fileSize;
	}
	
	public String getFormatSize(double size) {
		double kiloByte = size/1024;
		if(kiloByte < 1) {
			return size + "Byte(s)";
		}
		
		double megaByte = kiloByte/1024;
		if(megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}
		
		double gigaByte = megaByte/1024;
		if(gigaByte < 1) {
			BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}
		
		double teraBytes = gigaByte/1024;
		if(teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}
}
