package com.xsp.stock;

import com.xsp.texas.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shpng on 2017/12/21.
 */
public class Investor {

    private String id;

    private int maxPrice;

    private int midPrice;

    private int minPrice;

    private int expectAmount;

    private List<StockOrder> unfinishStockOrders = new ArrayList<>(); // 未成交完成的单
    private List<StockOrder> getUnfinishStockOrders = new ArrayList<>(); // 已经成交的单
    private List<DealOrder> dealOrders = new ArrayList<>();


    public static final String BUY = "buy";
    public static final String SELL = "sell";


    private String buyOrSell; // "buy" or "sell"

    private TradeCenter tradeCenter = TradeCenter.getInstance();

    /**
     * 策略一
     * 交易员今天要买多少量，按什么价格范围成交
     * 交易员今天要卖多少量，按什么价格范围成交
     * 假如低于某价格，则多成交多少量，假如高于某价格，则少成交多少量
     * 价格偏离度买卖意愿：
     * 当交易员要买，价格越低意愿越大，价格越高意愿越小
     * 当交易员要卖，价格越高意愿越大，价格越低意愿越小，由此算出想要下单的概率（意愿）
     * 什么情况下愿意加价买入，降价卖出
     *
     * @return
     */

    public void order1() {


        Stocks stocks = tradeCenter.getStocks();
        /*
        检查当前的挂单，是否能按自己想要的价格成交，如果可以则挂单，
        否则按比例挂单
         */
        if (StringUtils.equals(buyOrSell, BUY)) {
            // 买
            // 查找小于midPrice的卖单
            int sellAmount = stocks.getSellOrderAmountUnderPrice(midPrice);
            if (sellAmount > 0) {
                // 挂买单
                StockOrder stockOrder = new StockOrder();
                stockOrder.setBuyorsell(BUY);
                stockOrder.setAmount(sellAmount);
                stockOrder.setPrice(midPrice);
                stockOrder.setInvestorId(id);
                stockOrder.setStockCode(stocks.getCode());
                unfinishStockOrders.add(stockOrder);
                stocks.buy(stockOrder);
            } else {
                // 获取当前最低卖价
                PriceAndAmount priceAndAmount = stocks.getMinSellPrice();
                if (priceAndAmount == null) {
                    // 没人挂卖单 TODO
                    // 比当前最低买价挂更高的价格？ 但不超过maxPrice
                }
                if (priceAndAmount.price > maxPrice) {
                    // 大于最大金额，不挂单 TODO
                    return;
                }
                // 计算挂单的概率
                if (Math.random() < getBuyProbability(priceAndAmount.price)) {
                    // 挂买单


                }
                // 卖势较猛，预计会有更低价，所以挂更低价？
                // 本身希望挂个低价成交

            }
        }

    }

    /**
     * 策略二
     * 随便挂个比当前价低的买单
     * 随便挂个比当前价高的卖单
     */
    public void order2(){

    }

    /**
     * 策略三
     * 挂一个比当前价低很多的买单
     * 挂一个比当前价高很多的卖单
     */
    public void order3(){

    }

    /**
     * 策略四
     * 判断当前的趋势，
     * 买方：如果下跌则不买，上涨则买
     * 卖方：如果上涨则不卖，下跌则卖
     *
     */
    public void order4(){

    }

    /**
     * 策略五
     * 和策略四相反
     *
     */
    public void order5(){

    }
    private double getBuyProbability(int price){
        return (maxPrice - price)*1.0/(maxPrice-midPrice);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
