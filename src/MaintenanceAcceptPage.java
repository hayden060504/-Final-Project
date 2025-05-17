import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenanceAcceptPage extends Panel {

    public MaintenanceAcceptPage() {
        setLayout(new BorderLayout());

   
        JLabel titleLabel = new JLabel("接案系統", JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

       
        Panel listPanel = new Panel();
        listPanel.setLayout(new GridLayout(0, 1, 5, 10)); 

       
        String[] cases = {"教室冷氣異常", "電燈不亮", "門把壞了", "投影機壞掉"};

        for (String caseItem : cases) {
            listPanel.add(createCaseRow(caseItem));
        }

        add(listPanel, BorderLayout.CENTER);
    }

    private Panel createCaseRow(String caseName) {
        Panel rowPanel = new Panel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

        Label caseLabel = new Label(caseName);
        caseLabel.setPreferredSize(new Dimension(200, 30));

        Button acceptButton = new Button("A"); 
        Button deleteButton = new Button("D"); 

      
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("✅ 接受了案件：" + caseName);
    
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("❌ 刪除了案件：" + caseName);
                rowPanel.setVisible(false); 
            }
        });

        rowPanel.add(caseLabel);
        rowPanel.add(acceptButton);
        rowPanel.add(deleteButton);

        return rowPanel;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("維修接案系統");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MaintenanceAcceptPage page = new MaintenanceAcceptPage();
        frame.add(page);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
