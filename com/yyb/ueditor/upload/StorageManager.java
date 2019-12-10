//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.upload;

import com.yyb.ueditor.define.BaseState;
import com.yyb.ueditor.define.State;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class StorageManager {
    private static Logger logger = LoggerFactory.getLogger(StorageManager.class);
    public static final int BUFFER_SIZE = 8192;
    public StorageManager() {
    }
    public static State saveBinaryFile(byte[] data, String path) {
        File file = new File(path);
        State state = valid(file);
        if (!state.isSuccess()) {
            return state;
        } else {
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(data);
                bos.flush();
                bos.close();
            } catch (IOException var5) {
                return new BaseState(false, 4);
            }
            state = new BaseState(true, file.getAbsolutePath());
            state.putInfo("size", (long)data.length);
            state.putInfo("title", file.getName());
            return state;
        }
    }

    public static State saveFileByInputStream(InputStream is, String path, long maxSize) {
        State state = null;
        File tmpFile = getTmpFile();
        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, 8192);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), 8192);
            boolean var9 = false;

            int count;
            while((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }

            bos.flush();
            bos.close();
            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, 1);
            } else {
                state = saveTmpFile(tmpFile, path);
                if (!state.isSuccess()) {
                    tmpFile.delete();
                }

                return state;
            }
        } catch (IOException var10) {
            return new BaseState(false, 4);
        }
    }

    private static File getTmpFile() {
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("tmpfile","tmpfile");
        } catch (IOException e) {
            logger.error("",e);
        }
        return tmpFile;
    }

    public static State saveFileByInputStream(InputStream is, String path) {
        State state = null;
        File tmpFile = getTmpFile();

        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, 8192);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), 8192);
            int count;
            while((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }

            bos.flush();
            bos.close();
            state = saveTmpFile(tmpFile, path);
            if (!state.isSuccess()) {
                tmpFile.delete();
            }

            return state;
        } catch (IOException var8) {
            return new BaseState(false, 4);
        }
    }

    private static State saveTmpFile(File tmpFile, String path) {
        State state = null;
        File targetFile = new File(path);
        if (targetFile.canWrite()) {
            return new BaseState(false, 2);
        } else {
            try {
                FileUtils.moveFile(tmpFile, targetFile);

            } catch (IOException var5) {
                return new BaseState(false, 4);
            }
            state = new BaseState(true);
            state.putInfo("size", targetFile.length());
            state.putInfo("title", targetFile.getName());
            return state;
        }
    }

    public static State valid(File file) {
        File parentPath = file.getParentFile();
        if (!parentPath.exists() && !parentPath.mkdirs()) {
            return new BaseState(false, 3);
        } else {
            return !parentPath.canWrite() ? new BaseState(false, 2) : new BaseState(true);
        }
    }
}
