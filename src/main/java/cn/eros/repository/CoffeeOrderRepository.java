package cn.eros.repository;

import cn.eros.entity.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Create time: 2020/4/26 22:02</p>
 *
 * @author 周光兵
 */
@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
