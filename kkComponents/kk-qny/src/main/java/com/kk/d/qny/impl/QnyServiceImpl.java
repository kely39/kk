package com.kk.d.qny.impl;

import com.google.gson.Gson;
import com.kk.d.qny.QnyProperties;
import com.kk.d.qny.QnyService;
import com.kk.d.qny.dto.QnyDTO;
import com.kk.d.qny.dto.QnyStatDTO;
import com.kk.d.qny.dto.QnyStatResDTO;
import com.kk.d.qny.enums.PfopEnum;
import com.kk.d.qny.exception.QnyException;
import com.kk.d.qny.util.InnerUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
@Service
public class QnyServiceImpl implements QnyService {

    @Resource
    private QnyProperties qnyProperties;

    @Autowired(required = false)
    private Auth auth;

    @Override
    public String getUpToken(String bucketName) {
        return getUpToken(bucketName,null);
    }

    @Override
    public String getUpToken(String bucketName, String key) {
        if (key == null || key.trim().equals("")){
            return auth.uploadToken(getBucketName(bucketName).getBucket());
        }else {
            return auth.uploadToken(getBucketName(bucketName).getBucket(),key);
        }
    }

    @Override
    public String getDownUrl(String key, String bucketName, String style) {
        if (StringUtils.isNullOrEmpty(key)) {
            throw new QnyException("文件名key为空");
        }
        String downUrl = "";
        long expires = 21600;
        if (style != null && !style.trim().equals("")){
            key = key + "?" + style;
        }

        try {
            /*URLEncoder.encode 会依据规范将空格转为+号，以满足路径包含查询参数时的传输规范，这里仅用作路径拼接，不对空格进行替换，所以在encode后，将+（由空格转化而来，原来文件名中的+已转成+号的编码%2B）替换为空格的编码%20*/
            /*示例：user/test/测试+测试 .jpg 将会被转化成 user%2Ftest%2F%E6%B5%8B%E8%AF%95%2B%E6%B5%8B%E8%AF%95%20.jpg*/
            key = URLEncoder.encode(key, "utf-8").replaceAll("\\+","%20").replaceAll("%2F","/");
        } catch (Exception e) {
            throw new QnyException("获取下载地址异常");
        }

        QnyDTO bucket = getBucketName(bucketName);
        /*生产环境*/
        if (bucket != null){
            if (bucket.getIsPrivate()){
                /*私有文件夹*/
                downUrl = auth.privateDownloadUrl(bucket.getPrefix() + "/" + key, expires);
            }else {
                /*公有文件夹*/
                downUrl = String.format("%s/%s", bucket.getPrefix(), key);
            }
        }
        return downUrl;
    }

    @Override
    public String uploadFile(String filePath, String bucketName, boolean isGeneKey, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        if (isGeneKey || (key==null || key.trim().equals(""))){
            /*构建文件名：文件hash|uuid + 原文件名*/
            String hash;
            File file;
            try {
                hash = InnerUtils.calcETag(filePath);
                file = new File(filePath);
            } catch (Exception e) {
                throw new QnyException("上传七牛云异常，filePath calcETag异常。");
            }
            //如果拿hash异常，则使用uuid作为key前缀
            if (!StringUtils.isNullOrEmpty(hash)) {
                hash = InnerUtils.getNextId();
            }
            key = hash + "/" + file.getName();
        }

        /*指定key 覆盖上传*/
        String upToken = auth.uploadToken(getBucketName(bucketName).getBucket(), key);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return getDownUrl(putRet.key,bucketName,null);
        } catch (QiniuException ex) {
            throw new QnyException("上传七牛云异常：" + ex.code() + "," + ex.response.toString());
        } catch (Exception ex) {
            throw new QnyException("上传七牛云异常");
        }
    }

    @Override
    public String uploadFile(InputStream inputStream, String bucketName, boolean isGeneKey, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        if (isGeneKey || (key==null || key.trim().equals(""))){
            /*构建文件名：uuid*/
            key = InnerUtils.getNextId();
        }

        /*指定key 覆盖上传*/
        String upToken = auth.uploadToken(getBucketName(bucketName).getBucket(), key);
        try {
            Response response = uploadManager.put(inputStream, key, upToken,null,null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return getDownUrl(putRet.key,bucketName,null);
        } catch (QiniuException ex) {
            throw new QnyException("上传七牛云异常：" + ex.code() + "," + ex.response.toString());
        } catch (Exception ex) {
            throw new QnyException("上传七牛云异常");
        }
    }

    @Override
    public String fetchFile(String url, String bucketName, String key) {
        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FetchRet fetchRet = bucketManager.fetch(url,getBucketName(bucketName).getBucket(),(key == null || key.trim().equals(""))?null:key);
            return this.getDownUrl(fetchRet.key,bucketName,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String fetchFileReturnKey(String url, String bucketName, String key) {
        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FetchRet fetchRet = bucketManager.fetch(url,getBucketName(bucketName).getBucket(),(key == null || key.trim().equals(""))?null:key);
            return fetchRet.key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public QnyStatDTO getFileInfoByKeys(List<String> fileKeys, String bucketName) {
        QnyStatDTO statDTO = new QnyStatDTO();
        List<String> errorStatusKeys = new ArrayList<>();
        Map<String, QnyStatResDTO> fileInfoMap = new HashMap<>();

        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        if (fileKeys!=null && fileKeys.size()>0){
            for (String key : fileKeys){
                QnyStatResDTO dto = new QnyStatResDTO();
                try {
                    dto.setStatus(true);
                    dto.setFileInfo(bucketManager.stat(getBucketName(bucketName).getBucket(),(key == null || key.trim().equals(""))?null:key));
                } catch (QiniuException e) {
                    errorStatusKeys.add(key);
                    dto.setStatus(false);
                    dto.setResponse(e.response);
                }
                fileInfoMap.put(key,dto);
            }
        }
        statDTO.setErrorStatusKeys(errorStatusKeys);
        statDTO.setFileInfoMap(fileInfoMap);
        return statDTO;
    }

    @Override
    public void delByFileKey(String key, String bucketName) {
        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(getBucketName(bucketName).getBucket(), key);
        } catch (QiniuException ex) {
            throw new QnyException("删除七牛云图片异常：" + ex.code() + "," + ex.response.toString());
        }
    }

    @Override
    public void downLoad(String urlStr, String fileName, String savePath) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = InnerUtils.readInputStream(inputStream);
            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception ex) {
            throw new QnyException("七牛云下载异常，请检查是否存在该文件。");
        }
    }

    private QnyDTO getBucketName(String bucketName){
        if (bucketName == null || bucketName.trim().equals("")){
            throw new QnyException("缺乏必需信息！");
        }
        QnyDTO result = new QnyDTO();
        Map<String,String> qnyMap = qnyProperties.getPrefixMap().get(bucketName);
        if (qnyMap.get("bucket")!=null && !qnyMap.get("bucket").trim().equals("")){
            result.setBucket(qnyMap.get("bucket"));
        }else {
            throw new QnyException("获取配置信息异常！");
        }
        if (qnyMap.get("isPrivate")!=null && !qnyMap.get("isPrivate").trim().equals("")){
            result.setIsPrivate(qnyMap.get("isPrivate").equals("true"));
        }else {
            throw new QnyException("获取配置信息异常！");
        }
        if (qnyMap.get("prefix")!=null && !qnyMap.get("prefix").trim().equals("")){
            result.setPrefix(qnyMap.get("prefix"));
        }else {
            throw new QnyException("获取配置信息异常！");
        }
        return result;
    }

    /**
     * 添加水印处理
     * @param bucketName
     * @param pfopType: 1-图片添加水印（异步）
     * @return
     */
    @Override
    public String getUpTokenWithPfopType(String bucketName, Integer pfopType) {
        String token = null;
        PfopEnum pfopEnum = PfopEnum.getEnumByType(pfopType);
        if (pfopEnum!=null){
            switch (pfopEnum){
                case FACE_IMG://1-图片添加水印（异步）
                    StringMap policy = new StringMap();
                    policy.put("persistentOps",pfopEnum.getFops().replace("${dateStrBase64}",InnerUtils.urlSafeBase64Encode(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getBytes())));
                    policy.put("persistentNotifyUrl",qnyProperties.getNotifyUrlHost()+pfopEnum.getNotifyURL());
                    policy.put("persistentPipeline",pfopEnum.getPipeline());
                    token = auth.uploadToken(getBucketName(bucketName).getBucket(),null,3600,policy);
                    break;
                default:
                    break;
            }
        }
        return token;
    }

    /**
     * 文件多媒体处理持久化
     * @param bucketName
     * @param key
     * @param fops
     * @param pipeline
     * @param notifyURL
     * @return
     */
    @Override
    public String pfop(String bucketName, String key, String fops, String pipeline, String notifyURL) {
        String persistentId = null;
        Configuration cfg = new Configuration(Zone.zone2());
        OperationManager bucketManager = new OperationManager(auth, cfg);
        try {
            persistentId = bucketManager.pfop(getBucketName(bucketName).getBucket(),key,fops,pipeline,notifyURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persistentId;
    }

    /**
     * 查询文件多媒体处理持久化结果
     * @param persistentId
     * @return
     */
    @Override
    public OperationStatus prefop(String persistentId) {
        OperationStatus operationStatus = null;
        Configuration cfg = new Configuration(Zone.zone2());
        OperationManager bucketManager = new OperationManager(auth, cfg);
        try {
            operationStatus = bucketManager.prefop(persistentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operationStatus;
    }
}
