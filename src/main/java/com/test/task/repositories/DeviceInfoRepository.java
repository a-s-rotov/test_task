package com.test.task.repositories;

import com.test.task.model.db.DeviceInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInfoRepository extends CrudRepository<DeviceInfoEntity, Long> {

    @Query("SELECT e FROM DeviceInfoEntity e WHERE (:newspaperName is null or e.newspaperName = :newspaperName) "
            + "and (:width is null or e.width = :width) "
            + "and (:height is null or e.height = :height) "
            + "and (:dpi is null or e.dpi = :dpi) ")
    Page<DeviceInfoEntity> findByParams(
            @Param("newspaperName") String newspaperName,
            @Param("width") Integer width,
            @Param("height") Integer height,
            @Param("dpi") Integer dpi,
            Pageable pageable);
}
