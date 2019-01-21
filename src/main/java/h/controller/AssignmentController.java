package h.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import h.dao.ScheduleDao;
import h.model.khall.Assignment;
import io.swagger.annotations.Api;

@RestController
@Api(produces = "application/json", tags = "Assignment", description = "API")
@RequestMapping("/assignment")
public class AssignmentController
{
  @Autowired
  private ScheduleDao mDao;

  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void updateAssignment(@RequestBody Assignment inAssignment)
  {
    mDao.upsert(inAssignment);
  }
}