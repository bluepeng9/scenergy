create table scenergy.chat_room
(
    status     int          null,
    created_at datetime(6)  null,
    room_id    bigint auto_increment
        primary key,
    updated_at datetime(6)  null,
    room_name  varchar(255) null
);

create table scenergy.chat_message
(
    unread_count int          not null,
    chat_id      bigint auto_increment
        primary key,
    created_at   datetime(6)  null,
    room_id      bigint       null,
    sender_id    bigint       null,
    updated_at   datetime(6)  null,
    message_text varchar(255) null,
    constraint FKfvbc4wvhk51y0qtnjrbminxfu
        foreign key (room_id) references scenergy.chat_room (room_id)
);

create table scenergy.genre_tag
(
    id         bigint auto_increment
        primary key,
    genre_name varchar(255) null,
    constraint UK_s67cppofgg50u8u5po5fah5pv
        unique (genre_name)
);

create table scenergy.instrument_tag
(
    id              bigint auto_increment
        primary key,
    instrument_name varchar(255) null
);

create table scenergy.location_tag
(
    id            bigint auto_increment
        primary key,
    location_name varchar(255) null
);

create table scenergy.post
(
    id      bigint       not null
        primary key,
    content varchar(255) null,
    title   varchar(255) null,
    writer  varchar(255) null
);

create table scenergy.post_seq
(
    next_val bigint null
);

create table scenergy.users
(
    created_at datetime(6)             null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6)             null,
    email      varchar(255)            null,
    nickname   varchar(255)            null,
    password   varchar(255)            null,
    url        varchar(255)            null,
    username   varchar(255)            null,
    gender     enum ('FEMALE', 'MALE') null,
    role       enum ('user', 'admin')  null,
    constraint UK_2ty1xmrrgtn89xt7kyxx6ta7h
        unique (nickname),
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

create table scenergy.chat_user
(
    chat_user_id bigint auto_increment
        primary key,
    created_at   datetime(6) null,
    room_id      bigint      null,
    updated_at   datetime(6) null,
    user_id      bigint      null,
    constraint FKb1gw4q5ahnprgk3f47gj5o3nw
        foreign key (user_id) references scenergy.users (id),
    constraint FKel53xoqsapeo3xf7udd14hwt4
        foreign key (room_id) references scenergy.chat_room (room_id)
);

create table scenergy.chat_online_info
(
    online_status bit         null,
    chat_user_id  bigint      null,
    created_at    datetime(6) null,
    id            bigint auto_increment
        primary key,
    room_id       bigint      null,
    updated_at    datetime(6) null,
    constraint UK_nnm5btdtdifmknq3don5pak5x
        unique (chat_user_id),
    constraint FK2bibco09tdhdsndgerheu0005
        foreign key (room_id) references scenergy.chat_room (room_id),
    constraint FKhhmqmff8umx39tyto964rh2dg
        foreign key (chat_user_id) references scenergy.chat_user (chat_user_id)
);

create table scenergy.follow
(
    from_id bigint null,
    id      bigint auto_increment
        primary key,
    to_id   bigint null,
    constraint FK2rakgi62l6nr92ebugkknrvc8
        foreign key (from_id) references scenergy.users (id),
    constraint FKked4y51ngebkbltd0wbbtyfbm
        foreign key (to_id) references scenergy.users (id)
);

create table scenergy.job_post
(
    book_mark        bigint                      null,
    expiration_date  datetime(6)                 null,
    id               bigint                      not null
        primary key,
    people_recruited bigint                      null,
    total_applicant  bigint                      null,
    user_id_id       bigint                      null,
    is_active        enum ('active', 'inactive') null,
    constraint FKkq9w89hoqk1fsqgtm11wyo6qj
        foreign key (id) references scenergy.post (id),
    constraint FKpwu9xvgx8yshieov8k7kpfvk8
        foreign key (user_id_id) references scenergy.users (id)
);

create table scenergy.job_book_mark
(
    status      bit    not null,
    id          bigint auto_increment
        primary key,
    job_post_id bigint null,
    user_id     bigint null,
    constraint FK27bahvbf26j0fv9a4wf9jl94n
        foreign key (job_post_id) references scenergy.job_post (id)
            on delete cascade,
    constraint FKc391ffxv9stsac2j6hhkms9ek
        foreign key (user_id) references scenergy.users (id)
            on delete cascade
);

create table scenergy.job_post_apply
(
    status      bit    not null,
    id          bigint auto_increment
        primary key,
    job_post_id bigint null,
    user_id     bigint null,
    constraint FK54iamwlkg7tnkc08b4gdifskd
        foreign key (job_post_id) references scenergy.job_post (id)
            on delete cascade,
    constraint FK63vbik5hb2wimpxpu7hnqvfse
        foreign key (user_id) references scenergy.users (id)
            on delete cascade
);

create table scenergy.job_post_genre_tag
(
    genre_tag_id bigint null,
    id           bigint auto_increment
        primary key,
    job_post_id  bigint null,
    constraint FKpigfqs6w2yg127jxp56iqcn7w
        foreign key (job_post_id) references scenergy.job_post (id),
    constraint FKsrgmo7w7ddfk83vtl9icn7xxu
        foreign key (genre_tag_id) references scenergy.genre_tag (id)
            on delete cascade
);

create table scenergy.job_post_instrument_tag
(
    id                bigint auto_increment
        primary key,
    instrument_tag_id bigint null,
    job_post_id       bigint null,
    constraint FKaylqi61j3jh4tke5eljyhcxb4
        foreign key (instrument_tag_id) references scenergy.instrument_tag (id)
            on delete cascade,
    constraint FKhxo27w4svkw4qnqa9o06v4y7g
        foreign key (job_post_id) references scenergy.job_post (id)
);

create table scenergy.job_post_location_tag
(
    id              bigint auto_increment
        primary key,
    job_post_id     bigint null,
    location_tag_id bigint null,
    constraint FKel5e2mcg9twogm0jemvf2yl0j
        foreign key (job_post_id) references scenergy.job_post (id),
    constraint FKmmxsxrdty32sjiivt46uxc29r
        foreign key (location_tag_id) references scenergy.location_tag (id)
            on delete cascade
);

create table scenergy.unread_message
(
    chat_id    bigint      null,
    created_at datetime(6) null,
    id         bigint auto_increment
        primary key,
    room_id    bigint      null,
    updated_at datetime(6) null,
    user_id    bigint      null,
    constraint FK5iv8ed5syroh1r3aco93p1gwf
        foreign key (chat_id) references scenergy.chat_message (chat_id),
    constraint FK8s13290meu8n5ed14icpcgsxn
        foreign key (user_id) references scenergy.users (id),
    constraint FKnch9pnch3fm0mvpfnru45tgkl
        foreign key (room_id) references scenergy.chat_room (room_id)
);

create table scenergy.user_location_tag
(
    id              bigint auto_increment
        primary key,
    location_tag_id bigint null,
    user_id         bigint null,
    constraint FKitsk8vyer5roxfm4y9b4ft84w
        foreign key (location_tag_id) references scenergy.location_tag (id)
            on delete cascade,
    constraint FKp0aextcqxbokqggcxk8b1kvl0
        foreign key (user_id) references scenergy.users (id)
            on delete cascade
);

create table scenergy.video
(
    id                 bigint auto_increment
        primary key,
    artist             varchar(255) null,
    music_title        varchar(255) null,
    thumbnail_url_path varchar(255) null,
    video_url_path     varchar(255) null
);

create table scenergy.video_post
(
    id       bigint not null
        primary key,
    user_id  bigint null,
    video_id bigint null,
    constraint UK_dofcxcoe1uth0gpevwnb2s1k9
        unique (video_id),
    constraint FKhxmv57rkqofqjw4nj0vadq81q
        foreign key (id) references scenergy.post (id),
    constraint FKn31fu875jjf2327egpj9jlpip
        foreign key (user_id) references scenergy.users (id),
    constraint FKsu9p0oqnu1d1238mf0k3gvpxl
        foreign key (video_id) references scenergy.video (id)
);

create table scenergy.likes
(
    id            bigint auto_increment
        primary key,
    user_id       bigint null,
    video_post_id bigint null,
    constraint FKgcf5mbr28clycley3et1km9kp
        foreign key (video_post_id) references scenergy.video_post (id),
    constraint FKnvx9seeqqyy71bij291pwiwrg
        foreign key (user_id) references scenergy.users (id)
);

create table scenergy.video_post_genre_tag
(
    genre_tag_id  bigint null,
    id            bigint auto_increment
        primary key,
    video_post_id bigint null,
    constraint FKd5w1hef3be8umapx2e0gy5o19
        foreign key (genre_tag_id) references scenergy.genre_tag (id)
            on delete cascade,
    constraint FKtmamgx9ul6lr922ynmvcuwirp
        foreign key (video_post_id) references scenergy.video_post (id)
);

create table scenergy.video_post_instrument_tag
(
    id                bigint auto_increment
        primary key,
    instrument_tag_id bigint null,
    video_post_id     bigint null,
    constraint FKowphmcrv064bbkl6i74p3aq17
        foreign key (video_post_id) references scenergy.video_post (id)
            on delete cascade,
    constraint FKs3d5p5gdacgcustrbdmqo1t7u
        foreign key (instrument_tag_id) references scenergy.instrument_tag (id)
            on delete cascade
);


