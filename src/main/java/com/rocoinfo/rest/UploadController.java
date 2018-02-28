package com.rocoinfo.rest;

import com.google.common.collect.Maps;
import com.rocoinfo.common.service.ServiceException;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.enumeration.UploadCategory;
import com.rocoinfo.service.upload.UploadService;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/4/12 下午5:07</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object upload(@RequestParam(value = "file") CommonsMultipartFile file, @RequestParam UploadCategory category) {
        try {
            String saveTmpPath = uploadService.upload(file, category);
            Map<String, String> data = Maps.newHashMap();
            data.put("fileName", file.getOriginalFilename());
            data.put("path", WebUtils.getUploadFilePath(saveTmpPath)); // 图片的相对路径
            data.put("fullPath", WebUtils.getFullUploadFilePath(saveTmpPath)); // 图片的绝对路径
            return StatusDto.buildDataSuccessStatusDto(data);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "上传文件失败";
            if (e instanceof ServiceException) {
                msg = e.getMessage();
            }
            return StatusDto.buildFailureStatusDto(msg);
        }
    }

    /**
     * 删除附件
     *
     * @param path 附件的路径
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(@RequestParam String path) {
        if (StringUtils.isEmpty(path))
            return StatusDto.buildFailureStatusDto("文件路径不能为空！");
        String pathArr[]=path.split(",");
        for(int i=0;i<pathArr.length;i++) {

                String relatePath = this.uploadService.getRelatePath(pathArr[i]);
                if (this.uploadService.delete(relatePath)){

                }else {
                    return StatusDto.buildFailureStatusDto("删除失败");
                }
        }
        return StatusDto.buildDataSuccessStatusDto("删除成功");

    }
}
