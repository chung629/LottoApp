package org.chunghyun.lottoapp.useInternet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.chunghyun.lottoapp.R;

public class Lotto_QR extends AppCompatActivity {

    WebView wv;
    EditText et;
    Button bt;
    IntentIntegrator integrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_qr);
        getSupportActionBar().setTitle("QR코드 입력");
        et = findViewById(R.id.et);
        wv = findViewById(R.id.wv);
        bt = findViewById(R.id.bt);
        WebSettings webSettings = wv.getSettings(); //자바 스크립트 사용을 할 수 있도록 합니다.
        webSettings.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient(){
            //페이지 로딩이 끝나면 호출됩니다.
            @Override
            public void onPageFinished(WebView view,String url){
                Toast.makeText(getApplicationContext(),"로딩 끝", Toast.LENGTH_SHORT).show(); }
        });
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //bt의 onClick을 실행
                    bt.callOnClick(); //키보드 숨기기
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;
            } });
        integrator = new IntentIntegrator(this); //바코드 안의 텍스트
        integrator.setPrompt("바코드를 사각형 안에 비춰주세요"); //바코드 인식시 소리 여부
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setCaptureActivity(CaptureActivity.class); //바코드 스캐너 시작
        integrator.initiateScan();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRonClick(v);
            }
        });
    }
    public void QRonClick(View view){
        String address = et.getText().toString();
        if(!address.startsWith("http://")){
            address = "http://" + address;
        }
        wv.loadUrl(address);
    }
    @Override public void onBackPressed() {
        if(wv.isActivated()){
            if(wv.canGoBack()){
                wv.goBack();
            }else{
                //스캐너 재시작
                integrator.initiateScan();
            }
        }else
            {
                super.onBackPressed();
            }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){

            }else{
                //qr코드를 읽어서 EditText에 입력해줍니다.
                et.setText(result.getContents()); //Button의 onclick호출
                bt.callOnClick();
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }else
            {
                super.onActivityResult(requestCode, resultCode, data);
            }
    }
}

