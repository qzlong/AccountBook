package com.example.accountbook.setting;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.loader.content.AsyncTaskLoader;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

public class EmailSupporterThread extends Thread {
    private String receiverAddress = null;

    private final String myEmailAccount = "861673644@qq.com";
    private final String myEmailPassword = "nrstmsufjpkbbbch";
    private final String smtpPort = "587";
    Context mContext;
    private String info = null;
    private String code;

    public EmailSupporterThread(Context context,String receiverAddress,String code){
        this.mContext = context;
        this.receiverAddress = receiverAddress;
        this.code = code;
    }

    private Session createSession(){
        Log.d("test", "1");
        Properties p = new Properties();

        //1.创建参数配置，用于连接邮件服务器
        p.setProperty("mail.host", "smtp.qq.com");
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.transport.protocol", "smtp");
        p.setProperty("mail.smtp.port", smtpPort);
        p.setProperty("mail.smtp.socketFactory", "javax.net.ssl.SSLSocketFactory");
        p.setProperty("mail.smtp.socketFactory.fallback", "false");
        p.setProperty("mail.smtp.socketFactory.port", smtpPort);

        //2.根据配置创建会话对象，用于和邮件服务器交互
        Session session = Session.getDefaultInstance(p);
        session.setDebug(true);

        return session;
    }

    private void sendTextMail() throws Exception{
        Session session = createSession();
        info = "你的验证码是：" + code;
        Log.d("test","2");
        //3.创建一封邮件
        MimeMessage message = createMessage(session,myEmailAccount,receiverAddress,info);
        Log.d("test","3");
        //4.根据Session获取邮件传输对象
        Transport transport = session.getTransport();
        Log.d("test","4");

        //5.使用邮箱账号和密码连接邮件服务器
        try {
            transport.connect(myEmailAccount, myEmailPassword);
        }catch(Exception e)
        {
            Log.d("test", "error");
            e.printStackTrace();
            Looper.prepare();
            Toast.makeText(mContext,"验证码发送失败,请稍后再试",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
        Log.d("test","5");
        //6.发送邮件
        transport.sendMessage(message,message.getAllRecipients());
        Log.d("test","已发送");
        //7.关闭连接
        transport.close();
    }

    @Override
    public void run(){
        super.run();
        try {
            sendTextMail();
            Looper.prepare();
            Toast.makeText(mContext,"发送成功",Toast.LENGTH_SHORT).show();
            Looper.loop();
            Log.d("test", "run: success");
        } catch (Exception e) {
            //
        }
    }

    private MimeMessage createMessage(Session session, String myEmailAccount, String receiverAddress, String info) throws Exception{
        //1.创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //2.设置发件人
        message.setFrom(new InternetAddress(myEmailAccount,"随手记绑定邮箱验证码","UTF-8"));
        //3.设置收件人
        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiverAddress,"亲爱的用户","UTF-8"));
        //4.邮件主题
        message.setSubject("验证码","UTF-8");
        //5.邮件正文
        message.setContent("【验证码】"+info,"text/html;charset=UTF-8");
        //6.设置发件时间
        message.setSentDate(new Date());
        //7.保存设置
        message.saveChanges();

        return message;

    }
}