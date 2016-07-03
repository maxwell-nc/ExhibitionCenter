package pres.nc.maxwell.asynchttp.io;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO工具类
 */
public class IOUtils {

    /**
     * InputStream转byte[]
     *
     * @param is 输入流
     * @return byte数组
     */
    public static byte[] inputStream2bytes(InputStream is) throws IOException {
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();

        byte[] buff = new byte[1024];
        int rc;

        while ((rc = is.read(buff, 0, 1024)) > 0) {
            baoStream.write(buff, 0, rc);
        }

        return baoStream.toByteArray();
    }

    /**
     * 保存String到文件
     *
     * @param filename 文件名
     * @param content  内容
     */
    public static void writeStrToFile(String filename, String content) {

        if (TextUtils.isEmpty(content)) {
            return;
        }

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(filename, false);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文件到String
     * @param file 文件
     * @return String文本
     */
    public static String loadFileToString(File file) {

        BufferedReader br = null;
        String ret = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder sb = new StringBuilder((int) file.length());
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            ret = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    /**
     * 删除目录下的文件，非递归，遇到目录不删除
     *
     * @param dirFile
     *            目录File对象
     * @return 是否成功删除
     */
    public static boolean removeDir(File dirFile) {

        if (!dirFile.isDirectory()) {
            return false;
        }
        File files[] = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {

            if (files[i].isDirectory()) {
                break;
            }

            if (!files[i].delete()) {
                return false;
            }
        }
        return true;
    }

}
