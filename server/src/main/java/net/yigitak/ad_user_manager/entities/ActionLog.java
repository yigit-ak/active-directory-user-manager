package net.yigitak.ad_user_manager.entities;

import net.yigitak.ad_user_manager.enums.ActionType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "action_logs")
public class ActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String performedBy;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String targetUserCn;

    private LocalDateTime timestamp;

    public ActionLog() {
    }

    public ActionLog(String performedBy, ActionType actionType, String targetUserCn) {
        this.performedBy = performedBy;
        this.actionType = actionType;
        this.targetUserCn = targetUserCn;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getTargetUserCn() {
        return targetUserCn;
    }

    public void setTargetUserCn(String targetUserCn) {
        this.targetUserCn = targetUserCn;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

