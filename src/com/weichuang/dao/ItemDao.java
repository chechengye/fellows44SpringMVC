package com.weichuang.dao;

import com.weichuang.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemDao {
    List<Item> getItemList();

    Item getItemById( Integer id);

    int updateItemById(Item item);

    int deleteItemByIds(String[] ids);
}
