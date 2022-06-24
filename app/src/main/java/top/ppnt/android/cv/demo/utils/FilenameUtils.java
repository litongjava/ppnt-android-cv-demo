package top.ppnt.android.cv.demo.utils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022-06-23
 */
public class FilenameUtils {

  public static String getFilename(String path) {
    return path.substring(path.lastIndexOf(("/")));
  }
}
