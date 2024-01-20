package com.coutarel.dbpostgres.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.coutarel.dbpostgres.domain.dto.AuthorDto;
import com.coutarel.dbpostgres.domain.entities.AuthorEntity;
import com.coutarel.dbpostgres.mappers.Mapper;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto>{

  private ModelMapper modelMapper;

  public AuthorMapperImpl (ModelMapper modelMapper){
    this.modelMapper = modelMapper;
  }

  @Override
  public AuthorDto mapTo(AuthorEntity authorEntity){
    return modelMapper.map(authorEntity, AuthorDto.class);
  }

  @Override
  public AuthorEntity mapFrom(AuthorDto authorDto) {
    return modelMapper.map(authorDto, AuthorEntity.class);
  }
}
