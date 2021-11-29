package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //할인 정책을 변경하려면 클라이언트인 OrderServiceImpl을 고쳐야하는 문제점 발생
    //OCP, DIP 객체지향 설계 원칙을 준수하지 못함
    //구체(구현) 클래스 RateDiscountPolicy()에 의존하고 있음 => DIP 설계 원칙 위반
    //기능을 확장했더니 클라이언트 코드에 영향을 끼침 => OCP 설계 원칙 위반

    /*
    * 변경
    *
    * */


    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //추상 클래스에만 의존하고 있음 -> DIP 준수



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);

    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
