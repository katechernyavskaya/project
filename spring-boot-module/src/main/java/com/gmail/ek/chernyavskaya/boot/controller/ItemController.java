package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.ItemRepository;
import com.gmail.ek.chernyavskaya.repository.model.Item;
import com.gmail.ek.chernyavskaya.service.ItemService;
import com.gmail.ek.chernyavskaya.service.model.*;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
@SessionAttributes(types = ItemsWithPaginationDTO.class)
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final PaginationUtil paginationUtil;

    public ItemController(ItemService itemService, ItemRepository itemRepository, PaginationUtil paginationUtil) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public String showAllItems(ItemsWithPaginationDTO itemsWithPagination, Model model) {
        List<ItemDTO> allItems = itemService.findAll();
        int countOfItems = allItems.size();
        PaginationDTO pagination = paginationUtil.addPagination(itemsWithPagination.getPagination(), countOfItems);
        itemsWithPagination.setPagination(pagination);
        List<ItemDTO> items = itemService.getAllItemsWithPagination(pagination);
        itemsWithPagination.setItems(items);
        model.addAttribute("itemsWithPagination", itemsWithPagination);
        return "items";
    }

    @GetMapping("/{id}/delete")
    public String deleteItemById(@PathVariable(name = "id") Long id) {
        Item item = itemRepository.findById(id);
        itemRepository.delete(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/copy")
    public String copyItemById(@PathVariable(name = "id") Long id) {
        ItemDTO item = itemService.findById(id);
        ItemDTO itemCopy = itemService.copyItem(item);
        itemService.add(itemCopy);
        return "redirect:/items";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable(name = "id") Long id) {
        ItemDTO item = itemService.findById(id);
        model.addAttribute("item", item);
        return "item";
    }

}