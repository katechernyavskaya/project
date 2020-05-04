package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.model.OrderStatusEnum;
import com.gmail.ek.chernyavskaya.service.ItemService;
import com.gmail.ek.chernyavskaya.service.OrderService;
import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.model.*;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
@SessionAttributes(types = OrdersWithPaginationDTO.class)
public class InvoiceController {

    private final UserService userService;
    private final OrderService orderService;
    private final PaginationUtil paginationUtil;
    private final ItemService itemService;

    public InvoiceController(UserService userService, OrderService orderService, PaginationUtil paginationUtil, ItemService itemService) {
        this.userService = userService;
        this.orderService = orderService;
        this.paginationUtil = paginationUtil;
        this.itemService = itemService;
    }

    @GetMapping
    public String getAllOrders(OrdersWithPaginationDTO ordersWithPagination, Model model) {
        List<InvoiceDTO> allOrders = orderService.findAll();
        int countOfOrders = allOrders.size();
        PaginationDTO pagination = paginationUtil.addPagination(ordersWithPagination.getPagination(), countOfOrders);
        ordersWithPagination.setPagination(pagination);
        List<InvoiceDTO> orders = orderService.getAllOrdersWithPagination(pagination);
        ordersWithPagination.setUserOrders(orders);
        model.addAttribute("ordersWithPagination", ordersWithPagination);
        return "orders";
    }

    @PostMapping
    public String addOrder(@ModelAttribute(name = "order") InvoiceDTO order, Model model) throws SQLException {
        int quantity = order.getQuantity();
        UserDTO user = getCurrentUserDTO();
        order.setUser(user);
        Long itemId = order.getItemId();
        ItemDTO item = itemService.findById(itemId);
        order.setItem(item);
        BigDecimal price = item.getPrice();
        BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));
        order.setTotalPrice(totalPrice);
        model.addAttribute("order", order);
        orderService.add(order);
        return "redirect:/orders/successOrderCreate";
    }

    @GetMapping("/add/{itemId}/quantity/{quantityId}")
    public String orderItemById(Model model,
                                @PathVariable(name = "itemId") Long itemId,
                                @PathVariable(name = "quantityId") Integer quantity) {
        if (quantity == null) {
            quantity = 1;
        }
        BigDecimal totalPrice = BigDecimal.ZERO;
        InvoiceDTO order = new InvoiceDTO();
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatusEnum.NEW);
        ItemDTO item = itemService.findById(itemId);
        order.setItem(item);
        order.setItemId(itemId);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        order.setDate(date);
        model.addAttribute("order", order);
        return "basket";
    }

    @GetMapping("/successOrderCreate")
    public String showSuccessOrderCreate() {
        return "success_order_create";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable(name = "id") Long id) {
        InvoiceDTO order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping("/{id}/status")
    public String changeOrderStatus(Model model,
                                    @PathVariable(name = "id") Long id) {
        InvoiceDTO order = orderService.findById(id);
        model.addAttribute("order", order);
        List<OrderStatusEnum> statuses = getAllStatuses();
        model.addAttribute("statuses", statuses);
        return "change_status_order";
    }

    @PostMapping("/{id}/status")
    public String changeOrderStatus(@PathVariable(name = "id") Long id,
                                    @ModelAttribute(name = "order") InvoiceDTO order,
                                    @ModelAttribute(name = "status") OrderStatusEnum status,
                                    Model model, BindingResult errors) {
        if (errors.hasErrors()) {
            return "change_status_order";
        }
        order = orderService.findById(id);
        List<OrderStatusEnum> statuses = getAllStatuses();
        model.addAttribute("statuses", statuses);
        order.setStatus(status);
        orderService.merge(order);
        return "redirect:/orders";
    }

    @PostMapping("/{id}")
    public String updateOrder(@PathVariable(name = "id") Long id,
                              @ModelAttribute(name = "order") InvoiceDTO order,
                              Model model, BindingResult errors) {
        if (errors.hasErrors()) {
            return "order";
        }
        order = orderService.findById(id);
        model.addAttribute("order", order);
        orderService.merge(order);
        return "redirect:/orders";
    }

    @GetMapping("/myOrders")
    public String getUserOrders(OrdersWithPaginationDTO ordersWithPagination,
                                Model model) throws SQLException {
        UserDTO user = getCurrentUserDTO();
        List<InvoiceDTO> allUserOrders = orderService.getUserOrders(user);
        int countOfOrders = allUserOrders.size();
        PaginationDTO pagination = paginationUtil.addPagination(ordersWithPagination.getPagination(), countOfOrders);
        ordersWithPagination.setPagination(pagination);
        List<InvoiceDTO> userOrders = orderService.getAllUserOrdersWithPagination(pagination, user);
        ordersWithPagination.setUserOrders(userOrders);
        model.addAttribute("ordersWithPagination", ordersWithPagination);
        return "my_orders";
    }

    private List<OrderStatusEnum> getAllStatuses() {
        return Arrays.stream(OrderStatusEnum.values()).collect(Collectors.toList());
    }

    private UserDTO getCurrentUserDTO() throws SQLException {
        UserDTO userDTO = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return userDTO;
    }
}