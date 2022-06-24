package top.ppnt.android.cv.demo.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.litongjava.opencv.model.DebugInfo;
import com.litongjava.opencv.utils.MatUtils;
import com.litongjava.opencv.utils.RecognitionPlateUtils;

import org.apache.commons.io.FileUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import top.ppnt.android.cv.demo.utils.PaddleOcrAndroidUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022-06-23
 */
public class RecogPlateSevices {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  public void index(Context context, ArrayList<String> photos, EditText recogResult) throws IOException {

    StringBuffer message = new StringBuffer();
    for (String imagePath : photos) {
      DebugInfo debugInfo = new DebugInfo();
      debugInfo.setImagePath(imagePath);

      //获取扩展名
      String extensionName = debugInfo.getExtensionName();
      //获取图片流
      byte[] imageBytes = FileUtils.readFileToByteArray(new File(imagePath));
      //读取图片
      Mat src = MatUtils.imread(imageBytes);
      //plateArea,裁剪图图片区域
      Mat plateArea = RecognitionPlateUtils.pre(src, debugInfo);

      //重新编码
      MatOfByte mob = new MatOfByte();
      Imgcodecs.imencode("." + extensionName, plateArea, mob);
      //取出字节码
      imageBytes = mob.toArray();
      //bytes转Bitmap
      Bitmap plateBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
      //输入图片
      String ocrResult = PaddleOcrAndroidUtils.ocr(plateBitmap);
      //识别结果1: 国J556C2
      if (!StringUtils.isEmpty(ocrResult)) {
        String[] split = ocrResult.split("1: ");
        log.info("ocrResult:{}", split[1]);
        message.append("图片名称:" + debugInfo.getFilename() + ",识别结果:" + split[1]);
      } else {
        String toastMessage = "识别结果为空";
        ToastUtils.showLong(toastMessage);
        log.info(toastMessage);
      }
    }
    //显示识别结果
    recogResult.setText(message.toString());
  }
}
