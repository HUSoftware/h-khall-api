package h.dao.jdbc.sql;

import static h.dao.jdbc.DbUtil.newQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Component;

import h.dao.ScheduleDao;
import h.model.Db;
import h.model.khall.Assignment;
import h.model.khall.Hall;
import h.model.khall.Part;
import h.model.khall.Schedule;
import h.model.khall.StudyPoint;

@Component
public class ScheduleSql extends AbstractSql implements ScheduleDao
{
  private final SqlUpdate mUpsertYM;
  private final SqlUpdate mUpsertYMP;

  private final SqlUpdate mUpdate;

  private final MappingSqlQuery<Assignment> mSelectY;
  private final MappingSqlQuery<Assignment> mSelectYM;

  private final MappingSqlQuery<Db.Row> mSelectByYM;

  public ScheduleSql(DataSource inDataSource)
  {
    mStmts = getStatementsJson("Schedule.json");

    Statement upsertYM = mStmts.getStatement("UPSERT_YM");
    mUpsertYM = newSqlUpdate(inDataSource, upsertYM.getSql(), upsertYM.types());

    Statement upsertYMP = mStmts.getStatement("UPSERT_YMP");
    mUpsertYMP = newSqlUpdate(inDataSource, upsertYMP.getSql(), upsertYMP.types());

    Statement update = mStmts.getStatement("UPDATE");
    mUpdate = newSqlUpdate(inDataSource, update.getSql(), update.types());

    Statement selectY = mStmts.getStatement("SELECT_Y");
    mSelectY = new MappingSql<Assignment>(inDataSource, selectY.getSql(), selectY.types())
    {
      @Override
      public Assignment mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return Mapping.mapSchedule(inRs, "c", "s");
      }
    };

    Statement selectYM = mStmts.getStatement("SELECT_YM");
    mSelectYM = new MappingSql<Assignment>(inDataSource, selectYM.getSql(), selectYM.types())
    {
      @Override
      public Assignment mapRow(ResultSet inRs, int inRowNum) throws SQLException
      {
        return Mapping.mapSchedule(inRs, "c", "s");
      }
    };

    mSelectByYM = newQuery(inDataSource, selectYM.getSql(), selectYM.types());
  }

  public int upsert(long inCongId, Hall inSchool, int inYear, int inMonth)
  {
    return mUpsertYM.update(params(inSchool, inCongId, inYear, inMonth));
  }

  public int upsert(long inCongId, Hall inSchool, int inYear, int inMonth, Part inPart)
  {
    return mUpsertYMP.update(params(inSchool, inCongId, inYear, inMonth, inPart.name()));
  }

  public List<Assignment> select(long inCongregation, int inYear)
  {
    return mSelectY.execute(params(inCongregation, inYear));
  }

  public List<Assignment> select(long inCongregation, int inYear, int inMonth)
  {
    return mSelectYM.execute(params(inCongregation, inYear, inMonth));
  }

  public int update(Assignment inAssignment)
  {
    return update(inAssignment.getParticipantId(), inAssignment.getAssistantId(), inAssignment.getStudyPoint(), inAssignment.getId());
  }

  public int update(Long inParticipantId, Long inAssistantId, StudyPoint inStudyPoint, Long inId)
  {
    return mUpdate.update(params(inParticipantId, inAssistantId, StudyPoint.get(inStudyPoint), inId));
  }

  @Override
  public Db.Rows scheduleByYearAndMonth(long inCongId, int inYear, int inMonth)
  {
    return new Db.Rows(mSelectByYM.execute(inCongId, inYear, inMonth));
  }

  @Override
  public int upsert(Schedule inSchedule)
  {
    return 0;
  }

  @Override
  public int upsert(Assignment inAssignment)
  {
    return update(inAssignment);
  }
}