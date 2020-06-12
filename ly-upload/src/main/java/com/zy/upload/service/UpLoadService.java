package com.zy.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.sun.org.apache.xpath.internal.functions.FuncSubstringAfter;
import com.zy.common.enums.ExceptionEnum;
import com.zy.common.exception.LyException;
import com.zy.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@EnableConfigurationProperties({UploadProperties.class})
public class UpLoadService {
    //定义允许文件类型，这里先写一个，应该现在配置里
//    private static final List<String> ALLOW_TYPES= Arrays.asList("image/jpeg","image/png","image/bmp","image/jpg");//数组工具类
    @Autowired
    private UploadProperties uploadProperties;

    @Autowired
    private FastFileStorageClient fileStorageClient;
    public String uploadImage(MultipartFile file) {
//        this.getClass().getClassLoader().getResource("").getFile();//得到一个路径
        try {
            //校验文件类型

            String contextType= file.getContentType();
            if(!uploadProperties.getAllowTypes().contains(contextType)){
                throw new LyException(ExceptionEnum.FILE_TYPE_MSMATCH);
            }
            //校验文件内容,通过文件输入流方法
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image==null){
                throw new LyException(ExceptionEnum.FILE_TYPE_MSMATCH);
            }
            if(image.getWidth()<=0&&image.getHeight()<=0){ //读取文件的宽或高小于=0也是不合理的
                throw new LyException(ExceptionEnum.FILE_TYPE_MSMATCH);
            }
            //准备目标路径
//            File dest=new File("D:/idea/leyoul/images-leyou-upload",file.getOriginalFilename());//文件路径和文件名
//        //保存文件到本地
//            file.transferTo(dest);//把上传的文件转移到目的地，必须是一个文件
       //上传到nginx
//            String extension=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."+1));
            String extension= StringUtils.substringAfterLast(file.getOriginalFilename(),".");//截取最后一个点的后面
           StorePath storePath=fileStorageClient.uploadFile(file.getInputStream(),file.getSize(),extension,null);
           StorePath uploadImageAndCrtThumbImage=fileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(),extension,null);//包括缩略图和本图一起上传
            //返回路径
//            return "http://image.leyou.com"+file.getOriginalFilename();//www给前台门户用的
//            if(file.getSize()>20MB)
            if(file.getSize()>5000000){
                //超过5MB上传缩略图
                return uploadProperties.getBaseUrl()+uploadImageAndCrtThumbImage.getFullPath();
            }//5MB
                log.info(".......file.getsize"+file.getSize());
                log.info(".......file.bytes"+file.getBytes());
                log.info(".......file.getsize"+file.getSize());
            return uploadProperties.getBaseUrl()+storePath.getFullPath();//www给前台门户用的
        } catch (IOException e) {
            //上传失败记录日志
            log.error("上传文件失败",e);
            throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}
