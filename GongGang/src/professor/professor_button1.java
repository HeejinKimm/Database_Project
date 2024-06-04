package professor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mainFrame.mainGUI;


public class professor_button1 extends JFrame{

	private JPanel contentPane; //main JPanel ����
	
	String usage;            // ��� ����
    String seats;            // �¼� ��
    String cameraType;       // ī�޶� ����
    boolean outlet;          // �ܼ�Ʈ ����
    boolean projector;       // ��������Ʈ ����
    boolean reservation;     // ���� �ʿ� ����
    boolean recording;       // ��ȭ ���� ����
    boolean practicable;     // �ǽ� ���� ����
    Map<String, Boolean> timeDictionary = new HashMap<>(); // �ð� ���� ���� ������ ��
    private JTextArea infoArea; // ��� �������� �ؽ�Ʈ


    public professor_button1() {
    	
    	// main frame ���� 
        setTitle("GONG-GANG");
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setLocation(50, 50);
        
        // main JPanel ����
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);
        
        
        // �ΰ� ���̴� Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
        contentPane.add(logoPanel, BorderLayout.NORTH);
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));

        // GONG-GANG �ΰ� label ����
        JLabel logo = new JLabel("Gong-Gang");
        logo.setBackground(new Color(255, 255, 255));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo);

       
        // - ���ϴ� ���ǽ� ã�� - �ΰ� label ����
        JLabel userLabel = new JLabel("- \uC6D0\uD558\uB294 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("�������", Font.BOLD, 22));
        logoPanel.add(userLabel);

        // option & time table ���̴� mainPanel ����
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // options ��ư ���̴� Panel ���� 
        JPanel optionPanel = new JPanel();
        optionPanel.setBackground(new Color(255, 255, 255));
        mainPanel.add(optionPanel);
        optionPanel.setPreferredSize(new Dimension(500, 170));

        //comboBox ���̴� Panel
        JPanel dropdownsPanel = new JPanel();
        optionPanel.add(dropdownsPanel);
        dropdownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropdownsPanel.setBackground(new Color(255, 255, 255));
        dropdownsPanel.setSize(new Dimension(600, 200));
        dropdownsPanel.setLayout(new GridLayout(3, 2, 40, 10));

        //comboBox1 - ��������
        JLabel usageLabel = new JLabel("\uACF5\uAC04 \uC720\uD615   : ");
        usageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usageLabel.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(usageLabel);
        
        JComboBox usageComboBox = new JComboBox(new String[]{"����", "����", "���� ��"});
        usageComboBox.setBackground(new Color(255, 255, 255));
        usageComboBox.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(usageComboBox);
        usageComboBox.setPreferredSize(new Dimension(200, usageComboBox.getPreferredSize().height));

        
        //comboBox2 - �¼� ��
        JLabel seatsLabel = new JLabel("\uC88C\uC11D \uC218       : ");
        seatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        seatsLabel.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(seatsLabel);

        JComboBox seatsComboBox = new JComboBox(new String[]{"����", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"});
        seatsComboBox.setBackground(new Color(255, 255, 255));
        seatsComboBox.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(seatsComboBox);

        
        //comboBox2 - ī�޶�����
        JLabel cameraLabel = new JLabel("\uCE74\uBA54\uB77C \uC720\uD615 :");
        dropdownsPanel.add(cameraLabel);
        cameraLabel.setFont(new Font("�������", Font.BOLD, 14));

        JComboBox cameraComboBox = new JComboBox(new String[]{"����", "������ ī�޶�", "������ ī�޶�"});
        cameraComboBox.setBackground(new Color(255, 255, 255));
        cameraComboBox.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(cameraComboBox);
        
        
        //checkBox ���̴� Panel ����
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER,15,10));
        checkBoxPanel.setBackground(new Color(255, 255, 255));
        optionPanel.add(checkBoxPanel);

        //checkBox 1
        JCheckBox CheckBox1 = new JCheckBox("\uCF58\uC13C\uD2B8");
        CheckBox1.setBackground(new Color(255, 255, 255));
        checkBoxPanel.add(CheckBox1);
        CheckBox1.setFont(new Font("�������", Font.BOLD, 13));
        //checkBox 2
        JCheckBox CheckBox2 = new JCheckBox("\uBE54\uD504\uB85C\uC81D\uD2B8");
        checkBoxPanel.add(CheckBox2);
        CheckBox2.setBackground(new Color(255, 255, 255));
        CheckBox2.setFont(new Font("�������", Font.BOLD, 13));
        //checkBox 3
        JCheckBox CheckBox3 = new JCheckBox("\uC608\uC57D \uD544\uC694");
        checkBoxPanel.add(CheckBox3);
        CheckBox3.setBackground(new Color(255, 255, 255));
        CheckBox3.setFont(new Font("�������", Font.BOLD, 13));
        //checkBox 4
        JCheckBox CheckBox4 = new JCheckBox("\uB179\uD654 \uAC00\uB2A5");
        checkBoxPanel.add(CheckBox4);
        CheckBox4.setBackground(new Color(255, 255, 255));
        CheckBox4.setFont(new Font("�������", Font.BOLD, 13));
        //checkBox 5
        JCheckBox CheckBox5 = new JCheckBox("\uC2E4\uC2B5 \uAC00\uB2A5");
        checkBoxPanel.add(CheckBox5);
        CheckBox5.setBackground(new Color(255, 255, 255));
        CheckBox5.setFont(new Font("�������", Font.BOLD, 13));


     // �ð�ǥ �г� ����
        JPanel timePanel = new JPanel();
        timePanel.setBackground(new Color(255, 255, 255));
        timePanel.setLayout(new GridLayout(8, 5)); // �׸��� ���̾ƿ� ����
        timePanel.setBorder(BorderFactory.createTitledBorder("���ϴ� �ð� ã��")); // �׵θ� ����

        // �ð�ǥ �� �� üũ�ڽ� ����
        String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri" };
        String[] times = { "1", "2", "3", "4", "5", "6", "7", "8" };

        for (String time : times) {
            for (String day : days) {
                String name = day + time;
                timeDictionary.put(name, false); // �ʱⰪ ����
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.setBackground(new Color(255,255,255));
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeDictionary.put(name, checkBox.isSelected()); // üũ�ڽ� ���� ����
                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // �׵θ� ����
                timePanel.add(checkBox); // �ð�ǥ �гο� üũ�ڽ� �߰�
            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel); // ��ũ�� �г� ����
        mainPanel.add(scrollPane);
        scrollPane.setBackground(new Color(255,255,255));

        
        //resultButton & homeButton ���̴� Panel ����
        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(ButtonPanel, BorderLayout.SOUTH);
        ButtonPanel.setLayout(new BorderLayout(0, 0));

        //reuslt ��ư ���̴� Panel ���� 
        JPanel resultPanel = new JPanel(new FlowLayout());
        resultPanel.setBackground(new Color(255, 255, 255));
        ButtonPanel.add(resultPanel, BorderLayout.CENTER);
        //result ��ư ���� 
        JButton resultButton = new JButton("\uAC80\uC0C9");
        resultButton.setFont(new Font("�������", Font.BOLD, 16));
        resultButton.setBackground(new Color(255, 255, 255));
        resultPanel.add(resultButton);

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage = (String) usageComboBox.getSelectedItem();
                seats = (String) seatsComboBox.getSelectedItem();
                cameraType = (String) cameraComboBox.getSelectedItem();

                outlet = CheckBox1.isSelected();
                projector = CheckBox2.isSelected();
                reservation = CheckBox3.isSelected();
                recording = CheckBox4.isSelected();
                practicable = CheckBox5.isSelected();
                infoArea = new JTextArea(80, 40);

                searchClassroomInfo();

                // ���ο� â�� ���� ����� ������
                // �� â�� ���� ����
                JFrame newFrame = new JFrame("�˻��� ����");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setBackground(new Color(255, 255, 255));
                newFrame.setBounds(100, 100, 1100, 600);
                newFrame.setLocation(50, 50);

                newFrame.getContentPane().setForeground(new Color(255, 255, 255));
                newFrame.getContentPane().setLayout(new BorderLayout(10,10));

                infoArea.setEditable(false);
                newFrame.getContentPane().add(infoArea);
                newFrame.setVisible(true);

                // �ΰ� ���̴� Panel
                JPanel logoPanel = new JPanel();
                logoPanel.setBackground(new Color(255, 255, 255));
                logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
                newFrame.getContentPane().add(logoPanel, BorderLayout.NORTH);
                logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
                newFrame.getContentPane().setBackground(new Color(255,255,255));

                // GONG-GANG �ΰ� label ����
                JLabel logo = new JLabel("Gong-Gang");
                logo.setBackground(new Color(255, 255, 255));
                logo.setHorizontalAlignment(SwingConstants.CENTER);
                logo.setFont(new Font("Arial Black", Font.BOLD, 40));
                logoPanel.add(logo);

                JLabel userLabel = new JLabel("- ��� -");
                userLabel.setHorizontalAlignment(SwingConstants.CENTER);
                userLabel.setFont(new Font("�������", Font.BOLD, 22));
                logoPanel.add(userLabel);
            }
        });


        //homebuttom
        JPanel homePanel = new JPanel();
        homePanel.setBackground(new Color(255, 255, 255));
        JButton homeButton = new JButton("HOME");
        ButtonPanel.add(homePanel, BorderLayout.EAST);
        homePanel.setLayout(new BorderLayout(0, 0));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        homeButton.setFont(new Font("������� ExtraBold", Font.BOLD, 12));
        homePanel.add(homeButton, BorderLayout.SOUTH);

        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });

        // �����λ� �ʿ��� �κ�
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(255,255,255));
        ButtonPanel.add(emptyPanel, BorderLayout.WEST);
        emptyPanel.setPreferredSize(new Dimension(90,0));

    }

    private void searchClassroomInfo() {
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/DB2024Team05";
        final String USER = "DB2024Team05";
        final String PASS = "DB2024Team05";
        String message = "�˻��� ������ ��ȣ:\n";

        StringBuilder query = new StringBuilder("SELECT * FROM ClassroomView WHERE 1=1");

        String[] seatRange = seats.split("-");
        if (!seats.equals("����")) {
            query.append(" AND SeatCount BETWEEN ").append(Integer.parseInt(seatRange[0])).append(" AND ")
                    .append(Integer.parseInt(seatRange[1]));
        }
        if (!cameraType.equals("����")) {
            query.append(" AND CameraType = '").append(cameraType).append("'");
        }
        if (outlet)
            query.append(" AND OutletCount > 0");
        if (projector)
            query.append(" AND Projector = '�� ����'");
        if (reservation)
            query.append(" AND ReservationRequired = '���� �ʿ�'");
        if (recording)
            query.append(" AND RecordingAvailable = '����'");
        if (practicable)
            query.append(" AND Practicable = '�ǽ� ����'");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String roomNumber = rs.getString("Room_Number");
                String Room_name = rs.getString("Room_Name");
                String Location = rs.getString("Location");
                String availableTimes = getAvailableTimes(rs);
                if (!availableTimes.isEmpty()) {
                    message += roomNumber+" "+Room_name+" "+Location + " ���� �ð�: " + availableTimes + "\n";
                }
            }

            if (!message.equals("�˻��� ������ ��ȣ:\n")) {
                infoArea.setText(message);
            } else {
                infoArea.setText("���ϴ� ������ �����ϴ�. ������ �缱���ϼ���.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            infoArea.setText("�����͸� �ҷ����� �������� ������ �ֽ��ϴ�. �ٽ� Ȯ���ϼ���.");
        }
    }

    private String getAvailableTimes(ResultSet rs) throws SQLException {
        StringBuilder availableTimes = new StringBuilder();
        for (String time : timeDictionary.keySet()) {
            if (timeDictionary.get(time) && rs.getBoolean(time)) {
                availableTimes.append(time).append(" ");
            }
        }
        return availableTimes.toString().trim();
    }


}