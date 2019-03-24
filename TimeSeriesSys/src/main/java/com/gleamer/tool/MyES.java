package com.gleamer.tool;

import java.util.ArrayList;
import java.util.List;

import com.gleamer.model.TwoParam;

public class MyES {
	
	/**
	 * 计算时间序列数据前三期的平均值，当做指数平滑法初始值
	 * @param twoParamList 已有的时间序列数据
	 * @return 初始值（当期数不足三期，返回0）
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static double getInitialValue(List<TwoParam> twoParamList) throws IllegalAccessException{
		double initialValue = 0;
		if(twoParamList.size()<3){
			System.out.println("因为 n<3,无法计算一次指数平滑法的初始值");
		}else{
				initialValue=BigDecimalArith.div(BigDecimalArith.addThree(twoParamList.get(0).getValueK(),twoParamList.get(1).getValueK(),twoParamList.get(2).getValueK()), 3.0, 5);				
		}
		return initialValue;
	}
	
	/**
	 * 一次指数平滑法预测
	 * @param twoParamList 已有的时间序列数据
	 * @param alpha 平滑系数
	 * @return 一次指数平滑值
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static List<Double> singleES(List<TwoParam> twoParamList,double alpha) throws IllegalAccessException{
		List<Double> singleESList=new ArrayList<Double>();
		for(int i=0;i<twoParamList.size();++i){
			if(i==0){
				double initialValue=MyES.getInitialValue(twoParamList);//获取初始值
				double singleESResult=BigDecimalArith.add(BigDecimalArith.mul(alpha, twoParamList.get(i).getValueK()), 
						BigDecimalArith.mul(BigDecimalArith.sub(1.0, alpha), initialValue));
				singleESList.add(singleESResult);
			}else{
				double singleESResult=BigDecimalArith.add(BigDecimalArith.mul(alpha, twoParamList.get(i).getValueK()), 
						BigDecimalArith.mul(BigDecimalArith.sub(1.0, alpha), singleESList.get(i-1)));
				singleESList.add(singleESResult);
			}							
		}		
		return singleESList;
	}
	
	/**
	 * 求绝对误差
	 * @param singleESList 一次指数平滑值List
	 * @param twoParamList 已有的时间序列数据
	 * @return 绝对误差
	 * @author jqy
	 */
	public static List<Double> absoluteError(List<Double> singleESList,List<TwoParam> twoParamList){
		List<Double> absoluteErrorList=new ArrayList<Double>();
		for(int i=0;i<singleESList.size();++i){
			double absoluteError=BigDecimalArith.abs(BigDecimalArith.sub(singleESList.get(i), twoParamList.get(i).getValueK()));
			absoluteErrorList.add(absoluteError);
		}		
		return absoluteErrorList;
	}
	
	/**
	 * 求一次指数平滑的平均绝对误差
	 * @param absoluteErrorList 绝对误差List
	 * @return 一次指数平滑的平均绝对误差
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static double meanAbsoluteError(List<Double> absoluteErrorList) throws IllegalAccessException{
		double absoluteErrorSum=0;//绝对误差总和
		for(double a:absoluteErrorList){
			absoluteErrorSum=BigDecimalArith.add(a, absoluteErrorSum);
		}
		//一次指数平滑的平均绝对误差
		double meanAbsoluteErrorValue=BigDecimalArith.div(absoluteErrorSum, absoluteErrorList.size(), 5);
		return meanAbsoluteErrorValue;
		
	}
	
	/**
	 * 求两个数中较小那个数
	 * @param value1
	 * @param value2
	 * @return 两个数中较小的数
	 * @author jqy
	 */
	public static double getMin(double value1,double value2){
		if(value1<value2)
			return value1;
		else
			return value2;
	}
	
	/**
	 * 求三个数中最小那个数，用来求  平均绝对误差最小值
	 * @param value1
	 * @param value2
	 * @param value3
	 * @return 三个数中最小那个数
	 * @author jqy
	 */
	public static double minError(double value1,double value2,double value3){		
		return MyES.getMin(MyES.getMin(value1, value2),value3);
	}
	
	/**
	 * 获取三个一次指数平滑值序列中平均绝对误差最小的那个序列的最后那个值，即最佳预测结果
	 * @param singleESList1  一次指数平滑值List
	 * @param singleESList2 一次指数平滑值List
	 * @param singleESList3 一次指数平滑值List
	 * @param twoParamList 已知时间序列
	 * @return 一次指数平滑法预测结果
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static double esForecastResult(List<Double> singleESList1,List<Double> singleESList2,List<Double> singleESList3,List<TwoParam> twoParamList) throws IllegalAccessException{		
		double esForecastResult=0;		
		double minMeanAbsoluteError=MyES.minError(
				MyES.meanAbsoluteError(MyES.absoluteError(singleESList1,twoParamList)),
				MyES.meanAbsoluteError(MyES.absoluteError(singleESList2,twoParamList)), 
				MyES.meanAbsoluteError(MyES.absoluteError(singleESList3,twoParamList)));
		if(MyES.meanAbsoluteError(MyES.absoluteError(singleESList1,twoParamList))==minMeanAbsoluteError){
		esForecastResult=singleESList1.get(singleESList1.size()-1);		
		}
		else if(MyES.meanAbsoluteError(MyES.absoluteError(singleESList2,twoParamList))==minMeanAbsoluteError){
			esForecastResult=singleESList2.get(singleESList2.size()-1);		
			}
		else if(MyES.meanAbsoluteError(MyES.absoluteError(singleESList3,twoParamList))==minMeanAbsoluteError){
			esForecastResult=singleESList3.get(singleESList3.size()-1);		
			}
		
		return esForecastResult;
	}
	

}
