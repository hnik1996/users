package com.ecommerce.users.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_attributes")
public class UserDetail {
    @Id
    private long id;
    private char status;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "attribute_id")
    private String attributeId;
    private String value;
    @Column(name = "value_blob")
    private String valueBlob;
    @Column(name = "created_ts")
    private Date createdTs;
    @Column(name = "updated_ts")
    private Date updatedTs;
    @Column(name = "creator_user_id")
    private long creatorUserId;
    @Column(name = "updater_user_id")
    private long updaterUserId;
}
