package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//스프링은 기본적으로 타입에 기반해서 빈으로 등록함
//DiscountPolicy를 빈으로 등록할 때 FixDiscountPolicy와 RateDiscountPolicy 두 가지가 모두 component 스캔의 대상이 되기에 오류가 발생하게 된다.
@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }


    }
}
