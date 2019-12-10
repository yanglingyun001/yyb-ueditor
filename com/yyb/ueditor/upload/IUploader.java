//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yyb.ueditor.upload;

import java.io.File;


/**
 * 将此接口的实现类传到StorageManager.iStorageManager
 */
public interface IUploader {


    /**
     * 处理临时文件，可传到其它云
     * @param uploadFile  上传的文件
     * @param path ueditor生成的保存路径
     * @return
     */
    void saveFile(File uploadFile, String path);

}
