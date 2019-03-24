package com.gleamer.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.gleamer.model.TwoParam;


public class ExcelIO {
		
		
	/**
	 * 复制Excel  xls文件
	 * @param fileName
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public static String copyExcel(String fileName,List<TwoParam> list) throws IOException  {
		//把数据库内容导入  excel
		HSSFWorkbook wb1=new HSSFWorkbook();
		HSSFSheet sheet=wb1.createSheet();

		sheet.setColumnWidth(1, 25*256);
		HSSFRow row=sheet.createRow(0);
		
		HSSFCell cell1=row.createCell(0);
		cell1.setCellValue("时间");
		
		
		HSSFCell cell2=row.createCell(1);
		cell2.setCellValue("数据");
		
		for(int i=0;i<list.size();i++){
			TwoParam tp=(TwoParam) list.get(i);
			HSSFRow row2=sheet.createRow(i+1);
			HSSFCell cell1a=row2.createCell(0);
			cell1a.setCellValue(tp.getTimeK());
			
			HSSFCell cell2a=row2.createCell(1);
			cell2a.setCellValue(tp.getValueK());
					
		}
		//String filepath="C:"+File.separator+"tempfileTimeS"+File.separator+"clientcache";
		String filepath="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"mytmp"+File.separator+"clientcache";

		File f=new File(filepath);
		String path="";
		if(f.exists()){
			path=filepath+File.separator+fileName+".xls";
			FileOutputStream out1=new FileOutputStream(path);
			wb1.write(out1);
			out1.close();
		}
		else{//tempfileTimeS文件夹不存在，新建文件夹
			f.mkdirs();
			path=filepath+File.separator+fileName+".xls";
			FileOutputStream out1=new FileOutputStream(path);
			wb1.write(out1);
			out1.close();
		}
		
				
		wb1.close();
		return path;
	}
	
	
	
	/**
	 * 读取Excel  xls文件
	 * @param path
	 * @return Excel第二行开始的值是个对象TwoParam，存入List
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<TwoParam> readExcel(String path) throws FileNotFoundException, IOException{
		HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(path));
		HSSFSheet sheet=wb.getSheetAt(0);
		List<TwoParam> list=new ArrayList<>();
		for(int i=1;i<(sheet.getLastRowNum()+1);i++){//excel第1行表头，不插入数据库
			HSSFRow row=sheet.getRow(i);
			
			HSSFCell c0=row.getCell(0);
			int time=(int)c0.getNumericCellValue();
						
			HSSFCell c1=row.getCell(1);
			double value=(double)c1.getNumericCellValue();

			//String value=c1.toString();
			//float value=(float)c1.getNumericCellValue();
			//String value=c1.toString();
			//double v=Double.valueOf(value);
			TwoParam tp=new TwoParam();
			tp.setTimeK(time);
			tp.setValueK(value);
			list.add(tp);
		}
		wb.close();
		return list;
	}
	/**
	 * 一次移动平均预测，二次移动平均预测
	 * @param fileName
	 * @param list
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException 
	 */
	public static String maForecastExcel(String fileName,List<TwoParam> list) throws IOException, IllegalAccessException  {
		double atbtTForecastResult=0;//最终预测值
		HSSFWorkbook wb1=new HSSFWorkbook();	
		HSSFSheet sheet=wb1.createSheet();
		wb1.setSheetName(0, "二次移动平均预测");
		
		//-----设置字体样式
		HSSFFont fontStyle=wb1.createFont();//创建字体对象
		fontStyle.setColor(HSSFColor.ORANGE.index);//设置字体颜色
		HSSFCellStyle cellStyle=wb1.createCellStyle();
		cellStyle.setFont(fontStyle);//将字体设置添加到样式中
		//-----

		sheet.setColumnWidth(1, 18*256);
		sheet.setColumnWidth(2, 18*256);
		sheet.setColumnWidth(3, 18*256);
		sheet.setColumnWidth(4, 18*256);
		sheet.setColumnWidth(5, 18*256);
		sheet.setColumnWidth(6, 18*256);
		sheet.setColumnWidth(7, 22*256);
		sheet.setColumnWidth(8, 18*256);
		
		HSSFRow row=sheet.createRow(0);
		
		HSSFCell cell1=row.createCell(0);
		cell1.setCellValue("时间");
		
		
		HSSFCell cell2=row.createCell(1);
		cell2.setCellValue("数据");
		
		HSSFCell cell3=row.createCell(2);
		cell3.setCellValue("一次移动平均值 n=3");
		
		HSSFCell cell4=row.createCell(3);
		cell4.setCellValue("二次移动平均值 n=3");
				
		HSSFCell cell5=row.createCell(4);
		cell5.setCellValue("at");
		
		HSSFCell cell6=row.createCell(5);
		cell6.setCellValue("bt");
		
		HSSFCell cell7=row.createCell(6);
		cell7.setCellValue("at+bt·T");
		
		List<Double> oneMA=new ArrayList<Double>();
		oneMA=MyMA.threeNumOneMA(list);
		
		List<Double> twoMA=new ArrayList<Double>();
		twoMA=MyMA.threeNumTwoMA(oneMA);
		
		List<Double> atValue=new ArrayList<Double>();
		atValue=MyMA.threeNumAt(oneMA, twoMA);
		
		List<Double> btValue=new ArrayList<Double>();
		btValue=MyMA.threeNumBt(oneMA, twoMA);
		
		List<Double> atbtTValue=new ArrayList<Double>();
		atbtTValue=MyMA.threeNumAtBtT(atValue, btValue);
		
		int lastTime=0;//记录最后一行的 年数
		int j=0;
		for(int i=0;i<list.size()+1;i++){
			HSSFRow row2=sheet.createRow(i+1);
			
			
			if(i<list.size()){
			TwoParam tp=(TwoParam) list.get(i);
			
			
			HSSFCell cell1a=row2.createCell(0);
			cell1a.setCellValue(tp.getTimeK());
			lastTime=tp.getTimeK();//取到了最后一行的年数
					
			HSSFCell cell2a=row2.createCell(1);
			cell2a.setCellValue(tp.getValueK());
			
			HSSFCell cell3a=row2.createCell(2);
			cell3a.setCellValue("");
			
			HSSFCell cell4a=row2.createCell(3);
			cell4a.setCellValue("");
			
			HSSFCell cell5a=row2.createCell(4);
			cell5a.setCellValue("");
			
			HSSFCell cell6a=row2.createCell(5);
			cell6a.setCellValue("");
			
			HSSFCell cell7a=row2.createCell(6);
			cell7a.setCellValue("");
			
			//填入一次移动平均值
			if((i+1)>3){
				cell3a.setCellValue(oneMA.get(i-3));

			}
			
			
			if((i+1)>6){
				//填入二次移动平均值
				cell4a.setCellValue(twoMA.get(i-6));
				//填入at值
				cell5a.setCellValue(atValue.get(i-6));
				//填入bt值
				cell6a.setCellValue(btValue.get(i-6));
				
				//cell7a.setCellValue("");
				
				}
			if((i+1)>7){
				//填入at+bt·T值
				cell7a.setCellValue(atbtTValue.get(j));
				j++;
				
			}
		

			}
			
			
			//填入最后一行
			if(i>=list.size()){
				
				HSSFCell cell1aa=row2.createCell(0);
				cell1aa.setCellValue(lastTime+1);//生成预测的年份
				
				HSSFCell cell2aa=row2.createCell(1);
				cell2aa.setCellValue("");
				
				HSSFCell cell3aa=row2.createCell(2);
				cell3aa.setCellValue(oneMA.get(i-3));
				

				HSSFCell cell4aa=row2.createCell(3);
				cell4aa.setCellValue(twoMA.get(i-6));
				
				HSSFCell cell5aa=row2.createCell(4);
				cell5aa.setCellValue(atValue.get(i-6));
				
				HSSFCell cell6aa=row2.createCell(5);
				cell6aa.setCellValue(btValue.get(i-6));
				
				HSSFCell cell7aa=row2.createCell(6);
				cell7aa.setCellValue(atbtTValue.get(j));
				atbtTForecastResult=atbtTValue.get(j);
				j++;
			}

						
		}
		
		HSSFCell cell8=row.createCell(7);
		cell8.setCellStyle(cellStyle);
		cell8.setCellValue("二次移动平均预测结果：");
		HSSFCell cell9=row.createCell(8);
		cell9.setCellStyle(cellStyle);
		cell9.setCellValue(atbtTForecastResult);
		
		
		
		
		String filepath="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"mytmp"+File.separator+"clientdownload";
		//String filepath="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"mytmp"+File.separator+"clientdownload";
		//String filepath="C:"+File.separator+"tempfileTimeS"+File.separator+"clientdownload";
		File f=new File(filepath);
		String path="";
		if(f.exists()){
			path=filepath+File.separator+fileName+".xls";
			//path="C:"+File.separator+"tempfileTimeS"+File.separator+"clientdownload"+File.separator+fileName+".xls";
			FileOutputStream out1=new FileOutputStream(path);
			wb1.write(out1);
			out1.close();
		}
		else{//tempfileTimeS文件夹不存在，新建文件夹
			f.mkdirs();
			path=filepath+File.separator+fileName+".xls";
			FileOutputStream out1=new FileOutputStream(path);
			wb1.write(out1);
			out1.close();
		}

				
		wb1.close();
		return path;
	}
	
	

	/**
	 * 一次指数平滑预测
	 * @param fileName
	 * @param twoParamList
	 * @return path
	 * @throws IOException
	 * @throws IllegalAccessException
	 */
	public static String esForecastExcel(String fileName,List<TwoParam> twoParamList) throws IOException, IllegalAccessException  {

		HSSFWorkbook wb1=new HSSFWorkbook();
		HSSFSheet sheet=wb1.createSheet();
		wb1.setSheetName(0, "一次指数平滑预测");	
		
		//-----设置字体、单元格样式
		HSSFFont fontStyle=wb1.createFont();//创建字体对象
		fontStyle.setColor(HSSFColor.ORANGE.index);//设置字体颜色
		HSSFCellStyle cellStyle=wb1.createCellStyle();
		cellStyle.setFont(fontStyle);//将字体设置添加到样式中
		
		
		HSSFCellStyle cellStyle1=wb1.createCellStyle();
		cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle1.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//薄荷绿
		
		HSSFCellStyle cellStyle2=wb1.createCellStyle();
		cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle2.setFillForegroundColor(HSSFColor.SKY_BLUE.index);//蓝天蓝
		
		HSSFCellStyle cellStyle3=wb1.createCellStyle();
		cellStyle3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle3.setFillForegroundColor(HSSFColor.ROSE.index);//粉红
		
		HSSFCellStyle cellStyle4=wb1.createCellStyle();
		cellStyle4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle4.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);//浅蓝
		
		int lastRow=0;//记录最后一行行数
		//-----

		sheet.setColumnWidth(1, 18*256);
		sheet.setColumnWidth(2, 18*256);
		sheet.setColumnWidth(3, 18*256);
		sheet.setColumnWidth(4, 18*256);
		sheet.setColumnWidth(5, 18*256);
		sheet.setColumnWidth(6, 18*256);
		sheet.setColumnWidth(7, 18*256);
		
		HSSFRow row=sheet.createRow(0);
		
		HSSFCell cell1=row.createCell(0);
		cell1.setCellValue("时间");
		
		
		HSSFCell cell2=row.createCell(1);
		cell2.setCellValue("数据");
		
		HSSFCell cell3=row.createCell(2);
		
		cell3.setCellValue("平滑系数=0.1");
		cell3.setCellStyle(cellStyle1);
		
		HSSFCell cell4=row.createCell(3);
		cell4.setCellStyle(cellStyle1);
		cell4.setCellValue("绝对误差");
				
		HSSFCell cell5=row.createCell(4);
		cell5.setCellStyle(cellStyle2);
		cell5.setCellValue("平滑系数=0.5");
		
		HSSFCell cell6=row.createCell(5);
		cell6.setCellStyle(cellStyle2);
		cell6.setCellValue("绝对误差");
		
		HSSFCell cell7=row.createCell(6);
		cell7.setCellStyle(cellStyle3);
		cell7.setCellValue("平滑系数=0.9");
		
		HSSFCell cell8=row.createCell(7);
		cell8.setCellStyle(cellStyle3);
		cell8.setCellValue("绝对误差");
		

		//-----一次指数平滑值
		List<Double> singleES0_1=new ArrayList<Double>();
		singleES0_1=MyES.singleES(twoParamList, 0.1);
		
		List<Double> singleES0_5=new ArrayList<Double>();
		singleES0_5=MyES.singleES(twoParamList, 0.5);		
		
		List<Double> singleES0_9=new ArrayList<Double>();
		singleES0_9=MyES.singleES(twoParamList, 0.9);
		//-----绝对误差
		List<Double> absError0_1=new ArrayList<Double>();
		absError0_1=MyES.absoluteError(singleES0_1, twoParamList);
		
		List<Double> absError0_5=new ArrayList<Double>();
		absError0_5=MyES.absoluteError(singleES0_5, twoParamList);
		
		List<Double> absError0_9=new ArrayList<Double>();
		absError0_9=MyES.absoluteError(singleES0_9, twoParamList);
		
		//-----平均绝对误差
		double meanAbsError0_1=MyES.meanAbsoluteError(absError0_1);
		double meanAbsError0_5=MyES.meanAbsoluteError(absError0_5);
		double meanAbsError0_9=MyES.meanAbsoluteError(absError0_9);
		
		//----平均绝对误差最小值
		double minMeanAbsError=MyES.minError(meanAbsError0_1, meanAbsError0_5, meanAbsError0_9);
		
		//----一次指数平滑法预测结果
		double singleESForecastResult=MyES.esForecastResult(singleES0_1, singleES0_5, singleES0_9,twoParamList);
		System.out.println("singleESForecastResult========"+singleESForecastResult);
		//-------开始填充表格数据
		for(int i=0;i<twoParamList.size();++i){
			HSSFRow row2=sheet.createRow(i+1);
						
			TwoParam tp=(TwoParam) twoParamList.get(i);					
			HSSFCell cell1a=row2.createCell(0);
			cell1a.setCellValue(tp.getTimeK());
						
			HSSFCell cell2a=row2.createCell(1);
			cell2a.setCellValue(tp.getValueK());
			
			HSSFCell cell3a=row2.createCell(2);
			cell3a.setCellStyle(cellStyle1);
			cell3a.setCellValue(singleES0_1.get(i));
			
			HSSFCell cell4a=row2.createCell(3);
			cell4a.setCellStyle(cellStyle1);
			cell4a.setCellValue(absError0_1.get(i));
			
			HSSFCell cell5a=row2.createCell(4);
			cell5a.setCellStyle(cellStyle2);
			cell5a.setCellValue(singleES0_5.get(i));
			
			HSSFCell cell6a=row2.createCell(5);
			cell6a.setCellStyle(cellStyle2);
			cell6a.setCellValue(absError0_5.get(i));
			
			HSSFCell cell7a=row2.createCell(6);
			cell7a.setCellStyle(cellStyle3);
			cell7a.setCellValue(singleES0_9.get(i));
			
			HSSFCell cell8a=row2.createCell(7);
			cell8a.setCellStyle(cellStyle3);
			cell8a.setCellValue(absError0_9.get(i));
			
			lastRow=i+1;


		}
		
		HSSFRow row3=sheet.createRow(lastRow+1);
		HSSFCell meanABSErrorCell=row3.createCell(0);
		meanABSErrorCell.setCellStyle(cellStyle4);
		meanABSErrorCell.setCellValue("平均绝对误差");
		
		HSSFCell meanABSError0_1=row3.createCell(3);
		meanABSError0_1.setCellStyle(cellStyle4);
		meanABSError0_1.setCellValue(meanAbsError0_1);
		
		HSSFCell meanABSError0_5=row3.createCell(5);
		meanABSError0_5.setCellStyle(cellStyle4);
		meanABSError0_5.setCellValue(meanAbsError0_5);
		
		HSSFCell meanABSError0_9=row3.createCell(7);
		meanABSError0_9.setCellStyle(cellStyle4);
		meanABSError0_9.setCellValue(meanAbsError0_9);
		
		
		HSSFRow row4=sheet.createRow(lastRow+4);
		HSSFCell minMeanABSErrorCell=row4.createCell(0);
		minMeanABSErrorCell.setCellValue("最小误差");
		HSSFCell minMeanABSErrorCell1=row4.createCell(1);
		minMeanABSErrorCell1.setCellValue(minMeanAbsError);
		
		HSSFRow row5=sheet.createRow(lastRow+5);
		HSSFCell esForecastResultCell=row5.createCell(0);
		esForecastResultCell.setCellValue("预测值");
		HSSFCell esForecastResultCell1=row5.createCell(1);
		esForecastResultCell1.setCellStyle(cellStyle);
		esForecastResultCell1.setCellValue(singleESForecastResult);
		
		String filepath="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"mytmp"+File.separator+"clientdownload";
		
		File f=new File(filepath);
		String path="";
		if(f.exists()){
			path=filepath+File.separator+fileName+".xls";
			FileOutputStream out1=new FileOutputStream(path);
			wb1.write(out1);
			out1.close();
		}
		else{
			f.mkdirs();
			path=filepath+File.separator+fileName+".xls";
			FileOutputStream out1=new FileOutputStream(path);
			wb1.write(out1);
			out1.close();
		}
				
		wb1.close();
		return path;
	}
	
	
	
	
	public static String CreateTime(){
		Date nowTime = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'_'HH-mm-ss'_'z");
		String dateStr = dateFormat.format(nowTime);
		return dateStr;
		
	}
	
	

}
