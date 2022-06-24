package top.ppnt.android.cv.demo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022-06-23
 */
public class AssetsUtils {
  public static void copyAssetsDirToSDCard(Context context, String assetsDirName, String sdCardPath) {

    AssetManager assetManager = context.getAssets();

    try {
      String list[] = assetManager.list(assetsDirName);
      if (list.length == 0) {
        InputStream inputStream = assetManager.open(assetsDirName);
        byte[] mByte = new byte[1024*10];
        int bt = 0;
        File file = new File(sdCardPath + File.separator+assetsDirName.substring(assetsDirName.lastIndexOf('/')));
        if (!file.exists()) {
          file.createNewFile();
        } else {
          return;
        }
        FileOutputStream fos = new FileOutputStream(file);
        while ((bt = inputStream.read(mByte)) != -1) {
          fos.write(mByte, 0, bt);
        }
        fos.flush();
        inputStream.close();
        fos.close();
      } else {
        String subDirName = assetsDirName;
        if (assetsDirName.contains("/")) {
          subDirName = assetsDirName.substring(assetsDirName.lastIndexOf('/') + 1);
        }
        sdCardPath = sdCardPath + File.separator + subDirName;
        File file = new File(sdCardPath);
        if (!file.exists())
          file.mkdirs();
        for (String s : list) {
          copyAssetsDirToSDCard(context, assetsDirName + File.separator + s, sdCardPath);
        }
      }
    } catch (
      Exception e) {
      e.printStackTrace();
    }
  }
}
