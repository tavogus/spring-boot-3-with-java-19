CREATE TABLE config
(
    `key`   VARCHAR(255) NOT NULL,
    value VARCHAR(255),
    PRIMARY KEY (`key`)
);

insert into config (`key`, value)
values ('AWS_ACCESS_KEY_ID', 'AKIAVONGJFCBCWGNYGO3');
insert into config (`key`, value)
values ('AWS_SECRET_ACCESS_KEY', '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');