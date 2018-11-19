package org.techtowm.sqlitetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);

        SQLiteDatabase db = openOrCreateDatabase("data", MODE_PRIVATE, null);  // 앱폴더/databases/data.db
        db.execSQL("create table if not exists birthday (" +
                "name text, " +
                "date text" +
                ");" );
        db.execSQL("insert into birthday values ('홍길동', '2018-01-01')");
        db.execSQL("insert into birthday values ('박해영', '2018-04-10')");

//        Cursor cursor = db.rawQuery("select * from birthday;", null);

        Cursor cursor = db.rawQuery("select * from birthday where name LIKE '%해%';", null);

        String str = "";
        while(cursor.moveToNext()){
            str += cursor.getString(0) + " | " + cursor.getString(1) + "\n";

        }
        textView.setText(str);
    }
}
