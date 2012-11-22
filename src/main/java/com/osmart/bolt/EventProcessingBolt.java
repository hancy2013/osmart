package com.osmart.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.espertech.esper.client.*;
import com.osmart.event.MeasurementEvent;

import java.util.Map;

/**
 * @author Vadim Bobrov
 */
public class EventProcessingBolt implements IRichBolt, UpdateListener {
    OutputCollector collector;

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        System.out.println("avg=" + event.get("avg(measurement)"));
    }

    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;

        ////////////////////////////////
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        String expression = "select avg(measurement) from com.osmart.event.MeasurementEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);

    }

    public void execute(Tuple tuple) {
        // generate and send new event from input
        MeasurementEvent event = new MeasurementEvent(tuple.getLong(0), tuple.getInteger(1));
        EPServiceProviderManager.getDefaultProvider().getEPRuntime().sendEvent(event);
    }

    public void cleanup() {
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("newmeasurement"));
    }

    public Map getComponentConfiguration() {
        return null;
    }
}