package com.gleamer.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gleamer.model.TwoParam;
import com.gleamer.model.User;
import com.gleamer.tool.ExcelIO;
import com.gleamer.tool.RandomMix;

@RestController
public class TwoParamController {
	
	
	@PostMapping("ExportInfoExcel")
	public void createExcelTeacher(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("UTF-8");
		List<TwoParam> listtp= null;
		ExcelIO.copyExcel("YourTimeSeries",listtp);//调用工具类方法		
	}
	
	
	
	@PostMapping("fileuploadMA")
	public String fileupload(@RequestParam("file1") MultipartFile filelist,HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("UTF-8");
		User u=null;
		u=(User) request.getSession().getAttribute("loginSession");//文件名里面有uid  时间后面是uid
		String userid=String.valueOf(u.getUid());//String.valueOf(i)    Integer.toString(i)    i+""
		String radomStr=userid+"_"+RandomMix.getRandomCharAndNumr(9);
		//对于程序     output是写 （ 上传），input是读


		//String folder1="C:"+File.separatorChar+"tempfileTimeS";
		
		String folder1="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+
				"clientcache"+File.separator+ExcelIO.CreateTime()+radomStr;
		File temp1=new File(folder1);
		temp1.mkdirs();
		//File.separator与File.separatorChar 一样
		//获取原来的，上传的文件本身的，文件名称
		String orignalName=filelist.getOriginalFilename();// 文件完整路径
		String getName=filelist.getName();
			
		System.out.println("orignalName=="+orignalName+"   getName=="+getName);
		//orignalName==生成时间2017-03-18_11-05-42_CST.xls   getName==file1

		orignalName=orignalName.substring(orignalName.lastIndexOf("\\")+1);
		

		String path=folder1+File.separatorChar+orignalName;

		File temp=new File(path);
		//File temp=new File("tmpoo");
		
		temp.createNewFile();
		//temp.mkdirs();
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(temp));
		out.write(filelist.getBytes());
		out.flush();
		out.close();
		List<TwoParam> listtp=null;
		listtp=ExcelIO.readExcel(path);
		String xlsName="MA_"+ExcelIO.CreateTime()+radomStr;
	//	ExcelIO.copyExcel(xlsName,listtp);
		ExcelIO.maForecastExcel(xlsName, listtp);
		System.out.println("***fileuploadMA**downloadName是："+xlsName);
		return xlsName;
	}
	
	@PostMapping("fileuploadES")
	public String fileupload2(@RequestParam("file2") MultipartFile filelist,HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("UTF-8");
		User u=null;
		u=(User) request.getSession().getAttribute("loginSession");
		String userid=String.valueOf(u.getUid());
		String radomStr=userid+"_"+RandomMix.getRandomCharAndNumr(9);
		String folder1="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+
				"clientcache"+File.separator+ExcelIO.CreateTime()+radomStr;
		File temp1=new File(folder1);
		temp1.mkdirs();
		String orignalName=filelist.getOriginalFilename();// 文件完整路径
		String getName=filelist.getName();			
		System.out.println("orignalName=="+orignalName+"   getName=="+getName);
		orignalName=orignalName.substring(orignalName.lastIndexOf("\\")+1);		
		String path=folder1+File.separatorChar+orignalName;
		File temp=new File(path);		
		temp.createNewFile();
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(temp));
		out.write(filelist.getBytes());
		out.flush();
		out.close();
		List<TwoParam> listtp=null;
		listtp=ExcelIO.readExcel(path);
		String xlsName="ES_"+ExcelIO.CreateTime()+radomStr;
		ExcelIO.esForecastExcel(xlsName, listtp);
		System.out.println("***fileuploadES**downloadName是："+xlsName);
		return xlsName;
	}
	
	@PostMapping("forChart")
	public List<TwoParam> fileuploadForChart(@RequestParam("file3") MultipartFile filelist,HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("UTF-8");
		User u=null;
		u=(User) request.getSession().getAttribute("loginSession");
		String userid=String.valueOf(u.getUid());
		String radomStr=userid+"_"+RandomMix.getRandomCharAndNumr(9);
		String folder1="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+
				"clientcache"+File.separator+ExcelIO.CreateTime()+radomStr;
		File temp1=new File(folder1);
		temp1.mkdirs();
		String orignalName=filelist.getOriginalFilename();// 文件完整路径
		String getName=filelist.getName();			
		System.out.println("orignalName=="+orignalName+"   getName=="+getName);
		orignalName=orignalName.substring(orignalName.lastIndexOf("\\")+1);		
		String path=folder1+File.separatorChar+orignalName;
		File temp=new File(path);		
		temp.createNewFile();
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(temp));
		out.write(filelist.getBytes());
		out.flush();
		out.close();
		List<TwoParam> listtp=null;
		listtp=ExcelIO.readExcel(path);

		System.out.println("***fileuploadForChart**"+listtp.get(0).getValueK());
		return listtp;
	}



	
}
