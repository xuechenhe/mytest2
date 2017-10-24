package com.xuechenhe.ssm.common;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static String obj2JsonStr(Object obj)throws Exception {
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		
		return om.writeValueAsString(obj);
		
	}
	
}
