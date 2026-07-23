package qwins.myshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import qwins.myshop.user.User;
import qwins.myshop.user.UserService;

@SpringBootApplication
public class MyShopApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyShopApplication.class, args);
    }

}
