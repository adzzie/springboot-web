package com.belajar.java.web.dao;

import com.belajar.java.web.entity.Peserta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description PesertaDaoTest
 *
 * @author aji gojali
 */
@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //untuk order eksekusi
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/data/peserta.sql"
)
public class PesertaDaoTest {

    @Autowired
    private PesertaDao pesertaDao;

    @Autowired
    public DataSource dataSource;

    @Test
//    @Order(1)
    void testInsert() throws SQLException {
        Peserta p = new Peserta();
        p.setNama("peserta 001");
        p.setEmail("peserta001@gmail.com");
        p.setTanggalLahir(new Date());

        pesertaDao.save(p);

        String sql = "select count(*) as jumlah " +
                "from peserta " +
                "where email = 'peserta001@gmail.com'";

       try (Connection c = dataSource.getConnection()) {

           ResultSet rs = c.createStatement().executeQuery(sql);
           assertTrue(rs.next());

           Long jumlahRow = rs.getLong("jumlah");
           assertEquals(1L, jumlahRow.longValue());

       }

    }

    @Test
    public void testCariById(){
        Optional<Peserta> p = pesertaDao.findById("aa1");
        assertNotNull(p);
        assertEquals("peserta test 001", p.get().getNama());

        Optional<Peserta> px = pesertaDao.findById("xx");
        assertNull(px.orElse(null));
    }

    @Test
    public void testHitung(){
        Long jumlah = pesertaDao.count();
        assertEquals(3L,jumlah.longValue());
    }

//    @Test
//    @Order(2)
    @AfterEach
    void hapusData() throws SQLException {
        String sql = "delete from peserta where email = 'peserta001@gmail.com'";
        try (Connection c = dataSource.getConnection()){
            c.createStatement().executeUpdate(sql);

        }
    }



}
