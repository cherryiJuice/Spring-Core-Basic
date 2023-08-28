package hello.core.discount;

import hello.core.member.Member;

import java.util.Map;

public class DiscountService2 {

    private final Map<String, DiscountPolicy> policyMap;

    public DiscountService2(Map<String, DiscountPolicy> policyMap) {
        this.policyMap = policyMap;
        System.out.println("policyMap = " + policyMap);
    }

    public int discount(Member member, int price, String discountCode) {
        DiscountPolicy discountPolicy = policyMap.get(discountCode);
        return discountPolicy.discount(member,price);
    }
}
