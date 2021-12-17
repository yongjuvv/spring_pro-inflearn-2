package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//스프링 컨테이너가 MyLogger를 상속받은 가짜 프록시 객체를 생성한다.
//이후 스프링 컨테이너에 myLogger라는 이름으로 진짜 대신 가짜 프록시 객체를 등록해둔다.
//provider, proxy 핵심 아이디어 : 진짜 객체 조회를 꼭 필요한 시점까지 지연처리한다. 
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}
