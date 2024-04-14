package io.stocktrades.constant;

import java.util.HashMap;
import java.util.Map;

public class StockConstants {

    public static String REGULAR="regular";
    public static String NSE="NSE";
    public static String CNC_PRODUCT="CNC";

    public static String KITE_HEADER="X-Kite-Version";

    public static String V3="3";
/*    public Map<String,String> exchangeType =new HashMap<String,String>(){{}
        put("NSE","NSE");

            }};*/
public static Map<String, String> exchangeType = new HashMap<String, String>(){
    {
        put("NSE","NSE");
        put("BSE","BSE");
        put("MF","MF");
        put("BFO","BFO");
        put("NFO","NFO");
        put("CDS","CDS");
    }};

    public static Map<String, String> validityType = new HashMap<String, String>(){
        {
            put("IOC","IOC");
            put("NRML","NRML");
            put("MIS","MIS");
            put("BO","BO");
            put("CO","CO");
        }};

    public static Map<String, String> transactionType = new HashMap<String, String>(){
        {
            put("BUY","BUY");
            put("SELL","SELL");
        }};

    public static Map<String, String> orderType = new HashMap<String, String>(){
        {
            put("SL","SL");
            put("MARKET","MARKET");
            put("LIMIT","LIMIT");
            put("SL-M","SL-M");
        }};

}
