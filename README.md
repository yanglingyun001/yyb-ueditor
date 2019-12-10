# yyb-ueditor

可直接使用yyb-ueditor_1.1.2.jar
也可以使用源码

config.json可以放到classpath下，要做小修改

"fileManagerListPath": "/ueditor/jsp/upload/file/"
修改为
"fileManagerListPath": "ueditor/jsp/upload/file/"
其它类似地方，做相同修改


上传类配置
ueditor.config.js中
  // 服务器统一请求接口路径修改为自己的URL
serverUrl: "/ueditor/controller"

@ResponseBody
@RequestMapping("/controller")
public String umeditor(HttpServletRequest request,@RequestParam(value = "upfile",required = false) MultipartFile file) throws IOException {
    IUploader iUploader = 实现类，自己随意写，可传到第三方云
    return new ActionEnter(request,getWebRootDir(),null,iUploader,file).exec();
}