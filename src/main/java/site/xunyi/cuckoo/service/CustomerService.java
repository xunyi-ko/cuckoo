/**
 * 
 */
package site.xunyi.cuckoo.service;

import site.xunyi.cuckoo.entity.Customer;

/**
 * @author xunyi
 */
public interface CustomerService {
    Customer findByLogin(String login);
}
