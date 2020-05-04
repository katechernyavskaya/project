package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.repository.ItemRepository;
import com.gmail.ek.chernyavskaya.repository.model.Item;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.service.ItemService;
import com.gmail.ek.chernyavskaya.service.model.ItemDTO;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.util.ProductCodeGeneratorUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.ek.chernyavskaya.service.impl.PaginationUtilImpl.getStartElement;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ProductCodeGeneratorUtil productCodeGeneratorUtil;

    public ItemServiceImpl(ItemRepository itemRepository, ProductCodeGeneratorUtil productCodeGeneratorUtil) {
        this.itemRepository = itemRepository;
        this.productCodeGeneratorUtil = productCodeGeneratorUtil;
    }

    @Override
    public List<ItemDTO> findAll() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemsDTO = items.stream().map(this::convertItemToDTO).collect(Collectors.toList());
        return itemsDTO;
    }

    @Override
    public ItemDTO add(ItemDTO itemDTO) {
        Item item = convertDTOToItem(itemDTO);
        Item returnedItem = itemRepository.add(item);
        return convertItemToDTO(returnedItem);
    }

    @Override
    public void delete(ItemDTO itemDTO) {
        Item item = convertDTOToItem(itemDTO);
        itemRepository.delete(item);
    }

    @Override
    public ItemDTO findById(Long id) {
        Item item = itemRepository.findById(id);
        return convertItemToDTO(item);
    }

    @Override
    public ItemDTO copyItem(ItemDTO item) {
        ItemDTO itemCopy = new ItemDTO();
        String nameForCopy = item.getName().concat("copy");
        itemCopy.setName(nameForCopy);
        String codeForCopy = productCodeGeneratorUtil.generateProductCode();
        itemCopy.setCode(codeForCopy);
        itemCopy.setPrice(item.getPrice());
        itemCopy.setSummary(item.getSummary());
        return itemCopy;
    }

    @Override
    public List<ItemDTO> getAllItemsWithPagination(PaginationDTO paginationDTO) {
        Pagination pagination = convertDTOToPagination(paginationDTO);
        List<Item> items = itemRepository.findAllWithPagination(pagination);
        return items.stream().map(this::convertItemToDTO).collect(Collectors.toList());

    }

    private ItemDTO convertItemToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setCode(item.getCode());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setSummary(item.getSummary());
        return itemDTO;
    }

    private Item convertDTOToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setCode(itemDTO.getCode());
        item.setPrice(itemDTO.getPrice());
        item.setSummary(itemDTO.getSummary());
        return item;
    }

    private Pagination convertDTOToPagination(PaginationDTO paginationDTO) {
        Pagination pagination = new Pagination();
        pagination.setElementsPerPage(paginationDTO.getElementsPerPage());
        pagination.setStartElement(getStartElement(paginationDTO));
        return pagination;
    }
}
