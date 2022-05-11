package com.himanshu.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.himanshu.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub

		// File Name
		String fileName = file.getOriginalFilename();

		String fileRandomId = UUID.randomUUID().toString();
		String fileRandomName = fileRandomId.concat(fileName.substring(fileName.lastIndexOf(".")));

		// FullPath
		// Multipart error->if same file is uploaded again
//		String filePath = path + File.separator + fileName;
		String filePath = path + File.separator + fileRandomName;

		// path verification code
//		String filename="LWD Acceptance LTI - Sapiens Doc.png";
//	    Path pathToFile = Paths.get(filename);
//	    System.out.println(pathToFile.toAbsolutePath());

		// Create folder to save images if not created
		File fileOld = new File(path);
//		System.out.println(fileOld);
		if (!fileOld.exists()) {
			fileOld.mkdir();
		}

		// File copy
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileRandomName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}
	
}
