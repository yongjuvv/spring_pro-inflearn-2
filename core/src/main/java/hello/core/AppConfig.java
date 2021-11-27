package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정 정보로 이용한다.
//@Configuration이 붙은 곳에서 @Bean이라 적힌 메서드를 호출해서 반환된 객체를 스프링 컨테이너에 등록한다
//이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라고 한다.
@Configuration
public class AppConfig {

    @Bean
    //생성자를 통해서 주입 -> 생성자 주입
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    //스프링 컨테이너에 빈이 생성됨
    //빈 이름은 메소드 이름(memberService), 빈 객체는 return한 값이 저장된다.

    //참고 : 스프링은 빈을 생성하고 의존관계를 주입하는 단계가 나우어져 있음
    // -> 먼저 빈을 생성하고 이후에 의존관계를 주입(DI)하는 단계가 이루어진다.


    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
        //할인정책을 변경하면 AppConfig(구성 영역)에서만 변경하면 된다.
        //기존의 코드를 전혀 변경하지 않고 애플리케이션의 확장이 가능해졌다!(OCP 설계 원칙 준수)
    }


    //리팩토링을 통해서 AppConfig를 보면 역할과 구현 클래스를 한눈에 볼 수 있게 되었다.
    //애플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악할 수 있다.
    //AppConfig를 통해서 discountPolicy를 사용할 때 현재는 FixDiscountPolicy(고정 할인 정책)를 사용하는 것을 확인할 수 있음
    //==> 이후에 할인 정책이 바뀐다면 코드를 쉽게 수정할 수 있음
}
