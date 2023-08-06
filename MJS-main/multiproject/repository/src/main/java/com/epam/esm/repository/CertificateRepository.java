package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CertificateRepository extends PagingAndSortingRepository<Certificate, Long> {

    Certificate findByName(String certificateName);

    List<Certificate> findAll();

    @Query(value =
            "select c.certificate_id, c.certificate_name, c.description, c.price, c.create_date, c.last_update_date, c.duration , count(t.tag_name) from gift.gift_certificate c\n" +
                    "                    inner join gift.certificate_tag ct\n" +
                    "                    on c.certificate_id = ct.certificate_id                \n" +
                    "                    inner join gift.tag t\n" +
                    "                    on t.tag_id = ct.tag_id\n" +
                    "                     where (?1 is null or c.certificate_name like ?1% ) and  (?2 is null or c.description  like ?2% )\n" +
                    "                    and (?3 is null or FIND_IN_SET(t.tag_name, (?3)))\n" +
                    "GROUP BY   c.certificate_id, c.certificate_name, c.description, c.price, c.create_date, c.last_update_date, c.duration \n" +
                    "HAVING     (?4 = 0  or count(t.tag_name)=?4) ",
            countQuery = "select count(*) from gift.gift_certificate c\n" +
                    "                    inner join gift.certificate_tag ct\n" +
                    "                    on c.certificate_id = ct.certificate_id                \n" +
                    "                    inner join gift.tag t\n" +
                    "                    on t.tag_id = ct.tag_id \n" +
                    "\t\t\t\t\twhere (?1 is null or c.certificate_name like ?1% ) and  (?2 is null or c.description  like ?2% )\n" +
                    "\t\t\t\t\tand (?3 is null or FIND_IN_SET(t.tag_name, (?3))) \n" +
                    "HAVING     (?4 = 0  or count(t.tag_name)=?4)",
            nativeQuery = true)
    Page<Certificate> findCertificatesByNameAndDescription(@Param("name") String name, @Param("description") String description, @Param("tags") String tags, @Param("count") int count, Pageable pageable);

    Certificate findCertificateById(Long id);

    Certificate save(Certificate certificate);

    void deleteById(Long id);
}