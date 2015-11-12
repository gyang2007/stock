package org.stock.db.service.impl;

import org.springframework.stereotype.Service;
import org.stock.db.bean.StockMAInfo;
import org.stock.db.dao.IStockMAInfoDao;
import org.stock.db.service.IStockMAInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gyang on 15-9-8.
 */
@Service(value = "stockMAInfoService")
public class StockMAInfoServiceImpl implements IStockMAInfoService {

//    @Resource(name = "stockMAInfoDao")
    private IStockMAInfoDao stockMAInfoDao;

    @Override
    public List<StockMAInfo> selectAllStockMAInfoList() {
        return stockMAInfoDao.selectAll();
    }

    @Override
    public List<StockMAInfo> selectStockMAInfoListByCodeType(StockMAInfo stockMAInfo) {
        return stockMAInfoDao.selectByCodeType(stockMAInfo);
    }

    @Override
    public int saveStockMAInfo(StockMAInfo info) {
        return stockMAInfoDao.insert(info);
    }

    @Override
    public int saveStockMAInfoList(List<StockMAInfo> infos) {
        return stockMAInfoDao.insertBatch(infos);
    }
}
