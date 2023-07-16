package com.mirrorseek.mail.service.impl;

import com.mirrorseek.mail.service.VerifyService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource
    JavaMailSender mailSender;
    @Resource
    StringRedisTemplate template;

    @Value("${spring.mail.username}")
    String from;
    @Override
    public void sendVerifyCode(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("【mirrorseek】您的注册验证码！");
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        template.opsForValue().set("verify-code:" + email, code + "", 3, TimeUnit.MINUTES);
        mailMessage.setText("您的注册验证码为："+ code + "，三分钟有效，请及时完成注册！如非本人操作请忽略。");
        mailMessage.setTo(email);
        mailMessage.setFrom(from);
        mailSender.send(mailMessage);
    }

    @Override
    public boolean doVerify(String email, String code) {
        String string = template.opsForValue().get("verify-code:" + email);
        if (string == null) return false;
        if (!string.equals(code)) return false;
        template.delete("verify-code:" + email);
        return true;
    }
}
