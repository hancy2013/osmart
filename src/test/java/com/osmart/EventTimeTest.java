package com.osmart;

import backtype.storm.LocalCluster;
import backtype.storm.utils.Utils;
import com.espertech.esper.client.*;
import com.osmart.event.SensorEvent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import static org.mockito.Mockito.*;


/**
 * @author Vadim Bobrov
 */
public class EventTimeTest {

    private static EPServiceProvider epService;

    //@Mock
    private TestUpdateListener mockUpdateListener = new TestUpdateListener();

    public EventTimeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setEventEngine(){
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.osmart.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);
    }

    @Test
    public void sendEventTest(){
        // GIVEN
        //doNothing().when(mockUpdateListener).update(any(EventBean[].class), any(EventBean[].class));


        // WHEN
        String schema = "create schema MyEvent as com.osmart.event.SensorEvent starttimestamp timestamp";
        epService.getEPAdministrator().createEPL(schema);

        // receive a new and old average measurement every 20 sec
        String expression =
                "select * " +
                        "from " +
                        "MyEvent.std:lastevent()";

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        statement.addListener(mockUpdateListener);

        // generate and send new event from input
        SensorEvent event = new SensorEvent("", "", "", 101, 5.5);
        EPServiceProviderManager.getDefaultProvider().getEPRuntime().sendEvent(event);
        event = new SensorEvent("", "", "", 100, 5.5);
        EPServiceProviderManager.getDefaultProvider().getEPRuntime().sendEvent(event);


        // THEN
        // make sure only one event is output
       //verify(mockUpdateListener, times(2)).update(any(EventBean[].class), any(EventBean[].class));
    }
}
