import cn.hutool.core.thread.ThreadUtil;
import com.onenine.distributelock.DistributeLockMySqlApplication;
import com.onenine.distributelock.mysql.service.ICommonLockService;
import com.onenine.distributelock.mysql.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;

/**
 * Description：
 *
 * @author li.hongjian
 * @email lhj502819@163.com
 * @since 2024/11/9 13:54
 */
@SpringBootTest(classes = DistributeLockMySqlApplication.class)
public class CommonLockTest {

    @Autowired
    private ICommonLockService commonLockService;

    @Test
    public void testTryLock() throws Exception{
        InetAddress localHost = InetAddress.getLocalHost();
        String ipAddress = localHost.getHostAddress();

        String lockKey = "test";
        ThreadUtil.concurrencyTest(10, () -> {
            boolean r = commonLockService.tryLock(lockKey, ipAddress, String.valueOf(Thread.currentThread().getId()));
            System.out.println(r);
        });
    }

    @Autowired
    private IOrderService orderService;

    @Test
    public void testPay() throws Exception{
        ThreadUtil.concurrencyTest(10, () -> orderService.pay("SKU_BOOK_01", 5566L));
    }

}
