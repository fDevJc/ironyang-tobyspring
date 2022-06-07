package ironyang.tobyspring.study.embeddeddatabase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class EmbeddedDbTest {
    EmbeddedDatabase db;
    JdbcTemplate template;

    @BeforeEach
    void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:ironyang/tobyspring/study/embeddeddatabase/sql/schema.sql")
                .addScript("classpath:ironyang/tobyspring/study/embeddeddatabase/sql/data.sql")
                .build();

        template = new JdbcTemplate(db);
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }

    @Test
    void initData() {
        assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class)).isEqualTo(2);

        List<Map<String, Object>> list = template.queryForList("select * from sqlmap order by key_");

        assertThat(list.get(0).get("key_")).isEqualTo("KEY1");
        assertThat(list.get(0).get("sql_")).isEqualTo("SQL1");
        assertThat(list.get(1).get("key_")).isEqualTo("KEY2");
        assertThat(list.get(1).get("sql_")).isEqualTo("SQL2");
    }

    @Test
    void insert() {
        template.update("insert into sqlmap(key_, sql_) values(?,?)", "KEY3", "SQL3");

        assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class)).isEqualTo(3);
    }
}
