package com.ueboot.shiro.shiro.auditor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author yangkui
 */
public class JpaAuditingAwareImpl implements AuditorAware<String> {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {

        if (ThreadContext.getSubject() != null && SecurityUtils.getSubject() != null) {
            return Optional.ofNullable((String) SecurityUtils.getSubject().getPrincipal());
        }
        return Optional.empty();
    }
}
