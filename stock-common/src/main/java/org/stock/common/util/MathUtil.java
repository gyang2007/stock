package org.stock.common.util;

/**
 * 数学计算
 * <p>
 * Created by gyang on 15-11-17.
 */
public final class MathUtil {

    private MathUtil() {

    }

    /**
     * 标准差
     *
     * @param sampleValueArr
     * @return
     */
    public static double standardDeviation(double[] sampleValueArr) {
        int n = 0;
        double sum = 0;
        double sum2 = 0;

        for (double sampleValue : sampleValueArr) {
            n++;
            sum += sampleValue;
            sum2 += sampleValue * sampleValue;
        }

        return Math.sqrt(sum2 / n - sum * sum / n / n);
    }

    public static void main(String[] args) {
        double[] ss = new double[]{4.8, 5, 5};
        System.out.println(standardDeviation(ss));
    }
}
