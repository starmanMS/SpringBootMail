package com.mirrorseek.mail.controller;

import com.mirrorseek.mail.entity.ResetBean;
import com.mirrorseek.mail.service.VerifyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiController {

    @Resource
    VerifyService verifyService;

    @RequestMapping("/verify-code")
    public ResetBean verifyCode(@RequestParam("email") String email) {
        try {
            verifyService.sendVerifyCode(email);
            return new ResetBean(200, "邮件发送成功！");
        } catch (Exception e) {
            return new ResetBean(500, "邮件发送失败！");
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResetBean register(String username,
                         String password,
                         String email,
                         String code) {
            if (verifyService.doVerify(email, code)) {
                return new ResetBean(200, "注册成功！");
            }else {
                return new ResetBean(403, "注册失败，验证码填写错误！");
            }
        }


}
