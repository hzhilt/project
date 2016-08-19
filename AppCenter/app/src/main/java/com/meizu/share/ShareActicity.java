package com.meizu.share;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.appcenter.R;

/**
 * Created by root on 14-11-15.
 */
public class ShareActicity extends ActionBarActivity {

    private Toolbar mToolBar;
    private TextView mTextView;
    private EditText mEditText;
    private EditText mEditKeepState;
    private Button mShareButton;
    private Button mSaveButton;
    private Button mInputButton;
    private ImageView mImageView;
    private String mString;
    private TextView mTextNum;
    private Intent outIntent;
    private PopupWindow mPopupWindow;
    private Spinner mSpinner;
    private Spinner mSpinnerT;
    private ListView mListView;
    private ListView mShareListView;
    private EditText mBottomEdit;
    private static final String[] STRINGS = new String[]{
            "全部", "工作", "生活", "临时", "加密", "新建分组"
    };

    private static final String[] SHARE_STRINGS = new String[]{
            "全部", "工作", "生活", "临时", "加密", "新建分组", "全部", "工作", "生活", "临时", "加密", "新建分组", "全部", "工作", "生活", "临时", "加密", "新建分组"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //setTheme(R.style.MyThemeOne);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_main);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        mToolBar.setTitle("hello the world!");

        mToolBar.setLogo(R.drawable.gintama);

        //LinearLayout linearLayout = (LinearLayout) mToolBar.findViewById(R.id.toolbar_layout);
        TextView textView = (TextView) mToolBar.findViewById(R.id.content_toolbar);

        mSpinnerT = (Spinner) mToolBar.findViewById(R.id.inputType2);

        mShareListView = (ListView) findViewById(R.id.share_listView);

        mShareListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, SHARE_STRINGS));


        textView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);

        //setSupportActionBar(mToolBar);


        mTextView = (TextView) findViewById(R.id.saveText);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditKeepState = (EditText) findViewById(R.id.keep_state);
        mShareButton = (Button) findViewById(R.id.share);
        mSaveButton = (Button) findViewById(R.id.save);
        //mImageView = (ImageView)findViewById(R.id.imageView);
        mTextNum = (TextView) findViewById(R.id.textNum);
        mInputButton = (Button) findViewById(R.id.inputButton);
        mSpinner = (Spinner) findViewById(R.id.inputType);
        mBottomEdit = (EditText) findViewById(R.id.bottom_edit);


        initPopupWindow();

        mInputButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {

                    mPopupWindow.showAsDropDown(v, 200, 0, Gravity.RIGHT);
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinnerT.setAdapter(adapter);


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTextNum.setText(s.length() + "/140");
            }
        });

        outIntent = getIntent();
        String action = outIntent.getAction();
        String type = outIntent.getType();

        if (Intent.ACTION_SEND.equals(action)) {
            if (type.startsWith("text/")) {
                shareText();
            } else {
                //shareImage();
            }
        }
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mString = mEditText.getText().toString();
                if (TextUtils.isEmpty(mString))
                    Toast.makeText(ShareActicity.this, "你没有输入", Toast.LENGTH_SHORT).show();
                else {

                    mEditText.setText(null);
                    mEditKeepState.setTextKeepState(mString);
                    mTextView.setText(mEditKeepState.getSelectionStart() + "--" + mEditKeepState.getSelectionEnd() + "--" + mString.length());
                }
            }
        });

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_SEND);
//                intent.putExtra(Intent.EXTRA_TEXT, mString);
//                intent.setType("text/plain");
//                startActivity(intent);
                showToast();
            }
        });
    }

    private void showToast() {

        if (true) {
            Toast.makeText(ShareActicity.this, "showtoast", Toast.LENGTH_LONG).show();
            finish();
//            return;
        }

        Toast.makeText(ShareActicity.this, "showtoast1111111111111", Toast.LENGTH_LONG).show();
    }

    private void initPopupWindow() {

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.share_popupwindow_list, null);
        mListView = (ListView) layout.findViewById(R.id.popupList);
        mPopupWindow = new PopupWindow(layout);
        mPopupWindow.setFocusable(true);

        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, STRINGS));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mInputButton.setText(STRINGS[position]);
                mPopupWindow.dismiss();
            }
        });
        mListView.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        mPopupWindow.setWidth(mListView.getMeasuredWidth());
        mPopupWindow.setHeight((mListView.getMeasuredHeight() + 20)
                * 6);

        mPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(
                R.drawable.blue));
        mPopupWindow.setOutsideTouchable(true);
    }

//    private void shareImage() {
//        //to-do
//        Uri imageUri = outIntent.getParcelableExtra(Intent.EXTRA_STREAM);
//        if (imageUri != null) {
//            mImageView.setImageURI(imageUri);
//        }
//    }

    private void shareText() {
        String string = outIntent.getStringExtra(Intent.EXTRA_TEXT);
        mEditText.setText(string + " #shared of App");
    }
}
