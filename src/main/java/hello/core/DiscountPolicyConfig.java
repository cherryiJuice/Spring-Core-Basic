package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.DiscountService2;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DiscountPolicyConfig {

    @Bean
    public DiscountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }

    @Bean
    public Map<String, DiscountPolicy> discountServiceMap() {
        Map<String, DiscountPolicy> discountPolicyMap = new HashMap<>();
        discountPolicyMap.put("rateDiscountPolicy",rateDiscountPolicy());
        discountPolicyMap.put("fixDiscountPolicy", fixDiscountPolicy());
        return discountPolicyMap;
    }

    @Bean public DiscountService2 discountService() {
        return new DiscountService2(discountServiceMap());
    }

}
