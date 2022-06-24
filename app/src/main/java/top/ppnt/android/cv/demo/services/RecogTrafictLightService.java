package top.ppnt.android.cv.demo.services;

import android.content.Context;
import android.widget.EditText;

import com.litongjava.opencv.model.DebugInfo;
import com.litongjava.opencv.model.TrafficLight;
import com.litongjava.opencv.utils.TrafficLightUtils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import top.ppnt.android.cv.demo.utils.FilenameUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022-06-23
 */
public class RecogTrafictLightService {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public void index(Context baseContext, ArrayList<String> photos, EditText recogResult) throws IOException {

    StringBuffer message = new StringBuffer();
    for (String imagePath : photos) {
      log.info("photo:{}", imagePath);

      byte[] imageBytes = FileUtils.readFileToByteArray(new File(imagePath));
      DebugInfo debugInfo = new DebugInfo();
      TrafficLight trafficLight = TrafficLightUtils.index(imageBytes, debugInfo);

      String filename = FilenameUtils.getFilename(imagePath);

      int colorId = trafficLight.getColor();
      String color = null;
      if (colorId == 1) {
        color = "红色";
      } else if (colorId == 2) {
        color = "绿色";
      } else if (colorId == 3) {
        color = "黄色";
      }
      message.append("图片:" + filename + ",颜色:" + color + "\r\n");
    }

    recogResult.setText(message.toString());
  }
}
