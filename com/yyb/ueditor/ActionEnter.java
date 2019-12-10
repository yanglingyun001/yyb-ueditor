//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor;

import com.yyb.ueditor.define.ActionMap;
import com.yyb.ueditor.define.BaseState;
import com.yyb.ueditor.define.State;
import com.yyb.ueditor.hunter.FileManager;
import com.yyb.ueditor.hunter.ImageHunter;
import com.yyb.ueditor.upload.BinaryUploader;
import com.yyb.ueditor.upload.IUploader;
import com.yyb.ueditor.upload.StorageManager;
import com.yyb.ueditor.upload.Uploader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ActionEnter {
    private HttpServletRequest request = null;
    private String actionType = null;
    private ConfigManager configManager = null;
    private MultipartFile multipartFile = null;


    /**
     *
     * @param request
     * @param webRootDir 项目根目录
     * @param configJsonFilePath  传null取默认值，classpath:config.json
     * @param iUploader  实现它，然后传入
     */
    public ActionEnter(HttpServletRequest request,String webRootDir, String configJsonFilePath, IUploader iUploader, MultipartFile multipartFile) {
        this.request = request;
        this.actionType = request.getParameter("action");
        this.configManager = ConfigManager.getInstance(configJsonFilePath);
        Uploader.iUploader =iUploader;
        this.multipartFile = multipartFile;
        ConfigManager.webRootDir = webRootDir;
    }
    public String exec() {
        String callbackName = this.request.getParameter("callback");
        if (callbackName != null) {
            return !this.validCallbackName(callbackName) ? (new BaseState(false, 401)).toJSONString() : callbackName + "(" + this.invoke() + ");";
        } else {
            return this.invoke();
        }
    }
    public String invoke() {
        if (this.actionType != null && ActionMap.mapping.containsKey(this.actionType)) {
            if (this.configManager != null && this.configManager.valid()) {
                State state = null;
                int actionCode = ActionMap.getType(this.actionType);
                Map<String, Object> conf = null;
                switch(actionCode) {
                    case 0:
                        return this.configManager.getAllConfig().toString();
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        conf = this.configManager.getConfig(actionCode);
                        state = (new Uploader(this.request, conf,multipartFile)).doExec();
                        break;
                    case 5:
                        conf = this.configManager.getConfig(actionCode);
                        String[] list = this.request.getParameterValues((String)conf.get("fieldName"));
                        state = (new ImageHunter(conf)).capture(list);
                        break;
                    case 6:
                    case 7:
                        conf = this.configManager.getConfig(actionCode);
                        int start = this.getStartIndex();
                        state = (new FileManager(conf)).listFile(start);
                }

                return state.toJSONString();
            } else {
                return (new BaseState(false, 102)).toJSONString();
            }
        } else {
            return (new BaseState(false, 101)).toJSONString();
        }
    }

    public int getStartIndex() {
        String start = this.request.getParameter("start");

        try {
            return Integer.parseInt(start);
        } catch (Exception var3) {
            return 0;
        }
    }

    public boolean validCallbackName(String name) {
        return name.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }
}
