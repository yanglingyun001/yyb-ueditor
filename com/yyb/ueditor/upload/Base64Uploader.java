//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.upload;

import com.yyb.ueditor.PathFormat;
import com.yyb.ueditor.define.BaseState;
import com.yyb.ueditor.define.FileType;
import com.yyb.ueditor.define.State;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.util.Map;

public final class Base64Uploader {
    public Base64Uploader() {
    }

    public static State save(String content, Map<String, Object> conf) {
        byte[] data = decode(content);
        long maxSize = (Long)conf.get("maxSize");
        if (!validSize(data, maxSize)) {
            return new BaseState(false, 1);
        } else {
            String suffix = FileType.getSuffix("JPG");
            String savePath = PathFormat.parse((String)conf.get("savePath"), (String)conf.get("filename"));
            savePath = savePath + suffix;
            String physicalPath = (String)conf.get("rootPath") + savePath;
            State storageState = StorageManager.saveBinaryFile(data, physicalPath);
            if (storageState.isSuccess()) {

                if (Uploader.iUploader!=null){
                    File targetFile = new File(physicalPath);
                    Uploader.iUploader.saveFile(targetFile,savePath);
                    targetFile.delete();
                }

                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", "");
            }

            return storageState;
        }
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return (long)data.length <= length;
    }
}
