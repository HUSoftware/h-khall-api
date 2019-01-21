package h.dao.jdbc.sql;

import static h.dao.jdbc.DbUtil.newQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Component;

import h.dao.CurriculumDao;
import h.model.Db;
import h.model.khall.Curriculum;

@Component
public class CurriculumSql extends AbstractSql implements CurriculumDao
{
  private final SqlUpdate mUpsert;
  private final MappingSqlQuery<Curriculum> mSelect;
  private final MappingSqlQuery<Curriculum> mSelectR;
  private final MappingSqlQuery<Integer> mSelectY;
  private final MappingSqlQuery<Db.Row> mSelectYM;

  public CurriculumSql(DataSource inDataSource)
  {
    mStmts = getStatements("Curriculum.json");

    Statement upsert = mStmts.getStatement("UPSERT");
    mUpsert = newSqlUpdate(inDataSource, upsert.getSql(), upsert.gTypes());

    Statement select = mStmts.getStatement("SELECT");
    mSelect = newMapCurriculum(inDataSource, select);

    Statement selectR = mStmts.getStatement("SELECT_RANGE");
    mSelectR = newMapCurriculum(inDataSource, selectR);

    Statement selectY = mStmts.getStatement("SELECT_YEAR");
    mSelectY = new MappingSql<Integer>(inDataSource, selectY.getSql())
    {
      @Override
      public Integer mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return inRs.getInt("mYear");
      }
    };

    Statement selectYM = mStmts.getStatement("SELECT_YM");
    mSelectYM = newQuery(inDataSource, selectYM.getSql(), selectYM.gTypes());
  }

  private MappingSql<Curriculum> newMapCurriculum(DataSource inDataSource, Statement select)
  {
    return new MappingSql<Curriculum>(inDataSource, select.getSql(), select.gTypes())
    {
      @Override
      public Curriculum mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return Mapping.mapCurriculum(inRs, "c");
      }
    };
  }

  public List<Integer> selectYears()
  {
    return mSelectY.execute();
  }

  @Override
  public int upsert(Curriculum inCurriculum)
  {
    return mUpsert.update(params(inCurriculum.getDate(), inCurriculum.getPart().name(), inCurriculum.getTheme(), inCurriculum.getSource(), inCurriculum.getDurationMinutes(), inCurriculum.getSort(),
        inCurriculum.getTheme(), inCurriculum.getSource(), inCurriculum.getDurationMinutes(), inCurriculum.getSort()));
  }

  public List<Curriculum> selectByYear(int inYear)
  {
    return mSelect.execute(params(inYear));
  }

  public List<Curriculum> selectBetween(Date inStart, Date inEnd)
  {
    return mSelectR.execute(params(inStart, inEnd));
  }

  @Override
  public Db.Rows curriculumByYearAndMonth(int inYear, int inMonths)
  {
    return new Db.Rows(mSelectYM.execute(inYear, inMonths));
  }
}