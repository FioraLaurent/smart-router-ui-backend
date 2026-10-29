-- 指标明细表
CREATE TABLE IF NOT EXISTS metrics_detail (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    request_id      VARCHAR(64)  NOT NULL,
    session_id      VARCHAR(64),
    agent_id        VARCHAR(32),
    tier            INTEGER      NOT NULL,
    target_model    VARCHAR(128),
    provider        VARCHAR(64),
    prompt_tokens   INTEGER      DEFAULT 0,
    completion_tokens INTEGER    DEFAULT 0,
    total_tokens   INTEGER      DEFAULT 0,
    cached_tokens  INTEGER      DEFAULT 0,
    latency_ms      INTEGER      DEFAULT 0,
    queue_wait_ms   INTEGER      DEFAULT 0,
    status          VARCHAR(16) DEFAULT 'success',
    fail_reason     VARCHAR(64),
    retry_count     INTEGER      DEFAULT 0,
    switched_from   VARCHAR(128),
    switched_to     VARCHAR(128),
    ts              BIGINT       NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_metrics_ts       ON metrics_detail(ts);
CREATE INDEX IF NOT EXISTS idx_metrics_session ON metrics_detail(session_id);
CREATE INDEX IF NOT EXISTS idx_metrics_tier    ON metrics_detail(tier);
CREATE INDEX IF NOT EXISTS idx_metrics_model   ON metrics_detail(target_model);

-- 对话内容表
CREATE TABLE IF NOT EXISTS conversation_content (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    request_id          VARCHAR(64)  NOT NULL,
    session_id          VARCHAR(64),
    agent_id            VARCHAR(32),
    tier                INTEGER,
    user_message        TEXT,
    user_tokens         INTEGER      DEFAULT 0,
    assistant_message   TEXT,
    assistant_tokens    INTEGER      DEFAULT 0,
    turn_index          INTEGER      DEFAULT 0,
    context_snapshot    TEXT,
    context_tokens      INTEGER      DEFAULT 0,
    ts                  BIGINT
);

CREATE INDEX IF NOT EXISTS idx_conv_request ON conversation_content(request_id);
CREATE INDEX IF NOT EXISTS idx_conv_session ON conversation_content(session_id);

-- 关键词规则表
CREATE TABLE IF NOT EXISTS keyword_rule (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    keyword     TEXT        NOT NULL,
    tier        INTEGER     NOT NULL,
    weight      INTEGER     DEFAULT 5,
    enabled     INTEGER     DEFAULT 1,
    description VARCHAR(256),
    gray_ratio  INTEGER     DEFAULT 0,
    version     INTEGER     DEFAULT 1,
    created_at  BIGINT,
    updated_at  BIGINT
);

-- 模型配置表
CREATE TABLE IF NOT EXISTS model_config (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    model_id            VARCHAR(128) NOT NULL UNIQUE,
    display_name        VARCHAR(128),
    tier                INTEGER      NOT NULL,
    provider            VARCHAR(64),
    price_input         DECIMAL(10,4) DEFAULT 0,
    price_output        DECIMAL(10,4) DEFAULT 0,
    context_window      INTEGER      DEFAULT 4096,
    max_output          INTEGER      DEFAULT 2048,
    quality_score       INTEGER      DEFAULT 70,
    reliability_score   INTEGER      DEFAULT 100,
    circuit_breaker_count INTEGER   DEFAULT 0,
    enabled             INTEGER      DEFAULT 1,
    created_at          BIGINT
);

-- 告警规则表
CREATE TABLE IF NOT EXISTS alert_rule (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    name            VARCHAR(64)  NOT NULL,
    metric          VARCHAR(32)  NOT NULL,
    threshold       DECIMAL(10,2),
    duration_minutes INTEGER     DEFAULT 5,
    notify_type     VARCHAR(32) DEFAULT 'page',
    notify_target   VARCHAR(256),
    enabled         INTEGER      DEFAULT 1
);

-- 告警历史表
CREATE TABLE IF NOT EXISTS alert_history (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    rule_id         BIGINT,
    triggered_at     BIGINT,
    resolved_at     BIGINT,
    metric_value    DECIMAL(10,2),
    message         TEXT
);

CREATE INDEX IF NOT EXISTS idx_alert_history_rule ON alert_history(rule_id);
CREATE INDEX IF NOT EXISTS idx_alert_history_ts   ON alert_history(triggered_at);

-- 规则变更历史表
CREATE TABLE IF NOT EXISTS rule_change_history (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    rule_id     BIGINT        NOT NULL,
    action      VARCHAR(16)  NOT NULL,
    old_value   TEXT,
    new_value   TEXT,
    changed_at  BIGINT
);

-- 插入种子数据
INSERT OR IGNORE INTO keyword_rule (id, keyword, tier, weight, enabled, description, gray_ratio, version, created_at, updated_at) VALUES
(1, '你好|hi|hello|嗨', 1, 5, 1, '问候语', 0, 1, 1725000000000, 1725000000000),
(2, '今天天气|现在几点|时间', 1, 5, 1, '时间天气查询', 0, 1, 1725000000000, 1725000000000),
(3, '帮我写.*邮件|写.*通知|生成.*报告', 2, 7, 1, '文档生成', 0, 1, 1725000000000, 1725000000000),
(4, '分析|优化|重构|架构|设计模式', 3, 9, 1, '复杂分析任务', 0, 1, 1725000000000, 1725000000000),
(5, '翻译|convert|translate', 1, 4, 1, '翻译任务', 0, 1, 1725000000000, 1725000000000),
(6, '代码|code|编程|function|class', 2, 6, 1, '代码相关', 0, 1, 1725000000000, 1725000000000);

INSERT OR IGNORE INTO model_config (id, model_id, display_name, tier, provider, price_input, price_output, context_window, max_output, quality_score, reliability_score, enabled, created_at) VALUES
(1, 'gpt-4o-mini', 'GPT-4o Mini', 1, 'openai', 0.15, 0.60, 128000, 16384, 75, 99, 1, 1725000000000),
(2, 'gpt-4o', 'GPT-4o', 2, 'openai', 2.50, 10.00, 128000, 16384, 92, 98, 1, 1725000000000),
(3, 'gpt-4-turbo', 'GPT-4 Turbo', 3, 'openai', 10.00, 30.00, 128000, 4096, 96, 97, 1, 1725000000000),
(4, 'claude-3-haiku', 'Claude 3 Haiku', 1, 'anthropic', 0.25, 1.25, 200000, 4096, 78, 99, 1, 1725000000000),
(5, 'claude-3-sonnet', 'Claude 3 Sonnet', 2, 'anthropic', 3.00, 15.00, 200000, 8192, 90, 98, 1, 1725000000000),
(6, 'claude-3-opus', 'Claude 3 Opus', 3, 'anthropic', 15.00, 75.00, 200000, 4096, 97, 96, 1, 1725000000000),
(7, 'qwen-turbo', '通义千问 Turbo', 1, 'aliyun', 0.20, 0.60, 128000, 8192, 72, 99, 1, 1725000000000),
(8, 'qwen-plus', '通义千问 Plus', 2, 'aliyun', 1.00, 4.00, 128000, 8192, 85, 98, 1, 1725000000000);

INSERT OR IGNORE INTO alert_rule (id, name, metric, threshold, duration_minutes, notify_type, notify_target, enabled) VALUES
(1, '失败率告警', 'fail_rate', 5.00, 5, 'page', '', 1),
(2, 'P95延迟告警', 'latency_p95', 5000.00, 5, 'page', '', 1),
(3, '预算超限告警', 'budget', 80.00, 1, 'email', 'admin@example.com', 1),
(4, '熔断告警', 'circuit', 1.00, 1, 'page', '', 1);
