package com.ecommerce.users.service.user_detail_service;

import com.ecommerce.users.model.UserDetail;

import java.util.List;

public interface UserDetailService {
    void save(long userId, long creatorId, String attributeId, String attributeValue);

    List<UserDetail> getUserDetails(Long id);
}
