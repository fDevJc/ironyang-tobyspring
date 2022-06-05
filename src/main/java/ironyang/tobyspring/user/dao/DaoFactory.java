package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.service.*;
import ironyang.tobyspring.user.service.levelupgradepolicy.SimpleUserLevelUpgradePolicy;
import ironyang.tobyspring.user.service.levelupgradepolicy.UserLevelUpgradePolicy;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDaoMy(connectionMaker());
    }

    //@Bean
    public UserService userService1() {
//        return new UserServiceTx(userServiceImpl());
        String[] patterns = {"upgradeLevels*", "add*"};
        return (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{UserService.class},
                new TransactionHandler(userServiceImpl(), patterns));
    }

    @Bean
    public UserService userService() {
        ProxyFactory proxyFactory = new ProxyFactory(userServiceImpl());
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(new TransactionAdvice());
        proxyFactory.addAdvisor(advisor);
        return (UserService) proxyFactory.getProxy();
    }

    @Bean
    public UserService userServiceImpl() {
        return new UserServiceImpl(userDao(), userLevelUpgradePolicy());
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new SimpleUserLevelUpgradePolicy();
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
