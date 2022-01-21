package com.kk.d.framework.web;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author kk
 * @date 2019/12/26
 **/
public abstract class BaseController {

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}

	public <T> Result<T> response(int httpCode, int retCode, String message, String debug, T model) {
		getResponse().setStatus(httpCode);
		Result<T> result = new Result();
		result.setRet(retCode);
		result.setMsg(message);
		result.setDebug(debug);
		result.setModel(model);
		return result;
	}

	public Result response(int httpCode, Result result) {
		return response(httpCode, result.getRet(), result.getMsg(), result.getDebug(), result.getModel());
	}

	public <T> Result<T> successGet(T model) {
		return response(HttpCode.SC_OK, BaseResultCode.SUCCESS, BaseResultCode.get(BaseResultCode.SUCCESS), null, model);
	}

	public <T> Result<T> successGet() {
		return response(HttpCode.SC_OK, BaseResultCode.SUCCESS, BaseResultCode.get(BaseResultCode.SUCCESS), null, null);
	}

	public <T> Result<T> successGet(int resultCode) {
		return response(HttpCode.SC_OK, resultCode, BaseResultCode.get(resultCode), null, null);
	}

	public <T> Result<T> successGet(int resultCode, String message) {
		return response(HttpCode.SC_OK, resultCode, message, null, null);
	}

	public <T> Result<T> successGet(int resultCode, T model) {
		return response(HttpCode.SC_OK, resultCode, BaseResultCode.get(resultCode), null, model);
	}

	/**
	 * 主要用于直接返回service层数据
	 * 
	 * @param result
	 * @return
	 */
	public Result successGet(Result result) {
		return response(HttpCode.SC_OK, result);
	}

	public <T> Result<T> successCreated(T model) {
		return response(HttpCode.SC_CREATED, BaseResultCode.SUCCESS, BaseResultCode.get(BaseResultCode.SUCCESS), null, model);
	}

	public <T> Result<T> successCreated() {
		return response(HttpCode.SC_CREATED, BaseResultCode.SUCCESS, BaseResultCode.get(BaseResultCode.SUCCESS), null, null);
	}

	public <T> Result<T> successCreated(int resultCode) {
		return response(HttpCode.SC_CREATED, resultCode, BaseResultCode.get(resultCode), null, null);
	}
	
	public <T> Result<T> successCreated(int resultCode, T model) {
		return response(HttpCode.SC_CREATED, resultCode, BaseResultCode.get(resultCode), null, model);
	}

	public <T> Result<T> successCreated(int resultCode, String message) {
		return response(HttpCode.SC_CREATED, resultCode, message, null, null);
	}

	public Result successCreated(Result result) {
		return response(HttpCode.SC_CREATED, result);
	}

	public <T> Result<T> successAccepted(T model) {
		return response(HttpCode.SC_ACCEPTED, BaseResultCode.SUCCESS, BaseResultCode.get(BaseResultCode.SUCCESS), null, model);
	}

	public <T> Result<T> successAccepted() {
		return response(HttpCode.SC_ACCEPTED, BaseResultCode.SUCCESS, BaseResultCode.get(BaseResultCode.SUCCESS), null, null);

	}

	public <T> Result<T> successAccepted(int resultCode) {
		return response(HttpCode.SC_ACCEPTED, resultCode, BaseResultCode.get(resultCode), null, null);
	}

	public <T> Result<T> successAccepted(int resultCode, String message) {
		return response(HttpCode.SC_ACCEPTED, resultCode, message, null, null);
	}

	public Result successAccepted(Result result) {
		return response(HttpCode.SC_ACCEPTED, result);
	}

	public <T> Result<T> successDelete() {
		return successDelete();
	}

	public <T> Result<T> failUnauthorized(int retCode, T model) {
		return response(HttpCode.SC_UNAUTHORIZED, retCode, BaseResultCode.get(retCode), null, model);
	}

	public <T> Result<T> failUnauthorized(int retCode) {
		return failUnauthorized(retCode, null);
	}

	public <T> Result<T> failServerError() {
		return response(HttpCode.SC_INTERNAL_SERVER_ERROR, HttpCode.SC_INTERNAL_SERVER_ERROR, HttpCode.get(HttpCode.SC_INTERNAL_SERVER_ERROR), null, null);
	}

	public <T> Result<T> failServerError(String message) {
		return response(HttpCode.SC_INTERNAL_SERVER_ERROR, HttpCode.SC_INTERNAL_SERVER_ERROR, message, null, null);
	}

	public ModelAndView createMav(String viewName, Map<String, ?> model) {
		return new ModelAndView(viewName, model);
	}

	/**
	 * 内部模块返回接口成功判断
	 *
	 * @param result
	 * @return
	 */
	public boolean isSuccess(Result result) {
		return result.getRet() == BaseResultCode.SUCCESS;
	}

	/**
	 * 验证参数统一入口
	 * 
	 * @param bindingResult
	 *            验证参数结果
	 * @return 验证信息，如果验证通过返回null
	 * @author Sunny
	 */
	public String validate(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder message = new StringBuilder();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				if (message.length() > 0) {
					message.append("|");
				}
				// 收集错误信息
				message.append(objectError.getDefaultMessage());
			}
			return message.toString();
		}
		return null;
	}
}
