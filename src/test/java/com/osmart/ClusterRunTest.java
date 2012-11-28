package com.osmart;

import backtype.storm.LocalCluster;
import backtype.storm.utils.Utils;
import org.junit.Test;

/**
 * @author Vadim Bobrov
 */
public class ClusterRunTest {

    @Test
    public void runCluster(){
        LocalCluster cluster = new LocalCluster();
        Utils.sleep(10000);
        cluster.shutdown();
    }
}
