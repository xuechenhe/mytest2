package com.xuechenhe.ssm.common;

import org.springframework.core.convert.converter.Converter;

public class CustomStrTrimConverter implements Converter<String,String> {

	@Override
	public String convert(String source) {
		if(source!=null) {
			source=source.trim();
			if(!"".equals(source)) {
				return source;
			}
		}
		return null;
	}

}
