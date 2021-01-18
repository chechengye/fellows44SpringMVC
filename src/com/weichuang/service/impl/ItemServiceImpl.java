package com.weichuang.service.impl;

import com.weichuang.dao.ItemDao;
import com.weichuang.pojo.Item;
import com.weichuang.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;
    @Override
    public List<Item> getItemList() {
        return itemDao.getItemList();
    }

    /**
     * 根据ID获取商品
     * @param id
     * @return
     */
    @Override
    public Item getItemById(Integer id) {
        return itemDao.getItemById(id);
    }

    /**
     * 更新商品
     * @param item
     * @return
     */
    @Override
    public int updateItemById(Item item) {
        return itemDao.updateItemById(item);
    }

    /**
     * 根据传递的id进行删除操作
     * @param ids
     * @return
     */
    @Override
    public int deleteItemByIds(String[] ids) {
        return itemDao.deleteItemByIds(ids);
    }
}
