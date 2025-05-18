package Main.MaintenancePage;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceAcceptPage extends JPanel {

	private JPanel listPanel;
	
    public MaintenanceAcceptPage() {
    
    	//上面的title
        JLabel titleLabel = new JLabel("接案系統", JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        

        //包住rowPanel(含有案件 跟 接受&刪除按鈕)
        //因為rowPanel會根據user新增 或 maintenance刪除 而增減，但listPanel是不變的!
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0, 1, 5, 10)); 

        //加個scrollPane，案件多的時候可以往下滑
        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    //讓user上傳的案件(會變動)顯示在MaintenanceAcceptPage的方法
    public void addCase(String caseName) {
        JPanel rowPanel = createCaseRow(caseName);
        listPanel.add(rowPanel);
        listPanel.revalidate();	//重新布局
        listPanel.repaint();	//重新呈現畫面
    }

    private JPanel createCaseRow(String caseName) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

        JLabel caseLabel = new JLabel(caseName);
        caseLabel.setPreferredSize(new Dimension(200, 30));
        caseLabel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));

        Button acceptButton = new Button("A"); 
        Button deleteButton = new Button("D"); 

      
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MaintenancePage.getCardLayout().show(MaintenancePage.getMainPanel(), "SchedulePage");
    
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Container parent = rowPanel.getParent();
                if (parent != null) {
                    parent.remove(rowPanel);	//移除 rowPanel
                    parent.revalidate();		//重新布局
                    parent.repaint();			//重新呈現畫面
                }
            }
        });

        rowPanel.add(caseLabel);
        rowPanel.add(acceptButton);
        rowPanel.add(deleteButton);

        return rowPanel;
    }
}
