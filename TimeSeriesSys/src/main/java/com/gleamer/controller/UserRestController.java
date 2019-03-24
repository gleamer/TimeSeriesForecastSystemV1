package com.gleamer.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gleamer.mapper.UserMapper;
import com.gleamer.model.User;
import com.gleamer.tool.MailService;

@RestController
public class UserRestController {
	@Autowired
	private UserMapper userMapper;
/*	
	@Autowired
	private JavaMailSender mailSender;
*/	
	@Autowired
	private MailService ms;
	
	@PostMapping("userlogin")
	public User selectUserByEmailAndPassword(@RequestParam("emailo") String inputemail,
			@RequestParam("passwordo") String inputpassword,HttpServletRequest request)throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		User user=null;
		user=userMapper.selectUserByEmail(inputemail);
		System.out.println("---selectPasswordByEmail");
		if(user.getPassword().equals(inputpassword)){

		request.getSession().setAttribute("loginSession", user);
		return user;
		}
		else
		return null;
		
		
		//登陆应该是只用email，查询出一整条数据后比对password
	}
	
	//注册后发邮件到用户邮箱
	@PostMapping("usersignup")
	public int userRegister(@RequestParam("emailu") String inputemail,@RequestParam("passwordu") String password,
			@RequestParam("nicknameu") String nickname) throws MessagingException{
		int user=0;
		User u=null;
		u=userMapper.selectUserByEmail(inputemail);
		if(u==null){
		   user=userMapper.insertUserRegister(inputemail,password,nickname);
		   
		 //发邮件---  
		   ms.sendHtmlMail(inputemail, "你注册了时间序列分析预测系统", "<html><head></head><body>"
					+"<h1 style='color:#1BC0C6;'>你好：</h1>"
					+ "<div><h1 style='color:#1BC0C6;'>&nbsp;&nbsp;你已经注册了时间序列分析预测系统。</h1>"
					+ "<h6>邮箱："+inputemail+"</h6><h6>密码："+password+"</h6></div>"+
					"</body></html>");

		}

		//System.out.println("user="+user.getEmail()+" "+user.getPassword());
		System.out.println("---userRegister    user=="+user);
		return user;
	}
	
	@PostMapping("_showSession")
	public User showSession(HttpServletRequest request) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		User u=null;
		System.out.println("showSession==");
		if(request.getSession(false)!=null){
		u=(User) request.getSession().getAttribute("loginSession");
		}
		System.out.println("showSession=="+u);
		return u;
		
	}
	
	@PostMapping("_modifyInfo")
	public boolean modifyInfo(@RequestParam("opwd") String inputopwd,@RequestParam("pwd1") String inputpwd1,@RequestParam("pwd2") String inputpwd2,HttpServletRequest request) throws UnsupportedEncodingException{
		System.out.println("进入------改密码---------------");
		request.setCharacterEncoding("UTF-8");
		User u=null;
		
		if(request.getSession(false)!=null){
		u=(User) request.getSession().getAttribute("loginSession");
		}
		if(inputopwd.equals(u.getPassword())&&inputpwd1.equals(inputpwd2)){
			userMapper.updateUserPassword(u.getEmail(), inputpwd2);
			System.out.println("改密码-----成功----------");
			return true;//修改密码成功return true
		}
		else
		
		return false;
		
	}
	
	
	
	@PostMapping("adminSearch")
	public List<User> showAllUser(HttpServletRequest request)throws UnsupportedEncodingException{
		List<User> userlist=null;
		userlist=userMapper.selectUserByAdmin();
		System.out.println("----调用了--adminSearch------");

		return userlist;
	
	}
	
	@PostMapping("adminAddUser")
	public int adminInsertUser(@RequestParam("emaila") String email,@RequestParam("passworda") String password,
			@RequestParam("nicknamea") String nickname,@RequestParam("urolea") String urole){
		int a=0;
		a=userMapper.adminInsertUser(email, password, nickname, urole);

		System.out.println("***调用了**adminAddUser***");
		return a;
	}
	@PostMapping("adminUpdateUser")
	public void adminEditUser(@RequestParam("emaile") String email,@RequestParam("passworde") String password,
			@RequestParam("nicknamee") String nickname,@RequestParam("urolee") String urole,@RequestParam("uide") int uid){
		userMapper.adminUpdateUser(email, password, nickname, urole, uid);
		System.out.println("ooooooo调用了adminEditUseroooooooooooo");
	}




}
