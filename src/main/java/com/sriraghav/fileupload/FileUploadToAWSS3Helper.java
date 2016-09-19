package com.sriraghav.fileupload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class FileUploadToAWSS3Helper {
	
	private static final String SUFFIX = "/";

	public void uploadFileToS3Bucket(MultipartFile multipartFile) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAJ7YZ3TU4ICJOHKUQ", 
				"Nxh3FlHMa+J9gAHpU/rShCxAaTGZBQCsmUJrSDL+");
		
		// create a client connection based on credentials
		AmazonS3 s3client = new AmazonS3Client(credentials);
		
		// create bucket - name must be unique for all S3 users
		String bucketName = "sriraghav-bucket1";
		s3client.createBucket(bucketName);
		
		// create folder into bucket
		String quarter1 = "root/q1";
		String quarter2 = "root/q2";
		String quarter3 = "root/q3";
		createFolder(bucketName, quarter1, s3client);
		createFolder(bucketName, quarter2, s3client);
		createFolder(bucketName, quarter3, s3client);
		
		// upload file to folder and set it to public
		
		//my file location D:/aws_sample_data/Wildlife.wmv
		String fileName = quarter1 + SUFFIX + multipartFile.getOriginalFilename();
		
		 ObjectMetadata omd = new ObjectMetadata();
         omd.setContentType(multipartFile.getContentType());
         omd.setContentLength(multipartFile.getSize());
         omd.setHeader("filename", multipartFile.getOriginalFilename());
         
		 s3client.putObject(new PutObjectRequest(bucketName, fileName,multipartFile.getInputStream(), omd).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	private void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentLength(0);

				// create empty content
				InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

				// create a PutObjectRequest passing the folder name suffixed by /
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
						folderName + SUFFIX, emptyContent, metadata);

				// send request to S3 to create folder
				client.putObject(putObjectRequest);
		
	}
	
	
	

}
