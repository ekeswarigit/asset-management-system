package com.asset.ams.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.ams.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long>{

    boolean existsByLocationName(String locationName);
}
