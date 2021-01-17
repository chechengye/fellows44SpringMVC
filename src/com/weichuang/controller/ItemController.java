package com.weichuang.controller;

import com.weichuang.pojo.Item;
import com.weichuang.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
//@RequestMapping("item")//    /item/list.do   /item/update.do
public class ItemController {

   /* @Autowired
    JdbcTemplate jt;*/

    @Autowired
    ItemService itemService;
    //@RequestMapping : 配置前端映射的/路由
    @RequestMapping("/itemList.do")
    public ModelAndView getItemList(HttpServletRequest req , HttpSession session){
        //1、获取数据库内容
        /*List<Item> itemList = jt.query("select * from items", new RowMapper<Item>() {

            @Override
            public Item mapRow(ResultSet rs, int i) throws SQLException {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setDetail(rs.getString("detail"));
                item.setCreatetime(rs.getDate("createtime"));
                return item;
            }
        });*/
        List<Item> itemList = itemService.getItemList();
        //ModelAndView : Model 底层实现 LinkedHashMap 结构key，value
        //前端控制器会将model中配置的所有键值对通过req.setAttribute(key,val)存于域中
        //View : 视图。要跳转的路径
        ModelAndView mav = new ModelAndView();
        mav.addObject("itemList",itemList);
        //mav.setViewName("WEB-INF/jsp/itemList.jsp");
        mav.setViewName("itemList");//配置视图解析器之后
        return mav;
    }

    @RequestMapping("/itemEdit.do")
    public ModelAndView getItemEditById(int id){
        //String id = req.getParameter("id");
        Item item = itemService.getItemById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("item",item);
        mav.setViewName("editItem");//配置视图解析器之后
        return mav;
    }
}
