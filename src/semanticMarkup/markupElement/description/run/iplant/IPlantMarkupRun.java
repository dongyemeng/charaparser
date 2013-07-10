package semanticMarkup.markupElement.description.run.iplant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import semanticMarkup.log.LogLevel;
import semanticMarkup.markupElement.description.markup.IDescriptionMarkupCreator;
import semanticMarkup.run.AbstractRun;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class IPlantMarkupRun extends AbstractRun {

	private IDescriptionMarkupCreator creator;
	private String databaseHost;
	private String databasePort;
	private String databaseName;
	private String databaseUser;
	private String databasePassword;
	private String databasePrefix;

	@Inject
	public IPlantMarkupRun(@Named("GuiceModuleFile")String guiceModuleFile,
			@Named("Run_OutDirectory")String runOutDirectory, 
			@Named("DescriptionMarkupCreator") IDescriptionMarkupCreator creator, 
			@Named("DatabaseHost") String databaseHost,
			@Named("DatabasePort") String databasePort,
			@Named("DatabaseName") String databaseName,
			@Named("DatabaseUser") String databaseUser, 
			@Named("DatabasePassword") String databasePassword,
			@Named("DatabasePrefix") String databasePrefix) {
		super(guiceModuleFile, runOutDirectory);
		this.creator = creator;
		this.databaseHost = databaseHost;
		this.databasePort = databasePort;
		this.databaseName = databaseName;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		this.databasePrefix = databasePrefix;
	}

	@Override
	protected void doRun() throws Exception {
		if(!isValidRun()) {
			log(LogLevel.ERROR, "Not a valid run. Stop.");
			return;
		}
		
		log(LogLevel.INFO, "Creating markup using " + creator.getDescription() + "...");
		creator.create();
	}
	
	private boolean isValidRun() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://" + databaseHost + ":" + databasePort +"/" + databaseName + "?connecttimeout=0&sockettimeout=0&autoreconnect=true", 
				databaseUser, databasePassword);
		
		String sql = "CREATE TABLE IF NOT EXISTS datasetprefixes (prefix varchar(100) NOT NULL, oto_uploadid int(11) NOT NULL DEFAULT '-1', " +
				"PRIMARY KEY (prefix))";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.execute();
		
		sql = "SELECT * FROM datasetprefixes WHERE prefix = ? AND oto_uploadid != -1";
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, databasePrefix);
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getResultSet();
		if(resultSet.next()) {
			return true;
		}
		return false;
	}	
}