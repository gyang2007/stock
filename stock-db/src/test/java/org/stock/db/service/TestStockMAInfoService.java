package org.stock.db.service;

import org.junit.Test;
import org.stock.db.util.SpringService;

/**
 * Created by gyang on 15-9-8.
 */
public class TestStockMAInfoService {

    private IStockMAInfoService service = (IStockMAInfoService) SpringService.getInstance().getService("stockMAInfoService");

    @Test
    public void t1() {
        System.out.println(service);
    }
}
