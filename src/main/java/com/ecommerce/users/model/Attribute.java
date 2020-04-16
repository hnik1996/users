package com.ecommerce.users.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "attributes")
@Data
public class Attribute {
    @Id
    private String id;
    private String attr;
    private String value;
    @Column(name = "data_type")
    private String dataType;
}
