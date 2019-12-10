package site.xunyi.cuckoo.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import site.xunyi.cuckoo.entity.Customer;
import site.xunyi.cuckoo.service.CustomerService;


/**
 * realm实现类,用于实现具体的验证和授权方法
 * @author Bean
 *
 */
public class MyShiroRealm extends AuthorizingRealm {
	
	@Resource  
	private CustomerService customerService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return new SimpleAuthorizationInfo();
	}

	/** 
	 * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("Shiro登录认证启动");
		// 获取用户的输入的账号.
		String username = (String) token.getPrincipal();
		// 通过username从数据库中查找 User对象，如果找到，没找到.
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		Customer customer = customerService.findByLogin(username);

		System.out.println("----->>userInfo=" + customer);
		if (customer == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				// 用户名
				customer,
				// 密码
				customer.getPassword(),
				ByteSource.Util.bytes(customer.getSalt()),
				// realm name
				getName());
		return authenticationInfo;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		return principals.getPrimaryPrincipal();
	}

}

