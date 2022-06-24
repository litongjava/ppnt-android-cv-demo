package top.ppnt.android.cv.demo.application;

import android.app.Application;
import android.util.Log;

import com.hss01248.glidepicker.GlideIniter;
import com.litongjava.android.paddle.ocr.OCRPredictorNative;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import me.iwf.photopicker.PhotoPickUtils;
import top.ppnt.android.cv.demo.utils.PaddleOcrAndroidUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022/3/8
 */
public class App extends Application {
  private static final String TAG = "App";

  @Override
  public void onCreate() {
    initPhotoPicker();
    initOpenCv();
    initPaddleLite();
    super.onCreate();
  }

  private void initPaddleLite() {
    //加载库,初始化模型
    PaddleOcrAndroidUtils.init(getBaseContext());
    Log.e(TAG, "initPaddleLite: 加载库,初始化模型");
  }

  private void initPhotoPicker() {
    //初始化PhotoPicker
    PhotoPickUtils.init(getApplicationContext(), new GlideIniter());
  }

  private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
    @Override
    public void onManagerConnected(int status) {
      switch (status) {
        case LoaderCallbackInterface.SUCCESS: {
          Log.i(TAG, "OpenCV loaded successfully");
        }
        break;
        default: {
          Log.i(TAG, "OpenCV loaded not successfully");
          super.onManagerConnected(status);
        }
        break;
      }
    }
  };

  private void initOpenCv() {
    if (!OpenCVLoader.initDebug()) {
      Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
      OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
    } else {
      Log.d(TAG, "OpenCV library found inside package. Using it!");
      mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }
  }
}