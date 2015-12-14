package com.springdemo.module.usermgr.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springdemo.core.ActionException;
import com.springdemo.core.BaseContoller;
import com.springdemo.core.ServiceException;
import com.springdemo.module.usermgr.service.UserMgrService;
import com.springdemo.module.usermgr.vo.SUser;
/**
 * 用户登录管理
 * @author joe
 * @date  2011-10-24 下午03:34:39
 */
@Controller
public class UserLoginMgr extends BaseContoller{
	@Autowired
	private UserMgrService userMgrService;
	
	@RequestMapping(value="saveLogin.htm",method=RequestMethod.POST)
    public ModelAndView saveLogin(HttpSession session,SUser user,ModelMap modelMap){
		if(user!=null){
			String userName = user.getUserName();
			try {
				SUser dbuser = userMgrService.getUserByUserName(userName);
				if(dbuser==null){
					//成功后重定向的欢迎界面，防止重复提交
					return new ModelAndView("login",COMMON_FAIL_ALERT_KEY,"用户不存在!");
				}
				if(!dbuser.getPwd().equals(user.getPwd())){
					
					return new ModelAndView("login",COMMON_FAIL_ALERT_KEY,"密码错误!");
				}
				
				session.setAttribute(USER_SESSION_KEY, dbuser);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		//成功后重定向的欢迎界面，防止重复提交
		return new ModelAndView("redirect:login_succ.htm");
    }
	//获取
	@RequestMapping(value="get/{id}.htm",method=RequestMethod.GET)
    public ModelAndView get(@PathVariable Integer id) throws Exception{
		SUser sUser = userMgrService.getUserById(id);
		 if(sUser==null){
			throw  new ActionException("用户["+id+"]不存在!");
		 }
		return new ModelAndView("user_detail","user",sUser);
    }
	//列表
	@RequestMapping(value="list.htm",method=RequestMethod.GET)
    public ModelAndView list() throws ServiceException{
		List<SUser> users = userMgrService.getUsers();
		return new ModelAndView("user_list","users",users);
    }
	//注册成功
	@RequestMapping(value="login_succ.htm")
	public  String signuoSucc(HttpSession session,ModelMap map){
		SUser user = (SUser) session.getAttribute(USER_SESSION_KEY);
		if(user==null){
			return "redirect:login.htm";
		}
		map.put("user", user);
		return "index";
	}
	@RequestMapping("logout.htm")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:login.htm";
	}
	//首页
	@RequestMapping(value="index.htm")
	public  String index(){
		return "forward:login_succ.htm";
	}
}
