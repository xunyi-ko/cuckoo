/**
 * 聊天室房间信息
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
public class Room {
    
    private Long id;
    
    /**
     * 房间名
     */
    private String name;
    
    /**
     * 房间唯一编号
     */
    private String guid;
    
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
    private String owner;
    
    /**
     * 房间类型
     */
    private Integer type;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * 房间类型
     * @author xunyi
     */
    public enum RoomType{
        /**
         * 第一类房间，普通房，用socket发送和接收消息。
         * 类似于直播间的模式，只有在房间时才能接收到消息
         */
        NORMAL(0, "直播间", "normal"),
        
        /**
         * 第二类房间，交流群，类似qq群或微信群，但是不会有历史消息
         */
        GROUP(1, "交流群", "group")
        ;
        
        /**
         * 房间类型编号
         */
        private int value;
        /**
         * 房间类型名称
         */
        private String name;
        /**
         * 进入房间调用的方法名
         */
        private String methodName;
        
        RoomType(int value, String name, String methodName){
            this.value = value;
            this.name = name;
            this.methodName = methodName;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
        
        public String getMethodName() {
            return methodName;
        }
    }
}
