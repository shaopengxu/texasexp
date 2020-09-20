package com.xsp.stock;

/**
 * Created by shpng on 2017/12/22.
 * 成交的交易
 */
public class DealOrder {

    private String serialno;
    private StockOrder buy;
    private StockOrder sell;
    private int dealPrice;
    private int dealAmount;

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public StockOrder getBuy() {
        return buy;
    }

    public void setBuy(StockOrder buy) {
        this.buy = buy;
    }

    public StockOrder getSell() {
        return sell;
    }

    public void setSell(StockOrder sell) {
        this.sell = sell;
    }

    public int getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(int dealPrice) {
        this.dealPrice = dealPrice;
    }

    public int getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(int dealAmount) {
        this.dealAmount = dealAmount;
    }
}
