/**
 * 
 */
package site.xunyi.cuckoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xunyi
 */
@Controller
public class HomeController extends AbstractController{
    @RequestMapping(value = {"/", "home"})
    public ModelAndView homePage(ModelAndView view) {
        view.setViewName("Home");
        return view;
    }
}
