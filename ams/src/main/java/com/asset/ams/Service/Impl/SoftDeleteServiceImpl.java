package com.asset.ams.Service.Impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.asset.ams.model.BaseEntity;

@Service
public class SoftDeleteServiceImpl {

     public <T extends BaseEntity> void softDelete(T entity, String username) {
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(username);
    }

    public <T extends BaseEntity> void restore(T entity) {
        entity.setDeleted(false);
        entity.setDeletedAt(null);
        entity.setDeletedBy(null);
    }
}
