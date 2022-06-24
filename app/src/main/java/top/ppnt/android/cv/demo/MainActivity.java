package top.ppnt.android.cv.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.litongjava.android.utils.acp.AcpUtils;
import com.litongjava.android.utils.toast.ToastUtils;
import com.litongjava.android.view.inject.annotation.FindViewById;
import com.litongjava.android.view.inject.annotation.FindViewByIdLayout;
import com.litongjava.android.view.inject.annotation.OnClick;
import com.litongjava.android.view.inject.utils.ViewInjectUtils;
import com.mylhyl.acp.AcpListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import top.ppnt.android.cv.demo.services.FileService;
import top.ppnt.android.cv.demo.services.RecogPlateSevices;
import top.ppnt.android.cv.demo.services.RecogShapeSevices;
import top.ppnt.android.cv.demo.services.RecogTrafictLightService;

@FindViewByIdLayout(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

  @FindViewById(R.id.viewSelectPhoto)
  private MultiPickResultView viewSelectPhoto;

  @FindViewById(R.id.recogResult)
  private EditText recogResult;

  private FileService fileService = new FileService();
  private RecogTrafictLightService recogTrafictLightService = new RecogTrafictLightService();
  private RecogShapeSevices recogShapeSevices = new RecogShapeSevices();
  private RecogPlateSevices recogPlateSevices = new RecogPlateSevices();

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    ViewInjectUtils.injectActivity(this, this);
    //setContentView();
    initPermission();
    viewSelectPhoto.init(this, MultiPickResultView.ACTION_SELECT, null);

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    viewSelectPhoto.onActivityResult(requestCode, resultCode, data);

    PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {

      @Override
      public void onPickSuccess(ArrayList<String> photos, int requestCode) {//已经预先做了null或size为0的判断

      }

      @Override
      public void onPreviewBack(ArrayList<String> photos, int requestCode) {

      }

      @Override
      public void onPickFail(String error, int requestCode) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
      }

      @Override
      public void onPickCancle(int requestCode) {
        Toast.makeText(MainActivity.this, "取消选择", Toast.LENGTH_LONG).show();
      }
    });
  }

  @OnClick(R.id.btnSaveImage)
  public void btnSaveImage_OnClick(View view) {
    fileService.saveToLocal(this.getBaseContext());
  }

  @OnClick(R.id.btnRecogTrafictLight)
  public void btnRecogTrafictLight_OnClick(View view) throws IOException {
    ArrayList<String> photos = viewSelectPhoto.getPhotos();
    recogTrafictLightService.index(this.getBaseContext(), photos, recogResult);
  }


  @OnClick(R.id.btnRecogShape)
  public void btnRecogShape_OnClick(View view) throws IOException {
    ArrayList<String> photos = viewSelectPhoto.getPhotos();
    recogShapeSevices.index(this.getBaseContext(), photos, recogResult);
  }

  @OnClick(R.id.btnRecogPlate)
  public void btnRecogPlate_OnClick(View view) throws IOException {
    ArrayList<String> photos = viewSelectPhoto.getPhotos();
    recogPlateSevices.index(this.getBaseContext(), photos, recogResult);
  }

  /**
   * 初始权限
   */
  private void initPermission() {

    //创建acpListener
    AcpListener acpListener = new AcpListener() {
      @Override
      public void onGranted() {
        ToastUtils.defaultToast(getBaseContext(), "获取权限成功");
      }

      @Override
      public void onDenied(List<String> permissions) {
        ToastUtils.defaultToast(getBaseContext(), permissions.toString() + "权限拒绝");
      }
    };

    //写入外部设备权限,读取外部设备权限
    String[] permissions = new String[]{
      Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    AcpUtils.requestPermissions(this.getBaseContext(), permissions, acpListener);
  }
}