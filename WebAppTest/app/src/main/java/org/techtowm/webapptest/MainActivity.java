package org.techtowm.webapptest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static int INTENT_CALL_IMAGE = 101;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.webview);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        web.loadUrl("https://aooen.com/web.html");

        web.addJavascriptInterface(new JsObject(),"jsObject");

        final Context myApp = this;

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result){
                new AlertDialog.Builder(myApp)
                    .setTitle("알림")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            result.confirm();
                    }
                })
                .setCancelable(false)
                        .create()
                        .show();

                return true;
            }
        });

        web.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType(mimetype);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                if(!Uri.parse(url).getHost().endsWith("aooen.com")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data){
        super.onActivityResult(req,res,data);
        if(req == INTENT_CALL_IMAGE && res == RESULT_OK){
            Bitmap bitmap = null;
            if(data.hasExtra("data")){
                bitmap = (Bitmap)data.getExtras().get("data");
            }else{
                Uri img = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cur = getContentResolver().query(img, filePath, null,null,null);
                cur.moveToFirst();
                int columnIndex = cur.getColumnIndex(filePath[0]);
                String imgPath = cur.getString(columnIndex);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath, options);
            }
            web.loadUrl("javascript:changeimg('data:image/png;base64,"+imageEncoding(bitmap)+"')");
        }
    }

    private String imageEncoding(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,50,stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes,0);
    }


    private class JsObject{
        @JavascriptInterface
        public void file(String name, String name1, String name2){
            List<Intent> cameras = new ArrayList<Intent>();
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            List<ResolveInfo> listCam = getPackageManager().queryIntentActivities(camera,0);

            for(ResolveInfo res:listCam){
                Intent intent = new Intent(camera);
                cameras.add(intent);
            }


            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(intent, "이미지 선택");

            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameras.toArray(new Parcelable[]{}));
            startActivityForResult(chooserIntent, INTENT_CALL_IMAGE);

        }
    }
}
