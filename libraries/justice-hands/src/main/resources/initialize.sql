CREATE TABLE IF NOT EXISTS `fv_jh_player`
(
  `uuid`   CHAR(36) NOT NULL PRIMARY KEY,
  `points` INT      NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `fv_jh_sanction`
(
  `id`          INT(255) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `uuid`        CHAR(255)         NOT NULL,
  `name`        CHAR(255)         NOT NULL,
  `reason`      CHAR(255)         NOT NULL,
  `points`      INT(255) UNSIGNED NOT NULL,
  `date`        TIMESTAMP         NOT NULL DEFAULT (sysdate() + interval 2 hour),
  `expire_date` TIMESTAMP         NULL     DEFAULT NULL,
  `moderator`   CHAR(255)         NOT NULL,
  `type`        CHAR(255)         NOT NULL,
  `state`       CHAR(255)         NOT NULL DEFAULT 'active'
);
