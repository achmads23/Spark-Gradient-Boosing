/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.util.ArrayList;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

/**
 *
 * @author achma
 */
public class MyFunction implements Function<Tuple2<Double, Double>, Boolean> {

    public ArrayList<Double> output;

    @Override
    public Boolean call(Tuple2<Double, Double> pl) {

              //PL 1 = Output Prediction after boosting
        //PL 2 = Asumsi Prediction
        output.add(pl._1());
        System.out.println("PL 1 " + pl._1() + " - PL 2 " + pl._2());
        return !pl._1().equals(pl._2());
    }

}
