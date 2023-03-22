package spring.server.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.server.result.error.exception.FileConvertException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;



@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {

        File uploadFile = convert(multipartFile)
                .orElseThrow(FileConvertException::new);

        return upload(uploadFile, dirName);
    }

    public String upload(File uploadFile, String dirName){
        final String fileName = dirName + "/" + uploadFile.getName();
        final String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {

        if(targetFile.delete()){
            log.info("파일 삭제");
        }else{
            log.info("파일 삭제 못함");
        }
    }

    private String putS3(File uploadFile, String fileName){
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {

        File convertFile = new File(multipartFile.getOriginalFilename());

        log.info("converfile={}", convertFile);

        //convertFile.createNeFile 확인해서 같은 이름의 파일이 기존에 있는지 없는지 확인해야함


        log.info("convertFile.createNewFile 실행");
        try(FileOutputStream fileOutputStream = new FileOutputStream(convertFile)){
            fileOutputStream.write(multipartFile.getBytes());
        }
        return Optional.of(convertFile);


    }

}
