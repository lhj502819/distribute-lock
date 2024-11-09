package com.onenine.distributelock.repository;

import com.onenine.distributelock.entity.CommonLock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Description：
 *
 * @author li.hongjian
 * @email lhj502819@163.com
 * @since 2024/11/9 13:44
 */
@Repository
public interface CommonLockRepository extends CrudRepository<CommonLock, Long> {
    CommonLock findByLockKey(String lockKey);
}
