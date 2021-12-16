package hello.core.lifecycle;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출

    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출

    public void disconnect() {
        System.out.println("close: " + url);
    }

    //메소드 이름을 자유롭게 줄 수 있음.
    //스프링 빈이 스프링 코드에 의존하지 않음
    //설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화나 종료 메소드를 적용할 수 있음

    //@PostConstruct, @PreDestroy특징
    //스프링에서 가장 권장하는 방법이며, 어노테이션만 붙이면 되므로 매우 편리하다.
    //패키지를 보면 javax...인데 이는 스프링에 종속적인 기술이 아니라 자바 표준이라는 의미이다. 따라서 스프링이 아닌 다른 컨테이너에서도 정상적으로 동작한다.
    //유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것이다. 외부 라이브러리를 초기화, 종료 해야 하면 @Bean의 기능을 사용할 것
    @PostConstruct
    public void init(){ //의존관계 주입이 끝나고 나면 동작하는 메소드
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
