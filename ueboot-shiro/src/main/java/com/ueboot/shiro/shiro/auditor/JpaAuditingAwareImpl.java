package com.ueboot.shiro.shiro.auditor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.data.domain.AuditorAware;

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
    public String getCurrentAuditor() {

        //使用当前登录用户名称作为创建人和最后修改人字段的值
        if (ThreadContext.getSubject() != null && SecurityUtils.getSubject() != null) {
            return (String) SecurityUtils.getSubject().getPrincipal();
        }
        return null;
    }
}
