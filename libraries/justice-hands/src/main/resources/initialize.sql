CREATE TABLE IF NOT EXISTS `fv_jh_sanction`
(
  `sctn_id`                     INT(255) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `sctn_inculpated_player_uuid` CHAR(255)         NOT NULL,
  `sctn_name`                   CHAR(255)         NOT NULL,
  `sctn_reason`                 CHAR(255)         NOT NULL,
  `sctn_points`                 INT(255) UNSIGNED NOT NULL,
  `sctn_beginning_date`         TIMESTAMP         NOT NULL DEFAULT (sysdate() + interval 2 hour),
  `sctn_ending_date`            TIMESTAMP         NULL     DEFAULT NULL,
  `sctn_author_player_uuid`     CHAR(255)         NULL,
  `sctn_type`                   CHAR(255)         NOT NULL
);
