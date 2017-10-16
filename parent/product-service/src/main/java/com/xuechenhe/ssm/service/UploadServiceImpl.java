package com.xuechenhe.ssm.service;


import org.springframework.stereotype.Service;

import com.xuechenhe.ssm.common.FastDFSUtil;

@Service("uploadServiceImpl")
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadFile(byte[] pic, String fileName, long size) throws  Exception {
		String uploadFile = FastDFSUtil.uploadFile(pic, fileName, size);
		return uploadFile;
	}

}
