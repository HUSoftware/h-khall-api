package h.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import h.dao.CurriculumDao;
import h.model.Db;
import h.model.khall.Curriculum;
import io.swagger.annotations.Api;

@RestController
@Api(produces = "application/json", tags = "Curriculum", description = "API")
@RequestMapping("/curriculum")
public class CurriculumController
{
  @Autowired
  private CurriculumDao mDao;

  @GetMapping("/year/{year}/month/{month}")
  public Db.Rows selectByYearAndMonth(@PathVariable("year") int inYear, @PathVariable("month") int inMonth)
  {
    return mDao.curriculumByYearAndMonth(inYear, inMonth);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createCurriculum(@RequestBody Curriculum inCurriculum)
  {
    mDao.upsert(inCurriculum);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void updateCurriculum(@RequestBody Curriculum inCurriculum)
  {
    mDao.upsert(inCurriculum);
  }
}