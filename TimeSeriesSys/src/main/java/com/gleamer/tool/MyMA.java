package com.gleamer.tool;

import java.util.ArrayList;
import java.util.List;

import com.gleamer.model.TwoParam;

public class MyMA {
	
	/**
	 * 一次移动平均值, n=3
	 * @param twoParamList 已有的时间序列数据
	 * @return  一次移动平均值List
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static List<Double> threeNumOneMA(List<TwoParam> twoParamList) throws IllegalAccessException{
		List<Double> threeNumOneMAList=new ArrayList<Double>();
		if(twoParamList.size()<3){
			System.out.println("********因为 n<3,无法计算一次移动平均值");
		}else{
			for(int i=0;i<twoParamList.size()-2;++i){
				threeNumOneMAList.add(BigDecimalArith.div(BigDecimalArith.addThree(twoParamList.get(i).getValueK(),twoParamList.get(i+1).getValueK(),twoParamList.get(i+2).getValueK()), 3.0, 5));
			}
		}
		return threeNumOneMAList;
	}
	/**
	 * 二次移动平均值, n=3
	 * @param threeNumOneMAList n=3的一次移动平均值
	 * @return  二次移动平均值List
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static List<Double> threeNumTwoMA(List<Double> threeNumOneMAList) throws IllegalAccessException{
		List<Double> threeNumTwoMAList=new ArrayList<Double>();
		if(threeNumOneMAList.size()<3){
			System.out.println("********因为 n<3,无法计算二次移动平均值");
		}else{
			for(int i=0;i<threeNumOneMAList.size()-2;++i){
				threeNumTwoMAList.add(BigDecimalArith.div(BigDecimalArith.addThree(threeNumOneMAList.get(i), threeNumOneMAList.get(i+1), threeNumOneMAList.get(i+2)), 3.0, 5));
			}
		}
		
		return threeNumTwoMAList;
	}
	/**
	 * at
	 * @param threeNumOneMAList
	 * @param threeNumTwoMAList
	 * @return atList
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static List<Double> threeNumAt(List<Double> threeNumOneMAList,List<Double> threeNumTwoMAList) throws IllegalAccessException{
		List<Double> threeNumAtMAList=new ArrayList<Double>();
	
			for(int i=0;i<threeNumTwoMAList.size()-1;++i){
				threeNumAtMAList.add(BigDecimalArith.sub(BigDecimalArith.mul(2.0, threeNumOneMAList.get(i+3)), threeNumTwoMAList.get(i)));
			}			
		
		return threeNumAtMAList;
	}
	/**
	 * n=3时，bt
	 * @param threeNumOneMAList
	 * @param threeNumTwoMAList
	 * @return btList
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static List<Double> threeNumBt(List<Double> threeNumOneMAList,List<Double> threeNumTwoMAList) throws IllegalAccessException{
		List<Double> threeNumBtList=new ArrayList<Double>();
	
			for(int i=0;i<threeNumTwoMAList.size()-1;++i){
				threeNumBtList.add(BigDecimalArith.sub(threeNumOneMAList.get(i+3), threeNumTwoMAList.get(i)));
			}			
		
		return threeNumBtList;
	}
	/**
	 * 预测值at+bt·T
	 * @param threeNumAtList
	 * @param threeNumBtList
	 * @return at+bt·T
	 * @throws IllegalAccessException
	 * @author jqy
	 */
	public static List<Double> threeNumAtBtT(List<Double> threeNumAtList,List<Double> threeNumBtList) throws IllegalAccessException{
		List<Double> threeNumAtBtTList=new ArrayList<Double>();
	
			for(int i=0;i<threeNumAtList.size()-1;++i){
				threeNumAtBtTList.add(BigDecimalArith.add(threeNumAtList.get(i), threeNumBtList.get(i)));
			}			
		
		return threeNumAtBtTList;
	}

}


