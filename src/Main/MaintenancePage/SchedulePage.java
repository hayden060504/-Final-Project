package Main.MaintenancePage;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class SchedulePage extends JPanel{
	
	private JPanel calendarPanel;
    private JLabel monthLabel;
    private LocalDate currentDate;
    
    //連結資料庫用
    private final String url = "jdbc:mysql://140.119.19.73:3315/TG09";
    private final String user = "TG09";
    private final String password = "hGykqi";

	public SchedulePage() {
		
		 setLayout(new BorderLayout());
		 
		 	//抓現在的日期
	        currentDate = LocalDate.now();

	        //上面(目前月份 + 上下個月按鈕)
	        JPanel headerPanel = new JPanel(new BorderLayout());
	        JButton preButton = new JButton("<");
	        JButton nextButton = new JButton(">");
	        monthLabel = new JLabel("", SwingConstants.CENTER); //讓字可以置中，這個很重要、、、
	        
	        //按上個月按鈕後更新月曆
	        preButton.addActionListener(e -> {
	            currentDate = currentDate.minusMonths(1);
	            updateCalendar();
	        });
	        
	        //按下個月按鈕後更新月曆
	        nextButton.addActionListener(e -> {
	            currentDate = currentDate.plusMonths(1);
	            updateCalendar();
	        });

	        headerPanel.add(preButton, BorderLayout.WEST);
	        headerPanel.add(monthLabel, BorderLayout.CENTER);
	        headerPanel.add(nextButton, BorderLayout.EAST);

	        add(headerPanel, BorderLayout.NORTH);

	        //月曆設定
	        calendarPanel = new JPanel(new GridLayout(0, 7));
	        add(calendarPanel, BorderLayout.CENTER);
	        
	        updateCalendar();
	        
	        //回上一頁的按鈕(右下角)
	        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        JButton backButton = new JButton("返回");
	        backButton.addActionListener(e -> {
	            MaintenancePage.getCardLayout().show(MaintenancePage.getMainPanel(), "MaintenancePage");
	        });
	        bottomPanel.add(backButton);
	        add(bottomPanel, BorderLayout.SOUTH); 
		}

		private void updateCalendar() {
			//先清空
		    calendarPanel.removeAll();
		    
		    //更新月份
		    String monthName = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
	        monthLabel.setText(monthName + " " + currentDate.getYear());

		    YearMonth yearMonth = YearMonth.from(currentDate);	//得年跟月
		    LocalDate firstOfMonth = yearMonth.atDay(1);	//找到第一天
		    int daysInMonth = yearMonth.lengthOfMonth();	//這個月有幾天
		    int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue();	//這個月第一天是星期幾

		    //最上面加入星期
		    String[] weekDays = {"日","一", "二", "三", "四", "五", "六"};
		    for (String dayName : weekDays) {
		        JLabel label = new JLabel(dayName, SwingConstants.CENTER);
		        label.setFont(new Font("Microsoft JhengHei", Font.BOLD, 12));
		        calendarPanel.add(label);
		    }

		    //加空白對齊第一天，對齊真的真的很重要、、
		    int offset = (firstDayOfWeek % 7);
		    for (int i = 0; i < offset; i++) {
		        calendarPanel.add(new JLabel(""));
		    }

		    //加入那個月每一天的按鈕
		    for (int day = 1; day <= daysInMonth; day++) {
		        LocalDate date = yearMonth.atDay(day);
		        JButton dayButton = new JButton(String.valueOf(day));
		        dayButton.addActionListener(e -> showCaseDetail(date));
		        calendarPanel.add(dayButton);
		    }
		    
		    //更新整個月曆
		    calendarPanel.revalidate();
		    calendarPanel.repaint();
		}

		//案件的詳細內容
		private void showCaseDetail(LocalDate date) {
		    try (Connection conn = DriverManager.getConnection(url, user, password)) {	//連結資料庫
		        String sql = """
		        		SELECT reports.description_place, reports.description_situation,
		        		location.name AS location_name, categories.name AS category_name
		        		FROM accepted_cases
		        		JOIN reports ON accepted_cases.report_id = reports.id
		        		JOIN location ON reports.location_id = location.id
		        		JOIN categories ON reports.category_id = categories.id
		        		WHERE DATE(accepted_cases.accepted_at) = ?
		        """;

		        PreparedStatement stmt = conn.prepareStatement(sql);
		        stmt.setDate(1, java.sql.Date.valueOf(date));
		        ResultSet rs = stmt.executeQuery();

		        if (rs.next()) {	//有找到案件的話
		            String place = rs.getString("description_place");
		            String situation = rs.getString("description_situation");
		            String location = rs.getString("location_name");
		            String category = rs.getString("category_name");
		            
		            //顯示案件內容
		            String message = String.format("""
		                日期: %s
		                報修類別: %s
		                地點: %s - %s
		                狀況描述: %s
		            """, date, category, location, place, situation);
		            JOptionPane.showMessageDialog(this, message, "案件資訊", JOptionPane.INFORMATION_MESSAGE);
		            
		            int option = JOptionPane.showConfirmDialog(this, message + "\n\n是否要回報進度？", "案件資訊", JOptionPane.YES_NO_OPTION);

		            if (option == JOptionPane.YES_OPTION) {
		                showStatusUpdateDialog(date);	//用來改維修進度
		            }
		        } else {	//沒找到案件的話
		            JOptionPane.showMessageDialog(this, "這天沒有案件資料！", "查無資料", JOptionPane.INFORMATION_MESSAGE);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		//更新維修進度
		private void showStatusUpdateDialog(LocalDate date) {
		    JCheckBox notStarted = new JCheckBox("尚未維修");
		    JCheckBox inProgress = new JCheckBox("維修中");
		    JCheckBox completed = new JCheckBox("維修完成");

		    ButtonGroup group = new ButtonGroup();
		    group.add(notStarted);
		    group.add(inProgress);
		    group.add(completed);

		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		    panel.add(new JLabel("請選擇維修狀態："));
		    panel.add(notStarted);
		    panel.add(inProgress);
		    panel.add(completed);

		    int result = JOptionPane.showConfirmDialog(this, panel, "回報進度", JOptionPane.OK_CANCEL_OPTION);

		    if (result == JOptionPane.OK_OPTION) {
		        String status = null;
		        if (notStarted.isSelected()) {
		            status = "尚未維修";
		        } else if (inProgress.isSelected()) {
		            status = "維修中";
		        } else if (completed.isSelected()) {
		            status = "維修完成";
		        }

		        if (status != null) {
		            try (Connection conn = DriverManager.getConnection(url, user, password)) {
		                String sql = """
		                    UPDATE reports 
		                    SET status = ?
		                    WHERE id = (
		                        SELECT report_id 
		                        FROM accepted_cases 
		                        WHERE DATE(accepted_at) = ?
		                        LIMIT 1
		                    )
		                """;
		                PreparedStatement stmt = conn.prepareStatement(sql);
		                stmt.setString(1, status);
		                stmt.setDate(2, java.sql.Date.valueOf(date));
		                int rows = stmt.executeUpdate();

		                if (rows > 0) {
		                    JOptionPane.showMessageDialog(this, "進度已更新為：" + status);
		                } else {
		                    JOptionPane.showMessageDialog(this, "找不到案件或更新失敗。");
		                }
		            } catch (SQLException e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(this, "資料庫錯誤：" + e.getMessage());
		            }
		        }
		    }
		}
	}
