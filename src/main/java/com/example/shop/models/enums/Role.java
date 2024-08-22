package com.example.shop.models.enums;

import com.example.shop.serializators.RoleDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    //@JsonDeserialize(using = RoleDeserializer.class)
    ROLE_USER, ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
