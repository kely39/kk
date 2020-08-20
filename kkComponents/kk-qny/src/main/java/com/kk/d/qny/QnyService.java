package com.kk.d.qny;

import com.kk.d.qny.dto.QnyStatDTO;
import com.qiniu.processing.OperationStatus;

import java.io.InputStream;
import java.util.List;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
public interface QnyService {

    /**
     * @Description: 根据文件夹名称获取上传的凭证
     * @Date 14:26 2018/12/24
     * @Param [bucketName]
     * @Return java.lang.String
     **/
    String getUpToken(String bucketName);

    /**
     * @Description: 获取指定文件的上传token（用于同名文件覆盖上传）
     * @Date 14:26 2018/12/24
     * @Param [bucketName, key:自定义文件名（选填）]
     * @Return java.lang.String
     **/
    String getUpToken(String bucketName, String key);

    /**
     * @Description: 获取下载路径
     * @Date 17:54 2018/12/21
     * @Param [key:文件名（为上传后返回的key值）, bucketEnum, style:图片样式，如：imageView2/1/w/200/h/200]
     * @Return java.lang.String
     **/
    String getDownUrl(String key, String bucketName, String style);

    /**
     * 上传文件（本地上传）
     * @param filePath  本地文件路径
     * @param bucketName    上传文件夹
     * @param isGeneKey   是否生成有意义的文件名（从原文件名生成）
     * @param key   自定义文件名
     * @return
     */
    String uploadFile(String filePath, String bucketName, boolean isGeneKey, String key);

    /**
     * 文件上传（字节流）
     * @param inputStream   字节流
     * @param bucketName    上传文件夹
     * @param isGeneKey     是否生成有意义的文件名（从原文件名生成）
     * @param key   自定义文件名
     * @return
     */
    String uploadFile(InputStream inputStream, String bucketName, boolean isGeneKey, String key);

    /**
     * 抓取第三方资源（仅限小文件）
     * @param url   第三方资源请求url
     * @param bucketName    需要保存的七牛云文件夹名称
     * @param key   自定义key，无则使用默认文件hash作为key值
     * @return
     */
    String fetchFile(String url, String bucketName, String key);

    /**
     * 抓取第三方资源（仅限小文件）
     * @param url   第三方资源请求url
     * @param bucketName    需要保存的七牛云文件夹名称
     * @param key   自定义key，无则使用默认文件hash作为key值
     * @return
     */
    String fetchFileReturnKey(String url, String bucketName, String key);

    /**
     * 根据key删除文件
     * @param key
     * @param bucketName
     */
    void delByFileKey(String key, String bucketName);

    /**
     * @Description: 下载文件到本地
     * @Date 17:59 2018/12/21
     * @Param [urlStr:文件全路径, fileName:下载文件名, savePath:下载路径]
     * @see {savePath遵循File.separator}
     * @Return void
     **/
    void downLoad(String urlStr, String fileName, String savePath);

    /**
     * 批量校验文件key
     */
    QnyStatDTO getFileInfoByKeys(List<String> fileKeys, String bucketName);

    /**
     * 添加水印处理
     * @param bucketName
     * @param pfopType: 1-图片添加水印（异步）
     * @return
     */
    String getUpTokenWithPfopType(String bucketName, Integer pfopType);

    /**
     * 文件多媒体处理持久化
     * @param bucketName
     * @param key
     * @return
     */
    String pfop(String bucketName, String key, String fops, String pipeline, String notifyURL);

    /**
     * 查询文件多媒体处理持久化结果
     * @param persistentId
     * @return
     */
    OperationStatus prefop(String persistentId);
}
