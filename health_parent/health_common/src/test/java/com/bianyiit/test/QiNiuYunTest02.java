package com.bianyiit.test;

import com.bianyiit.utils.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QiNiuYunTest02 {
    /*删除上传的文件*/
    @Test
    public void test02() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        //...生成上传凭证，然后准备上传
        String accessKey = "Q_-ihw1DRHon_26-nrCrdkPLvnLSZ8JDJOQ_sI3V";
        String secretKey = "xPbaYyqy8tHPx2-4D7o-kqZ69tAV4El6b3jv-7XK";
        //创建的存储空间的名称
        String bucket = "suigan";
        String key = "1";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
    /*测试工具类*/
    @Test
    public void test03(){
        String fileName="preview.jpg";
        String filePath="G:\\图片\\preview.jpg";

        QiniuUtils.upload2Qiniu(filePath,fileName);
    }
}
