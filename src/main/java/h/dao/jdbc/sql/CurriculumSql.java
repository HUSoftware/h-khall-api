package h.dao.jdbc.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import h.model.khall.Curriculum;

public class CurriculumSql extends AbstractSql
{
  private final SqlUpdate mUpsert;
  private final MappingSqlQuery<Curriculum> mSelect;
  private final MappingSqlQuery<Curriculum> mSelectR;
  private final MappingSqlQuery<Integer> mSelectY;

  public CurriculumSql(DataSource inDataSource)
  {
    mStmts = getStatements("Curriculum.xml");

    Statement upsert = mStmts.getStatement("UPSERT");
    mUpsert = newSqlUpdate(inDataSource, upsert.getSql(), upsert.types());

    Statement select = mStmts.getStatement("SELECT");
    mSelect = new MappingSql<Curriculum>(inDataSource, select.getSql(), select.types())
    {
      @Override
      public Curriculum mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return Mapping.mapCurriculum(inRs, "c");
      }
    };

    Statement selectR = mStmts.getStatement("SELECT_RANGE");
    mSelectR = new MappingSql<Curriculum>(inDataSource, selectR.getSql(), selectR.types())
    {
      @Override
      public Curriculum mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return Mapping.mapCurriculum(inRs, "c");
      }
    };

    Statement selectY = mStmts.getStatement("SELECT_YEAR");
    mSelectY = new MappingSql<Integer>(inDataSource, selectY.getSql())
    {
      @Override
      public Integer mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return inRs.getInt("mYear");
      }
    };
  }

  public List<Integer> selectYears()
  {
    return mSelectY.execute();
  }

  public int upsert(Curriculum inCurriculum)
  {
    return mUpsert.update(params(inCurriculum.getDate(), inCurriculum.getPart().name(),
        inCurriculum.getTheme(), inCurriculum.getSource(), inCurriculum.getDurationMinutes(),
        inCurriculum.getSort(), inCurriculum.getTheme(), inCurriculum.getSource(),
        inCurriculum.getDurationMinutes(), inCurriculum.getSort()));
  }

  public List<Curriculum> selectByYear(int inYear)
  {
    return mSelect.execute(params(inYear));
  }

  public List<Curriculum> selectBetween(Date inStart, Date inEnd)
  {
    return mSelectR.execute(params(inStart, inEnd));
  }
}