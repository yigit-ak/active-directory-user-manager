package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.entities.ActionLog;
import net.yigitak.ad_user_manager.enums.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ActionLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * Service for logging user actions such as password resets, account locks, and unlocks.
 * <p>
 * Captures which authenticated user performed an action on which target user.
 */
@Service
public class ActionLogService {

    @Autowired
    private ActionLogRepository actionLogRepository;

    /**
     * Logs an action performed by the currently authenticated user.
     *
     * @param actionType the type of action performed (e.g., RESET_PASSWORD, LOCK_USER)
     * @param targetUserCn the common name (CN) of the user the action was performed on
     */
    public void logAction(ActionType actionType, String targetUserCn) {
        String performedBy = getCurrentUsername();
        ActionLog log = new ActionLog(performedBy, actionType, targetUserCn);
        actionLogRepository.save(log);
    }

    /**
     * Retrieves the username (email) of the currently authenticated user.
     *
     * @return the username (email) if available, otherwise "UNKNOWN_USER"
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser oidcUser) {
            return oidcUser.getEmail();
        }
        return "UNKNOWN_USER";
    }
}
