package ge.tbc.testautomation.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSteps {

    public Object[][] getRegistrationData() {
        List<Object[]> dataList = new ArrayList<>();
        String query = "SELECT * FROM RegistrationData ORDER BY id;";

        try (Connection conn = MSSQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                dataList.add(row);
            }
        } catch (SQLException e) {
            System.out.println("!!!!!!!!!! DATABASE CONNECTION FAILED !!!!!!!!!!");
            e.printStackTrace();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            throw new RuntimeException("Failed to fetch registration data. See console for details.", e);
        }

        return dataList.toArray(new Object[0][]);
    }
}