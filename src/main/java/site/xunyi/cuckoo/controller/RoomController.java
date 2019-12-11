/**
 * 
 */
package site.xunyi.cuckoo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.hutool.core.exceptions.ValidateException;
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
            throw new ValidateException(getMsg("common.paramWrong"));
        }
        
        Method m = this.getClass().getMethod(type.getMethodName(), new Class[]{ModelAndView.class, String.class, String.class});
        return (ModelAndView) m.invoke(this, view, roomId, name);
    }
    
    /**
     * 进入房间
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
}
