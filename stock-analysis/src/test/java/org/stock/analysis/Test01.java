package org.stock.analysis;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.junit.Test;

/**
 * Created by gyang on 15-11-24.
 */
public class Test01 {

    @Test
    public void t01() {
        double[] ss = new double[]{4.8, 5, 5};
        StandardDeviation sd = new StandardDeviation();
        System.out.println(sd.evaluate(ss));
    }

    @Test
    public void t02() {
    }
}
