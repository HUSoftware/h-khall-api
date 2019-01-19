package h.dao.jdbc.sql;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import com.thoughtworks.xstream.XStream;

import h.json.JsonMapper;
import h.parser.Parser;
import h.util.EnumUtil;
import h.util.ResourceUtil;

public abstract class AbstractSql
{
  public static SqlUpdate newSqlUpdate(DataSource inDataSource, String inSql, int... inTypes)
  {
    SqlUpdate ret = new SqlUpdate(inDataSource, inSql, inTypes);
    ret.compile();
    return ret;
  }

  public static SqlUpdate newSqlUpdate(DataSource inDataSource, Statement inStmt)
  {
    SqlUpdate ret = new SqlUpdate(inDataSource, inStmt.getSql(), inStmt.types());
    ret.compile();
    return ret;
  }

  public static String name(Enum<?> inEnum)
  {
    return inEnum != null ? inEnum.name() : null;
  }

  public static <T extends Enum<?>> List<T> toList(String inString, T[] inValues)
  {
    List<T> list = toValidList(inString, inValues);
    return list.size() == 0 ? null : list;
  }

  public static <T extends Enum<?>> List<T> toValidList(String inString, T[] inValues)
  {
    List<T> ret = new ArrayList<>();

    if (inString != null && inString.indexOf("|") != -1)
    {
      for (String value : inString.split("\\|"))
      {
        T valueOf = valueOf(value, inValues);
        if (valueOf != null)
        {
          ret.add(valueOf);
        }
      }
    }

    return ret;
  }

  public static <T extends Enum<?>> String toString(List<T> inEnum)
  {
    String ret = null;

    if (inEnum != null && inEnum.size() > 0)
    {
      StringBuilder sb = new StringBuilder();
      for (T t : inEnum)
      {
        sb.append(name(t)).append("|");
      }
      ret = sb.toString();
    }

    return ret;
  }

  @SafeVarargs
  public static <T extends Enum<?>> String toString(T... inEnum)
  {
    String ret = null;

    if (inEnum != null && inEnum.length > 0)
    {
      StringBuilder sb = new StringBuilder();
      for (T t : inEnum)
      {
        sb.append(name(t)).append("|");
      }
      ret = sb.toString();
    }

    return ret;
  }

  public static abstract class MappingSqlEncrypt<T> extends MappingSqlQuery<T>
  implements RowMapper<T>
  {
    public MappingSqlEncrypt()
    {
    }

    public MappingSqlEncrypt(DataSource inDataSource, String inSql, int... inTypes)
    {
      super(inDataSource, inSql);
      setTypes(inTypes);
      compile();
    }

    public List<T> select(String inKey, Object[] inParams)
    {
      getJdbcTemplate().execute("set @key = '" + inKey + "'");
      return execute(inParams);
    }
  }

  public static abstract class MappingSql<T> extends MappingSqlQuery<T> implements RowMapper<T>
  {
    public MappingSql(DataSource inDataSource, String inSql, int... inTypes)
    {
      super(inDataSource, inSql);
      setTypes(inTypes);
      compile();
    }
  }

  private static final Map<String, Integer> MAP = getJdbcTypeName();

  protected Statements mStmts;
  protected JsonMapper mJsonMapper;

  public AbstractSql()
  {
    mJsonMapper = new JsonMapper();
  }

  protected String getSql(Class<?> inClass)
  {
    return ResourceUtil.getSql(inClass);
  }

  protected String sqlFile()
  {
    return getSql(this.getClass());
  }

  protected String fileContent(String inFileName)
  {
    String s = this.getClass().getName().replaceAll("\\.", "/");
    s = s.replaceAll(this.getClass().getSimpleName(), inFileName);
    return ResourceUtil.contents(s);
  }

  public static <T> T first(List<T> inList)
  {
    return inList != null && inList.size() > 0 ? inList.get(0) : null;
  }

  public static <T> T only(List<T> inList)
  {
    return inList != null && inList.size() == 1 ? inList.get(0) : null;
  }

  public static <T extends Enum<?>> T valueOf(String inValue, T[] inValues)
  {
    return EnumUtil.valueOf(inValue, inValues);
  }

  protected static <T> T valueOf(String inValue, Parser<T> inParser)
  {
    return inParser.fromXml(inValue);
  }

  protected static <T> String valueOf(T inObj, Parser<T> inParser)
  {
    return inParser.toXml(inObj);
  }

  public static int[] types(int... inTypes)
  {
    return inTypes;
  }

  protected static String replaceIn(String inSql, int inLength)
  {
    return inSql.replaceAll("=\\?", replaceIn(inLength));
  }

  protected static String replaceIn(int inLength)
  {
    StringBuilder sb = new StringBuilder(" in (");
    for (int i = 0; i < inLength; i++)
    {
      if (i != 0)
      {
        sb.append(",");
      }
      sb.append("?");
    }
    sb.append(")");
    return sb.toString();
  }

  public static Object[] params(Object... inTypes)
  {
    return inTypes;
  }

  public static int[] numericTypes(int inLength)
  {
    int[] ret = new int[inLength];
    for (int i = 0; i < inLength; i++)
    {
      ret[i] = Types.NUMERIC;
    }
    return ret;
  }

  public void setJsonMapper(JsonMapper inJsonMapper)
  {
    mJsonMapper = inJsonMapper;
  }

  public String writeValue(Object inModel)
  {
    return mJsonMapper.writeValue(inModel);
  }

  public <M> M readValue(String inText, Class<M> inClass)
  {
    return mJsonMapper.readValue(inText, inClass);
  }

  protected Statements getStatements(String inXmlName)
  {
    String fileContent = fileContent(inXmlName);
    return fromXml(fileContent);
  }

  protected static Statements fromXml(String inXml)
  {
    XStream xs = xStream();

    Statements ret = (Statements) xs.fromXML(inXml);

    return ret;
  }

  private static XStream xStream()
  {
    XStream ret = new XStream();

    ret.alias("Statements", Statements.class);
    ret.addImplicitMap(Statements.class, "mStmt", null, Statement.class, "mKey");

    ret.alias("Statement", Statement.class);
    ret.aliasField("Key", Statement.class, "mKey");
    ret.aliasField("Types", Statement.class, "mTypes");
    ret.aliasField("Types", Statement.class, "mTypes");
    ret.aliasField("Sql", Statement.class, "mSql");

    return ret;
  }

  private static Map<String, Integer> getJdbcTypeName()
  {
    Map<String, Integer> ret = new HashMap<>();
    try
    {
      Field[] fields = java.sql.Types.class.getFields();
      for (Field field : fields)
      {
        String name = field.getName();
        Integer value = (Integer) field.get(null);
        ret.put(name, value);
      }
    }
    catch (IllegalAccessException e)
    {
      // do nothing
    }
    return ret;
  }

  public static class Statements
  {
    private Map<String, Statement> mStmt;

    public Statement getStatement(String inKey)
    {
      return mStmt.get(inKey);
    }
  }

  public static class Statement
  {
    public String mKey;

    private String mTypes;
    private String mSql;

    public String getSql()
    {
      return mSql;
    }

    public int[] types()
    {
      int[] ret = null;
      if (mTypes != null && mTypes.length() > 0)
      {
        String[] types = mTypes.split(",");
        ret = new int[types.length];
        for (int i = 0; i < types.length; i++)
        {
          ret[i] = MAP.get(types[i]);
        }
      }
      return ret;
    }
  }

  protected BatchSqlUpdate newBatchUpdate(DataSource inDataSource, String inKey)
  {
    Statement stmt = mStmts.getStatement(inKey);
    BatchSqlUpdate ret = new BatchSqlUpdate(inDataSource, stmt.getSql(), stmt.types());
    ret.compile();
    return ret;
  }
}