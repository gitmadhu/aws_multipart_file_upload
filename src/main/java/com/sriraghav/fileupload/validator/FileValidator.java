package com.sriraghav.fileupload.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.sriraghav.fileupload.model.File;

public class FileValidator implements Validator {
	public boolean supports(Class<?> paramClass) {
		setValidTypes();
		return File.class.equals(paramClass);
	}
	
	private void setValidTypes() {
		validTypes.add(".zip");
		validTypes.add(".png");
		validTypes.add(".jpg");
		validTypes.add(".doc");
		validTypes.add(".docx");
		validTypes.add(".rar");
		validTypes.add(".txt");
		//add few more valid types
	}

	private final List<String> validTypes = new ArrayList<>();

	public void validate(Object obj, Errors errors) {

		File file = (File) obj;
		  if (file.getFiles().length == 0) {
		// add more validates here..
		   errors.rejectValue("files", "valid.file");
		  }else{
			  validFileType(file,errors);
		  }
	}

	private boolean validFileType(File file,Errors errors) {
		MultipartFile[] files = file.getFiles();
		for(int i = 0; i < files.length;i++){
			String filename = files[i].getOriginalFilename();
			
			if(filename.lastIndexOf(".") == -1 || filename.lastIndexOf(".") == 0){
				errors.rejectValue("files", "inValid.file.type");
				return false;
			}
			
			String ext = filename.substring(filename.lastIndexOf("."),filename.length());
			if(!validTypes.contains(ext)){
				errors.rejectValue("files", "notallowed.file.type");
				return false;
			}
		}
		return true;
	}
}
