package core.category.service;

import core.common.category.dto.CategoryDto;
import core.common.category.dto.CategoryRequestDto;

import java.util.List;

public interface CategoryService {

    // Admin API:
    CategoryDto add(CategoryRequestDto newDto);

    CategoryDto update(Long catId, CategoryRequestDto updDto);

    void delete(Long categoryId);

    // Public API:
    CategoryDto findById(Long categoryId);

    List<CategoryDto> findAllById(int from, int size);

    List<CategoryDto> findCategoriesByIds(List<Long> categoryIds);
}