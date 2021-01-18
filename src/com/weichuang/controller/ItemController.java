package com.weichuang.controller;

import com.weichuang.config.Constants;
import com.weichuang.pojo.Item;
import com.weichuang.service.ItemService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        //int i = 1/0;
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

    /**
     * 1、使用MultipartFile pictureFile接收前端上传的图片
     * 2、获取图片存在的真实路径
     * 3、修改图片名称。防止多次上传重复问题
     * 4、获取原文件的扩展名
     * 5、上传文件至服务器上
     * 6、将图片的相对地址存于数据库中
     * @param pictureFile
     * @param model
     * @param item
     * @return
     */
    //Model 与 String 返回值的方式是推荐的
    @RequestMapping("/itemUpdate.do")
    public String updateItemById(MultipartFile pictureFile , Model model , Item item , HttpServletRequest req){

        try {
            ////xxx.jpg  xxxx.png  xxx.
            System.out.println("item = " + item);
            System.out.println("pictureFile = " + pictureFile);
            //获取图片存在的真实路径
            String realPath = req.getServletContext().getRealPath(Constants.UPLOAD_DIR);
            //修改图片名称。防止多次上传重复问题
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileNewName = df.format(new Date());
            //获取原文件扩展名 pictureFile.getOriginalFilename() ： 获取文件的全名称
            String extension = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
            System.out.println("extension = " + extension);
            String newOriginalFilename = fileNewName + "." + extension;
            //上传文件至服务器上
            pictureFile.transferTo(new File(realPath + "/" + newOriginalFilename));
            //将图片的相对地址存于数据库中
            item.setPic(Constants.UPLOAD_DIR + "/" + newOriginalFilename);
            //1、将接受到的对象进行更新操作
            int rows = itemService.updateItemById(item);
            if(rows > 0){
                List<Item> itemList = itemService.getItemList();
                model.addAttribute("itemList",itemList);
                return "itemList";
            }
        } catch (IOException e) {
            e.printStackTrace();
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
