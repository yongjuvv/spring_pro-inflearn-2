package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        //프로토타입 빈 2개 생성 --> 완전히 다른 스프링 빈이 생성됨
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        //Prototype.destroy이 실행되지 않음
        //-> 싱글톤 빈은 스프링 컨테이너가 관리하기에 스프링 컨테이너가 종료될 때 빈의 종료 메소드가 실행되지만,
        //프로토타입 빈은 스프링 컨테이너가 생성, 의존관계 주입, 초기화까지만 관여하고 더는 관리하지 않기에 destroy메소드가 실행되지 않는다.
        ac.close();

        //prototypeBean1.destroy(); 처럼 클라이언트가 직접 종료해야 한다.
    }
    //프로토타입 빈 생성
    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init() {
            System.out.println("Prototype.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Prototype.destroy");
        }
    }
}
