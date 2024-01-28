package com.item.tomatofarm;

import java.util.List;

public class ItemService {
	
	ItemDAO dao = new ItemDAO();
	
	public List<ItemDTO> itemList(String name) {
		return dao.itemList(name);
	}
}
