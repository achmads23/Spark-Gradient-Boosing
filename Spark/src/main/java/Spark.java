/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author achma
 */
import java.util.HashMap;
import java.util.Map;

import scala.Tuple2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.GradientBoostedTrees;
import org.apache.spark.mllib.tree.configuration.BoostingStrategy;
import org.apache.spark.mllib.tree.model.GradientBoostedTreesModel;
import org.apache.spark.mllib.util.MLUtils;

public class Spark {
    
    public static void main(String[] args){
      System.setProperty("hadoop.home.dir", "E:\\Teknik Informatika 2012\\Tugas Akhir\\Spark\\hadoop-common-2.2.0-bin-master");
      SparkConf sparkConf = new SparkConf()
        .setAppName("JavaGradientBoostedTreesClassificationExample").setMaster("local");;
      JavaSparkContext jsc = new JavaSparkContext(sparkConf);

      // Load and parse the data file.
      String datapath = "spark-1.6.0/data/mllib/dataTraining.txt";
      JavaRDD<LabeledPoint> trainingData = MLUtils.loadLibSVMFile(jsc.sc(), datapath).toJavaRDD();
      
      String testingDatapath = "spark-1.6.0/data/mllib/TESTING.txt";
      JavaRDD<LabeledPoint> testData = MLUtils.loadLibSVMFile(jsc.sc(), testingDatapath).toJavaRDD();

      // Train a GradientBoostedTrees model.
      // The defaultParams for Classification use LogLoss by default.
      BoostingStrategy boostingStrategy = BoostingStrategy.defaultParams("Classification");
      boostingStrategy.setNumIterations(5); // Note: Use more iterations in practice.
      boostingStrategy.getTreeStrategy().setNumClasses(2);
      boostingStrategy.getTreeStrategy().setMaxDepth(5);
      // Empty categoricalFeaturesInfo indicates all features are continuous.
      Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
      boostingStrategy.treeStrategy().setCategoricalFeaturesInfo(categoricalFeaturesInfo);

      final GradientBoostedTreesModel model =
        GradientBoostedTrees.train(trainingData, boostingStrategy);
      // Evaluate model on test instances and compute test error
      JavaPairRDD<Double, Double> predictionAndLabel =
        testData.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
          @Override
          public Tuple2<Double, Double> call(LabeledPoint p) {
            return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
          }
        });
      
      Double testErr =
        1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
          @Override
          public Boolean call(Tuple2<Double, Double> pl) {
            return !pl._1().equals(pl._2());
          }
        }).count() / testData.count();
      
      Double failData =
        1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
          @Override
          public Boolean call(Tuple2<Double, Double> pl) {
              //PL 1 = Output Prediction after boosting
              //PL 2 = Asumsi Prediction
              System.out.println("PL 1 " + pl._1() + "PL 2 " + pl._2());
            return !pl._1().equals(pl._2());
          }
        }).count();
      System.out.println(failData);
      System.out.println("Test Error: " + testErr);
      System.out.println("Prediction and Label: " + predictionAndLabel.toDebugString());
      System.out.println("Learned classification GBT model:\n" + model.toDebugString());

//      // Save and load model
//      model.save(jsc.sc(), "output/myGradientBoostingClassificationModel");
//      GradientBoostedTreesModel sameModel = GradientBoostedTreesModel.load(jsc.sc(),
//        "output/myGradientBoostingClassificationModel");
      
//      model.save(jsc.sc(), "target/tmp/myGradientBoostingClassificationModel");
//      GradientBoostedTreesModel sameModel = GradientBoostedTreesModel.load(jsc.sc(),
//        "target/tmp/myGradientBoostingClassificationModel");
    }
    
}
