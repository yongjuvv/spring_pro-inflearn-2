package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
//@Primary //Autowired시에 빈이 여러개 매칭되면 @Primary가 우선순위를 갖는다.
// 참고 : @Qualifier가 @Primary보다 우선순위가 높다(수동이 자동보다 높은 우선순위를 가지는 원칙)
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else{
            return 0;
        }
    }
}
