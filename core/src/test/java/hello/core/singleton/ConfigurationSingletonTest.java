package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository -> memberRepository = " + memberRepository);
        //AppConfig에서 memberService, orderService, memberRepository를 호출 할 때 new MemoryMemberRepository가 3번 호출됨(3번 생성됨)
        // ! 다른 인스턴스가 생성된 것 처럼 보이나, 확인해보면 같은 인스턴스임을 확인할 수 있음
        // 해석 : Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환, 없으면 빈을 생성 ==> 덕분에 싱글톤이 보장된다.


        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
        //AppConfig의 클래스 정보를 출력
        // 결과 : xxxCGLIB가 붙으면서 상당히 복잡한 클래스 정보가 출력됨
        // 결과 해석: AppConfig는 내가 만든 클래스가 아니라 스프링이 CGLIB라는 조작 라이브러리를 사용해서 AppConfig를 상속받은 임의의 다른 클래스르 만들고,
        // 그 다른 클래스를 스프링 빈으로 등록한 것이다.
        // ! --> 이 임의의 다른 클래스가 싱글톤이 보장되도록 해준다.
        //@Configuration 어노테이션이 존재하지 않는다면 CGLIB가 사용되지 않고, 결과적으로 MemoryMemberRepository객체가 3번 생성되어 싱글톤이 깨지게 된다.
    }
}
