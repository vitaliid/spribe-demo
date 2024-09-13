package com.spribe.demo.mapper;

import com.spribe.demo.dto.RatesTakeResponse;
import com.spribe.demo.entity.RatesTake;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatesTakeMapper {

     RatesTakeResponse fromEntity(RatesTake ratesTake);
}
