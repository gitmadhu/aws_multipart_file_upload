package com.sriraghav.fileupload;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.sriraghav.fileupload.model.File;
import com.sriraghav.fileupload.validator.FileValidator;

@Controller
@RequestMapping("/file.html")
public class FileController {

	@Autowired
	FileValidator validator;
	@Resource
	FileUploadToAWSS3Helper fileUploadToAWSS3Helper;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getForm(Model model) {
		File fileModel = new File();
		model.addAttribute("file", fileModel);
		return "file";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fileUploaded(Model model, @Validated File file,
			BindingResult result) {

		String returnVal = "successFile";
		if (result.hasErrors()) {
			returnVal = "file";
		} else {
			System.out.println("file.getFiles()..."+file.getFiles());
			MultipartFile[] multipartFiles = file.getFiles();
			for(int i = 0;i<multipartFiles.length ; i++){
				//System.out.println("file uploaded... "+multipartFiles[i].getOriginalFilename());
				
				try {
					fileUploadToAWSS3Helper.uploadFileToS3Bucket(multipartFiles[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return returnVal;
	}
}
