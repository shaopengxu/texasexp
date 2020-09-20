package com.xsp.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by shpng on 2017/12/21.
 */
public class TradeCenter {

    // 只有一只股票
    private Stocks stocks = new Stocks();


    private TradeCenter() {

    }

    private static TradeCenter tradeCenter = new TradeCenter();

    public static TradeCenter getInstance(){
        return tradeCenter;
    }

    public Stocks getStocks(){
        return stocks;
    }

    /**
     * 初始化股民
     */
    public void init() {

    }


    /*
    模拟交易现场
     有哪几种买入模式
     1 当价格低于某个价格，则买入
     2 当价格当天跌了百分之几，则买入
     3 当价格走了一个凹型的曲线正在上升，则买入
     4 当成交量加大时买入
     对应卖出
     */
}
