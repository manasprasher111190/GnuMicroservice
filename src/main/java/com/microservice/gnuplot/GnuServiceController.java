package com.microservice.gnuplot;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

@RestController
public class GnuServiceController {

	@RequestMapping("/")
	public String index(){
		return "GnuPlot Service is running";
	}
	
	@RequestMapping(value = "/plot",produces = "image/png")
	public byte[] plot(@RequestParam(value="template", required=true)String template) throws IOException{
		String scriptFilePath = writeGnuScriptFileFromTemplate(template).toString();
		String[] arrayofCommands = new String[]{"/usr/bin/gnuplot","-e","set term png;set output \"~/data/ouput.png\"",scriptFilePath};
		ProcessBuilder builder = new ProcessBuilder(arrayofCommands);
		builder.redirectErrorStream(true);
		Process process = builder.start();
		BufferedReader reader = 
                new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builders = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			builders.append(line);
			builders.append(System.getProperty("line.separator"));
		}
		String string = Paths.get("/home", "manas","data","ouput.png").toString();
		System.out.println("PATH"+string);
		byte[] readImage = readImage(string);
		if(readImage!= null){
			return readImage;	
		}else {
			return null;
		}
	}
	
	private byte[] readImage(String path){
		try{
		      BufferedImage image = ImageIO.read(new File(path));
		      ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      ImageIO.write(image, "png", baos);
		     byte[] res=baos.toByteArray();
		      String encodedImage = Base64Utils.encodeToString(baos.toByteArray());
		      System.out.println("The encoded image byte array is shown below.Please use this in your webpage image tag.\n"+encodedImage);
		      return res;
		} 
		catch(Exception e) {
		     e.printStackTrace();
		return null;
		}
	}

	
	private Path writeGnuScriptFileFromTemplate(String template) throws IOException {
		File tempGnuScriptFile = File.createTempFile("gnuscript", "");
		Files.write(template.getBytes(), tempGnuScriptFile);
		return tempGnuScriptFile.toPath();
	}
	
	@RequestMapping(value = "/listOfServices")
	public String listOfServices() throws IOException{
		
		byte[] rModulejsonData = ByteStreams.toByteArray(this.getClass().getResourceAsStream("listofservices.txt"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		RRoot rmodule = objectMapper.readValue(rModulejsonData, RRoot.class);
		return objectMapper.writeValueAsString(rmodule);
	}
}
