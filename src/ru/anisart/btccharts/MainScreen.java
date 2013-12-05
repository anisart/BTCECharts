package ru.anisart.btccharts;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.stockchart.StockChartView;
import org.stockchart.core.Area;
import org.stockchart.series.StockSeries;
import ru.anisart.btccharts.utils.StockDataGenerator;
import ru.anisart.btccharts.utils.StockDataGenerator.Point;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends Activity {

    private static final String TAG = "BTC-E Charts";
//    private static final int POINTS_COUNT = 24;

    private StockSeries mStockSeries;
//    private StockChartView mChartView;
    private StockDataGenerator mStockDataGenerator;
//    private PriceView mPriceView;
    private LinearLayout mBtcUsdLine;
    private TextView mLastUsdView;
    private TextView mLowUsdView;
    private TextView mHighUsdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
//
//        mPriceView = new PriceView(this);
//        mPriceView.setTitle("BTC/USD");
//        mPriceView.setValue(0);
//        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        mainLayout.addView(mPriceView, layoutParams);
//
//        mChartView = new StockChartView(this);
//        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
//        mainLayout.addView(mChartView, layoutParams);
//
//        initChart();
//        refreshData();

        mBtcUsdLine = (LinearLayout) findViewById(R.id.btc_usd);
        mLastUsdView = (TextView) findViewById(R.id.last_usd);
        mLowUsdView = (TextView) findViewById(R.id.low_usd);
        mHighUsdView = (TextView) findViewById(R.id.high_usd);

        ImageView mImageView = (ImageView) findViewById(R.id.image_view);
        new DownloadImageTask(mImageView)
                .execute("http://bitcoincharts.com/charts/chart.png?width=720&noheader=1&m=btceUSD&r=1&i=30-min&t=S&v=1");
        requestTicker();
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
                requestTicker();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void initChart() {
//        mStockSeries = new StockSeries();
//        mStockSeries.getAppearance().setOutlineColor(Color.BLACK);
//        Area area = mChartView.addArea();
//        area.getSeries().add(mStockSeries);
//        mStockDataGenerator = new StockDataGenerator();
//    }

//    private void refreshData() {
//        Point point;
//        for (int i = 0; i < POINTS_COUNT; i++) {
//            point = mStockDataGenerator.getNextPoint();
//            mStockSeries.addPoint(point.o, point.h, point.l, point.c);
//        }
//        mPriceView.setValue(mStockDataGenerator.getLastPrice());
//        mChartView.invalidate();
//    }

    private void requestTicker() {
        new RequestTickerTask().execute();
    }

    private void updatePrices(BtceTicker ticker) {
        Log.d(TAG, ticker.toString());

        BtcePair btc_usd = ticker.getPair();
        mLastUsdView.setText(String.valueOf(btc_usd.getLast()));
        mLowUsdView.setText(String.valueOf(btc_usd.getLow()));
        mHighUsdView.setText(String.valueOf(btc_usd.getHigh()));
    }

    private void setPriceColor(int color) {
        mBtcUsdLine.setBackgroundColor(color);
    }

    private class RequestTickerTask extends AsyncTask<Void, Void, BtceTicker> {

        @Override
        protected void onPreExecute() {
            setPriceColor(Color.YELLOW);
        }

        @Override
        protected BtceTicker doInBackground(Void... params) {
            try {
                // The URL for making the GET request
//              final String url = "http://192.168.1.15:8080/index.json";
                final String url = "https://btc-e.com/api/3/ticker/btc_usd";

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(MediaType.TEXT_HTML);
                messageConverter.setSupportedMediaTypes(supportedMediaTypes);
                restTemplate.getMessageConverters().add(messageConverter);

                return restTemplate.getForObject(url, BtceTicker.class);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(BtceTicker ticker) {
            setPriceColor(Color.TRANSPARENT);
            updatePrices(ticker);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
