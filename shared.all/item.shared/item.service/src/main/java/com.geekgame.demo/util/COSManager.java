package com.geekgame.demo.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class COSManager {
    private static final String secretId = "AKIDueASVw96nERRDj3B3gV7YklbEbjdJkv8";
    private static final String secretKey = "A7pHUTDrJYYKtEKAqLceS1OZWFk01oRs";
    private static final String reg="ap-shanghai";
    private static final String bucketName = "7072-prod-7gcbt0vgdd0f6605-1309556468";
    private static COSClient cosClient;


    public COSManager(){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(reg);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        cosClient = new COSClient(cred, clientConfig);
    }

    public String upload(MultipartFile[] images, String name) throws IOException {

        InputStream inputStream = null;
        ObjectMetadata objectMetadata = null;
        PutObjectRequest putObjectRequest = null;
        String key = null;
        List<String> list = new ArrayList();
        for (int i=0; i < images.length; i++){
            inputStream = images[i].getInputStream();
            objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            key = "itemImages/"+name+"-"+i+".jpg";
            putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
            try {
                cosClient.putObject(putObjectRequest);
                list.add("https://7072-prod-7gcbt0vgdd0f6605-1309556468.tcb.qcloud.la/"+key);
            } catch (CosClientException e) {
                e.printStackTrace();
            }
        }
        return list.toString();
    }
}
