package com.app.mdc.utils.file;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 将MultipartFile保存到指定的路径下
	 * 
	 * @param file
	 *            Spring的MultipartFile对象
	 * @param savePath
	 *            保存路径
	 * @return 保存的文件名，当返回NULL时为保存失败。
	 * @throws IOException ioException
	 * @throws IllegalStateException IllegalStateException
	 */
	public static String save(MultipartFile file, String savePath) throws IllegalStateException, IOException {
		if (file != null && file.getSize() > 0) {
			File fileFolder = new File(savePath);
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			File saveFile = getFile(savePath, file.getOriginalFilename());
			file.transferTo(saveFile);
			return saveFile.getName();
		}
		return null;
	}

	private static File getFile(String savePath, String originalFilename) {
		String fileName = FileUtil.getUUIDFileName(originalFilename);
		File file = new File(savePath + fileName);
		if (file.exists()) {
			return getFile(savePath, originalFilename);
		}
		return file;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 是否删除成功：true-删除成功，false-删除失败
	 */
	public static boolean delete(String filePath) {
		File file = new File(filePath);
		if (file.isFile()) {
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * 生成唯一的文件名:
	 */
	public static String getUUIDFileName(String fileName){
		// 将文件名的前面部分进行截取：xx.jpg   --->   .jpg
		int idx = fileName.lastIndexOf(".");
		String extention = fileName.substring(idx);
		return UUID.randomUUID().toString().replace("-", "")+extention;
	}

	public static String getDateFileName(String fileName){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String resTime = sdf.format(new Date());
		return resTime+"_"+fileName;
	}

	public static boolean isNotEmpty(MultipartFile multipartFile){
		return multipartFile != null && multipartFile.getSize() > 0;
	}

	/**
	 * 根据路径判断文件是否存在
	 * @param filePath 文件路径
	 * @return boolean
	 */
	public static boolean isExist(String filePath){
		File file = new File(filePath);
		return file.exists();
	}


	public static String xDocServiceChangeFile(String inputFile,String fileChangePath){
		XDocService xDocService = new XDocService();
		String outputFile = "";
		File src = new File(inputFile);
		if(src.exists()){
			File changeFile = new File(fileChangePath);
			if(!changeFile.exists()){
				changeFile.mkdir();
			}
			outputFile = fileChangePath+UUID.randomUUID().toString().replace("-","")+".pdf";
			logger.info("设置转换后的文件:"+outputFile);
			try {
				xDocService.to(new java.io.File(inputFile),new java.io.File(outputFile));
			}catch (IOException e){
				e.printStackTrace();
			}
			logger.info("转换文件"+inputFile+"为"+outputFile);
		}else{
			logger.error("需要转换的文件不存在，请重新选择需要转换的文件!");
		}
		return outputFile;
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
	}
}