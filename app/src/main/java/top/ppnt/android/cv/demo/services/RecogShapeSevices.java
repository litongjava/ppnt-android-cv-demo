package top.ppnt.android.cv.demo.services;

import android.content.Context;
import android.widget.EditText;

import com.litongjava.opencv.model.DebugInfo;
import com.litongjava.opencv.model.Shape;
import com.litongjava.opencv.model.ShapeShape;
import com.litongjava.opencv.model.ShapeType;
import com.litongjava.opencv.utils.RecognitionShapeUtils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.ppnt.android.cv.demo.utils.FilenameUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022-06-23
 */
public class RecogShapeSevices {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  public void index(Context context, ArrayList<String> photos, EditText recogResult) throws IOException {
    StringBuffer message = new StringBuffer();
    for (String imagePath : photos) {
      log.info("photo:{}", imagePath);

      byte[] imageBytes = FileUtils.readFileToByteArray(new File(imagePath));
      DebugInfo debugInfo = new DebugInfo();
      List<Shape> shapes = RecognitionShapeUtils.index(imageBytes, debugInfo);

      String filename = FilenameUtils.getFilename(imagePath);

      ShapeType type = null;
      ShapeShape shapeShape = null;
      String color = null;
      StringBuffer shapMessage = new StringBuffer();
      for (Shape shape : shapes) {
        //锐角三角形
        type = shape.getType();
        //三角形
        shapeShape = shape.getShape();
        //紫色
        color = shape.getColor();
        shapMessage.append(type + "," + shapeShape + "," + color+"\r\n");
      }

      message.append("图片:" + filename + ",图形结果:" +shapMessage);
    }

    recogResult.setText(message.toString());
  }
}
