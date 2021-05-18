package uet.example.demoMS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Sender implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Sender(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = mapper.writeValueAsString(getStudentInfo());
        rabbitTemplate.convertAndSend(DemoMsApplication.topicExchangeName, "foo.bar.baz", json);
        receiver.getLatch().await(15, TimeUnit.SECONDS);
    }

    private List<StudentInfo> getStudentInfo() {
        List<StudentInfo> studentInfos = new ArrayList<>();

        studentInfos.add( StudentInfo.builder()
                .fullName("Nguyen Quoc An")
                .className("CACLC2")
                .studentId("18020106")
                .dob("01-01-2000")
                .address("Duong Buoi")
                .build());

        studentInfos.add( StudentInfo.builder()
                .fullName("Nguyen Duc Anh")
                .className("CACLC5")
                .studentId("18080575")
                .dob("01-02-2000")
                .address("Cau Giay")
                .build());

        studentInfos.add( StudentInfo.builder()
                .fullName("Hoang Phuong Linh")
                .className("CACLC2")
                .studentId("18080575")
                .dob("01-09-2000")
                .address("Cau Giay")
                .build());

        return studentInfos;
    }

}