package org.hibernate.dialect.extension;


import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.internal.CoreLogging;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

/**
 * PostgreSQLDialectFix that add support for ClobType storage directly in table column instead of using external oid.
 */
public class PostgreSQLDialectFix extends PostgreSQL95Dialect
{
    private static final CoreMessageLogger LOG = CoreLogging.messageLogger(PostgreSQLDialectFix.class);


    public PostgreSQLDialectFix()
    {
        registerColumnType(Types.CLOB, "varchar");
        registerColumnType(Types.BLOB, "bytea");
    }


    @Override
    public SqlTypeDescriptor getSqlTypeDescriptorOverride(int sqlCode)
    {
        if (Types.BLOB == sqlCode)
        {
            LOG.infof("getSqlTypeDescriptorOverride sqlCode=%s  default=%s  return=%s", sqlCode,
                      super.getSqlTypeDescriptorOverride(sqlCode), BinaryTypeDescriptor.INSTANCE);
            return BinaryTypeDescriptor.INSTANCE;
        }
        else if (Types.CLOB == sqlCode)
        {
            LOG.infof("getSqlTypeDescriptorOverride sqlCode=%s  default=%s  return=", sqlCode,
                      super.getSqlTypeDescriptorOverride(sqlCode), VarcharTypeDescriptor.INSTANCE);
            return VarcharTypeDescriptor.INSTANCE;
        }
        else
        {
            return super.getSqlTypeDescriptorOverride(sqlCode);
        }
    }
}
