package cn.spark.study.core;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

/**
 * 分组取top3
 * @author Aayers-ghw
 *
 */
public class GroupTop3 {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf()
				.setAppName("GroupTop3")
				.setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> lines = sc.textFile("E:/eclipseApp/spark-study-java/src/main/java/cn/spark/study/core/score.txt");
		JavaPairRDD<String, Integer> pairs = lines.mapToPair(
				new PairFunction<String, String, Integer>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Tuple2<String, Integer> call(String t) throws Exception {
						String[] lineSplited = t.split(" ");
						return new Tuple2<String, Integer>(
								lineSplited[0], Integer.valueOf(lineSplited[1]));
					}
		});
		
		JavaPairRDD<String, Iterable<Integer>> groupedPairs = pairs.groupByKey();
		
		JavaPairRDD<String, Iterable<Integer>> top3Score = groupedPairs.mapToPair(
				new PairFunction<Tuple2<String,Iterable<Integer>>, String, Iterable<Integer>>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Tuple2<String, Iterable<Integer>> call(
							Tuple2<String, Iterable<Integer>> t) throws Exception {
						String className = t._1;
						Integer[] top3 = new Integer[3];
						Iterator<Integer> scores = t._2.iterator();
						
						while(scores.hasNext()) {
							Integer score = scores.next();
							for(int i = 0; i < 3; i++) {
								if(top3[i] == null) {
									top3[i] = score;
									break;
								}else if(top3[i] < score) {
									for(int j = 2; j > i; j--) {
										top3[j] = top3[j - 1];
									}
									top3[i] = score;
									break;
								}
							}
						}
						return new Tuple2<String, Iterable<Integer>>(
								className, Arrays.asList(top3));
					}
		});
		
		top3Score.foreach(new VoidFunction<Tuple2<String,Iterable<Integer>>>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Tuple2<String, Iterable<Integer>> t) throws Exception {
				System.out.println("class : " + t._1);
				Iterator<Integer> scoreIterator = t._2.iterator();
				while(scoreIterator.hasNext()) {
					System.out.println(scoreIterator.next());
				}
				System.out.println("================================");
			}
		});
		
		sc.close();
	}
}
