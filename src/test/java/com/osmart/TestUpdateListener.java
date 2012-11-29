package com.osmart;

import com.espertech.esper.client.*;
import com.osmart.event.SensorEvent;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Vadim Bobrov
 */
public class TestUpdateListener implements UpdateListener{

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean newEvent = newEvents == null ? null : newEvents[0];
        EventBean oldEvent = oldEvents == null ? null : oldEvents[0];

        // newEvent and oldEvent are MapEventBeans here
        System.out.println(newEvent.get("timestamp"));
    }
}
