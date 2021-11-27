package hello.core.member;

public class MemberServiceImpl implements MemberService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //추상에도 의존하고(MemberRepository), 구현에도 의존하고 있음(MemoryMemberRepository)
    //==> 구체화에 의존해서는 안된다는 DIP 의존원칙을 위반함 -> 좋은 객체지향 설계라고 할 수 없음


 /*
 * 추상에만 의존하도록 변경
 * */

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //추상클래스에만 의존 -> DIP 준수
    //MemberServiceImpl의 입장에서는 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
    //MemberServiceImpl의 생성자를 통해서 어떤 구현 객체가 주입될지는 오직 외부(AppConfig)에서 결정된다.
    //MemberServiceImpl의 입장에서 보면 의존관계를 외부에서 주입하는 것 -> 의존성 주입(DI)



    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
