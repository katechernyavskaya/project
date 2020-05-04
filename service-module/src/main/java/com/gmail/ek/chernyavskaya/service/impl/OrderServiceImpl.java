package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.repository.OrderRepository;
import com.gmail.ek.chernyavskaya.repository.model.Item;
import com.gmail.ek.chernyavskaya.repository.model.Invoice;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.User;
import com.gmail.ek.chernyavskaya.service.OrderService;
import com.gmail.ek.chernyavskaya.service.model.ItemDTO;
import com.gmail.ek.chernyavskaya.service.model.InvoiceDTO;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.ek.chernyavskaya.service.impl.PaginationUtilImpl.getStartElement;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(PasswordEncoder passwordEncoder, OrderRepository orderRepository) {
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
    }

    @Override
    public InvoiceDTO add(InvoiceDTO order) {
        Invoice convertedOrder = convertDTOToOrder(order);
        Invoice orderSavedInDatabase = orderRepository.add(convertedOrder);
        return convertOrderToDTO(orderSavedInDatabase);
    }

    @Override
    public List<InvoiceDTO> findAll() {
        List<Invoice> orders = orderRepository.findAll();
        return orders.stream().map(this::convertOrderToDTO).collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {
        Invoice order = orderRepository.findById(id);
        return convertOrderToDTO(order);
    }

    @Override
    public List<InvoiceDTO> getUserOrders(UserDTO userDTO) {
        User user = convertDTOToUser(userDTO);
        List<Invoice> orders = orderRepository.getUserOrders(user);
        return orders.stream().map(this::convertOrderToDTO).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getAllOrdersWithPagination(PaginationDTO paginationDTO) {
        Pagination pagination = convertDTOToPagination(paginationDTO);
        List<Invoice> orders = orderRepository.findAllWithPagination(pagination);
        return orders.stream().map(this::convertOrderToDTO).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getAllUserOrdersWithPagination(PaginationDTO paginationDTO, UserDTO userDTO) {
        Pagination pagination = convertDTOToPagination(paginationDTO);
        User user = convertDTOToUser(userDTO);
        List<Invoice> orders = orderRepository.findAllUserOrdersWithPagination(pagination, user);
        return orders.stream().map(this::convertOrderToDTO).collect(Collectors.toList());
    }

    @Override
    public void merge(InvoiceDTO orderDTO) {
        Invoice order = convertDTOToOrder(orderDTO);
        orderRepository.merge(order);
    }

    private Pagination convertDTOToPagination(PaginationDTO paginationDTO) {
        Pagination pagination = new Pagination();
        pagination.setElementsPerPage(paginationDTO.getElementsPerPage());
        pagination.setStartElement(getStartElement(paginationDTO));
        return pagination;
    }

    private InvoiceDTO convertOrderToDTO(Invoice order) {
        InvoiceDTO orderDTO = new InvoiceDTO();
        orderDTO.setId(order.getId());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());
        ItemDTO itemDTO = convertItemToDTO(order.getItem());
        orderDTO.setItem(itemDTO);
        UserDTO userDTO = convertUserToDTO(order.getUser());
        orderDTO.setUser(userDTO);
        orderDTO.setDate(order.getDate());
        return orderDTO;

    }

    private Invoice convertDTOToOrder(InvoiceDTO orderDTO) {
        Invoice order = new Invoice();
        if (orderDTO.getId() != null) {
            order.setId(orderDTO.getId());
        }
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        Item item = convertDTOToItem(orderDTO.getItem());
        order.setItem(item);
        User user = convertDTOToUser(orderDTO.getUser());
        order.setUser(user);
        order.setDate(orderDTO.getDate());
        return order;
    }

    private User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        if (userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setCouldNotBeDeleted(userDTO.getCouldNotBeDeleted());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(userDTO.getRole());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        return user;
    }

    private Item convertDTOToItem(ItemDTO itemDTO) {
        Item item = new Item();
        if (itemDTO.getId() != null) {
            item.setId(itemDTO.getId());
        }
        item.setName(itemDTO.getName());
        item.setCode(itemDTO.getCode());
        item.setPrice(itemDTO.getPrice());
        item.setSummary(itemDTO.getSummary());
        return item;
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

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setCouldNotBeDeleted(user.getCouldNotBeDeleted());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }
}