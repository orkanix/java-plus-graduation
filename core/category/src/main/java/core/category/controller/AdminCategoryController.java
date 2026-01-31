package core.category.controller;

import core.common.category.dto.CategoryDto;
import core.common.category.dto.CategoryRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import core.category.service.CategoryService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody CategoryRequestDto newDto) {
        log.debug("Метод addCategory(); categoryParamDto={}", newDto);
        return categoryService.add(newDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.debug("Метод deleteCategory(); catId={}", catId);
        categoryService.delete(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId,
                                                      @Valid @RequestBody CategoryRequestDto updDto) {
        log.debug("Метод updateCategory(); categoryParamDto={}", updDto);
        return categoryService.update(catId, updDto);
    }
}