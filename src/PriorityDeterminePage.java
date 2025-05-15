import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class PriorityDeterminePage extends JFrame {
	private JTable table;
	private DefaultTableModel model;

	public PriorityDeterminePage() {
		setTitle("接案系統");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		String[] columns = { "地點名稱", "報修次數", "最後回報時間", "操作" };
		model = new DefaultTableModel(null, columns);
		table = new JTable(model);

		table.getColumn("操作").setCellRenderer(new ButtonRenderer());
		table.getColumn("操作").setCellEditor(new ButtonEditor(new JCheckBox()));

		loadData();

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		setVisible(true);
	}

	private void loadDate() {
		try (Connection conn = getConnection()) {
			String sql = """
					SELECT r.location_id, l.name AS location_name, COUNT(*) AS report_count, MAX(r.created_at) AS last_report_time
					FROM report r
					JOIN location l ON r.location_id = l.id
					WHERE r.status = 'pending'
					GROUP BY r.location_id
					ORDER BY report_count DESC, last_report_time DESC
					""";
			
			try(PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					Object[] row = {
							rs.getString("location_name");
							rs.getInt("report_count");
							rs.getTimestamp("last_report_time"),
					};
					model.addRow(row);
					}
				}
			
		} catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "資料載入失敗：" + e.getMessage());
        }
	}

}
