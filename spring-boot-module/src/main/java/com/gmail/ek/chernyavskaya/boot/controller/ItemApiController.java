package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.ItemRepository;
import com.gmail.ek.chernyavskaya.repository.model.Item;
import com.gmail.ek.chernyavskaya.service.ItemService;
import com.gmail.ek.chernyavskaya.service.model.ItemDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemApiController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    public ItemApiController(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<ItemDTO> getItems() {
        return itemService.findAll();
    }

    @PostMapping
    public String addItem(
            @Valid @RequestBody ItemDTO item,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getValidationMessage(bindingResult);
        } else {
            itemService.add(item);
            return "Created Successfully";
        }
    }

    @GetMapping("/{id}")
    public ItemDTO getItemById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteItems(@PathVariable Long id) {
        Item item = itemRepository.findById(id);
        itemRepository.delete(item);
        return "Deleted Successfully";
    }

    private String getValidationMessage(BindingResult bindingResult) {  //повтор
        List<FieldError> errors = bindingResult.getFieldErrors();
        List<String> message = new ArrayList<>();
        for (FieldError e : errors) {
            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
        }
        return message.toString();
    }

}
