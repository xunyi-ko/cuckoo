/**
 * 聊天室用户
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
