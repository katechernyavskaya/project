package com.gmail.ek.chernyavskaya.service.model;

import com.gmail.ek.chernyavskaya.repository.model.OrderStatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class InvoiceDTO {

    private Long id;
    private OrderStatusEnum status;
    @Min(value = 1, message = "Minimal quantity is 1")
    private int quantity;
    @DecimalMin(value = "1.00", message = "Minimal price is 1.00")
    private BigDecimal totalPrice;
    private ItemDTO item;
    private UserDTO user;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Long itemId;

    public InvoiceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDTO orderDTO = (InvoiceDTO) o;
        return Objects.equals(id, orderDTO.id) &&
                Objects.equals(totalPrice, orderDTO.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", status=" + status +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", item=" + item +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
