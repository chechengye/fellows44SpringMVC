package com.weichuang.service;

import com.weichuang.pojo.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItemList();

    Item getItemById(String id);
}
