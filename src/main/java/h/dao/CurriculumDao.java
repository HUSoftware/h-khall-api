package h.dao;

import h.model.Db;
import h.model.khall.Curriculum;

public interface CurriculumDao
{
  Db.Rows curriculumByYearAndMonth(int inYear, int inMonths);

  int upsert(Curriculum inCurriculum);
}