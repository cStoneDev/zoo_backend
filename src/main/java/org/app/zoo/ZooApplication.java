package org.app.zoo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ZooApplication {
    public static void main(String[] args) {
      SpringApplication.run(ZooApplication.class, args);
    }
}
