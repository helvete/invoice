package cz.helvete.invoice;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

@Startup
@Singleton
public class DbMigration {

    @Inject
    Logger log;

    @Resource(lookup = "${ENV=PG_DATASOURCE_NAME}")
    DataSource dataSource;

    @PostConstruct
    public void onStartup() {
        // at first migrate db
        migrate();

    }

    private void migrate() {
        if (dataSource == null) {
            log.severe("no datasource found to execute the db migrations!");
            throw new EJBException(
                    "no datasource found to execute the db migrations!");
        }

        Flyway flyway = null;

        try {
            // flyway properties
            InputStream is = getClass().getClassLoader().getResourceAsStream("db/flyway.conf");
            Properties flywayConf = new Properties();
            flywayConf.load(is);
            is.close();
            log.fine("flyway : properties loaded");

            // init flyway
            FluentConfiguration flyconf = Flyway.configure();
            flyconf.configuration(flywayConf);
            flyconf.dataSource(dataSource);
            flyway = flyconf.load();

            // migrate
            flyway.migrate();

        } catch (Exception ex) {
            log.severe("flyway : " + ex.getMessage());
        } finally {
            if (flyway != null) {
                // info
                for (MigrationInfo i : flyway.info().all()) {
                    log.info("flyway : migrate task : "
                            + i.getVersion() + " " + i.getDescription()
                            + " from file " + i.getScript()
                            + " status " + i.getState()
                            + " installed on " + i.getInstalledOn()
                    );
                }
            }
        }
    }
}
