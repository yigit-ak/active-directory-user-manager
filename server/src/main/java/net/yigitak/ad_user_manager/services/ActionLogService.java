package net.yigitak.ad_user_manager.services;

import net.yigitak.ad_user_manager.entities.ActionLog;
import net.yigitak.ad_user_manager.enums.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ActionLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Service
public class ActionLogService {

    @Autowired
    private ActionLogRepository actionLogRepository;

    public void logAction(ActionType actionType, String targetUserCn) {
        String performedBy = getCurrentUsername();
        ActionLog log = new ActionLog(performedBy, actionType, targetUserCn);
        actionLogRepository.save(log);
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser oidcUser) {
            return oidcUser.getEmail();
        }
        return "UNKNOWN_USER";
    }
}
