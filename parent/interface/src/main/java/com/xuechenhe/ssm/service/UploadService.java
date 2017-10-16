package com.xuechenhe.ssm.service;

public interface UploadService {
	
	public abstract String uploadFile(byte[] pic ,String fileName,long size)throws  Exception;
}
