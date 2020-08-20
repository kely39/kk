package com.kk.d.framework.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[values.length];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = escape(values[i]);
		}
		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return escape(value);
	}

	/*
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return escape(value);
	}
	*/

	private static String escape(String value) {
		String rtn = value;
		if (rtn  == null) {
			return rtn;
		}
		// 1-Escape
		/*
		rtn = StringEscapeUtils.escapeSql(rtn);
		rtn = StringEscapeUtils.escapeHtml(rtn);
		*/
		// 2-半角转全角
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < rtn.length(); i++) {
			char c = rtn.charAt(i);
			switch(c){
	            case '>':
	                stringBuilder.append('＞');//全角大于号
	                break;
	            case '<':
	                stringBuilder.append('＜');//全角小于号
	                break;
	            case '\'':
	                stringBuilder.append('＇');//全角单引号
	                break;
	            case '&':
	                stringBuilder.append('＆');//全角
	                break;
	            case '(':
	                stringBuilder.append('（');//全角做括号
	                break;
	            case ')':
	                stringBuilder.append('）');//全角右括号
	                break;
	            case '!':
	                stringBuilder.append('！');//全角感叹号
	                break;
	            case '*':
	                stringBuilder.append('＊');//全角星号
	                break;
	            case '+':
	                stringBuilder.append('＋');//全角加号
	                break;
	            case '=':
	                stringBuilder.append('＝');//全角等号
	                break;
	            default:
	                stringBuilder.append(c);
	                break;
	        }
        }
		rtn = stringBuilder.toString();
		// 3-URL编码
		/*
		try {
			rtn = URLEncoder.encode(rtn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("编码异常", e)
		}
		*/
		//rtn = rtn.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", ""); //过滤emoji表情
		return rtn;
	}
}