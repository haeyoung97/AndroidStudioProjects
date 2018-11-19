package org.techtowm.sharedpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = pref.edit();

        boolean check = pref.getBoolean("check1",false);

        TextView textView = findViewById(R.id.text);
        textView.setText(check ? "체크되었습니다." : "체크되지 않았습니다.");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editor.clear();
//                editor.commit();
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });
    }
}
