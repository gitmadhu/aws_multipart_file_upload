package com.sriraghav.fileupload.model;

import org.springframework.web.multipart.MultipartFile;

public class File {
		 
		MultipartFile[] files;


		public MultipartFile[] getFiles() {
			return files;
		}

		public void setFiles(MultipartFile[] files) {
			this.files = files;
		}

		
}
