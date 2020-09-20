package com.xsp.stock;

import com.xsp.texas.StringUtils;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by shpng on 2017/12/22.
 */
public class Stocks {


    private String code;
    private String name;
    private int currentPrice = 1000;

    private int max = 10000;
    private ConcurrentLinkedQueue<StockOrder>[] buys = new ConcurrentLinkedQueue[max];
    private ConcurrentLinkedQueue<StockOrder>[] sells = new ConcurrentLinkedQueue[max];

    private int[] buyAmount = new int[max];
    private int[] sellAmount = new int[max];

    private ConcurrentLinkedQueue<StockOrder> dealStockOrders = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<DealOrder> dealOrders = new ConcurrentLinkedQueue<>();

    public Stocks() {
        for (int i = 0; i < max; i++) {
            buys[i] = new ConcurrentLinkedQueue();
            sells[i] = new ConcurrentLinkedQueue();
        }
    }

    public synchronized void buy(StockOrder stockOrder) {
        buys[stockOrder.getPrice()].offer(stockOrder);
        synchronized (buyAmount){
            buyAmount[stockOrder.getPrice()] += stockOrder.getAmount();
        }
    }

    public synchronized void sell(StockOrder stockOrder) {
        sells[stockOrder.getPrice()].offer(stockOrder);
        synchronized (sellAmount) {
            sellAmount[stockOrder.getPrice()] += stockOrder.getAmount();
        }
    }

    public ConcurrentLinkedQueue getMaxBuy() {
        for (int i = buys.length - 1; i >= 0; i--) {
            if (!buys[i].isEmpty()) {
                return buys[i];
            }
        }
        return null;
    }



    public ConcurrentLinkedQueue getMinSell() {
        for (int i = 0; i < sells.length; i++) {
            if (!sells[i].isEmpty()) {
                return sells[i];
            }
        }
        return null;
    }



    public PriceAndAmount getMaxBuyPrice(){
        for (int i = buyAmount.length - 1; i >= 0; i--) {
            if (buyAmount[i] > 0) {
                PriceAndAmount priceAndAmount = new PriceAndAmount();
                priceAndAmount.price = i;
                priceAndAmount.amount = buyAmount[i];
                return priceAndAmount;
            }
        }
        return null;
    }

    public PriceAndAmount getSecondMaxBuyPrice() {
        int max = -1;
        for (int i = buyAmount.length - 1; i >= 0; i--) {
            if (buyAmount[i] > 0) {
                if (max == -1) {
                    max = i;
                } else {
                    PriceAndAmount priceAndAmount = new PriceAndAmount();
                    priceAndAmount.price = i;
                    priceAndAmount.amount = buyAmount[i];
                    return priceAndAmount;
                }
            }
        }
        return null;
    }

    public PriceAndAmount getMinSellPrice(){
        for (int i = 0; i < sellAmount.length; i++) {
            if (sellAmount[i] > 0) {
                PriceAndAmount priceAndAmount = new PriceAndAmount();
                priceAndAmount.price = i;
                priceAndAmount.amount = sellAmount[i];
                return priceAndAmount;
            }
        }
        return null;
    }

    public PriceAndAmount getSecondMinSellPrice() {
        int min = -1;
        for (int i = 0; i < sellAmount.length; i++) {
            if (sellAmount[i] > 0) {
                if (min == -1) {
                    min = i;
                } else {
                    PriceAndAmount priceAndAmount = new PriceAndAmount();
                    priceAndAmount.price = i;
                    priceAndAmount.amount = sellAmount[i];
                    return priceAndAmount;
                }
            }
        }
        return null;
    }

    public void match() {
        ConcurrentLinkedQueue<StockOrder> buys = getMaxBuy();
        if (buys == null)
            return;
        ConcurrentLinkedQueue<StockOrder> sells = getMinSell();
        if (sells == null)
            return;
        StockOrder buyStockOrder = buys.poll();
        if (buyStockOrder == null)
            return;
        StockOrder sellStockOrder = sells.poll();
        if (sellStockOrder == null)
            return;


        int dealPrice = 0;
        int dealAmount = 0;
        if (buyStockOrder.getPrice() >= sellStockOrder.getPrice()) {
            PriceAndAmount secondbuy = getSecondMaxBuyPrice();
            if (secondbuy != null && sellStockOrder.getPrice() < secondbuy.price) {
                dealPrice = buyStockOrder.getPrice();
            }else{
                dealPrice = sellStockOrder.getPrice();
            }

        } else {
            //dealPrice = buyStockOrder.getPrice(); // 这个搞错了吧。。。
            return;
        }
        currentPrice = dealPrice;
        if (buyStockOrder.getLeftAmount() > sellStockOrder.getLeftAmount()) {
            dealAmount = sellStockOrder.getLeftAmount();
        } else {
            dealAmount = buyStockOrder.getLeftAmount();
        }
        synchronized (buyAmount) {
            buyAmount[buyStockOrder.getPrice()] -= dealAmount;
        }
        synchronized (sellAmount) {
            sellAmount[sellStockOrder.getPrice()] -= dealAmount;
        }
        buyStockOrder.setDealedAmount(buyStockOrder.getDealedAmount() + dealAmount);
        sellStockOrder.setDealedAmount(sellStockOrder.getDealedAmount() + dealAmount);
        if (buyStockOrder.getLeftAmount() == 0)
            dealStockOrders.offer(buyStockOrder);
        if (sellStockOrder.getLeftAmount() == 0)
            dealStockOrders.offer(sellStockOrder);
        DealOrder dealOrder = new DealOrder();
        dealOrder.setBuy(buyStockOrder);
        dealOrder.setSell(sellStockOrder);
        dealOrder.setDealPrice(dealPrice);
        dealOrder.setDealAmount(dealAmount);
        dealOrders.offer(dealOrder);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int[] getSellOrBuyOrderAmount(String buyOrSell) {
        if (StringUtils.equals(buyOrSell, "buy"))
            return buyAmount;
        else
            return sellAmount;
    }

    /**
     * 获取某个价格下的卖单有多少
     * @param maxPrice
     * @return
     */
    public int getSellOrderAmountUnderPrice(int maxPrice) {
        int mount = 0;
        for(int i=0;i<=maxPrice;i++) {
            mount += sellAmount[i];

        }
        return mount;
    }

    /**
     * 获取某个价格之上的买单有多少
     * @param minPrice
     * @return
     */
    public int getBuyOrderAmountUpperPrice(int minPrice) {
        int mount = 0;
        for(int i=minPrice;i< buyAmount.length;i++) {
            mount += buyAmount[i];

        }
        return mount;
    }
}
