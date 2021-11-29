package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core", //탐색할 패키지의 시작 위치를 지정, 이 패키지를 포함하고 하위 패키지 또한 모두 탐색
        //지정하지 않으면 @ComponentScan이 붙은 설정 정보 클래스의 패키지(여기에선 hello.core)가 시작위치가 된다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) //@Component 어노테이션이 붙은 클래스를 모두 찾아서 자동으로 Bean으로 등록
//이때 스프링 빈의 기본 이름은 클래스명을 사용하되, 맨 앞글자만 소문자를 사용한다.
// ex) MemberServiceImpl 클래스 -> memberServiceImpl
public class AutoAppConfig {


    //수동 빈 등록, 자동 빈 등록 중복되는 경우
    //이 경우는 수동 빈 등록이 우선권을 가지고, 수동 빈이 자동 빈을 오버라이딩 한다.
    //그러나 스프링부트에서 실행하게 되면, bean overriding관련 오류가 발생하게 된다. -> 오버라이딩으로부터 발생하는 에러를 잡기 위함
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
