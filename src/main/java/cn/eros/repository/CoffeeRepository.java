package cn.eros.repository;

import cn.eros.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Create time: 2020/4/26 22:01</p>
 *
 * @author 周光兵
 */
@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
