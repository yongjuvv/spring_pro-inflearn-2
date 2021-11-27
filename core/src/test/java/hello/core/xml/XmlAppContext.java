package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class XmlAppContext {

    //xml 설정 파일을 생성하고, 빈을 등록해서 사용
    @Test
    void XmlAppContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        //xml 기반의 설정 파일을 이용하기 위해선 GenericXmlApplicationContext 사용
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);


    }
}
