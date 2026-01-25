package ru.practicum.ewm.category.service;

import core.common.category.dto.CategoryDto;
import core.common.category.dto.CategoryRequestDto;

import java.util.List;

public interface CategoryService {

    // Admin API:
    CategoryDto add(CategoryRequestDto newDto);

    CategoryDto update(Long catId, CategoryRequestDto updDto);

    void delete(Long categoryId);

    // Public API:
    CategoryDto getById(Long categoryId);

    List<CategoryDto> getAll(int from, int size);

    List<CategoryDto> getCategoriesByIds(List<Long> categoryIds);
}