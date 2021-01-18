package com.weichuang.controller;

import com.weichuang.pojo.Item;
import com.weichuang.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping(value = {"/itemList.do" , "/listItem.do"} , method = {RequestMethod.POST , RequestMethod.GET})
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
        int i = 1/0;
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

    /**
     * @RequestParam :
     *      value ： 与前端参数名称保持一致。
     *      required ： 默认是true，必要属性。false不必须属性，可以传递可以不传。若是设定了必传，就不要加defaultValue属性了。
     *      defaultValue : 默认值，前端传递了用传递的。不传使用默认值。(分页) defaultValue 与 required = false配合使用
     *      defaultValue 与 required = true 不建议同时使用。
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/itemEdit.do")
    public ModelAndView getItemEditById(@RequestParam(value = "id" , required = false , defaultValue = "2") Integer id , Boolean status){
        //String id = req.getParameter("id");
        System.out.println("status = " + status);
        Item item = itemService.getItemById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("item",item);
        mav.setViewName("editItem");//配置视图解析器之后
        return mav;
    }

    //Model 与 String 返回值的方式是推荐的
    @RequestMapping("/itemUpdate.do")
    public String updateItemById(Model model , Item item){

        System.out.println("item = " + item);
        //1、将接受到的对象进行更新操作
        int rows = itemService.updateItemById(item);
        if(rows > 0){
            List<Item> itemList = itemService.getItemList();
            model.addAttribute("itemList",itemList);
            return "itemList";
        }
        return "editItem";
    }

    @RequestMapping("/itemDelete.do")
    public String deleteItemByIds(Model model , String[] ids){
        System.out.println("ids = " + ids);
        int rows = itemService.deleteItemByIds(ids);
        if(rows > 0){
            List<Item> itemList = itemService.getItemList();
            model.addAttribute("itemList",itemList);
        }
        return "itemList";
    }
}
