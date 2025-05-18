package com.example.morago.model.dto;

public enum CallStatusEnum {
    COMPLETED,      // Звонок завершен
    RINGING,        // Звонок дозванивается
    HOLD,           // Удержание
    BUSY,           // Линия занята
    NO_ANSWER,      // Нет ответа
    REJECTED,       // Отклонён
    TIMEOUT,        // Превышено время ожидания
    MISSED
}
