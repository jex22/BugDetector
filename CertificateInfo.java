package bugDector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import soot.Scene;
import soot.jimple.infoflow.android.axml.ApkHandler;
import soot.jimple.toolkits.callgraph.CallGraph;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.ParsingException;

public class CertificateInfo {
	private static CertificateInfo certInfo;
	private String apkPath;
	private X509Certificate certKeyInfo;
	public CertificateInfo(){
		
	}
	
	public static CertificateInfo getInstance(){
		if(certInfo==null){
			certInfo = new CertificateInfo();
		}
		return certInfo;
	}
	
	public void setInfo(String apkPath){
		this.apkPath = apkPath;
		InputStream is = null;
		try {
			is = getInputStream("SA");
			certKeyInfo = readSignatureBlock(is);
			//handle(is);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	
	}
	
	public InputStream getInputStream(String filename) throws IOException {
		File apk = new File(this.apkPath);
		InputStream is = null;
		
		ZipFile zip = new ZipFile(apk);
		
		// search for file with given filename
		Enumeration<?> entries = zip.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String entryName = entry.getName();
			if (entryName.endsWith(filename)) {
				is = zip.getInputStream(entry);
				break;
			}
		}
		
		return is;
	}
	
	public void handle(){
		
	}
	
	public X509Certificate readSignatureBlock(InputStream in) {  
        PKCS7 pkcs7 = null;
		try {
			pkcs7 = new PKCS7(in);
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return pkcs7.getCertificates()[0];  
    } 
	
	public Principal getIssuerDN(){
		return this.certKeyInfo.getIssuerDN();
	}
	
	public Principal getSubjectDN(){
		return this.certKeyInfo.getSubjectDN();
	}
	
	public PublicKey getPublicKey(){
		return this.certKeyInfo.getPublicKey();
	}
}
