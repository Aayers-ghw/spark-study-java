package cn.spark.study.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * 使用json文件创建DataFrame
 * @author Aayers-ghw
 *
 */
public class DateFrameCreate {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf()
				.setAppName("DateFrameCreate");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		DataFrame df = sqlContext.read().json("hdfs://hadoop-3:9000/students.json");
		df.show();
	}
}
