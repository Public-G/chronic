package com.github.chronic.action.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.User;
import com.github.chronic.service.user.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User>, ServletRequestAware {

	private User user;

	private String verifyCode;

	private HttpServletRequest request;

	private JsonBean jsonBean = new JsonBean();

	@Autowired
	private UserService userService;

	public String userLogout() {
		// 销毁session
		request.getSession().invalidate();
		jsonBean.setCode(200);
		jsonBean.setMsg("退出成功");
		return SUCCESS;
	}

	public String userLogin() {

		// 接收session中的验证码
		String chekcode = (String) request.getSession().getAttribute("verifyCode");
		// 判断验证码
		if (!verifyCode.equalsIgnoreCase(chekcode)) {
			this.addActionError("验证码错误");
			return "loginFail";
		}

		User userExist = userService.login(user);

		if (userExist == null) {
			// 登录失败
			this.addActionError("用户名或密码错误");
			return "loginFail";
		}
		// 登录成功
		request.getSession().setAttribute("loginUser", userExist);

		// 存放用户所属机构
		Location location = userExist.getLocation();
		request.getSession().setAttribute("location", location);

		// 存放用户角色
		return "index";
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public JsonBean getJsonBean() {
		return jsonBean;
	}

	@Override
	public User getModel() {
		user = new User();
		return user;
	}

}
