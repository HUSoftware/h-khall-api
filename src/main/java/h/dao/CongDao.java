package h.dao;

import h.model.Db;

public interface CongDao
{
  Db.Rows select();

  Db.Rows select(String inNumber);
}