package cn.eros.repository;

import cn.eros.entity.CoffeeOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Create time: 2020/4/26 22:02</p>
 *
 * @author 周光兵
 */
@Repository
public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);

    List<CoffeeOrder> findByCoffees_Name(String name);
}
