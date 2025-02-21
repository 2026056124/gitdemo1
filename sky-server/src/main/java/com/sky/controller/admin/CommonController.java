package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @Value("${local.images}")
    String localImagesPath;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传:{}",file);

        try {
            //阿里云储存
            String originalFilename = file.getOriginalFilename();
            String s = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
//            String path = aliOssUtil.upload(file.getBytes(), s);

            //本地存储
            File f = new File(localImagesPath);
            String absolutePath = f.getAbsolutePath();
            file.transferTo(new File(absolutePath +"//"+ s));
            return Result.success("http://localhost:8080/images/" + s);
        } catch (IOException e) {
            log.error("文件上传失败:{}",e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
