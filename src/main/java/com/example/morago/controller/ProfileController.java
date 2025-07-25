package com.example.morago.controller;

import com.example.morago.model.dto.requests.PageRequest;
import com.example.morago.model.dto.requests.call.CallHistoryRequest;
import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.requests.user.UpdatePasswordRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.model.entity.File;
import com.example.morago.service.CallService;
import com.example.morago.service.CategoryService;
import com.example.morago.service.UserService;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("profile")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ProfileController", description = "Access: [TRANSLATOR, USER]")
public class ProfileController {
    private final FileService fileService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CallService callService;

    //TODO реализовать
    @GetMapping("/balance")
    @Operation(description = "Get current user balance")
    public void getBalance() {
    }

    //TODO реализовать Vlana
    @GetMapping("/calls/history")
    @Operation(description = "Get current user call history")
    public void getCallHistory(Authentication authentication, CallHistoryRequest req) {
    }

    //TODO реализовать
    @GetMapping("/notifications")
    @Operation(description = "Get current user notifications")
    public void getNotifications(Authentication authentication, PageRequest req) {
    }

    //TODO реализовать
    @PostMapping("/notifications/clear")
    @Operation(description = "Clear all current user notifications")
    public void clearNotifications(Authentication authentication, PageRequest req) {
    }

    @PostMapping("/password/update")
    @Operation(description = "Update password")
    public void updatePassword(Authentication authentication, @RequestBody UpdatePasswordRequest request) {
        Long userId = userService.extractUserId(authentication);
        userService.updatePassword(userId, request);
    }

    @Operation(description = "Upload avatar image", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)))
    @PostMapping(value = "/avatar/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public File uploadAvatar(
            @Parameter(description = "Avatar file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        Long userId = userService.extractUserId(authentication);
        return fileService.replaceUserAvatar(userId, file);
    }

    @DeleteMapping("/avatar/delete")
    @Operation(description = "Delete avatar image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatar(Authentication authentication) {
        Long userId = userService.extractUserId(authentication);
        fileService.deleteFile(userId);
    }

    /** Themes  and Categories*/

    @GetMapping("/categories")
    @Operation(description = "Get public list of categories")
    public Page<Category> getPublicCategories(@ModelAttribute CategoryPageRequest categoryPageRequest) {
        return categoryService.getPublicCategories(categoryPageRequest);
    }
    //TODO доделать-переделать связи с Call и Translator
    @GetMapping("/category{id}/themes")
    @Operation(description = "Get public themes by category")
    public PageResponse<ThemeResponse> getPublicThemesByCategory(
            @PathVariable Long id,
            @ModelAttribute ThemePageRequest themePageRequest,
            Authentication auth) {

        Long userId = (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Jwt)
                ? userService.extractUserId(auth)
                : null;
        return categoryService.getThemesByCategoryId(id, themePageRequest, userId);
    }
    //TODO реализовать Vlana
    @GetMapping("/theme{id}")
    @Operation(description = "Get current user favorite themes")
    public void getThemes() {
    }
}
