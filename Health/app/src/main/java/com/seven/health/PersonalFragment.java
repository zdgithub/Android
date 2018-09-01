package com.seven.health;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seven.health.aboutpersonal.CollectionActivity;
import com.seven.health.aboutpersonal.PersonalHealthActivity;
import com.seven.health.aboutpersonal.PersonalInformationActivity;
import com.seven.health.aboutpersonal.SettingActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lenovo on 2018/8/16.
 */

public class PersonalFragment extends BasePageTitleFragment {
    private ImageView touxiang;
    private Bitmap head; // 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径

    private TextView setting;
    private TextView userid;
    private LinearLayout profile;
    private LinearLayout health_file;
    private LinearLayout mycollect;
    private LinearLayout share;

    private SharedPreferences nickprefs;


    @Override
    protected View initView() {
        setTitleText("个 人 中 心", false);
        View personFragment = View.inflate(getContext(), R.layout.fragment_personal_center, null);

        touxiang = (ImageView) personFragment.findViewById(R.id.personal_head);
        setting = (TextView) personFragment.findViewById(R.id.personal_setting);
        userid = (TextView) personFragment.findViewById(R.id.personal_userid);
        profile = (LinearLayout) personFragment.findViewById(R.id.personal_information);
        health_file = (LinearLayout) personFragment.findViewById(R.id.personal_health_file);
        share = (LinearLayout) personFragment.findViewById(R.id.personal_share);
        mycollect = (LinearLayout) personFragment.findViewById(R.id.personal_collect);

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            //如果本地有头像图片的话
            touxiang.setImageBitmap(bt);
        } else {
            //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略

        }
        return personFragment;

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences nick = getActivity().getSharedPreferences("nick", Context.MODE_PRIVATE);
        String tmp = nick.getString("nickname","昵称");
        Log.e("当前昵称为=",tmp);
        userid.setText(tmp);
    }


    @Override
    protected void initData() {


        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.personal_head:// 更换头像
                        showTypeDialog();
                        break;
                }
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                startActivity(intent);
            }
        });
        health_file.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalHealthActivity.class);
                startActivity(intent);
            }
        });
        mycollect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickShare();
            }
        });
    }

    public void onClickShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "https://pan.baidu.com/s/16k983GkZRcYuiOEbSE69Mg");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, null));
    }


    //选择上传头像照片
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.personal_selectphoto, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册里面取相片的返回结果
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            //相机拍照后的返回结果
            case 2:
                if (resultCode == getActivity().RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            //调用系统裁剪图片后
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        touxiang.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
