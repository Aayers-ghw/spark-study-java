package cn.spark.study.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * 手动指定数据类型
 * @author Aayers-ghw
 *
 */
public class ManuallySpecifyOptions {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf()
				.setAppName("ManuallySpecifyOptions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		
		DataFrame personDF = sqlContext.read().
				format("json").load("hdfs://hadoop-3:9000/person.json");
		personDF.select("name").write().
				format("parquet").save("hdfs://hadoop-3:9000/personName_java.parquet");
	}
}
