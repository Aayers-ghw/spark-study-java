package cn.spark.study.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;

/**
 * SaveModle示例
 * @author Aayers-ghw
 *
 */
public class SaveModeTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		SparkConf conf = new SparkConf()
				.setAppName("SaveModeTest");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		
		DataFrame personDF = sqlContext.read().format("json").load("hdfs://hadoop-3:9000/person.json");
		//如果目标位置已存在数据，那么抛出一个异常
		personDF.save("hdfs://hadoop-3:9000/person.json", "json", SaveMode.ErrorIfExists);
		//如果目标位置已存在数据，那么将数据追加进去
		personDF.save("hdfs://hadoop-3:9000/person.json", "json", SaveMode.Append);
		//如果目标位置已存在数据，那么忽略，不做任何处理操作
		personDF.save("hdfs://hadoop-3:9000/person.json", "json", SaveMode.Ignore);
		//如果目标位置已存在数据，那么将已存在的数据删掉，用新数据进行覆盖
		personDF.save("hdfs://hadoop-3:9000/person.json", "json", SaveMode.Overwrite);
	}
}
