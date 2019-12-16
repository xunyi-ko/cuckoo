/**
 * 
 */
package site.xunyi.cuckoo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.hutool.core.lang.Validator;
import site.xunyi.cuckoo.entity.Room.RoomType;

/**
 * @author xunyi
 */
@Controller
@RequestMapping("/room")
public class RoomController extends AbstractController{
    /**
     * 选择房间页
     */
    @RequestMapping("/choose")
    public ModelAndView choose(ModelAndView view) {
        view.addObject("types", RoomType.values());
        view.setViewName("room/Choose");
        return view;
    }
    
    @RequestMapping("/entry")
    public ModelAndView entry(ModelAndView view, String roomId, String name, Integer roomType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Validator.validateNotEmpty(roomId, getMsg("common.paramLost"));
        Validator.validateNotEmpty(name, getMsg("common.paramLost"));
        Validator.validateNotEmpty(roomType, getMsg("common.paramLost"));
        
        RoomType type = null;
        RoomType[] types = RoomType.values();
        for(RoomType rt : types) {
            if(roomType == rt.getValue()) {
                type = rt;
                break;
            }
        }
        if(type == null) {
            paramWrong();
        }
        
        Method m = this.getClass().getMethod(type.getMethodName(), new Class[]{ModelAndView.class, String.class, String.class});
        return (ModelAndView) m.invoke(this, view, roomId, name);
    }
    
    /**
     * 进入普通房/直播间
     * @param view
     * @param roomId
     * @param name
     * @return
     */
    @RequestMapping("/normal")
    public ModelAndView normal(ModelAndView view, String roomId, String name) {
        view.addObject("roomId", roomId);
        view.addObject("name", name);
        
        view.setViewName("room/Normal");
        return view;
    }
    
    /**
     * 个人的群聊页面
     * @param view
     * @param roomId
     * @param name
     * @return
     */
    @RequestMapping("/group")
    public ModelAndView group(ModelAndView view, String roomId, String name) {
        
        
        view.setViewName("room/Group");
        return view;
    }
}
