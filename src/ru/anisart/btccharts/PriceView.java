package ru.anisart.btccharts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: anisart
 * Date: 01.12.13
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class PriceView extends FrameLayout {

    private TextView mTitleView;
    private TextView mValueView;

    public PriceView(Context context) {
        super(context);
        init(context);
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PriceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.price_view, this);

        mTitleView = (TextView) findViewById(R.id.price_view_title);
        mValueView = (TextView) findViewById(R.id.price_view_value);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setValue(double value) {
        mValueView.setText(String.valueOf(value));
    }
}
