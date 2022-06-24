package top.ppnt.android.cv.demo.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import top.ppnt.android.cv.demo.utils.FilenameUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022-06-23
 */
public class FileService {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  /**
   * 保存图片到本地相册
   */
  public void saveToLocal(Context context) {

    File extDir = Environment.getExternalStorageDirectory();
    File picDir = new File(extDir + File.separator + Environment.DIRECTORY_PICTURES);
    log.info("extDir:{},picDir:{}", extDir, picDir);

    //获取assets文件下的路径
    AssetManager assetManager = context.getAssets();

    List<String> inputFile = new ArrayList<>();
    inputFile.add("images/plate/plate-001.png");

    inputFile.add("images/shape/shape-001.png");
    inputFile.add("images/shape/shape-002.jpg");
    inputFile.add("images/shape/shape-003.jpg");
    inputFile.add("images/shape/shape-004.jpg");
    inputFile.add("images/shape/shape-005.jpg");
    inputFile.add("images/shape/shape-006.jpg");
    inputFile.add("images/shape/shape-007.jpg");
    inputFile.add("images/shape/shape-008.jpg");
    inputFile.add("images/shape/shape-009.jpg");

    inputFile.add("images/traffic-light/green.jpg");
    inputFile.add("images/traffic-light/red.jpg");
    inputFile.add("images/traffic-light/yellow.jpg");

    List<String> optputFilepaths = new ArrayList<>(inputFile.size());
    for (String filepath : inputFile) {
      String filename = FilenameUtils.getFilename(filepath);
      try {
        InputStream inputStream = assetManager.open(filepath);
        //写入完成会会自动关闭InputStream
        File file = new File(picDir + File.separator + filename);
        boolean b = FileIOUtils.writeFileFromIS(file, inputStream);
        if (b) {
          log.info("保存文件:{},成功", filename);
          String absolutePath = file.getAbsolutePath();
          optputFilepaths.add(absolutePath);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    String[] arr = new String[optputFilepaths.size()];
    optputFilepaths.toArray(arr);
    updateGallery(context, arr);
    ToastUtils.showLong("保存文件到" + Environment.DIRECTORY_PICTURES + "成功");
  }



  /**
   * @param context
   * @param filename filename是我们的文件全名，包括后缀哦
   */
  private void updateGallery(Context context, String filename) {
    updateGallery(context, new String[]{filename});
  }

  private void updateGallery(Context context, String[] paths) {

    MediaScannerConnection.OnScanCompletedListener listener = new MediaScannerConnection.OnScanCompletedListener() {
      public void onScanCompleted(String path, Uri uri) {
        log.info("path:{},uri:{}", path, uri);
      }
    };

    MediaScannerConnection.scanFile(context, paths, null, listener);
  }
}
