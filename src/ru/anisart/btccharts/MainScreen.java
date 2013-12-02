package ru.anisart.btccharts;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.stockchart.StockChartView;
import org.stockchart.core.Area;
import org.stockchart.series.StockSeries;
import ru.anisart.btccharts.utils.StockDataGenerator;
import ru.anisart.btccharts.utils.StockDataGenerator.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainScreen extends Activity {

    private static final String TAG = "BTC-E Charts";
    private static final int POINTS_COUNT = 24;

    private StockSeries mStockSeries;
    private StockChartView mChartView;
    private StockDataGenerator mStockDataGenerator;
    private PriceView mPriceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        mPriceView = new PriceView(this);
        mPriceView.setTitle("BTC/USD");
        mPriceView.setValue(0);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mainLayout.addView(mPriceView, layoutParams);

        mChartView = new StockChartView(this);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        mainLayout.addView(mChartView, layoutParams);

        initChart();
//        refreshData();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshData();
                return true;
            case R.id.action_request:
                requestTicker();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initChart() {
        mStockSeries = new StockSeries();
        mStockSeries.getAppearance().setOutlineColor(Color.BLACK);
        Area area = mChartView.addArea();
        area.getSeries().add(mStockSeries);
        mStockDataGenerator = new StockDataGenerator();
    }

    private void refreshData() {
        Point point;
        for (int i = 0; i < POINTS_COUNT; i++) {
            point = mStockDataGenerator.getNextPoint();
            mStockSeries.addPoint(point.o, point.h, point.l, point.c);
        }
        mPriceView.setValue(mStockDataGenerator.getLastPrice());
        mChartView.invalidate();
    }

    private void requestTicker() {
//        String uri = "https://btc-e.com/api/3/ticker/btc_usd";
//
//        URL url = null;
//        try {
//            url = new URL(uri);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        InputStream in = null;
//        try {
//            in = url.openStream();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//        try {
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        String result = sb.toString();
//        Log.d(TAG, result);
//        try {
//            JSONObject json = new JSONObject(result);
//            Log.d(TAG, json.toString());
//
//            JSONArray names = json.names();
//            JSONArray values = json.toJSONArray(names);
//            for(int i=0;i<values.length();i++)
//            {
//                Log.i(TAG,"<jsonname"+i+">\n"+names.getString(i)+"\n</jsonname"+i+">\n"
//                        +"<jsonvalue"+i+">\n"+values.getString(i)+"\n</jsonvalue"+i+">");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        // The URL for making the GET request
//        final String url = "http://192.168.1.15:8080/index.json";
        final String url = "https://btc-e.com/api/3/ticker/btc_usd";

//        // Set the Accept header for "application/json"
//        HttpHeaders requestHeaders = new HttpHeaders();
//        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
//        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
//        requestHeaders.setAccept(acceptableMediaTypes);
//
//        // Populate the headers in an HttpEntity object to use for the request
//        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);
        restTemplate.getMessageConverters().add(messageConverter);

//        // Perform the HTTP GET request
//        ResponseEntity<BtceTicker> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
//                BtceTicker.class);

//        // convert the array to a list and return it
//        BtceTicker ticker = responseEntity.getBody();
        BtceTicker ticker = restTemplate.getForObject(url, BtceTicker.class);
        Log.d(TAG, ticker.toString());
    }
}
