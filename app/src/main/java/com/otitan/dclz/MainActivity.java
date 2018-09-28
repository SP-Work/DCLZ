package com.otitan.dclz;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.otitan.dclz.adapter.MyFragmentPagerAdapter;
import com.otitan.dclz.fragment.GroundFragment;
import com.otitan.dclz.fragment.HomeFragment;
import com.otitan.dclz.fragment.InspectionFragment;
import com.otitan.dclz.fragment.MonitorFragment;
import com.otitan.dclz.fragment.SatelliteFragment;
import com.otitan.dclz.permission.PermissionsChecker;
import com.otitan.dclz.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    /*@BindView(R.id.vp_main)
    ViewPager mVp_main;*/
    @BindView(R.id.vp_main)
    NoScrollViewPager mVp_main;
    @BindView(R.id.rg_main)
    RadioGroup mRg_main;
    @BindView(R.id.rb_home)
    RadioButton mRb_home;
    @BindView(R.id.rb_satellite)
    RadioButton mRb_satellite;
    @BindView(R.id.rb_ground)
    RadioButton mRb_ground;
    @BindView(R.id.rb_inspection)
    RadioButton mRb_inspection;
    @BindView(R.id.rb_monitor)
    RadioButton mRb_monitor;

    /*动态检测权限*/
    private static final int REQUEST_CODE = 10000; // 权限请求码
    private PermissionsChecker permissionsChecker;
    private String[] permissions = new String[]{
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        permissionsChecker = new PermissionsChecker(this);
        // 缺少权限时, 进入权限配置页面
        if (permissionsChecker.lacksPermissions(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }

        initView();
    }

    private void initView() {
        List<Fragment> fmList = new ArrayList<>();
        fmList.add(new HomeFragment()); // 首页
        fmList.add(new SatelliteFragment()); // 卫星遥感
        fmList.add(new GroundFragment()); // 地基遥感
        fmList.add(new InspectionFragment()); // 移动巡检
        fmList.add(new MonitorFragment()); // 监测周报

        mVp_main.setScanScroll(true); // 设置ViewPager不可滑动
        mVp_main.addOnPageChangeListener(this); // ViewPager滑动监听
        mVp_main.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fmList));
        mRg_main.setOnCheckedChangeListener(this); // RadioGroup点击监听
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_home:
                mVp_main.setCurrentItem(0, false);
                break;
            case R.id.rb_satellite:
                mVp_main.setCurrentItem(1, false);
                break;
            case R.id.rb_ground:
                mVp_main.setCurrentItem(2, false);
                break;
            case R.id.rb_inspection:
                mVp_main.setCurrentItem(3, false);
                break;
            case R.id.rb_monitor:
                mVp_main.setCurrentItem(4, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRb_home.setChecked(true);
                break;
            case 1:
                mRb_satellite.setChecked(true);
                break;
            case 2:
                mRb_ground.setChecked(true);
                break;
            case 3:
                mRb_inspection.setChecked(true);
                break;
            case 4:
                mRb_monitor.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
