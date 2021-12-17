package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }
    @Scope("singleton")
    static class ClientBean{


        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            //1.ObjectProvider 사용
            //prototypeBeanProvider.getObject()를 통해서 항상 새로운 프로토타입 빈이 생성됨
            //ObjectProvider의 getObject()를 호출하면 내부에서 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다.

            //2. javax.Provider 사용
            // get으로 호출해서 빈을 찾아 반환
            //get()메서드 하나로 기능이 매우 단순
            //자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있다.
           PrototypeBean prototypeBean = prototypeBeanProvider.get();

           prototypeBean.addCount(); //logic을 호출할 때 생성시점에 주입된 prototypeBean을 사용하게 됨.
            //client1, client2가 같은 prototypeBean을 사용
            return prototypeBean.getCount();
        }
    }


    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public void addCount() {
            count++;
        }
        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
