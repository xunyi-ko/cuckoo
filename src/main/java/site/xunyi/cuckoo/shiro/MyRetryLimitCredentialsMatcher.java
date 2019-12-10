/**   
 * @Title: MyRetryLimitCredentialsMatcher.java
 * @Package com.ypkj.boss.shiro
 * @Description: shiro免密码登录
 * @date 2019年8月19日
 * @Copyright (c) 2019, 杭州映派科技有限公司 All Rights Reserved.
 */
package site.xunyi.cuckoo.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @ClassName: MyRetryLimitCredentialsMatcher
 * @author chen
 * @date 2019年8月19日
 * @version 1.0.0
 */
public class MyRetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		return super.doCredentialsMatch(token, info);
	}

}
