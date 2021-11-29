package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA : A사용자 10000원 주문
        int userA = statefulService1.order("userA", 10000);
        //ThreadB : B사용자 20000원 주문
        int userB = statefulService2.order("userB", 20000);

        //ThreadA : 사용자 A가 주문 금액 조회
        System.out.println("userA = " + userA);
        System.out.println("userB = " + userB);
         //20000원 출력
        assertThat(userA).isEqualTo(10000);
        //지역변수를 통해서 문제를 해결할 수 있음
        

//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        //사용자 A는 10000원을 주문했는데, 주문서에는 20000원을 사용했다고 출력될 수 있음 -> 문제 발생
    }
    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}