package com.example.test;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.huobi.wss.event.MarketDepthSubResponse;
import com.huobi.wss.event.MarketDetailSubResponse;
import com.huobi.wss.event.MarketKLineSubResponse;
import com.huobi.wss.handle.WssMarketHandle;
import com.huobi.wss.event.MarketTradeDetailSubResponse;


@SpringBootApplication
public class TestApplication {

    private final Logger logger = LoggerFactory.getLogger(TestApplication.class);
//    合约订阅地址：wss://api.hbdm.com/swap-ws 国内可以使用：wss://api.btcgateway.pro/swap-ws

    //    private String URL = "wss://api.hbdm.com/swap-ws";//合约站行情请求以及订阅地址
    private String URL = "wss://api.btcgateway.pro/swap-ws";//合约站行情请求以及订阅地址
    WssMarketHandle wssMarketHandle = new WssMarketHandle(URL);


    /**
     * 订阅 KLine 数据
     *
     * 注：一个webSocket 可以一次订阅多个
     *
     * @throws URISyntaxException
     * @throws InterruptedException
     */

    public void test1() throws URISyntaxException, InterruptedException {
//        List<String> channels = Lists.newArrayList();
        List<String> channels =new ArrayList<>();
        channels.add("market.BTC-USD.kline.1min");
        // channels.add("market.BTC-USD.kline.5min");
        // channels.add("market.BTC-USD.kline.15min");
        // channels.add("market.BTC-USD.kline.30min");
        // channels.add("market.BTC-USD.kline.60min");
        // channels.add("market.BTC-USD.kline.4hour");
        // channels.add("market.BTC-USD.kline.1day");
        // channels.add("market.BTC-USD.kline.1week");
        // channels.add("market.BTC-USD.kline.1mon");

        wssMarketHandle.sub(channels, response -> {
            logger.info("kLineEvent用户收到的数据===============:{}", JSON.toJSON(response));
            Long currentTimeMillis = System.currentTimeMillis();
            MarketKLineSubResponse event = JSON.parseObject(response, MarketKLineSubResponse.class);
            logger.info("kLineEvent的ts为：{},当前的时间戳为：{},时间间隔为：{}毫秒", event.getTs(), currentTimeMillis, currentTimeMillis - event.getTs());
        });
        Thread.sleep(Integer.MAX_VALUE);


    }



    public static void main(String[] args) throws URISyntaxException, InterruptedException {
//        SpringApplication.run(TestApplication.class, args);

        TestApplication testApplication = new TestApplication();
        testApplication.test1();


    }

}
