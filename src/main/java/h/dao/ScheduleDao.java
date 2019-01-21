package h.dao;

import h.model.Db;
import h.model.khall.Assignment;
import h.model.khall.Schedule;

public interface ScheduleDao
{
  Db.Rows scheduleByYearAndMonth(long inCongId, int inYear, int inMonth);

  int upsert(Schedule inSchedule);

  int upsert(Assignment inAssignment);
}