package ironyang.tobyspring.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class TransactionHandler implements InvocationHandler {
    private final Object target;
    private String[] pattern;

    public TransactionHandler(Object target, String[] pattern) {
        this.target = target;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (PatternMatchUtils.simpleMatch(pattern, method.getName())) {
            return transactionInvoke(method, args);
        }
        return method.invoke(target, args);
    }

    private Object transactionInvoke(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        log.info("[{}.{}] transaction start", method.getDeclaringClass().getSimpleName(), method.getName());
        Object obj = method.invoke(target, args);
        log.info("[{}.{}] transaction end", method.getDeclaringClass().getSimpleName(), method.getName());
        return obj;
    }
}
