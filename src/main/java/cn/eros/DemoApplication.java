package cn.eros;

import cn.eros.entity.Coffee;
import cn.eros.entity.CoffeeOrder;
import cn.eros.repository.CoffeeOrderRepository;
import cn.eros.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Create time: 2020/4/26 07:12</p>
 *
 * @author 周光兵
 */
@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class DemoApplication implements ApplicationRunner {
    private final CoffeeRepository coffeeRepository;
    private final CoffeeOrderRepository coffeeOrderRepository;

    public DemoApplication(CoffeeRepository coffeeRepository, CoffeeOrderRepository coffeeOrderRepository) {
        this.coffeeRepository = coffeeRepository;
        this.coffeeOrderRepository = coffeeOrderRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initDb();

        findOrder();
    }

    /**
     * 初始化DB
     */
    private void initDb() {
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        this.coffeeRepository.save(espresso);
        log.info("Coffee:{}", espresso);

        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();
        this.coffeeRepository.save(latte);
        log.info("Coffee:{}", latte);

        CoffeeOrder order = CoffeeOrder.builder()
                .customer("Eros")
                .coffees(Collections.singletonList(espresso))
                .state(0)
                .build();
        this.coffeeOrderRepository.save(order);
        log.info("order:{}", order);

        order = CoffeeOrder.builder()
                .customer("Eros")
                .coffees(Arrays.asList(espresso, latte))
                .state(0)
                .build();
        this.coffeeOrderRepository.save(order);
        log.info("order:{}", order);
    }

    private void findOrder() {
        this.coffeeOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .forEach(item -> log.info("loading {}", item));

        List<CoffeeOrder> list = this.coffeeOrderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(list));

        list = this.coffeeOrderRepository.findByCustomerOrderById("eros");
        log.info("findByCustomerOrderById: {}", getJoinedOrderId(list));

        list.forEach(o -> {
            log.info("Order {}", o.getId());
            o.getCoffees().forEach(i -> log.info("Item: {}", i));
        });

        list = this.coffeeOrderRepository.findByCoffees_Name("latte");
        log.info("findByCoffees_Name: {}", getJoinedOrderId(list));

    }

    private String getJoinedOrderId(List<CoffeeOrder> list) {
        return list.stream()
                .map(o -> o.getId().toString())
                .collect(Collectors.joining(","));
    }
}
