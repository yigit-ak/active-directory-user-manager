package net.yigitak.ad_user_manager.repositories;

import net.yigitak.ad_user_manager.entities.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
}
