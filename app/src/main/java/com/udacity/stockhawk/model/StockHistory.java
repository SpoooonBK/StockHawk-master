package com.udacity.stockhawk.model;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Used to hold the stock history data and used by the GraphFragment to extract data and display on graph
 */

public class StockHistory {


    private String mSymbol;
    private String mHistory;
    private List<Entry> mHistoryData = new ArrayList<>();


    public StockHistory(String symbol, String history) {
        this.mSymbol = symbol;
        this.mHistory = history;
        setHistoryData();
    }

    public List<Entry> getHistoryEntries() {
        return mHistoryData;
    }

    public void setHistoryData() {

            parseHistoryString();
    }


    public void parseHistoryString(){

        //Splits the historyString into an array of strings by new line
        String[] lines = mHistory.split("[\\r\\n]+");
        for(String line: lines){
            //Parses each line by locating the "," and extracting the data substrings
            //The date will be the x axis and the endPrice will be the y axis of the GraphFragment Graph
            long dateInMillis = Long.parseLong(line.substring(0, line.indexOf(",")-1));
            float endPrice = Float.parseFloat(line.substring(line.indexOf(",")+1));
            mHistoryData.add(new Entry(dateInMillis, endPrice));
        }
    }
}
