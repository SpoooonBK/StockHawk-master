package com.udacity.stockhawk.ui;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.model.StockHistory;

import timber.log.Timber;

/**
 * Created by spoooon on 3/23/17.
 */

public class GraphFragment extends Fragment {


    private static final String LOG_TAG = GraphFragment.class.getSimpleName();
    private StockHistory mStockHistory;
    private LineChart mLineChart;
    private TextView mTextViewSymbolHeader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
        mLineChart = (LineChart) rootView.findViewById(R.id.line_chart);
        mTextViewSymbolHeader = (TextView) rootView.findViewById(R.id.text_header_stock_symbol);

        Timber.v(LOG_TAG, "Fragment inflated");


        return rootView;
    }

    public void setStockHistory(String symbol) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        String[] projection = {Contract.Quote.COLUMN_HISTORY};


        Cursor cursor = contentResolver.query(
                Contract.Quote.makeUriForStock(symbol),
                projection,
                null,
                null,
                null);

        cursor.moveToFirst();
        String history = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
        mStockHistory = new StockHistory(symbol, history);
    }

    public void updateGraph(String symbol){

        mTextViewSymbolHeader.setText(symbol);

        setStockHistory(symbol);

        LineDataSet lineDataSet = new LineDataSet(mStockHistory.getHistoryEntries(), symbol);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.invalidate();

    }


    @Override
    public void onStart() {
        super.onStart();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
    }
}
