// UI of cruds

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

class cUI {

	private Scanner scanner = new Scanner(System.in);
	private String table = "Item";
	private ResultSet records;
	private int maximumLengthOfName = 25;
	private ArrayList<String> fieldNames;
	private Statement statement;

	cUI(ArrayList<String> pFieldNames) {

		fieldNames = new ArrayList<>();

		fieldNames = pFieldNames;
		System.out.println(fieldNames);
	}

	Statement createStatement(Connection pConnection) {


		try {

			statement = pConnection.createStatement();
		} 

		catch (Exception error) {

			printErrorMessage(error);			
		}

		return statement;
	}


	void printErrorMessage(Exception pError) {

		System.out.println("Error: " + pError);
	}

	// ArrayList<String> getFieldNames(Connection pConnection) {

	// 	fieldNames = new ArrayList<>();

	// 	try {

	// 		DatabaseMetaData metaData = pConnection.getMetaData();


	// 		try (ResultSet columnsResultSet = metaData.getColumns(null, null, table, null)){

	// 			String columnName;

	// 			while (columnsResultSet.next()) {
					
	// 				columnName = columnsResultSet.getString("COLUMN_NAME");

	// 				if (fieldNames.contains(columnName)) {

	// 					break;
	// 				}
	// 				else {

	// 					fieldNames.add(columnName);
	// 				}
	// 			}
	// 		}
	// 		catch (Exception error) {
				
	// 			printErrorMessage(error);
	// 		}
	// 	}

	// 	catch (Exception error) {

	// 		printErrorMessage(error);
	// 	}
	// 	// System.out.println(fieldNames);
	// 	return fieldNames;
	// }

	String getFieldData(String fieldName) {

		String fieldData;

		while (true) {

			System.out.print("Enter " + fieldName + ": ");

			fieldData = scanner.nextLine().trim();

			if (fieldData != "") {

				return fieldData;
			}
			else {

				System.out.println(fieldName + " doesn't empty! Please enter a valid " + fieldName + ".");
			}
		}
	}
	
	String getRecordId() {

		return getFieldData(fieldNames.get(0));
	}

	ArrayList<String> getRecord() {

		// System.out.println("getRecord");
		ArrayList<String> recordData = new ArrayList<>();

		recordData.add(getRecordId());

		for (int index = 1; index < fieldNames.size(); index++) {

			recordData.add(getFieldData(fieldNames.get(index)));
		}

		return recordData;
	}

	// void executeProcess(String pRecordId, String processNameInV2Form, PreparedStatement pPreparedStatement) throws Exception {

	// 	if (getCountOfRowsAffected(pPreparedStatement) > 0 ) {

	// 		System.out.println(fieldNames.get(0) + " '" + pRecordId + "' " + processNameInV2Form + " successfully.");
	// 	}
				
	// 	else {

	// 		System.out.print("Error occured while updating.");
	// 	}
	// }
	
	// int getCountOfRowsAffected(PreparedStatement pPreparedStatement) throws Exception {

	// 	return pPreparedStatement.executeUpdate();
	// }

	// boolean isAnyRecordFound() {

	// 	boolean isAnyRecordFound = false;

	// 	try {

	// 		loadRecords();

	// 		if (records.next()) {

	// 			isAnyRecordFound = true;
	// 		}
			
	// 		else {
				
	// 			System.out.println("No records found!");
	// 		}
	// 	}
		
	// 	catch (Exception error) {

	// 		printErrorMessage(error);
	// 	}

	// 	return isAnyRecordFound;
	// }
	
	void loadRecords() {

		try {

			records = getResultSetOfQuery("SELECT * FROM " + table + ";");
		}
		
		catch (Exception error) {

			printErrorMessage(error);
		}	
	}

	ResultSet getResultSetOfQuery(String pQuery) {

		ResultSet resultSet;

		try {

			resultSet =	statement.executeQuery(pQuery);
			return resultSet;
		}
		catch (Exception error) {

			printErrorMessage(error);
			return null;
		}


	}
	
	void printPipeSymbol() {

		System.out.print("|");
	}

	void printPlusSymbol() {

		System.out.print("+");
	}

	void printTableHeader() {

		printTableLine();
		printRowValues(fieldNames);
	}

	void printRowValues(List<String> pRecordData) {

		printPipeSymbol();

		for (String rowValue : pRecordData)	{

			System.out.printf("%-25s", rowValue);
			printPipeSymbol();
		}

		printNewLine();
		printTableLine();
	}


	void printTableLine() {

		printPlusSymbol();

		for (int index = 0; index < fieldNames.size(); index++)	{

			System.out.print("-".repeat(maximumLengthOfName));

			printPlusSymbol();
		}

		printNewLine();
	}

	void printNewLine() {

		System.out.println();
	}
	
	void showRecords(ArrayList<List<String>> pRecords) {

		printTableHeader();

		for (List<String> recordData : pRecords) {

			printRowValues(recordData);
		}
		// try {

		// 	printTableHeader();

		// 	// while (pRecords.next()) {

		// 		// List<String> recordData = loadRecord(pRecords);
				
		// 		printRowValues(recordData);		
		// 	// }
		// }

		
		// catch (Exception error) {

		// 	printErrorMessage(error);
		// }

	}

	// List<String> loadRecord(ResultSet resultRecord) throws Exception {

	// 	List<String> recordData = new ArrayList<>();

	// 	try {

	// 		for (String fieldName : fieldNames) {

	// 			recordData.add(resultRecord.getString(fieldName));
	// 		}
	// 	}
		
	// 	catch (Exception error) {

	// 		printErrorMessage(error);
	// 	}
		
	// 	return recordData;
	// }

	// void showMenu(String pMenu, iCruds pOCruds) {

	// 	System.out.println(pMenu);
	// 	System.out.print("Enter your choice here: ");

	// 	int choice = scanner.nextInt();

	// 	switch (choice)
	// 	{
	// 		case 1: pOCruds.create();
	// 			break;

	// 		case 2: pOCruds.showAllRecords();
	// 			break;
				
	// 		// case 3: pOCruds.update();
	// 		// 	break;
				
	// 		// case 4: pOCruds.delete();
	// 		// 	break;
				
	// 		// case 5: pOCruds.search();
	// 		// 	break;
				
	// 		// case 6: pOCruds.sort();
	// 		// 	break;

	// 		default: System.out.println("Invalid choice!");
	// 			break;
				
	// 		case 0: System.exit(0);
	// 	}
	// }
}
