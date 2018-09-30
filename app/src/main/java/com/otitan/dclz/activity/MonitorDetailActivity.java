package com.otitan.dclz.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lling.photopicker.PhotoPickerActivity;
import com.lling.photopicker.utils.OtherUtils;
import com.otitan.dclz.R;
import com.otitan.dclz.adapter.SelectPictureAdapter;
import com.otitan.dclz.permission.PermissionsChecker;
import com.titan.baselibrary.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 事件上报
 */
public class MonitorDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView mIv_back;
    @BindView(R.id.et_name)
    EditText mEt_name;
    @BindView(R.id.et_description)
    EditText mEt_description;
    @BindView(R.id.et_longitude)
    EditText mEt_longitude;
    @BindView(R.id.et_latitude)
    EditText mEt_latitude;
    @BindView(R.id.et_address)
    EditText mEt_address;
    @BindView(R.id.rv_picture)
    RecyclerView mRv_picture;
    @BindView(R.id.rv_audio)
    RecyclerView mRv_audio;
    @BindView(R.id.rv_video)
    RecyclerView mRv_video;

    @BindView(R.id.tv_picture)
    TextView mTv_picture;

    private List<String> picList = new ArrayList<>();

    /**动态检测权限*/
    private static final int REQUEST_CODE = 10000; // 权限请求码
    private PermissionsChecker permissionsChecker;
    private String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            android.Manifest.permission.MODIFY_AUDIO_SETTINGS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_detail);

        ButterKnife.bind(this);

        permissionsChecker = new PermissionsChecker(this);
        // 缺少权限时, 进入权限配置页面
        if (permissionsChecker.lacksPermissions(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }

        initView();
    }

    private void initView() {
        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int screenWidth = OtherUtils.getWidthInPx(getApplicationContext());
        int mColumnWidth = (screenWidth - OtherUtils.dip2px(getApplicationContext(), 4)) / 4;

        // 选择图片
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        mRv_picture.setLayoutManager(gridLayoutManager);
        SelectPictureAdapter spAdapter = new SelectPictureAdapter(this, picList, mColumnWidth);
        mRv_picture.setAdapter(spAdapter);
        spAdapter.setItemClickListener(new SelectPictureAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toSelectPic();
            }
        });
    }

    /**
     * 跳转到选择图片界面
     */
    public void toSelectPic() {
        if (picList.size() != 0 && picList.size() != 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("重新选择会覆盖之前的图片");
            builder.setMessage("是否重新选择");
            builder.setCancelable(true);
            builder.setPositiveButton("重新选择", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(MonitorDetailActivity.this, PhotoPickerActivity.class);
                    intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true); // 是否显示相机
                    intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI); // 选择模式（默认多选模式）
                    intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM); // 最大照片张数
                    startActivityForResult(intent, 1);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(16);
        }
        if (picList.size() == 0) {
            Intent intent = new Intent(MonitorDetailActivity.this, PhotoPickerActivity.class);
            intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true); // 是否显示相机
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI); // 选择模式（默认多选模式）
            intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM); // 最大照片张数
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // 图片选择成功
            picList.clear();
            picList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);

            // 选择图片
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
            mRv_picture.setLayoutManager(gridLayoutManager);

            int screenWidth = OtherUtils.getWidthInPx(getApplicationContext());
            int mColumnWidth = (screenWidth - OtherUtils.dip2px(getApplicationContext(), 4)) / 4;

            SelectPictureAdapter spAdapter = new SelectPictureAdapter(this, picList, mColumnWidth);
            mRv_picture.setAdapter(spAdapter);

            spAdapter.setItemClickListener(new SelectPictureAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (position == 0) {
                        toSelectPic();
                    }
                }
            });
        }
    }
}