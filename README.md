# TimeSeriesForecastSystemV1
Time Series Analysis and Forecast System
时间序列分析预测系统


最后更新：201705

# 系统总体结构图
![](diagram.png)

# 技术栈
Spring Boot,
MyBatis,
Maven

MySQL

AngularJS,
jQuery,
Bootstrap,
ECharts


# 使用预测算法
## 二次移动平均法

[二次移动平均预测模型的建立方法](http://xueshu.baidu.com/usercenter/paper/show?paperid=633c03aa282fd6a61da9e09334188798&site=xueshu_se)

[MA代码](https://github.com/gleamer/TimeSeriesForecastSystemV1/blob/master/TimeSeriesSys/src/main/java/com/gleamer/tool/MyMA.java)

运行结果：生成的xls格式文件截图

![](MA.png)

## 一次指数平滑法

[一次指数平滑模型预测法及实际应用](http://xueshu.baidu.com/usercenter/paper/show?paperid=b2ccaeff967ae2c9125fc7af900a3e7c&site=xueshu_se)

[ES代码](https://github.com/gleamer/TimeSeriesForecastSystemV1/blob/master/TimeSeriesSys/src/main/java/com/gleamer/tool/MyES.java)

运行结果：生成的xls格式文件截图

![](ES.png)

