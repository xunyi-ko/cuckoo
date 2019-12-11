/**
 * 
 */
package site.xunyi.cuckoo.service;

import site.xunyi.cuckoo.entity.Customer;

/**
 * @author xunyi
 */
public interface CustomerService {
    /**
     * 根据用户账号获取用户信息
     * @param login 用户登录账号
     * @return
     */
    Customer findByLogin(String login);
}
