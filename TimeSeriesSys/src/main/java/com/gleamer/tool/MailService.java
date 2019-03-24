package com.gleamer.tool;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailService {
	@Autowired
	private JavaMailSender sender;

	
	//读取配置文件里的application.properties里的spring.mail.username
	@Value("${spring.mail.username}")
	private String fromUser;
	/**发送纯文本格式的，给某一个邮箱的，简单邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	public void sendSimpMail(String to,String subject,String content){
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(fromUser);
		message.setTo(to);
		message.setSubject(subject);//邮件标题
		message.setText(content);
		sender.send(message);
	}
	
	/**
	 * 发送带有附件的邮件
	 * @param to
	 * @param subject
	 * @param content
	 * @param filePath
	 * @throws MessagingException
	 */
	public void sendAttachmentMail(String to,String subject,String content,String filePath) throws MessagingException{
		//filePath文件在服务器的路径
		MimeMessage message=sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
		helper.setFrom(fromUser);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content,true);
		FileSystemResource file=new FileSystemResource(new File(filePath));
		String fileName=filePath.substring(filePath.lastIndexOf(File.separator)+1);
		//helper.addAttachment(attachmentFilename, file);
		helper.addAttachment(fileName, file);
		sender.send(message);
	}
	/**
	 * 发送html格式的邮件
	 * @param to
	 * @param subject
	 * @param content
	 * @throws MessagingException
	 */
	public void sendHtmlMail(String to,String subject,String content) throws MessagingException{
		MimeMessage message=sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
		helper.setFrom(fromUser);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content,true);
		sender.send(message);
		
	}
	public void sendIncludeResourceMail(String to,String subject,String content,String filePath,String contentId) throws MessagingException{
		MimeMessage message=sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
		helper.setFrom(fromUser);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content,true);
		
		FileSystemResource fsr=new FileSystemResource(new File(filePath));
		//helper.addInline(contentId, file);
		helper.addInline(contentId, fsr);
		sender.send(message);
	}
	

}
