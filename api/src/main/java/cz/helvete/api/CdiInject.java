package cz.helvete.api;

import cz.helvete.api.config.AppConfig;
import cz.helvete.api.config.Config;
import java.util.logging.Logger;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CdiInject {

    @Produces
    @PersistenceContext(name="PGSGL_DS", unitName="PGSGL_DS")
    EntityManager em;

    @Inject
    AppConfig conf;

    @Produces
    Logger logger(InjectionPoint ip) {
        return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
    }

    @Produces
    @Default
    String getValue(InjectionPoint ip) {
        return conf.get(ip.getAnnotated().getAnnotation(Config.class).value());
    }
}
