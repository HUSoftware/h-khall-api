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

import h.dao.ScheduleDao;
import h.model.Db;
import h.model.khall.Schedule;
import io.swagger.annotations.Api;

@RestController
@Api(produces = "application/json", tags = "Schedule", description = "API")
@RequestMapping("/schedule")
public class ScheduleController
{
  @Autowired
  private ScheduleDao mDao;

  @GetMapping("/congid/{congid}/year/{year}/month/{month}")
  public Db.Rows selectByYearAndMonth(@PathVariable("congid") long inCongId, @PathVariable("year") int inYear, @PathVariable("month") int inMonth)
  {
    return mDao.scheduleByYearAndMonth(inCongId, inYear, inMonth);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createSchedule(@RequestBody Schedule inSchedule)
  {
    mDao.upsert(inSchedule);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void updateSchedule(@RequestBody Schedule inSchedule)
  {
    mDao.upsert(inSchedule);
  }
}