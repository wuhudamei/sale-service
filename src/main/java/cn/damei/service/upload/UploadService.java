package cn.damei.service.upload;

import cn.damei.common.service.ServiceException;
import cn.damei.enumeration.UploadCategory;
import cn.damei.common.PropertyHolder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UploadService {

    /**
     * 文件上传的主目录
     */
    @Value("${upload.dir}")
    private String uploadDir;

    /**
     * 临时文件目录（在主目录下）
     */
    @Value("${upload.tmp.dir}")
    private String tmpDir;

    /**
     * 用于构建访问文件的url的前缀
     */
    @Value("${upload.base.url}")
    private String uploadBaseUrl;

    /**
     * 上传文件的大小
     */
    @Value("${upload.max.file.size}")
    private long maxUploadBytes;

    public static final String REQUEST_KEY = "request";
    public static final String USER_ID = "userid";
    private static final SimpleDateFormat DATE_PATH_SDF = new SimpleDateFormat("yyyy/MM/dd");
    // 匹配 src="tmp/xxx"
    // 有点复杂，[^\\1]不匹配1，所以写成([^\\1]|1)
    private static final Pattern imgSrcPattern = Pattern.compile(" src=(\"|')(([^\\\\1]|1)+?)\\1");
    public static ThreadLocal<Map<String, Object>> uploadThreadLocal = new ThreadLocal<Map<String, Object>>();
    protected static List<String> XH_EDITOR_ALLOWED_TYPE = Arrays.asList("jpg", "jpeg", "gif", "png");
    private Logger logger = LoggerFactory.getLogger(UploadService.class);
    //文件上传 基 目录


    @Autowired
    private SequenceService sequenceService;

    /**
     * 保存上传的文件，返回文件保存路径. 文件将保存在临时目录。
     *
     * @param file           上传文件
     * @param uploadCategory 类别.文件会存储在该类别对应的子目录下。
     * @return 返回文件保存路径。如tmp/2014/09/04/1.jpg
     * @throws ServiceException 发生错误时
     */
    public String upload(MultipartFile file, UploadCategory uploadCategory) {
        validateUploadFile(file, uploadCategory);
        InputStream input;
        try {
            input = file.getInputStream();
        } catch (IOException e) {
            logger.debug("读取上传文件输入流发生错误", e);
            throw new ServiceException("读取上传文件发生错误");
        }
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return saveTmp(input, ext, uploadCategory.getPath());
    }

    public String upload(InputStream stream, UploadCategory uploadCategory, String ext) {
        return saveTmp(stream, ext, uploadCategory.getPath());
    }

    /**
     * 保存文件内容到临时目录，返回文件路径.
     *
     * @param input        文件内容
     * @param ext          文件扩展名，如jpg，不带"."
     * @param categoryPath 类别路径。如product
     * @return 返回保存的文件路径. 如tmp/product/2014/09/04/1.jpg
     * @throws ServiceException 当发生错误时
     */
    private String saveTmp(InputStream input, String ext, String categoryPath) {
        Assert.state(StringUtils.isNotBlank(ext));
        Assert.notNull(input);
        String path = tmpDir + categoryPath + "/" + DateFormatUtils.format(new Date(), "yyyy/MM/dd") + "/" + sequenceService.getNextVal(SequenceService.SequenceTable.UPLOAD) + "." + ext;
        this.save(path, input);
        return path;
    }

    /**
     * 保存文件内容到上传目录的指定路径.
     *
     * @param path  文件路径 如tmp/2014/09/04/1.png
     * @param input 文件内容
     * @throws ServiceException 当发生错误时
     */
    private void save(String path, InputStream input) {
        Assert.state(StringUtils.isNotBlank(uploadDir));
        Assert.state(StringUtils.isNotBlank(path));

        BufferedInputStream bufInput = null;
        BufferedOutputStream bufOut = null;

        try {
            File dest = new File(uploadDir, path);
            if (!dest.getParentFile().exists()) {
                if (!dest.getParentFile().mkdirs()) {
                    throw new ServiceException("创建目录失败 " + dest.getParentFile().getAbsolutePath());
                }
            }

            bufInput = new BufferedInputStream(input);
            bufOut = new BufferedOutputStream(new FileOutputStream(dest));

            IOUtils.copy(bufInput, bufOut);
        } catch (IOException e) {
            logger.warn("保存文件出错", e);
            throw new ServiceException("保存文件出错");
        } finally {
            IOUtils.closeQuietly(bufOut);
            IOUtils.closeQuietly(bufInput);
        }
    }

    public void validateUploadFile(MultipartFile file, UploadCategory uploadCategory) {
        if (file.isEmpty()) {
            throw new ServiceException("上传文件不能为空");
        }

        if (StringUtils.isBlank(file.getOriginalFilename())) {
            throw new ServiceException("上传文件名不能为空");
        }

        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isBlank(ext)) {
            throw new ServiceException("上传文件扩展名不能为空");
        }

        if (file.getSize() > maxUploadBytes) {
            throw new ServiceException("上传文件不能大于 " + FileUtils.byteCountToDisplaySize(maxUploadBytes));
        }
    }

    /**
     * 表单提交后，将文件从临时目录移至普通目录，删除临时目录
     *
     * @param tmpPath 文件临时路径。如/static-content/tmp/product/2014/09/04/1.jpg
     * @return 如果tmpPath是临时路径，则返回新路径，如product/2014/09/04/1.jpg。否则返回原路径tmpPath
     */
    public String submitPath(String tmpPath) {
        // 由于文件上传成功后加了前缀,详见：application.properties:image.base.url,去掉前缀 /static-content/
        tmpPath = this.removePrefix(tmpPath);

        if (!tmpPath.startsWith(tmpDir)) {
            return PropertyHolder.getUploadBaseUrl() + tmpPath;
        }

        File tmpFile = new File(uploadDir, tmpPath);

        // 去掉文件路径的tmp/，得到文件存放的目标路径
        final String destPath = tmpPath.substring(tmpDir.length());
        File destFile = new File(uploadDir, destPath);
        if (!tmpFile.exists() && destFile.exists()) {
            // 当系统保存表单，已将一部分图片移至持久目录，处理后面事务发生错误，有可能符合业务规则或其他原因，事务回滚，
            // 用户修改表单后，再次提交，就会tmpFile不存在destFile已存在的情况。这种情况应该返回destPath。
            // 实际例子：添加商品时，填写多个sku，如果第1个sku之后的某个sku编码已存在，就会出现这种情况。
            return destPath;
        }

        if (!destFile.getParentFile().exists()) {
            if (!destFile.getParentFile().mkdirs()) {
                throw new ServiceException("创建目录失败" + destFile.getParentFile().getAbsolutePath());
            }
        }
        try {
            FileUtils.moveFile(tmpFile, destFile);
        } catch (IOException e) {
            logger.warn("移动文件失败", e);
            throw new ServiceException("移动文件失败");
        }
        // 路径拼上用于访问的前缀
        return PropertyHolder.getUploadBaseUrl() + destPath;
    }

    /**
     * 截取地址前半部分
     */
    private String removePrefix(String url) {
        if (!url.startsWith(uploadBaseUrl)) {
            return url;
        }
        return url.substring(uploadBaseUrl.length());
    }

    /**
     * 删除指定路径的单个文件
     *
     * @param saveRelatePath 上传文件的相对路径 例如：product/2012/11/12/1.jpg
     */
    public boolean delete(String saveRelatePath) {
        if (StringUtils.isBlank(saveRelatePath)) {
            return false;
        }

        char first = saveRelatePath.charAt(0);
        if (first == '/' || first == '.') {
            return false;
        }

        File file = new File(uploadDir, saveRelatePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件的过程中 path传过来的是：/static-content/contract/2017/04/18/34.pdf
     * 需要去掉前缀，获取文件真实的路径：contract/2017/04/18/34.pdf
     *
     * @param path
     * @return
     */
    public String getRelatePath(String path) {
        if (StringUtils.isBlank(path))
            return StringUtils.EMPTY;
        if (path.startsWith(PropertyHolder.getUploadBaseUrl())) {
            int i = path.indexOf(PropertyHolder.getUploadBaseUrl());
            return path.substring(i + PropertyHolder.getUploadBaseUrl().length());
        }
        if (path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }
}