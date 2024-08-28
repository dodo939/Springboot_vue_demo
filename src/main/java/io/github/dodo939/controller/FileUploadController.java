package io.github.dodo939.controller;

import io.github.dodo939.pojo.Result;
import io.github.dodo939.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<?> upload(@RequestBody MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf('.'));
                String url = AliOssUtil.uploadFile(filename, file.getInputStream());
                if (!url.isEmpty()) {
                    return Result.success(url);
                } else {
                    return Result.error("上传失败");
                }
            } else {
                return Result.error("文件名为空");
            }
        }
        else {
            return Result.error("上传失败");
        }
    }
}
