package core.category.controller;

import core.common.category.dto.CategoryDto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import core.category.service.CategoryService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Метод getCategories(); from={}, size={}", from, size);
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        return categoryService.getById(catId);
    }

    @PostMapping("/getIds")
    public List<CategoryDto> getCategoriesByIds(@RequestBody List<Long> categoryIds) {
        return categoryService.getCategoriesByIds(categoryIds);
    }
}