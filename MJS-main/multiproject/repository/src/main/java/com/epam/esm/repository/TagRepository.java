package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    Tag findByName(String tagName);

    List<Tag> findAll();

    Page<Tag> findAllBy(Pageable pageable);

    Tag findTagById(Long id);

    Tag save(Tag tag);

    void deleteById(Long id);

    @Query(value =
            "SELECT t.tag_name ,t.tag_id, t.operation, t.timestamp\n" +
                    "                            FROM gift.orders o\n" +
                    "                            INNER JOIN (\n" +
                    "                            SELECT \n" +
                    "                            o.user_account_id\n" +
                    "                            FROM gift.order_details od \n" +
                    "                            INNER JOIN gift.orders o\n" +
                    "                            ON od.order_id = o.order_id\n" +
                    "                     \n" +
                    "                            INNER JOIN gift.gift_certificate gc\n" +
                    "                            ON od.certificate_id = gc.certificate_id \n" +
                    "                            GROUP BY user_account_id\n" +
                    "                            ORDER BY SUM(gc.price*od.quantity) DESC\n" +
                    "                            LIMIT 1\n" +
                    "                                   ) u\n" +
                    "                            ON u.user_account_id = o.user_account_id                        \n" +
                    "                            INNER JOIN  gift.order_details od\n" +
                    "                            ON o.order_id = od.order_id\n" +
                    "                            INNER JOIN gift.gift_certificate gc \n" +
                    "                            ON od.certificate_id = gc.certificate_id\n" +
                    "                            INNER JOIN gift.certificate_tag ct\n" +
                    "                            ON gc.certificate_id = ct.certificate_id\n" +
                    "                            \n" +
                    "                            INNER JOIN gift.tag t\n" +
                    "                            ON t.tag_id = ct.tag_id\n" +
                    "                            GROUP BY t.tag_name\n" +
                    "                            ORDER BY count(t.tag_id) DESC\n" +
                    "                            LIMIT 1",
            nativeQuery = true)
    Tag findTopByCertificatesMatches();
}