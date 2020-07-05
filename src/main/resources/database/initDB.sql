create table if not exists faq
(
    id bigserial not null
        constraint "FAQ_pkey"
            primary key
        constraint cat_id_fk
            references categories,
    question_rus     varchar   not null,
    question_kaz     varchar   not null,
    question_eng     varchar   not null,
    answer_rus       varchar   not null,
    answer_kaz       varchar   not null,
    answer_eng       varchar   not null,
    question_counter bigint,
    category_question_id      bigint    not null
);

create table if not exists categories
(
    id bigserial not null
        constraint categories_pkey
            primary key,
    category_name      varchar   not null,
    parent_category_id bigint
);