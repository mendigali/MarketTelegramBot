package Database;

public class SQL {

    public static final String CREATE_TABLE = """
            CREATE TABLE `marketbot`.`advertisements` (
                `id` SERIAL,
                `author_id` BIGINT UNSIGNED NOT NULL,
                `author_username` VARCHAR(250) NOT NULL,
                `title` VARCHAR(250) NOT NULL,
                `description` VARCHAR(5000) NOT NULL,
                `price` INT UNSIGNED NOT NULL,
                `payment_type` VARCHAR(50) NOT NULL,
                `date` TIMESTAMP NOT NULL,
                `is_visible` BOOLEAN NOT NULL,
                PRIMARY KEY (`id`)
            ) ENGINE = InnoDB;""";

    public static final String INSERT_ADVERTISEMENT = """
            INSERT INTO `advertisements`
                (`author_id`, `author_username`, `title`, `description`, `price`, `payment_type`, `date`, `is_visible`)
            VALUES
                (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String READ_ADVERTISEMENTS = """
            SELECT * FROM `advertisements` ORDER BY `date` ASC LIMIT 10
            """;

    public static final String UPDATE_ADVERTISEMENT = """
            UPDATE `advertisements` SET ?=? WHERE `id`=?
            """;

    public static final String DELETE_ADVERTISEMENT = """
            DELETE FROM `advertisements` WHERE `id`=?
            """;
}
