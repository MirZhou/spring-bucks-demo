package cn.eros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * <p>Create time: 2020/4/26 22:50</p>
 *
 * @author 周光兵
 */
@NoRepositoryBean
public interface BaseRepository<T, R> extends JpaRepository<T, R> {
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
