package h.dao.jdbc.sql;

import javax.sql.DataSource;

import org.springframework.jdbc.object.SqlUpdate;

public abstract class AbstractSqlUpdate<T> extends SqlUpdate
{
  public abstract Object[] params(T inObject);

  public AbstractSqlUpdate(DataSource inDataSource, String inSql, int... inTypes)
  {
    super(inDataSource, inSql, inTypes);
    compile();
  }

  public int execute(T inObject)
  {
    return update(params(inObject));
  }
}