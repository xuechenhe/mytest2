package com.xuechenhe.ssm.common;

 
import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

 
public class FastDFSUtil {

	/**
	 * 上传文件
	 * @param pic		文件内容
	 * @param fileName	文件名称
	 * @param size		文件大小
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(byte[] pic, String fileName, long size) throws Exception {
		//加载配置文件
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		//再次加载配置文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		
		//创建管理端对象
		TrackerClient trackerClient = new TrackerClient();
		//获取管理端连接
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建存储端对象, 第二个参数是无用的所以传入null就可以
		StorageClient1 storageClient = new StorageClient1(trackerServer, null);
		
		//获取文件扩展名
		String ext = FilenameUtils.getExtension(fileName);
		//文件描述信息对象
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("fileName", fileName);
		meta_list[1] = new NameValuePair("fileExt", ext);
		meta_list[2] = new NameValuePair("fileSize", String.valueOf(size));
		
		//上传文件, 第一个参数:文件内容, 第二个参数:文件扩展名, 第三个参数: 文件的描述信息
		String path = storageClient.upload_file1(pic, ext, meta_list);
		
		return path;
	}
}
