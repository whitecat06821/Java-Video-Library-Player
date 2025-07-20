package com.example.bunnyvideolibrary;

import java.util.List;

public class BunnyVideoResponse {
    private int totalItems;
    private int currentPage;
    private int itemsPerPage;
    private List<BunnyVideo> items;

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getItemsPerPage() { return itemsPerPage; }
    public void setItemsPerPage(int itemsPerPage) { this.itemsPerPage = itemsPerPage; }

    public List<BunnyVideo> getItems() { return items; }
    public void setItems(List<BunnyVideo> items) { this.items = items; }
} 