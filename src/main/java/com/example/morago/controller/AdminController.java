package com.example.morago.controller;

import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.category.CategoryRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.requests.theme.ThemeRequest;
import com.example.morago.model.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.model.dto.requests.user.UserGetRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.dto.response.translator.TranslatorGetResponse;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.model.entity.File;
import com.example.morago.model.enums.FileType;
import com.example.morago.service.CategoryService;
import com.example.morago.service.ThemeService;
import com.example.morago.service.TranslatorService;
import com.example.morago.service.UserProfileService;
import com.example.morago.service.UserService;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "AdminController", description = "Access: [ADMIN]")
public class AdminController {

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final TranslatorService translatorService;
    private final CategoryService categoryService;
    private final ThemeService themeService;
    private final FileService fileService;

    /** Translators */
    @GetMapping("/translators")
    @Operation(description = "Get a list of translators by request body parameters")
    public ResponseEntity<Page<TranslatorGetResponse>> getTranslators(
        @ModelAttribute TranslatorGetRequest request) {
        return ResponseEntity.ok(translatorService.searchTranslators(request));
    }

    @GetMapping("/translators/{id}")
    @Operation(description = "Get translator by ID")
    public ResponseEntity<TranslatorGetResponse> getTranslatorById(
        @PathVariable("id")
        @Parameter(description = "Translator ID", example = "2")
        Long id) {
        return ResponseEntity.ok(translatorService.mapToDto(translatorService.findById(id)));
    }

    /** Users */
    @GetMapping("/users")
    @Operation(description = "Get list of users with filters")
    public ResponseEntity<Page<UserGetResponse>> getUserProfiles(
        @ModelAttribute UserGetRequest request) {
        return ResponseEntity.ok(userProfileService.searchUsers(request));
    }

    @GetMapping("/users/{id}")
    @Operation(description = "Get user by id")
    public ResponseEntity<UserGetResponse> getUserProfile(
            @PathVariable("id")
            @Parameter(description = "User Id", example = "6")
            Long id) {
        return ResponseEntity.ok(userProfileService.mapToDto(userProfileService.findById(id)));
    }

    @DeleteMapping("/users/{id}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<Void> deleteUserProfile(
        @PathVariable("id") @Parameter(description = "User Id", example = "6") Long id) {
        userProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //TODO реализовать методы
    /** Calls */
    @GetMapping("/calls/history/{userId}")
    @Operation(description = "Get call history of selected user. Depends on Role")
    public ResponseEntity<Page<UserGetResponse>> getCallHistory(
        @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(Page.empty());
    }

    /** Deposit, Withdrawal */
    //TODO реализовать
    @GetMapping("/transactions/history/{userId}")
    @Operation(description = "Get transaction history of selected user. Depends on Role")
    public ResponseEntity<Page<UserGetResponse>> getTransactionsHistory(
        @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(Page.empty());
    }

    @PutMapping("/deposits/{id}")
    @Operation(description = "Approve last user deposit")
    public ResponseEntity<Page<UserGetResponse>> approveDeposit(
        @PathVariable("id") Long id) {
        return ResponseEntity.ok(Page.empty());
    }

    @PutMapping("/withdrawals/{id}")
    @Operation(description = "Approve last user withdrawal")
    public ResponseEntity<Page<UserGetResponse>> approveWithdrawal(
        @PathVariable("id") Long id) {
        return ResponseEntity.ok(Page.empty());
    }

    /** Categories */
    //TODO Саша посмотри
    @GetMapping("/categories")
    @Operation(description = "Get list of categories (admin)")
    public Page<Category> getCategories(@ModelAttribute CategoryPageRequest categoryPageRequest) {
        return categoryService.getAdminCategories(categoryPageRequest);
    }

    @GetMapping("/categories/{id}")
    @Operation(description = "Get category by Id (admin)")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryByIdOrThrow(id);
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Category deleteCategory(@PathVariable Long id) {
        return categoryService.softDeleteCategoryById(id);
    }

    /** Themes */
    //TODO Саша посмотри
    @GetMapping("/themes")
    @Operation(description = "Get themes [ADMIN]")
    public PageResponse<ThemeResponse> getThemes(@Valid @ModelAttribute ThemePageRequest themePageRequest) {
        return themeService.getAdminThemes(themePageRequest);
    }

    @PostMapping("/themes")
    @Operation(description = "Create theme [ADMIN]")
    @ResponseStatus(HttpStatus.CREATED)
    public ThemeResponse createTheme(@Valid @RequestBody ThemeRequest themeRequest) {
        return themeService.createTheme(themeRequest);
    }

    @PostMapping("/themes/{id}/icon")
    @Operation(description = "Update theme icon [ADMIN]")
    public ThemeResponse updateThemeIcon(@PathVariable Long id, @RequestParam("icon") MultipartFile iconFile) {
        return themeService.updateThemeIcon(id, iconFile);
    }

    //TODO Саша посмотри*
    @PutMapping("/themes/update-popular")
    @Operation(description = "Recalculate popular theme")
    public ResponseEntity<Page<UserGetResponse>> updatePopularThemes() {
        return ResponseEntity.ok(Page.empty());
    }

    @PutMapping("/themes/{id}")
    @Operation(description = "Update theme [ADMIN]")
    public ThemeResponse updateTheme(@PathVariable Long id, @Valid @RequestBody ThemeRequest request) {
        return themeService.updateTheme(id, request);
    }

    @DeleteMapping("/themes/{id}")
    @Operation(description = "Delete theme [ADMIN]")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
    }

    /** Files */
    @GetMapping("/files/{id}")
    @Operation(description = "Get file by Id [ADMIN]")
    public File getFile(@PathVariable Long id) {
        return fileService.getFileById(id);
    }

    @PostMapping("/files/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public File uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") FileType type) {
        return fileService.uploadFile(file, type, null);
    }

    @DeleteMapping("/files/{id}")
    @Operation(description = "Delete file by Id [ADMIN]")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }
}
