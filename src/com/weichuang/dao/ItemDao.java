package com.weichuang.dao;

import com.weichuang.pojo.Item;

import java.util.List;

public interface ItemDao {
    List<Item> getItemList();

    Item getItemById(String id);
}
