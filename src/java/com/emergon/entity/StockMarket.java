package com.emergon.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//@Startup
//@Singleton
public class StockMarket {

    private List<Stock> stockList;
    
    public StockMarket(){
        initializeMarket();
    }
    
    private void initializeMarket(){
        stockList = new ArrayList();
        stockList.add(new Stock("IBM", 100, 23456));
        stockList.add(new Stock("Google", 512, 65432));
    }
    
    public synchronized Stock getStock(String name){
        Stock returnStock = null;
        Iterator<Stock> it = stockList.iterator();
        Stock stock;
        while(it.hasNext()){
            stock = it.next();
            if(stock.getName().equalsIgnoreCase(name)){
                returnStock = stock;
                break;
            }
        }
        
        return returnStock;
    }
    
    @Schedule(second="*/15", minute="*", hour="*")
    private void heartBeat(){
        Stock stock;
        Iterator<Stock> it = stockList.iterator();
        while(it.hasNext()){
            stock = it.next();
            if(Math.random()>0.5){
                stock.setPrice(stock.getPrice()+Math.random());
            }else{
                stock.setPrice(stock.getPrice()-Math.random());
            }
            stock.setVolume(Math.random()*10000);
        }
    }
}
