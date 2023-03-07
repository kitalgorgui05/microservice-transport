package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.GroupeTransport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupeTransportRepository extends JpaRepository<GroupeTransport, String>, JpaSpecificationExecutor<GroupeTransport> {

    @Query(value = "select distinct groupeTransport from GroupeTransport groupeTransport left join fetch groupeTransport.zones",
        countQuery = "select count(distinct groupeTransport) from GroupeTransport groupeTransport")
    Page<GroupeTransport> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct groupeTransport from GroupeTransport groupeTransport left join fetch groupeTransport.zones")
    List<GroupeTransport> findAllWithEagerRelationships();

    @Query("select groupeTransport from GroupeTransport groupeTransport left join fetch groupeTransport.zones where groupeTransport.id =:id")
    Optional<GroupeTransport> findOneWithEagerRelationships(@Param("id") String id);
}
