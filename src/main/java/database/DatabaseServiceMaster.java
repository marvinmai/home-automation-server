package database;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import database.DatabaseProperties.*;

@Service
public class DatabaseServiceMaster
{
	private Connection connection;

	HashMap<DB_Table, IDatabaseService> databaseServices = new HashMap<>();

	public DatabaseServiceMaster()
	{
		try
		{
			connection = IDatabaseService.connect();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// Generate all nessecary database-services
		IDatabaseService dbs;
		for(DB_Table t: DatabaseProperties.tables)
		{
			try
			{
				dbs = (IDatabaseService) t.getDBS_Class().newInstance();
				databaseServices.put(t, dbs);
			} catch (InstantiationException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void addData(HashMap<String, Object> dataMap, DB_Table DB_Table)
	{
		try
		{
			databaseServices.get(DB_Table).addData(dataMap);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}


}
