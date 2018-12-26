package com.binjar.sample.app


interface ItemClickListener<Item> {
    fun onItemClick(position: Int, item: Item)
}