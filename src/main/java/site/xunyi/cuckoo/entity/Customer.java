/**
 * 聊天室用户
 * 
 * 暂定有三种
 *  1.只能看到进入聊天室后产生的信息
 *  2.能看到加入聊天室后产生的信息，但是看过一次后，下次进入不会再看到
 *  3.可以看到加入聊天室后的一切信息（有时间限制）
 */
package site.xunyi.cuckoo.entity;

/**
 * @author xunyi
 */
public class Customer {
    /**
     * id
     */
    private Long id;
    
    /**
     * 用户昵称
     */
    private String name;
    
    /**
     * 登录账号
     */
    private String login;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 盐
     */
    private String salt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
