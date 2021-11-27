package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        MemberService memberService = new MemberServiceImpl();

        //ApplicationContext를 스프링 컨테이너라고 한다.
        //스프링 컨테이너 생성
        //스프링 컨테이너를 생성할 때 구성(설정) 정보를 지정해줘야 함 -> 파라미터로 AppConfig.class(설정 정보)를 넘김
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);//Bean의 이름은 Configuration의 메소드 이름과 같음, 타입은 MemberService.class
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("Find Member = " + findMember.toString());
        System.out.println("New member = " + member.toString());
    }
}
