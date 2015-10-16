package com.example.jtec.test_view_web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static TextView textView;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new WebAppInterface(this), "Android");
        wv.getSettings().setBuiltInZoomControls(true);
        wv.clearCache(true);
        wv.loadUrl("http://192.168.0.15/a/web/js.html");
        wv.addJavascriptInterface(new Object() {
            @JavascriptInterface           // For API 17+
            public void performClick(String strl) throws IOException {
                String Variable = strl+" test default";
                Toast.makeText (MainActivity.this, Variable, Toast.LENGTH_SHORT).show();
                //textView.setText(strl);
                //action_btn();
                music_play();
            }
        }, "ok");
    }
    public void music_play() throws IOException {
//        String url = "http://192.168.0.15/a/music/animals__martin.mp3"; // your URL here
//        MediaPlayer mPlayer = new MediaPlayer();
//        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//            mPlayer.setDataSource(url);
//        } catch (IllegalArgumentException e) {
//            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
//        } catch (SecurityException e) {
//            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
//        } catch (IllegalStateException e) {
//            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mPlayer.start();
        String url = "http://192.168.0.15/a/music/animals__martin.mp3"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare(); // might take long! (for buffering, etc)
        mediaPlayer.start();
    }

    public void btn_view(View v) throws IOException {

        WebAppInterface data_get = new WebAppInterface(this);
        textView.setText(data_get.str_link);
        music_play();
    }
    public void action_btn(){
        //textView = (TextView) findViewById(R.id.textView);
        TextView textView = new TextView(this);
        WebAppInterface data_get = new WebAppInterface(this);
        textView.setText(data_get.str_link+"_default");
        setContentView(textView);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}

class WebAppInterface {
    public static String str_link = "null";
    Context mContext;
    MainActivity main =  new MainActivity();

    /**
     * Instantiate the interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        str_link = toast + "12345";
        //Toast.makeText(mContext, str_link, Toast.LENGTH_SHORT).show();
        //main.action_btn();
    }
}
