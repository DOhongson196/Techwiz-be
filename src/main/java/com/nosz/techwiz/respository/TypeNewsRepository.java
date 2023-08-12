package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.TypeNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeNewsRepository  extends JpaRepository<TypeNews, Long>  {
    boolean existsByName(String name);

    TypeNews findByName(String name);
}
