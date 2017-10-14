package com.xuechenhe.ssm.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("upload")
public class UploadController {
	@RequestMapping("uploadPic")
	public void uploadPic(MultipartFile pic,HttpServletResponse res) throws IllegalStateException, IOException {
		String originalFilename = pic.getOriginalFilename();
		String filename=UUID.randomUUID().toString().replaceAll("-", "")+"."+FilenameUtils.getExtension(originalFilename);
		pic.transferTo(new File("G:\\image\\"+filename));
		JSONObject jo = new JSONObject();
		jo.put("url", "/pic/"+filename);
		res.getWriter().write(jo.toString());
	}
}
