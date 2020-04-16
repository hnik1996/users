package com.ecommerce.users.service.user_detail_service;

import com.ecommerce.users.model.UserDetail;
import com.ecommerce.users.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    private final UserDetailRepository userDetailRepository;

    @Autowired
    public UserDetailServiceImpl(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public void save(long userId, long creatorId, String attributeId, String attributeValue) {
        try {
            userDetailRepository.user_attribute_create(userId, creatorId, attributeId, attributeValue);
        }catch (Exception ignored){}
    }

    @Override
    public List<UserDetail> getUserDetails(Long id) {
        return userDetailRepository.user_attributes_fetch(id);
    }
}
