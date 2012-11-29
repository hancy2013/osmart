package com.osmart.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.espertech.esper.client.*;
import com.osmart.event.SensorEvent;

import java.util.Map;

/**
 * @author Vadim Bobrov
 */
public class EventProcessingBolt implements IRichBolt, UpdateListener {
    OutputCollector collector;

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean newEvent = newEvents == null ? null : newEvents[0];
        EventBean oldEvent = oldEvents == null ? null : oldEvents[0];

        // newEvent and oldEvent are MapEventBeans here
        System.out.println("avg=" + newEvent.get("avg(measurement)"));
    }

    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;

        //TODO: think where to place initialization
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.osmart.event");
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

        String customerContext =
                "create context SegmentedByCircuit partition by " +
                "customer and location and circuit from SensorEvent";

        String valueContext =
            "create context CategoryByValue " +
            "group temp < 65 as cold, " +
            "group temp between 65 and 85 as normal, " +
            "group temp > 85 as large " +
            "from SensorEvent";
        // context CategoryByValue select context.label, count(*) from SensorEvent

        // receive a new and old average measurement every 20 sec
        String expression =
                "context SegmentedByCircuit " +
                "select avg(measurement), context.key1, context.key2, context.key3 " +
                "from " +
                "SensorEvent.win:time_batch(7 sec)";

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);

    }

    public void execute(Tuple tuple) {
        // generate and send new event from input
        SensorEvent event = new SensorEvent(tuple.getString(0), tuple.getString(1), tuple.getString(2), tuple.getLong(3), tuple.getDouble(4));
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