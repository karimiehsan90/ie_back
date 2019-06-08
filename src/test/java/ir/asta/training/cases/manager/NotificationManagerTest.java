package ir.asta.training.cases.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.asta.wise.core.response.NotificationConf;
import org.junit.Before;
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

    private NotificationConf conf;

    private boolean setup = false;

    @Before
    public void setConf() throws IOException {
        if (setup){
            return;
        }
        StringBuilder json = new StringBuilder();
        try (Scanner scanner = new Scanner(new FileInputStream("conf/notification.json"))) {
            while (scanner.hasNextLine()){
                json.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        conf = mapper.readValue(json.toString(), NotificationConf.class);
        System.out.println("here");
        setup = true;
    }

    @Test
    public void testSMS() throws IOException {
        manager.sendSMS(conf.getDefaultText(),conf.getDefaultNumber());
    }

    @Test
    public void testEmail(){
        manager.sendEmail(conf.getDefaultText(), "<h1>"+ conf.getDefaultText() + "</h1>", conf.getEmail());
    }
}
