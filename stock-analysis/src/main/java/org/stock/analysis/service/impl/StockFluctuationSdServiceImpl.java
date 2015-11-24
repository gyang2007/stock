package org.stock.analysis.service.impl;

import com.google.common.math.DoubleMath;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.analysis.service.IStockFluctuationSdService;
import org.stock.common.bean.StockExchangeInfo;

import java.util.List;

/**
 * Created by gyang on 15-11-25.
 */
public class StockFluctuationSdServiceImpl implements IStockFluctuationSdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockFluctuationSdServiceImpl.class);

    @Override
    public double standardDeviation(List<StockExchangeInfo> stockExchangeInfos) {
        double[] doubles = new double[stockExchangeInfos.size()];
        for(int i = 0; i < stockExchangeInfos.size(); i++) {
            doubles[i] = stockExchangeInfos.get(i).getClose();
        }

        // 平均收盘价格
        double meanVal = DoubleMath.mean(doubles);
        if(meanVal == 0.0) {
            LOGGER.warn("Calc mean value is 0.0, code = {}, type ={}", new Object[]{stockExchangeInfos.get(0).getCode(), stockExchangeInfos.get(0).getType()});
            return -1;
        }

        // 每一天收盘价于平均收盘价的比值
        double[] quotient = new double[stockExchangeInfos.size()];
        StockExchangeInfo info;
        for(int i = 0; i < stockExchangeInfos.size(); i++) {
            info = stockExchangeInfos.get(i);
            quotient[i] = (info.getClose() - meanVal) / meanVal;
        }
        // 比值的标准差
        StandardDeviation sd = new StandardDeviation();
        double standardValue = sd.evaluate(quotient);

        return standardValue;
    }
}
