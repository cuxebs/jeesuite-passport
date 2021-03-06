package com.jeesuite.passport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.passport.dto.UserInfo;
import com.jeesuite.passport.service.UserService;
import com.jeesuite.security.SecurityDelegating;
import com.jeesuite.security.model.UserSession;
import com.jeesuite.springweb.model.WrapperResponse;

@Controller  
@RequestMapping(value = "/ucenter")
public class UserCenterController {

	private static final String USER_INFO_ATTR_NAME = "userInfo";
	@Autowired
	protected UserService userService;
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		UserInfo userInfo = userService.findAcctountById(Integer.parseInt(session.getUserId().toString()));
		model.addAttribute(USER_INFO_ATTR_NAME, userInfo);
		return "ucenter/index";
	}

	@RequestMapping(value = "snsbinding", method = RequestMethod.GET)
	public String snsbinding(Model model){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		model.addAttribute(USER_INFO_ATTR_NAME, session.getUserInfo());
		return "ucenter/snsbinding";
	}
	
	@RequestMapping(value = "setting", method = RequestMethod.GET)
	public String setting(Model model){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		model.addAttribute(USER_INFO_ATTR_NAME, session.getUserInfo());
		return "ucenter/setting";
	}
	
	@RequestMapping(value = "setting/password", method = RequestMethod.GET)
	public String passwordsetting(Model model){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		model.addAttribute(USER_INFO_ATTR_NAME, session.getUserInfo());
		return "ucenter/passwordsetting";
	}
	
	@RequestMapping(value = "setting/email", method = RequestMethod.GET)
	public String emailsetting(Model model){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		model.addAttribute(USER_INFO_ATTR_NAME, session.getUserInfo());
		return "ucenter/emailsetting";
	}
	
	@RequestMapping(value = "setting/mobile", method = RequestMethod.GET)
	public String mobilesetting(Model model){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		model.addAttribute(USER_INFO_ATTR_NAME, session.getUserInfo());
		return "ucenter/mobilesetting";
	}
	
	@RequestMapping(value = "user/update", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> updateMyUser(@RequestBody UserInfo account){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		account.setId(session.getUserId());
		userService.updateAccount(account);
		return new WrapperResponse<>();
	}
	
	@RequestMapping(value = "/unbind/{snsType}", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> unbindSnsAccount(@PathVariable("snsType") String snsType){
		UserSession session = SecurityDelegating.getAndValidateCurrentSession();
		userService.cancelSnsAccountBind(Integer.parseInt(session.getUserId().toString()), snsType);
		return new WrapperResponse<>();
	}
	
}
