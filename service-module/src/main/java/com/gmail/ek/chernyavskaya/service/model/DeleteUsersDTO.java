package com.gmail.ek.chernyavskaya.service.model;

import java.util.List;

public class DeleteUsersDTO {
    private List<Long> userIds;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) { //!!!!!!!!!!!!!используется на гуйне, без него не сработает удаление
        this.userIds = userIds;
    }

    public DeleteUsersDTO() {
    }
}
