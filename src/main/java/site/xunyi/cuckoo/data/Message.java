/**
 * 
 */
package site.xunyi.cuckoo.data;

import java.util.Date;

/**
 * @author xunyi
 */
public class Message {
    private Long id;
    
    private String msg;
    
    private Date sendTime;
    
    private String account;
    
    private Integer type;
    
    private String roomName;
    
    private String roomGuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomGuid() {
        return roomGuid;
    }

    public void setRoomGuid(String roomGuid) {
        this.roomGuid = roomGuid;
    }

    public enum Type{
        CREATE_ROOM,
        MESSAGE;
    }
}
