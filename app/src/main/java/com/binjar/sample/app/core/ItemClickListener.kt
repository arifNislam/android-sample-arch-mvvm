package com.binjar.sample.app.core


interface ItemClickListener<Item> {
    fun onItemClick(position: Int, item: Item)
}