package kr.go.seoul.noper2.util;

import com.imoxion.sensrelay.client.ImReceiptBean;
import com.imoxion.sensrelay.client.ImRelayClient;
import com.imoxion.sensrelay.client.ImRelayClientProc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class EmailUtil {
    @Value("${email.email}")
    private String sendEmail;
    @Value("${email.api-key}")
    private String apiKey;

    public boolean sendEmailToMe(String subject, String content) {
        List<String> targetEmails = new ArrayList<>();
        targetEmails.add(sendEmail);
        return sendEmail(subject, content, targetEmails);
    }

    public boolean sendEmail(String subject, String content, List<String> targetEmails) {
        if(sendEmail.isEmpty() || apiKey.isEmpty() || subject.isEmpty() || content.isEmpty() || targetEmails == null) return false;
        ImRelayClientProc r = new ImRelayClientProc();
        r.setFrom(sendEmail);
        r.setTo(sendEmail);
        r.setClient_key(apiKey);
        r.setSubject(subject);
        r.setBody("<p>" + content + "</p>");
        targetEmails.forEach(r::addReceipt);
        ImRelayClient rClient = r.doSend();

        if(!rClient.getResultStatus().equals("00")){
            log.error("메일 발송 실패");
        } else{
            log.info("메일 발송 성공");
        }

        return true;
    }
}
