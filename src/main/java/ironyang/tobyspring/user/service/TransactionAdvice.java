package ironyang.tobyspring.user.service;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TransactionAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("[{}.{}]transaction start", invocation.getThis().getClass().getSimpleName(), invocation.getMethod().getName());
        Object result = invocation.proceed();
        log.info("[{}.{}]transaction end", invocation.getThis().getClass().getSimpleName(), invocation.getMethod().getName());
        return result;
    }
}
