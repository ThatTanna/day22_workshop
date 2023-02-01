package sg.edu.nus.iss.day22_workshop.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.day22_workshop.model.RSVP;

@Repository
public class RsvpRepoImpl {

        @Autowired
        JdbcTemplate jdbcTemplate;

        private final String findAllSQL = "SELECT * FROM rsvp";

        private final String findByNameSQL = "SELECT * FROM rsvp WHERE full_name like '%?%'";

        private final String insertSQL = "INSERT INTO rsvp " +
                        "(full_name, email, phone, confirmation_date, comments ) " +
                        "VALUES (?, ?, ?, ?, ?)";

        private final String updateSQL = "UPDATE rsvp " +
                        "SET full_name = ?, email = ?, phone = ?, " +
                        "confirmation_date = ?, comments = ?" +
                        "WHERE id = ?";

        private final String countSQL = "SELECT count(*) as cnt FROM rsvp";

        private final String findByIdSQL = "select * FROM rsvp WHERE id = ?";

        public RSVP findById(Integer id) {
                return jdbcTemplate.queryForObject(findByIdSQL, BeanPropertyRowMapper
                                .newInstance(RSVP.class), id);
        }

        public List<RSVP> findALL() {
                return jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper
                                .newInstance(RSVP.class));
        }

        public List<RSVP> findByName(String name) {
                List<RSVP> resultList = new ArrayList<RSVP>();
                resultList = jdbcTemplate.query(findByNameSQL,
                                BeanPropertyRowMapper.newInstance(RSVP.class), name);
                return resultList;
        }

        public Boolean save(RSVP rsvp) {
                Integer result = jdbcTemplate.update(insertSQL,
                                rsvp.getFullName(),
                                rsvp.getEmail(),
                                rsvp.getPhone(),
                                rsvp.getConfirmationDate(),
                                rsvp.getComments());
                return result > 0 ? true : false;
        }

        public Boolean update(RSVP rsvp) {
                Integer result = jdbcTemplate.update(updateSQL,
                                rsvp.getFullName(),
                                rsvp.getEmail(),
                                rsvp.getPhone(),
                                rsvp.getConfirmationDate(),
                                rsvp.getComments(),
                                rsvp.getId());
                return result > 0 ? true : false;
        }

        public Integer countAll() {
                Integer result = jdbcTemplate.queryForObject(countSQL, Integer.class);
                return result;
        }

        @Transactional
        public int[] batchInsert(List<RSVP> rsvp) {
                return jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setString(1, rsvp.get(i).getFullName());
                                ps.setString(2, rsvp.get(i).getEmail());
                                ps.setString(3, rsvp.get(i).getPhone());
                                ps.setDate(4, rsvp.get(i).getConfirmationDate());
                                ps.setString(5, rsvp.get(i).getComments());
                        }

                        @Override
                        public int getBatchSize() {
                                return rsvp.size();
                        }

                });

        }

}
