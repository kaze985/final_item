package com.lppnb.gatewayimpl.oss;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OSSManager {
    private static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "LTAI5tKyQtHzdsqdK6rooVud";
    private static final String accessKeySecret = "P07oagsbHdxwjsx0FoTOsSV5U5m5GT";
    private static final String bucketName = "lppnb-item-images";
    private static final String prefix = "https://lppnb-item-images.oss-cn-hangzhou.aliyuncs.com/";

    public static String upload(MultipartFile[] images, String name) throws IOException {
        List<String> list = new ArrayList();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        for (int i=0; i < images.length; i++){
            InputStream inputStream = images[i].getInputStream();
            String key = name+"-"+i+".jpg";

            try {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("image/jpg");
                putObjectRequest.setMetadata(metadata);
                ossClient.putObject(putObjectRequest);
                list.add(prefix + key);
            } catch (OSSException oe) {
                System.out.println("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                System.out.println("Error Message:" + oe.getErrorMessage());
                System.out.println("Error Code:" + oe.getErrorCode());
                System.out.println("Request ID:" + oe.getRequestId());
                System.out.println("Host ID:" + oe.getHostId());
            } catch (ClientException ce) {
                System.out.println("Caught an ClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with OSS, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message:" + ce.getMessage());
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }
        return JSON.toJSONString(list);
    }

    public static void delete(String url) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String key = StringUtils.removeStart(url, prefix);
        try {
            // 删除文件。
            ossClient.deleteObject(bucketName, key);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
