package h.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Db
{
  public static class Rows extends ArrayList<Db.Row>
  {
    public Rows()
    {
    }

    public Rows(Collection<Db.Row> inRows)
    {
      for (Row value : inRows)
      {
        add(value);
      }
    }
  }

  public static class Row extends HashMap<String, Object>
  {
    public Row()
    {
    }

    public Row(int inSize)
    {
      super(inSize);
    }
  }
}