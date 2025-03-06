package com.accenture.service.mapper;

import com.accenture.repository.entity.Moto;
import com.accenture.service.dto.MotoRequestDTO;
import com.accenture.service.dto.MotoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MotoMapper {

    Moto toMoto(MotoRequestDTO motoRequestDTO);
    MotoResponseDTO toMotoResponseDTO(Moto moto);

}
