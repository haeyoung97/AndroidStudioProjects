package org.techtowm.ex1_study;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> tabNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        addTabName();
        setTabLayout();
        setViewPager();
    }

    private void addTabName(){
        tabNames.add("tab1");
        tabNames.add("tab2");
        tabNames.add("tab3");
    }

    private void setTabLayout(){
        for(String name : tabNames){
            tabLayout.addTab(tabLayout.newTab().setText(name));
        }
//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    }

    private void setViewPager(){
        // 어댑터를 만들어서 ViewPager에 넣어주는 역할
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        // ViewPager랑 TabLayout 연동
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 페이지를 선택했을 때 해당 Fragment를 선택하게 해주는 것
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());  //현재 위치 가져오기
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
