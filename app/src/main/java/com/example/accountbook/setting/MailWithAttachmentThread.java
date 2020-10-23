package com.example.accountbook.setting;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailWithAttachmentThread extends Thread {
    private String receiverAddress = null;

    private final String myEmailAccount = "861673644@qq.com";
    private final String myEmailPassword = "nrstmsufjpkbbbch";
    private final String smtpPort = "587";
    Context mContext;
    private String info = null;
    private File file;


    public MailWithAttachmentThread(Context context,String receiverAddress,File file){
        this.mContext = context;
        this.receiverAddress = receiverAddress;
        this.file = file;
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

    private boolean sendMail() throws Exception {
        Session session = createSession();
        info = "您的账单信息已导出，请注意查收";
        //创建一封邮件
        MimeMessage message = createMessage(session,myEmailAccount,receiverAddress,info,file);
        if(message == null){
            return false;
        }
        //根据session获得串数对象
        Transport transport = session.getTransport();
        //使用邮箱账号和密码连接邮件服务器
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
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
        return true;
    }

    @Override
    public void run(){
        super.run();
        try {
            boolean sendState =  sendMail();
            if(sendState) {
                Looper.prepare();
                Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
                Log.d("test", "run: success");
            }
        } catch (Exception e) {
            //
        }
    }
    private MimeMessage createMessage(Session session, String myEmailAccount, String receiverAddress, String info,File file) throws Exception{
        //1.创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //2.设置发件人
        message.setFrom(new InternetAddress(myEmailAccount,"随手记账单信息导出","UTF-8"));
        //3.设置收件人
        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiverAddress,"亲爱的用户","UTF-8"));
        //4.邮件主题
        message.setSubject("验证码","UTF-8");
        //5.设置发件时间
        message.setSentDate(new Date());
        //6.像multipart对象添加邮件的各个部分的内容（文本+附件）
        Multipart multipart = new MimeMultipart();
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent("【随手记】"+info,"text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
        if(file != null){
            BodyPart fileBodyPart = new MimeBodyPart();
            DataSource source  = new FileDataSource(file);
            fileBodyPart.setDataHandler(new DataHandler(source));
            fileBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
            multipart.addBodyPart(fileBodyPart);
        }
        else{
            Toast.makeText(mContext,"未找到附件",Toast.LENGTH_SHORT);
            return null;
        }
        //7.将multipart对象放在message中
        message.setContent(multipart);
        //8.保存设置
        message.saveChanges();

        return message;

    }
}
