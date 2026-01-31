package core.category.mapper;

import core.common.category.dto.CategoryDto;
import core.common.category.dto.CategoryRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import core.category.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDto dto);
}