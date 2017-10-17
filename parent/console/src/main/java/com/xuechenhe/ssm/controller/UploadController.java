package com.xuechenhe.ssm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xuechenhe.ssm.common.Constants;
import com.xuechenhe.ssm.service.UploadService;

@Controller
@RequestMapping("upload")
public class UploadController {
	/*@RequestMapping("uploadPic")
	public void uploadPic(MultipartFile pic,HttpServletResponse res) throws IllegalStateException, IOException {
		String originalFilename = pic.getOriginalFilename();
		String filename=UUID.randomUUID().toString().replaceAll("-", "")+"."+FilenameUtils.getExtension(originalFilename);
		pic.transferTo(new File("G:\\image\\"+filename));
		JSONObject jo = new JSONObject();
		jo.put("url", "/pic/"+filename);
		res.getWriter().write(jo.toString());
	}*/
	@Autowired
	private UploadService  uploadService;
	
	@RequestMapping("uploadPic")
	public void uploadPic(MultipartFile pic,HttpServletResponse res) throws Exception {
		System.out.println(pic.getBytes());
		System.out.println(pic.getOriginalFilename());
		System.out.println(pic.getSize());
		
		String path = uploadService.uploadFile(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		JSONObject jo = new JSONObject();
		jo.put("url",Constants.File_Server+path);
		res.getWriter().write(jo.toString());
	}
	
	/**
	 * @RequestParam("pics") MultipartFile [] pics
	 * 从页面接收数据"pics" 传到形参pics
	 * @ResponseBody将List<String> java对象以json格式返回到页面
	 * @param pics
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("uploadPics")
	@ResponseBody
	public List<String> uploadPics(@RequestParam("pics") MultipartFile [] pics) throws IOException, Exception{
		List<String> urls=new ArrayList<>();
		if(pics!=null) {
			for (MultipartFile pic : pics) {
				String path = uploadService.uploadFile(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
				urls.add(Constants.File_Server+path);
			}
		}
		return urls;
		
	}
}
