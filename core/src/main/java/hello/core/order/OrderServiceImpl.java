package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //final이 붙으면 필수값 -> final이 붙은 필드를 모아서 생성자를 만들어줌
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


    //필드 주입 -> 이름 그대로 필드에 바로 주입하는 방법
    //코드가 간결하지만, 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 존재한다.
    //테스트 코드에서만 작성하고, 실제 애플리케이션 관련 코드에서는 가급적 사용하지 않는게 좋다.
    /*@Autowired*/ private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //수정자 주입(Setter 주입)
    //선택, 변경 가능성이 있는 상황에 사용
    /*@Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    //@RequiredArgsConstructor를 통해서 생성자가 만들어짐
    @Autowired //생성자 주입방법
    //생성자가 1개만 있을 때는 @Autowired가 없어도 자동으로 의존관계 주입이 된다.
    public OrderServiceImpl(MemberRepository memberRepository,@MainDiscountPolicy DiscountPolicy discountPolicy) {
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
