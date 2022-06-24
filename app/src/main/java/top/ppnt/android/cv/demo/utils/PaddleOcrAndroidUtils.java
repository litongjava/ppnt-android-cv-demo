package top.ppnt.android.cv.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.litongjava.android.paddle.ocr.OCRPredictorNative;
import com.litongjava.android.paddle.ocr.Predictor;


public class PaddleOcrAndroidUtils {
  private static Predictor predictor = new Predictor();

  static {
    //加载类库和模型
    OCRPredictorNative.loadLibrary();
  }

  public static boolean init(Context context) {
    return predictor.init(context);
  }

  public static void releaseModel() {
    predictor.releaseModel();
  }

  /**
   * 识别图片,返回数据
   *
   * @param image
   * @return
   */
  public static String ocr(Bitmap image) {
    if (predictor.isLoaded()) {
      predictor.setInputImage(image);
    } else {
      return null;
    }
    if (predictor.runModel()) {
      String result = predictor.outputResult();
      return result;
    }
    return null;
  }
}
