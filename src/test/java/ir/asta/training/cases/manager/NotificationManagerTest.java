package ir.asta.training.cases.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.asta.wise.core.response.NotificationConf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {NotificationManager.class})
public class NotificationManagerTest {
    @Inject
    private NotificationManager manager;

    @Test
    public void testSMS() throws IOException {
        String json = "";
        try (Scanner scanner = new Scanner(new FileInputStream("conf/notification.json"))) {
            while (scanner.hasNextLine()){
                json += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        NotificationConf conf = mapper.readValue(json, NotificationConf.class);
        System.out.println(conf.getDefaultText());
        manager.sendSMS(conf.getDefaultText(),conf.getDefaultNumber());
    }
}
