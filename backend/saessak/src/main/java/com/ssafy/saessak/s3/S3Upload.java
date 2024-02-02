package com.ssafy.saessak.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Upload {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String dirName) throws IOException {
        String fileName = dirName+"/"+file.getOriginalFilename(); //오리지널 파일이름
        String ext = fileName.substring(fileName.indexOf('.')); // 확장자
        String saveFileName = getUuid() + ext; // 저장 할 파일이름 -> 중복될 수 있기 때문에 고유값인 UUID를 통해 임시 파일명으로 업로드

        amazonS3.putObject(new PutObjectRequest(bucket, saveFileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        // 저장된 이미지의 url 리턴
        return amazonS3.getUrl(bucket, saveFileName).toString();
    }

    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
