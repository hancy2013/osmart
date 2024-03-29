package com.osmart.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * @author Vadim Bobrov
 */

public class RandomMeasurementSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Random rand;


    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        rand = new Random();
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        collector.emit(new Values("SnS","Framingham","HVAC", System.currentTimeMillis(), rand.nextDouble()));
    }

    @Override
    public void ack(Object id) {
    }

    @Override
    public void fail(Object id) {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("customer", "location", "circuit", "timestamp", "measurement"));
    }

}