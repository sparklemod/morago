package com.example.morago.controller;

import com.example.morago.model.dto.requests.PageRequest;
import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.category.CategoryRequest;
import com.example.morago.model.dto.requests.transactions.deposit.DepositApproveRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.requests.theme.ThemeRequest;
import com.example.morago.model.dto.requests.transactions.TransactionGetHistoryResponse;
import com.example.morago.model.dto.requests.transactions.withdrawal.WithdrawalApproveRequest;
import com.example.morago.model.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.model.dto.requests.user.UserGetRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.dto.response.translator.TranslatorGetResponse;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.FileType;
import com.example.morago.service.*;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "AdminController", description = "Access: [ADMIN]")
public class AdminController {

    private final UserProfileService userProfileService;
    private final TranslatorService translatorService;
    private final CategoryService categoryService;
    private final ThemeService themeService;
    private final FileService fileService;
    private final DepositService depositService;
    private final WithdrawalService withdrawalService;

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
        return ResponseEntity.ok(TranslatorGetResponse.mapToDto(translatorService.findById(id)));
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
        return ResponseEntity.ok(UserGetResponse.mapToDto(userProfileService.findById(id)));
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

    /** Deposit */
    @GetMapping("/deposits/history/{userId}")
    @Operation(description = "Get deposit history of selected user")
    public ResponseEntity<Page<TransactionGetHistoryResponse>> getDepositHistory(
        @PathVariable("userId") Long userId,
        @ParameterObject PageRequest pageRequest
    ) {
        Page<TransactionGetHistoryResponse> page = depositService.getHistory(userId,
            pageRequest.toPageable());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/deposits")
    @Operation(description = "Get user's last deposit")
    public ResponseEntity<Deposit> getDeposit(
        @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(depositService.getLastDepositByUser(userId));
    }

    @PutMapping("/deposits/{id}")
    @Operation(description = "Approve deposit")
    public ResponseEntity<Void> approveDeposit(
        @PathVariable("id") Long id,
        @Valid @RequestBody DepositApproveRequest request
    ) {
        depositService.approveDeposit(id, request);
        return ResponseEntity.ok().build();
    }

    /** Withdrawal */
    @GetMapping("/withdrawals")
    @Operation(description = "Get translator's last withdrawal")
    public ResponseEntity<Withdrawal> getWithdrawal(
        @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(withdrawalService.getLastWithdrawalByUser(userId));
    }

    @GetMapping("/withdrawals/history/{userId}")
    @Operation(description = "Get withdrawal history of selected user")
    public ResponseEntity<Page<TransactionGetHistoryResponse>> getWithdrawalHistory(
        @PathVariable("userId") Long userId,
        @ParameterObject PageRequest pageRequest
    ) {
        Page<TransactionGetHistoryResponse> page = withdrawalService.getHistory(userId, pageRequest.toPageable());
        return ResponseEntity.ok(page);
    }

    @PutMapping("/withdrawals/{id}")
    @Operation(description = "Approve withdrawal")
    public ResponseEntity<Void> approveWithdrawal(
        @PathVariable("id") Long id,
        @Valid @RequestBody WithdrawalApproveRequest request) {
        withdrawalService.approveWithdrawal(id, request);
        return ResponseEntity.ok().build();
    }

    /** Categories */
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
    @Operation(description = "Create category")
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping("/categories/{id}")
    @Operation(description = "Update category fields")
    public Category updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/categories/{id}")
    @Operation(description = "Switch status isActive to false")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Category deleteCategory(@PathVariable Long id) {
        return categoryService.softDeleteCategoryById(id);
    }

    /** Themes */
    @GetMapping("/themes")
    @Operation(description = "Get themes [ADMIN]")
    public PageResponse<ThemeResponse> getThemes(@Valid @ModelAttribute ThemePageRequest themePageRequest) {
        return themeService.getAdminThemes(themePageRequest);
    }

    @GetMapping("/themes/{id}")
    @Operation(description = "Get theme by Id")
    public ThemeResponse getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }


    @PostMapping("/themes")
    @Operation(description = "Create theme [ADMIN]")
    @ResponseStatus(HttpStatus.CREATED)
    public ThemeResponse createTheme(@Valid @RequestBody ThemeRequest themeRequest) {
        return themeService.createTheme(themeRequest);
    }

    @Operation(description = "Upload new theme icon file", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)))
    @PostMapping(value = "/themes/{id}/icon", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ThemeResponse updateThemeIcon(@PathVariable Long id, @RequestParam("icon") MultipartFile iconFile) {
        return themeService.updateThemeIcon(id, iconFile);
    }

    @PutMapping("/themes/update-popular")
    @Operation(description = "Recalculate popular theme")
    public PageResponse<ThemeResponse> updatePopularThemes(@Valid @ModelAttribute ThemePageRequest themePageRequest,
                                                           @AuthenticationPrincipal User user) {
        Long userId = user != null ? user.getId() : null;
        return themeService.getPublicThemes(themePageRequest, userId, themePageRequest.getCategoryId());

    }

    @PutMapping("/themes/update/{id}")
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

    @Operation(description = "Upload file", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)))
    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
