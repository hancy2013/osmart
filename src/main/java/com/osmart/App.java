package com.osmart;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import com.osmart.bolt.CalculatorBolt;
import com.osmart.bolt.EventProcessingBolt;
import com.osmart.bolt.StoringBolt;
import com.osmart.spout.RandomMeasurementSpout;

/**
 * @author Vadim Bobrov
 */
public class App {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("measurements", new RandomMeasurementSpout(), 10);

        builder.setBolt("eventprocess", new EventProcessingBolt(), 3).shuffleGrouping("measurements");
        builder.setBolt("calculator", new CalculatorBolt(), 3).shuffleGrouping("measurements");
        builder.setBolt("storage", new StoringBolt(), 3).shuffleGrouping("measurements");



        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(2);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(20000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
