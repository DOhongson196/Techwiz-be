package com.nosz.techwiz.respository;

import com.nosz.techwiz.dto.StadiumDto;
import com.nosz.techwiz.dto.filter.StadiumFilter;
import com.nosz.techwiz.entity.Stadium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    @Query("select s from Stadium s where (:#{#filter.nameLike} is null or s.name like %:#{#filter.nameLike}%) AND (:#{#filter.addressLike} is null or s.address like %:#{#filter.addressLike}%)")
    Page<Stadium> getAllWithFilter(@Param("filter") StadiumFilter filter, Pageable pageable);
}
