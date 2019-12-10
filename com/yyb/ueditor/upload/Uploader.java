//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.upload;

import com.yyb.ueditor.define.State;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public  class Uploader {
    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;
    private MultipartFile multipartFile;
    public static IUploader iUploader =null;

    public Uploader(HttpServletRequest request, Map<String, Object> conf,MultipartFile multipartFile) {
        this.request = request;
        this.conf = conf;
        this.multipartFile = multipartFile;
    }

    public final State doExec() {

        String filedName = (String)this.conf.get("fieldName");
        State state = null;
        if ("true".equals(this.conf.get("isBase64"))) {
            state = com.yyb.ueditor.upload.Base64Uploader.save(this.request.getParameter(filedName), this.conf);
        } else {
            state = com.yyb.ueditor.upload.BinaryUploader.save(this.request, this.conf,multipartFile);
        }

        return state;
    }
}
