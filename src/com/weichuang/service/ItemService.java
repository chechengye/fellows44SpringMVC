package com.weichuang.service;

import com.weichuang.pojo.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItemList();

    Item getItemById(Integer id);

    int updateItemById(Item item);

    int deleteItemByIds(String[] ids);
}
