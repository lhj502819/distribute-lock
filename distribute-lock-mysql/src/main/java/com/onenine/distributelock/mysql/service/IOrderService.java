package com.onenine.distributelock.mysql.service;

/**
 * Description：
 *
 * @author li.hongjian
 * @email lhj502819@163.com
 * @since 2024/11/9 14:37
 */
public interface IOrderService {
    void pay(String skuNo, long userId);
}
