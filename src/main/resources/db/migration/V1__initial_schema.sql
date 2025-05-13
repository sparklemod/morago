CREATE TABLE calls
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at           DATETIME,
    duration             INT,
    status               BIT(1),
    sum                  DECIMAL(10, 2),
    commission           DECIMAL(10, 2),
    translator_has_rated BIT(1),
    user_has_rated       BIT(1),
    updated_at           DATETIME,
    caller_id            BIGINT,
    recipient_id         BIGINT,
    theme_id             BIGINT,
    channel_name         VARCHAR(50),
    call_status          INT,
    is_end_call          BIT(1)
);

CREATE TABLE debtors
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_holder VARCHAR(200),
    name_of_bank   VARCHAR(200),
    is_paid        BIT(1),
    user_id        BIGINT,
    created_at     DATETIME,
    updated_at     DATETIME
);

CREATE TABLE deposits
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_holder VARCHAR(200),
    name_of_bank   VARCHAR(200),
    coin           DECIMAL(10, 2),
    won            DECIMAL(10, 2),
    status         VARCHAR(50),
    user_id        BIGINT,
    created_at     DATETIME,
    updated_at     DATETIME
);

CREATE TABLE withdrawals
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(200),
    account_holder VARCHAR(200),
    name_of_bank   VARCHAR(200),
    sum            DECIMAL(10, 2),
    status         VARCHAR(50),
    user_id        BIGINT,
    created_at     DATETIME,
    updated_at     DATETIME
);

CREATE TABLE notifications
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    title   VARCHAR(200),
    text    VARCHAR(1000),
    date    DATE,
    time    TIME,
    user_id BIGINT
);

CREATE TABLE password_resets
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at DATETIME,
    phone      VARCHAR(100),
    token      VARCHAR(255),
    reset_code INT
);