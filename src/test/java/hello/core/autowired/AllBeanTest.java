package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.DiscountPolicyConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.DiscountService2;
import hello.core.member.Grade;
import hello.core.member.Member;
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
//        이 코드가 스프링 컨테이너 그 자체이기 때문에 스프링 컨테이너에 스프링 빈으로 직접 등록하는 것이여서
//        @Component 없이 스프링 빈으로 등록이 됩니다.

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "memberA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);
    }

    @Test
    void findAllBean2() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DiscountPolicyConfig.class);

        DiscountService2 discountService = ac.getBean(DiscountService2.class);
        Member member = new Member(1L, "memberA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService2.class);
        assertThat(discountPrice).isEqualTo(1000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        // @Autowired가 붙은 메서드의 파라미터로 Map<String, BeanType>이 온다면
        // 스프링은 BeanType에 해당하는 빈들을 찾아서 Map으로 의존관계를 주입해줍니다.
        // 이는 스프링에서 제공하는 기능 중 하나
        // Map<String, DiscountPolicy>의 String에는 빈 객체들의 이름이 들어가게 됨
        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member,price);
        }
    }
}
