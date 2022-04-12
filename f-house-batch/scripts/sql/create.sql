# 동 코드 테이블
CREATE TABLE lawd
(
    lawd_id    bigint auto_increment primary key,
    lawd_cd    char(10)     not null,
    lawd_dong  varchar(100) not null,
    exist      tinyint(1)   not null,
    created_at datetime     not null,
    updated_at datetime     not null,
    CONSTRAINT uk_lawdcd unique (lawd_cd)
);

# 아파트 테이블
CREATE TABLE apt
(
    apt_id      bigint auto_increment primary key,
    apt_name    char(10)     not null,
    jibun       varchar(100) not null,
    dong        tinyint(1)   not null,
    gu_lawd_cd  char(5)      not null,
    built_year  int          not null,
    created_at datetime      not null,
    updated_at datetime      not null
);

# 아파트 거래 테이블
CREATE TABLE apt_deal
(
    apt_deal_id         int auto_increment primary key,
    apt_id              bigint     not null,
    exclusive_area      double not null,
    deal_date           date not null,
    deal_amount         bigint not null,
    floor               int not null,
    deal_canceled       tinyint(1) default 0 not null,
    deal_canceled_date  date null,
    created_at          datetime      not null,
    updated_at          datetime      not null
);

# 아파트 거래 알림 테이블
CREATE TABLE apt_notification
(
    apt_notification_id     int auto_increment primary key,
    email                   bigint     not null,
    gu_lawd_cd              double not null,
    enabled                 tinyint(1) null,
    created_at              datetime      not null,
    updated_at              datetime      not null,
    constraint uk_email_gulawdcd unique (email, gu_lawd_cd)
);