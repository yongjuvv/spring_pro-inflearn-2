package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "memberA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);
        //fixDiscountPolicy일 경우에는 고정적으로 1000원 할인

        int rateDiscountPrice = discountService.discount(member, 10000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(1000);
        //rateDiscountPolicy일 때는 구매가격의 10% 할인 -> 이 경우 price 10000 => 1000원 할인인
    }
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        //Map 형태로 fixDiscountPolicy, rateDiscountPolicy 빈을 모두 저장함
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            //discountCode로 fixDiscountPolicy인지 rateDiscountPolicy인지 값을 주게 되면 이것을 통해서 discountPolicy에 저장함
            return discountPolicy.discount(member, price);
            //선택된 discountPolicy에 맞는 discount값을 return
        }
    }

}

