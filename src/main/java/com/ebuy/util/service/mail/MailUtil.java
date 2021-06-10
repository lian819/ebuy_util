package com.ebuy.util.service.mail;

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

	public static void sendQQMail(File file, String mailTo) {
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
			DefaultAuthenticator auth = new DefaultAuthenticator(MailConsts.QQ_MAIL_NAME, MailConsts.QQ_MAIL_PWD);
			email.setAuthenticator(auth);
			email.setHtmlMsg("");
			email.setFrom(MailConsts.QQ_MAIL_NAME);

			email.setCharset("UTF-8");
			email.attach(attachment);
			email.setSubject("Subject");

			email.setSubject(MailConsts.TARGET_MAIL);

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
		email.setHostName(MailConsts.EBUY_HOST);
		email.setSmtpPort(MailConsts.EBUY_PORT);
		email.setAuthenticator(new DefaultAuthenticator(MailConsts.EBUY_MAIL_NAME, MailConsts.EBUY_MAIL_PWD));
//		email.setSSLOnConnect(true);

		try {
			email.setFrom(MailConsts.EBUY_MAIL_NAME);
			email.setMsg("This is a test mail_common_email");
			email.addTo(MailConsts.TARGET_MAIL);
			email.setSubject("Test mail text msg");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public static void sendNeteaseTextMail() {
		Email email = new SimpleEmail();
		email.setHostName(MailConsts.NETEASE_HOST);
		email.setSmtpPort(MailConsts.EBUY_PORT);
		email.setAuthenticator(new DefaultAuthenticator(MailConsts.EBUY_MAIL_NAME, MailConsts.EBUY_MAIL_PWD));

		try {
			email.setFrom(MailConsts.EBUY_MAIL_NAME);
			email.setMsg("This is a test Netease mail");
			email.addTo(MailConsts.TARGET_MAIL);
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
