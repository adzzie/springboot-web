package com.belajar.java.web.dao;

import com.belajar.java.web.entity.Materi;
import com.belajar.java.web.entity.Peserta;
import com.belajar.java.web.entity.Sesi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        scripts = {"/data/peserta.sql","/data/materi.sql","/data/sesi.sql"}
)
public class SesiDaoTest {

    @Autowired
    private SesiDao sd;

    @Autowired
    private DataSource ds;

    @Test
    public void testCariByMateri(){
        Materi m = new Materi();
        m.setId("aa6");

        PageRequest page = PageRequest.of(0,5);

        Page<Sesi> hasilQuery = sd.findByMateri(m, page);
        assertEquals(2L,hasilQuery.getTotalElements());

        assertFalse(hasilQuery.getContent().isEmpty());
        Sesi s =hasilQuery.getContent().get(0);
        assertNotNull(s);
        assertEquals("Java Fundamental",s.getMateri().getNama());
    }

    @Test
    public void testCariBerdasarkanTanggalMulaiDanKodeMateri() throws ParseException {
        PageRequest page = PageRequest.of(0,5);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formater.parse("2019-01-01");
        Date sampai = formater.parse("2019-01-03");

        Page<Sesi> hasil = sd.cariBerdasarkanTanggalMulaiDanKodeMateri(sejak, sampai,"JF-002", page);
        assertEquals(1L,hasil.getTotalElements());
        assertFalse(hasil.getContent().isEmpty());

        Sesi s = hasil.getContent().get(0);
        assertEquals("Java Web",s.getMateri().getNama());
    }

    @Test
    public void testSaveSesi() throws ParseException {
        Peserta p1 = new Peserta();
        p1.setId("aa1");

        Peserta p2 = new Peserta();
        p2.setId("aa3");

        Materi m = new Materi();
        m.setId("aa8");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formatter.parse("2019-02-01");
        Date sampai = formatter.parse("2019-02-03");

        Sesi s = new Sesi();
        s.setMateri(m);
        s.setMulai(sejak);
        s.setSampai(sampai);
        s.getDaftarPeserta().add(p1);
        s.getDaftarPeserta().add(p2);

        sd.save(s);
        String idSesiBaru = s.getId();
        assertNotNull(idSesiBaru);
        System.out.println("ID Baru : "+s.getId());

        String sql = "select count(*) from sesi where id_materi='aa8'";
        String sqlManytoMany = "select count(*) from peserta_pelatihan " +
                "where id_sesi=?";

        try(Connection c = ds.getConnection()) {
            //cek table sesi
            ResultSet rs = c.createStatement().executeQuery(sql);
            assertTrue(rs.next());
            assertEquals(1L,rs.getLong(1));

            //cek table ralasi many to many dengan peserta
            PreparedStatement ps = c.prepareStatement(sqlManytoMany);
            ps.setString(1, idSesiBaru);
            ResultSet rs2 = ps.executeQuery();

            assertTrue(rs2.next());
            assertEquals(2L, rs2.getLong(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
