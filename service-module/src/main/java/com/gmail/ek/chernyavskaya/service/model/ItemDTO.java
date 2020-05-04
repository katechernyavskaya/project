package com.gmail.ek.chernyavskaya.service.model;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class ItemDTO {

    private Long id;
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters long")
    private String name;
    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters long")
    private String code;
    private BigDecimal price;
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters long")
    private String summary;

    public ItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return Objects.equals(id, itemDTO.id) &&
                Objects.equals(code, itemDTO.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", summary='" + summary + '\'' +
                '}';
    }
}
