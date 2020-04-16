package com.ecommerce.users.service.attribute_service;

import com.ecommerce.users.model.Attribute;
import com.ecommerce.users.repository.AttributeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;

    public AttributeServiceImpl(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Override
    public List<Attribute> getAll() {
        return attributeRepository.attributes_fetch();
    }
}
