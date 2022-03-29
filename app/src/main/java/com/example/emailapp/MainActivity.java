package com.example.emailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.chrono.MinguoChronology;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {
    Button Click_Btn;
    EditText senderEmail,senderPassword,receiverEmail,messageField,subjectField;
    String messageText="";
    String subjectText="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Click_Btn = findViewById(R.id.Click_Btn);
        senderEmail = findViewById(R.id.senderEmail);
        senderPassword = findViewById(R.id.senderPassword);
        receiverEmail = findViewById(R.id.receiverEmail);
        messageField = findViewById(R.id.messageField);
        subjectField = findViewById(R.id.subjectField);



        Click_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    messageText = messageField.getText().toString();
                    subjectText = subjectField.getText().toString();
                String stringSenderEmail = senderEmail.getText().toString();
                String stringReceiverEmail = receiverEmail.getText().toString();
                String stringSenderPassword = senderPassword.getText().toString();

                String stringHost = "smtp.gmail.com";

                Properties properties = System.getProperties();
                properties.put("mail.smtp.host",stringHost);
                properties.put("mail.smtp.port","465");
                properties.put("mail.smtp.ssl.enable","true");
                properties.put("mail.smtp.auth","true");

                javax.mail.Session session= Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(stringSenderEmail,stringSenderPassword);
                    }
                });
                MimeMessage message = new MimeMessage(session);

                    message.addRecipient(Message.RecipientType.TO,new InternetAddress(stringReceiverEmail));

                    message.setSubject(subjectText);
                    message.setText(messageText);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Transport.send(message);

                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                    Toast.makeText(getApplicationContext(),"Message sent Successfully",Toast.LENGTH_LONG).show();


                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}