package org.hibernate.dialect.extension;


import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.internal.CoreLogging;
import org.hibernate.internal.CoreMessageLogger;

/**
 * Dialect that make use of {@link org.hibernate.dialect.extension.PostgreSQLDialectFix}
 */
public class DialectResolver extends StandardDialectResolver
{
    private static final long serialVersionUID = 1L;

    private static final CoreMessageLogger LOG = CoreLogging.messageLogger(DialectResolver.class);


    @Override
    public Dialect resolveDialect(DialectResolutionInfo dialectResolutionInfo)
    {
        Dialect dialect = lookupDialect(dialectResolutionInfo.getDatabaseName());
        if (dialect != null)
        {
            return dialect;
        }
        return super.resolveDialect(dialectResolutionInfo);
    }


    private Dialect lookupDialect(String databaseName)
    {
        if (databaseName.startsWith("PostgreSQL"))
        {
            LOG.infof("Instantiate dialect=%s for database=%s", PostgreSQLDialectFix.class, databaseName);
            return new PostgreSQLDialectFix();
        }
        else
        {
            LOG.infof("No dialect is known for %s deferring to standard dialect resolver.", databaseName);
            return null;
        }
    }
}
