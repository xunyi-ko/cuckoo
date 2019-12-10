/**
 * 聊天室房间信息
 */
package site.xunyi.cuckoo.entity;

/**
 * @author xunyi
 */
public class Room {
    
    private Long id;
    
    /**
     * 房间名
     */
    private String name;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 盐
     */
    private String salt;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 创建者
     */
    private String creater;
    
    /**
     * 房主
     */
    private Long owner;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
