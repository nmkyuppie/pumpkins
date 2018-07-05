package com.equiniti.pumpkins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PumpkinController {

	@Autowired
	private Environment env;

	@RequestMapping(value = "/", method=RequestMethod.GET)
	public ModelAndView index(@RequestParam(value = "version", required = false) String version) {
		List<String> versions=getVersions();
		ModelMap model = new ModelMap();
		Collections.reverse(versions);
		model.put("versions", versions);
		model.put("version", version);
		model.put("groupId", env.getProperty("groupId"));
		model.put("artifactId", env.getProperty("artifactId"));
		System.out.println();
		return new ModelAndView("index", model);
	}

	public List<String> getVersions() {
		String versionDir="D:\\apache-archiva-2.2.3-bin\\apache-archiva-2.2.3\\repositories\\Xanite_Maven\\com\\equiniti\\xanite\\Xanite_Maven";
		File dir = new File(versionDir);
		File[] files=dir.listFiles();
		List<String> folderList=new ArrayList<String>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				folderList.add(files[i].getName());
			}
		}
		
		return folderList;
	}

	@RequestMapping(value = "/deploy")
	@ResponseBody
	public String showConfiguration(@RequestParam(value = "groupId", required = true) String groupId,
			@RequestParam(value = "artifactId", required = true) String artifactId,
			@RequestParam(value = "version", required = true) String version,
			@RequestParam(value = "environment", required = true) String environment) throws IOException {
		String archivaDir = "";
		String warFileName = "";
		String codeBaseFolderName="Xanite_Maven";
		File[] warFiles=null;
		
		groupId = groupId.replaceAll("\\.", "\\\\");
		archivaDir = env.getProperty("archiva-path") + artifactId + "\\" + groupId + "\\" + artifactId + "\\" + version + "\\";
		
		warFiles = getWarFile(archivaDir);
		if(warFiles == null || warFiles.length<=0) {
			File f=new File(archivaDir+codeBaseFolderName);
			if(f.exists()) {
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \""+archivaDir+codeBaseFolderName+"\" && jar -cvf "+archivaDir+"Xanite_Maven.war *");
				
				builder.redirectErrorStream(true);
				
				Process p = builder.start();
				
				if(p.isAlive()) {
					BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line;
					while (true) {
						line = r.readLine();
						if (line == null) {
							break;
						}
						System.out.println(line);
					}
				}
				warFiles = getWarFile(archivaDir);
				warFileName = warFiles[0].getName();
			}
		}
		else {
			warFileName = warFiles[0].getName();
		}
		
		String tomcatURL = env.getProperty(environment+"-tomcat-URL") + "?update=true&path=/" + artifactId;
		String warFilePath=env.getProperty("archiva-path") + artifactId + "\\" + groupId + "\\" + artifactId + "\\" + version
				+ "\\" + warFileName;
		
		String message=deployInTomcat(tomcatURL, warFilePath);

		return message;
	}

	private String deployInTomcat(String url, String warFilePath) {
		File binaryFile = new File(warFilePath);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader(HTTP.EXPECT_DIRECTIVE, HTTP.EXPECT_CONTINUE); 
 
        FileEntity fileEntity = new FileEntity(binaryFile, ContentType.APPLICATION_OCTET_STREAM); 
        httpPut.setEntity(fileEntity);
        
        String statusLine=null;
		try {
			statusLine = httpClient.execute(httpPut, new ResponseHandler<String>() {

				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					String responseBody = EntityUtils.toString(response.getEntity());
					int responseCode = response.getStatusLine().getStatusCode();
					if(responseCode == 200) {
						return "pumpkins-success"+responseBody;
					}else if(responseCode == 401){
						return "pumpkins-failure"+"Unauthorized.";
					}else if(responseCode == 403){
						return "pumpkins-failure"+"Access Denied.";
					}else {
						return "pumpkins-failure"+"Deployment Failed";
					}
				}
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			return "pumpkins-failure"+"Connection refused.";
		}catch (SocketException e) {
			e.printStackTrace();
			return "pumpkins-failure"+"Connection refused.";
		} catch (IOException e) {
			e.printStackTrace();
		} 
 
        return statusLine;
            
	}

	private File[] getWarFile(String archivaDir) {
		File dir = new File(archivaDir);

		return dir.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String filename) {
				return filename.endsWith(".war");
			}
		});
	}
}
