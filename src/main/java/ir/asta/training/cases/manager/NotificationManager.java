package ir.asta.training.cases.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ir.asta.wise.core.response.NotificationConf;
import ir.asta.wise.core.sms.SMSTokenRequest;
import ir.asta.wise.core.sms.SMSTokenResponse;
import ir.asta.wise.core.sms.SendSMSRequest;

import javax.inject.Named;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Named("notificationManager")
public class NotificationManager {
    private String ip = "RestfulSms.com";

    private int port = 443;

    private NotificationConf conf;

    public NotificationManager(){
        String json = "";
        try (Scanner scanner = new Scanner(new FileInputStream("conf/notification.json"))) {
            while (scanner.hasNextLine()){
                json += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            conf = mapper.readValue(json, NotificationConf.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSMS(String text, String number) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer();
        String json = writer.writeValueAsString(new SMSTokenRequest(conf.getSmsSecret(), conf.getSmsKey()));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String post = sendRequest("POST", "/api/Token", json, headers);
        SMSTokenResponse smsTokenResponse = mapper.readValue(post, SMSTokenResponse.class);
        headers.put("x-sms-ir-secure-token", smsTokenResponse.getToken());
        json = writer.writeValueAsString(new SendSMSRequest(text, number, conf.getSmsNumber()));
        sendRequest("POST", "/api/MessageSend", json, headers);
    }

    public String sendRequest(String method,String uri,String body, Map<String, String> headers) throws IOException {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL("https://" + ip + uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            for (String key:headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }

            connection.setRequestProperty("Content-Length",
                    Integer.toString(body.getBytes().length));
            connection.setRequestProperty("Content-Language", "fa-IR");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(body);
            wr.close();
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
