package com.meizu.paint;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.appcenter.R;

/**
 * Created by root on 15-1-5.
 */
public class PaintActivity extends Activity implements ColorPickerDialog.OnColorChangedListener{

    private PaintView mPaintView;

    private Button mMinusButton;
    private Button mPlusButton;

    private Button mUndoButton;
    private Button mRedoButotn;

    private Button mClearButton;
    private Button mEraseButton;
    private Button mPenButton;

    private TextView mTextView;

    private int WIDTH = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.paint_main);

        mPaintView = (PaintView)findViewById(R.id.paintView);

        mMinusButton = (Button)findViewById(R.id.minus);
        mPlusButton  = (Button)findViewById(R.id.plus);
        mUndoButton  = (Button)findViewById(R.id.undo);
        mRedoButotn  = (Button)findViewById(R.id.redo);

        mClearButton = (Button)findViewById(R.id.clear);
        mEraseButton = (Button)findViewById(R.id.erase);
        mPenButton   = (Button)findViewById(R.id.pen);

        mTextView    = (TextView)findViewById(R.id.widthText);
        mTextView.setText("20");

        mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (WIDTH > 1) {
                    WIDTH--;
                    mTextView.setText(WIDTH + "");

                if (WIDTH > 1){
                    WIDTH --;
                    mTextView.setText(WIDTH+"");

                    mPaintView.setWidth(WIDTH);
                } else {
                    Toast.makeText(PaintActivity.this, "min", Toast.LENGTH_SHORT).show();
                }
            }
        }});

        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(WIDTH < 50){
                    WIDTH ++;
                    mTextView.setText(WIDTH+"");
                    mPaintView.setWidth(WIDTH);
                }
                else {
                    Toast.makeText(PaintActivity.this,"max",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaintView.undo();
            }
        });

        mRedoButotn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaintView.redo();
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaintView.clear();
            }
        });

        mPenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WIDTH = (int)mPaintView.getWidthFromView();
                mTextView.setText(WIDTH+"");
                mPaintView.setWidth(WIDTH);
                mPaintView.pen();
                //new ColorPickerDialog(PaintActivity.this, PaintActivity.this, Color.RED).show();
            }
        });

        mEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WIDTH = (int)mPaintView.getWidthFromView();
                mTextView.setText(WIDTH+"");
                mPaintView.setWidth(WIDTH);
                mPaintView.erase();
            }
        });
    }

    @Override
    public void colorChanged(int color) {
        mPaintView.setColor(color);
        mPaintView.pen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.paint_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_home:
                new ColorPickerDialog(PaintActivity.this, PaintActivity.this, Color.RED).show();
                break;
        }
        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }
}
