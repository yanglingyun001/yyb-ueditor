//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.upload;

import com.yyb.ueditor.PathFormat;
import com.yyb.ueditor.define.BaseState;
import com.yyb.ueditor.define.FileType;
import com.yyb.ueditor.define.State;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {



    public BinaryUploader() {
    }

    public static final State save(HttpServletRequest request, Map<String, Object> conf, MultipartFile multipartFile) {
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, 5);
        } else {

            try {

                if (multipartFile == null) {
                    return new BaseState(false, 7);
                } else {
                    String savePath = (String)conf.get("savePath");
                    String originFileName = multipartFile.getOriginalFilename();
                    String suffix = FileType.getSuffixByFilename(originFileName);
                    originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
                    savePath = savePath + suffix;
                    long maxSize = (Long)conf.get("maxSize");

                    if (multipartFile.getSize() > maxSize) {
                        return new BaseState(false, 1);
                    }

                    if (!validType(suffix, (String[])conf.get("allowFiles"))) {
                        return new BaseState(false, 8);
                    } else {
                        savePath = PathFormat.parse(savePath, originFileName);
                        String rootPaht = (String)conf.get("rootPath");
                        if(!rootPaht.endsWith("/")){
                            rootPaht+="/";
                        }
                        String physicalPath = rootPaht + savePath;
                        File  targetFile = new File(physicalPath);
                        StorageManager.valid(targetFile);
                        multipartFile.transferTo(targetFile);
                        if (Uploader.iUploader!=null){
                            Uploader.iUploader.saveFile(targetFile,savePath);
                            targetFile.delete();
                        }

                        State storageState = new BaseState(true);
                        storageState.putInfo("size", targetFile.length());
                        storageState.putInfo("title", targetFile.getName());
                        if (storageState.isSuccess()) {
                            storageState.putInfo("url", PathFormat.format(savePath));
                            storageState.putInfo("type", suffix);
                            storageState.putInfo("original", originFileName + suffix);
                        }

                        return storageState;
                    }
                }
            } catch (IOException var15) {
                return new BaseState(false, 4);
            }
        }
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);
        return list.contains(type);
    }
}
