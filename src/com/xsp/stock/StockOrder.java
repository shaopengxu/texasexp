package com.xsp.stock;

/**
 * Created by shpng on 2017/12/21.
 * 下单
 */
public class StockOrder {

    private String investorId;
    private String serialno;
    private String stockCode;

    private String buyorsell; // "buy" or "sell"
    private int price;
    private int amount;

    /**
     * 已经成交的数量
     */
    private int dealedAmount;

    public int getLeftAmount(){
        return amount - dealedAmount;
    }

    public int getDealedAmount() {
        return dealedAmount;
    }

    public void setDealedAmount(int dealedAmount) {
        this.dealedAmount = dealedAmount;
    }

    public String getInvestorId() {
        return investorId;
    }

    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getBuyorsell() {
        return buyorsell;
    }

    public void setBuyorsell(String buyorsell) {
        this.buyorsell = buyorsell;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
