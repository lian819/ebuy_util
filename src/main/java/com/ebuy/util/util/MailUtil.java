package com.ebuy.util.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * 
 *
 * @author 	Lian
 * @time	2016年8月1日
 *
 */
public class MailUtil {

	public final static String QQ_HOST = "smtp.qq.com";
	public final static int QQ_PORT = 465;
	public final static String QQ_MAIL_NAME = "738978190@qq.com";
	public final static String QQ_MAIL_PWD = "Lian_199188";

	public final static String GMAIL_HOST = "smtp.googlemail.com";
	public final static int GMAIL_PORT = 465;
	public final static String GMAIL_NAME = "lianxinzhong88@gmail.com";
	public final static String GMAIL_PWD = "Lian_199188";

	public final static String EBUY_HOST = "mail.e-buychina.com.cn";
	public final static int EBUY_PORT = 25;
	public final static String EBUY_MAIL_NAME = "lianxinzhong@e-buychina.com.cn";
	public final static String EBUY_MAIL_PWD = "lianxinzhong199188";

	public final static String NETEASE_HOST = "mail.e-buychina.com.cn";
	public final static int NETEASE_PORT = 25;
	public final static String NETEASE_MAIL_NAME = "lianxinzhong@126.com";
	public final static String NETEASE_MAIL_PWD = "Lian_199188";

	public final static String TARGET_MAIL = "lianxinzhong@126.com";

	public static void sendMail(File file, String mailTo) {
		// send mail --------------------------------------
		try {
			EmailAttachment attachment = null;
			String fileName = file.getName();
			if(file != null) {
				attachment = new EmailAttachment();
				try {
					attachment.setPath(file.getAbsolutePath());
					attachment.setName(MimeUtility.encodeText(fileName));
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
				} catch (UnsupportedEncodingException e) {
					// TODO log
					return;
				}
			}

			// Send Register Email
			HtmlEmail email = new HtmlEmail();

			// qq
			email.setHostName("");
			email.setSmtpPort(0);
			DefaultAuthenticator auth = new DefaultAuthenticator(QQ_MAIL_NAME, QQ_MAIL_PWD);
			email.setAuthenticator(auth);
			email.setHtmlMsg("");
			email.setFrom(QQ_MAIL_NAME);

			email.setCharset("UTF-8");
			email.attach(attachment);
			email.setSubject("Subject");

			email.setSubject(TARGET_MAIL);

			String emailResult = email.send();
			file.delete();
		} catch(Exception e) {
			
		}
	}

	/**
	 * 使用Ebuy邮箱发送文本邮件
	 * 
	 * status：send success
	 * @attention：不使用SSL
	 */
	public static void sentEbuyTextMail() {
		Email email = new SimpleEmail();
		email.setHostName(EBUY_HOST);
		email.setSmtpPort(EBUY_PORT);
		email.setAuthenticator(new DefaultAuthenticator(EBUY_MAIL_NAME, EBUY_MAIL_PWD));
//		email.setSSLOnConnect(true);

		try {
			email.setFrom(EBUY_MAIL_NAME);
			email.setMsg("This is a test mail_common_email");
			email.addTo(TARGET_MAIL);
			email.setSubject("Test mail text msg");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public static void sendNeteaseTextMail() {
		Email email = new SimpleEmail();
		email.setHostName(NETEASE_HOST);
		email.setSmtpPort(EBUY_PORT);
		email.setAuthenticator(new DefaultAuthenticator(EBUY_MAIL_NAME, EBUY_MAIL_PWD));

		try {
			email.setFrom(EBUY_MAIL_NAME);
			email.setMsg("This is a test mail_common_email");
			email.addTo(TARGET_MAIL);
			email.setSubject("Test mail text msg");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		sentEbuyTextMail();
		System.out.println("send mail done");
	}
}
