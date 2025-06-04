package com.example.morago.repository;

import com.example.morago.enums.CallStatusEnum;
import com.example.morago.model.entity.Call;
import com.example.morago.model.entity.Theme;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
//@Rollback(false) //запись в БД сохранится
class CallRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private CallRepository callRepository;

    @Test
    void testCreateAndSaveCall() {
        Theme theme = new Theme();

        theme.setName("Technical support");
        theme.setTitle("title");
        theme.setNightPrice(123);
        theme.setPrice(123);

        themeRepository.save(theme);

        Call call = new Call();
        call.setCallStatus(CallStatusEnum.COMPLETED);
        call.setTheme(theme);
        callRepository.save(call);

        Optional<Call> saved = callRepository.findById(call.getId());
        assertTrue(saved.isPresent());
        assertEquals("Technical support", saved.get().getTheme().getName());
    }
}
