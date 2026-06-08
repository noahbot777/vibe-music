package cn.edu.seig.vibemusic.service.impl;

import cn.edu.seig.vibemusic.constant.MessageConstant;
import cn.edu.seig.vibemusic.service.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * 上传文件到 Minio
     *
     * @param file   文件
     * @param folder 文件夹
     * @return 可访问的 URL
     */
    @Override
    public String uploadFile(MultipartFile file, String folder) {
        try {
            // 生成唯一文件名
            String fileName = folder + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            // 获取文件流
            InputStream inputStream = file.getInputStream();

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 返回可访问的 URL
            return endpoint + "/" + bucketName + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException(MessageConstant.FILE_UPLOAD + MessageConstant.FAILED + "：" + e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件 URL
     */
    @Override
    public void deleteFile(String fileUrl) {
        try {
            // 解析 URL，获取文件路径
            String filePath = fileUrl.replace(endpoint + "/" + bucketName + "/", "");

            // 删除文件
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException("文件删除失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件的预签名URL
     *
     * @param fileUrl 文件 URL
     * @return 预签名URL
     */
    @Override
    public String getPresignedUrl(String fileUrl) {
        try {
            // 解析 URL，获取文件路径
            // 直接从bucketName之后截取，不依赖endpoint配置
            String filePath = extractFilePath(fileUrl, bucketName);

            // 生成预签名URL，有效期1小时
            String presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(filePath)
                            .expiry(3600) // 1小时过期
                            .build()
            );

            return presignedUrl;

        } catch (Exception e) {
            // 如果生成预签名URL失败（比如存储桶不存在），返回原始URL
            // 这样可以保证系统在MinIO配置问题时仍然能工作
            System.err.println("获取预签名URL失败，返回原始URL: " + e.getMessage());
            return fileUrl;
        }
    }

    /**
     * 从完整URL中提取存储桶后的文件路径
     *
     * @param fileUrl   文件完整URL
     * @param bucketName 存储桶名称
     * @return 文件路径
     */
    private String extractFilePath(String fileUrl, String bucketName) {
        System.out.println("=== 调试信息 ===");
        System.out.println("输入的 fileUrl: " + fileUrl);
        System.out.println("bucketName: " + bucketName);
        
        // 方法1：使用正则表达式提取存储桶后的路径
        String regex = "/" + Pattern.quote(bucketName) + "/(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileUrl);
        
        if (matcher.find()) {
            String result = matcher.group(1);
            System.out.println("正则匹配成功，提取的路径: " + result);
            return result;
        }
        System.out.println("正则匹配失败");
        
        // 方法2：尝试简单的字符串截取
        String bucketPattern = "/" + bucketName + "/";
        int bucketIndex = fileUrl.indexOf(bucketPattern);
        System.out.println("bucketPattern: " + bucketPattern);
        System.out.println("bucketIndex: " + bucketIndex);
        
        if (bucketIndex != -1) {
            String result = fileUrl.substring(bucketIndex + bucketPattern.length());
            System.out.println("字符串截取成功，提取的路径: " + result);
            return result;
        }
        
        System.out.println("所有方法都失败，返回原始URL");
        return fileUrl;
    }
}
