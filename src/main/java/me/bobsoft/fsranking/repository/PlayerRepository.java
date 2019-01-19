package me.bobsoft.fsranking.repository;

import me.bobsoft.fsranking.model.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query(value = "select p.id " +
            "from player p join score s on p.id=s.id_player join competition c on c.id=s.id_competition " +
            "join public.group g on g.id=c.id_group where c.id=" +
            "(select id from competition where id_group=?1 order by date desc limit 1)", nativeQuery = true)
    List<Integer> findPlayersIdByLastCompetitionOfGivenGroup(Integer groupId);
}