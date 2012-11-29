package com.osmart;

import backtype.storm.LocalCluster;
import backtype.storm.utils.Utils;
import com.espertech.esper.client.*;
import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.SendableEvent;
import com.espertech.esperio.csv.CSVInputAdapter;
import com.osmart.event.SensorEvent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.*;


/**
 * @author Vadim Bobrov
 */
public class EventTimeTest {

    private static EPServiceProvider epService;

    @Mock private UpdateListener mockUpdateListener;
    //private TestUpdateListener mockUpdateListener = new TestUpdateListener();

    public EventTimeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setEventEngine(){
        Configuration config = new Configuration();
        config.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
        //config.addEventTypeAutoName("com.osmart.event");
        config.addEventType(com.osmart.event.SensorEvent.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
    }

    @Test
    public void sendEventTest(){
        // GIVEN
        doNothing().when(mockUpdateListener).update(any(EventBean[].class), any(EventBean[].class));


        // WHEN
        //String schema = "create schema MyEvent as com.osmart.event.SensorEvent starttimestamp timestamp";
        //epService.getEPAdministrator().createEPL(schema);

        // receive a new and old average measurement every 20 sec
        String expression =
                "select * " +
                        "from " +
                        "com.osmart.event.SensorEvent";


        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        statement.addListener(mockUpdateListener);


        // generate and send new event from input
        //SensorEvent event = new SensorEvent("", "", "", 101, 5.5);
        //EPServiceProviderManager.getDefaultProvider().getEPRuntime().sendEvent(event);
        //event = new SensorEvent("", "", "", 100, 5.5);
        //EPServiceProviderManager.getDefaultProvider().getEPRuntime().sendEvent(event);

        /////////////////
        File file = new File("/home/default/dev/osmart/src/main/resources/simulation.csv");
        AdapterInputSource source = new AdapterInputSource(file);

        CSVInputAdapter csvInputAdapter = new CSVInputAdapter(epService, source, "com.osmart.event.SensorEvent");
        csvInputAdapter.start();

        // THEN
        // make sure only one event is output
        verify(mockUpdateListener, times(4)).update(any(EventBean[].class), any(EventBean[].class));
    }
}
