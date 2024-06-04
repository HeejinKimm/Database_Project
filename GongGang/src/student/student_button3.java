package student;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;


import professor.professor_button2;
import mainFrame.mainGUI;

public class student_button3 extends JFrame{

    private JPanel contentPane;
    private JTextField nameField;
    private JTextArea infoArea;
    private JTable lectureTable;
    private Connection connection;


    public student_button3() {
        setTitle("GONG-GANG");
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setLocation(50, 50);
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

        JLabel userLabel = new JLabel("- \uAD50\uC218\uB2D8 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("�������", Font.BOLD, 22));
        logoPanel.add(userLabel);

        //inputPanel & Info display area ���̴� mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255,255,255));
        mainPanel.setPreferredSize(new Dimension(700,400));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(0, 1, 0, 0));

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        mainPanel.add(inputPanel);

        JLabel nameLabel = new JLabel("������ �̸��� �Է��ϼ���");
        nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setPreferredSize(new Dimension(1050,100));
        nameLabel.setFont(new Font("������� ExtraBold", Font.PLAIN, 20));
        nameField = new JTextField(10);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 12));
        searchButton.setBackground(new Color(255, 255, 255));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameField.getText().trim().isEmpty()) {
                    connectToDatabase(); // �����ͺ��̽� ����
                    searchProfessorInfo(nameField.getText().trim());
                    searchProfessor();
                } else {
                    JOptionPane.showMessageDialog(null, "������ �̸��� �Է��ϼ���.");
                }
            }
        });
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(searchButton);



        //output Panel
        JPanel outPanel = new JPanel();
        outPanel.setBackground(new Color(255,255,255));
        outPanel.setPreferredSize(new Dimension(700,400));
        mainPanel.add(outPanel, BorderLayout.SOUTH);
        outPanel.setLayout(new GridLayout(0, 2, 0, 0));

        // Info display area
        infoArea = new JTextArea(10, 10);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        outPanel.add(scrollPane);

        // ��� ���� (���� ���̺�)
        String[] columnNames = { "", "��", "ȭ", "��", "��", "��" }; // ���̺� �÷� �̸� ����
        String[][] data = new String[9][6]; // ���̺� ������ �迭 ����, 9���� ����
        for (int i = 0; i < 9; i++) {
            data[i][0] = String.valueOf(i + 1); // ���� ��ȣ ����
        }
        lectureTable = new JTable(data, columnNames); // ���̺� ����
        lectureTable.setFont(new Font("���� ���", Font.PLAIN, 20)); // ��Ʈ ����
        lectureTable.setRowHeight(30);
        // �� ���� ����
        JScrollPane scrollPane_Time = new JScrollPane(lectureTable); // ��ũ�� �г� ����
        outPanel.add(scrollPane_Time); // ���� �гο� �߰�



        //home button
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(50,100));
        westPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(50,100));
        eastPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(eastPanel, BorderLayout.EAST);



        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });




    }

    private void searchProfessorInfo(String name) {

        //JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        //Database credentials
// MySQL ������ ��ȣ �Է�
        final String user = "root";
        final String password = "mint1241";

        String query = "SELECT Email, Lab_Location, Phone FROM DB2024_Professor WHERE Name = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("Email");
                String Lab_Location = rs.getString("Lab_Location");
                String Phone = rs.getString("Phone");
                infoArea.setText("Email: " + email + "\nLab_Location: " + Lab_Location + "\nPhone Number: " + Phone);
            } else {
                infoArea.setText("No information found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            infoArea.setText("Error retrieving data.");
        }
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // MySQL ����̹� �ε�
            connection = DriverManager.getConnection("jdbc:mysql://localhost/DB2024Team05", "root", "4542"); // �����ͺ��̽� ����
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }
    private void searchProfessor() {
        String professorName =nameField.getText().trim(); // �˻��� ��������
        if (professorName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "������ �̸��� �Է��ϼ���.", "���", JOptionPane.WARNING_MESSAGE); // �˻��� ���� �� ��� �޽���
            return;
        }

        try {
            ProfessorInfo professorInfo = getProfessorInfo(professorName); // ���� ���� ��������
            if (professorInfo != null) {

                updateLectureTable(professorName); // ���̺� ������Ʈ
            } else {
                JOptionPane.showMessageDialog(this, "�ش� �������� ������ ã�� �� �����ϴ�.", "���", JOptionPane.WARNING_MESSAGE); // ����

            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL ���� �߻� �� ���� Ʈ���̽� ���
        }
    }

    private ProfessorInfo getProfessorInfo(String professorName) throws SQLException {
        ProfessorInfo professorInfo = null;

        String professorQuery = "SELECT Name, Lab_Location, Phone, Email FROM DB2024_Professor WHERE Name = ?"; // ���� ����
        // ����
        PreparedStatement professorStmt = connection.prepareStatement(professorQuery); // ���� �غ�
        professorStmt.setString(1, professorName); // �˻��� ����
        ResultSet professorRs = professorStmt.executeQuery(); // ���� ����

        if (professorRs.next()) {
            professorInfo = new ProfessorInfo();
            professorInfo.setName(professorRs.getString("Name")); // �̸� ����
        }

        return professorInfo; // ���� ���� ��ȯ
    }

    private void updateLectureTable(String professorName) {
        // ���̺� �ʱ�ȭ

        for (int i = 0; i < 9; i++) {
            for (int j = 1; j < 6; j++) {

                lectureTable.setValueAt("", i, j);
                // ���̺� �� �ʱ�ȭ
            }
        }

        try {
            String lectureQuery = "SELECT Room_Number, Lecture_Time1, Lecture_Time2 FROM DB2024_Lecture WHERE Professor_Name = ?";
            PreparedStatement lectureStmt = connection.prepareStatement(lectureQuery);
            lectureStmt.setString(1, professorName);
            ResultSet lectureRs = lectureStmt.executeQuery();

            while (lectureRs.next()) {
                String RoomNumber = lectureRs.getString("Room_Number");


                String lectureTime1 = lectureRs.getString("Lecture_Time1");
                String lectureTime2 = lectureRs.getString("Lecture_Time2");

                int dayIndex = getDayIndex(lectureTime1);
                if (dayIndex != -1) {
                    int period = Integer.parseInt(lectureTime1.substring(1)) - 1;
                    lectureTable.setValueAt(RoomNumber, period, dayIndex);
                }

                if (lectureTime2 != null) {
                    dayIndex = getDayIndex(lectureTime2);
                    if (dayIndex != -1) {
                        int period = Integer.parseInt(lectureTime2.substring(1)) - 1;
                        lectureTable.setValueAt(RoomNumber, period, dayIndex);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ���� ���ϰ� ���� ���� ǥ��
        highlightCurrentDayAndPeriod();
    }

    private int getDayIndex(String time) {
        switch (time.charAt(0)) {
            case '��':
                return 1;
            case 'ȭ':
                return 2;
            case '��':
                return 3;
            case '��':
                return 4;
            case '��':
                return 5;
            default:
                return -1;
        }
    }

    private void highlightCurrentDayAndPeriod() {
        Calendar calendar = Calendar.getInstance(); // ���� ��¥�� �ð��� �������� Ķ���� �ν��Ͻ� ����
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // ���� ���� ��������
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // ���� �ð� ��������

        int currentDayIndex = -1; // ���� ���� �ε��� �ʱ�ȭ
        switch (dayOfWeek) { // ���Ͽ� ���� �ε��� ����
            case Calendar.MONDAY:
                currentDayIndex = 1;
                break;
            case Calendar.TUESDAY:
                currentDayIndex = 2;
                break;
            case Calendar.WEDNESDAY:
                currentDayIndex = 3;
                break;
            case Calendar.THURSDAY:
                currentDayIndex = 4;
                break;
            case Calendar.FRIDAY:
                currentDayIndex = 5;
                break;
        }

        int currentPeriod = -1; // ���� ���� �ε��� �ʱ�ȭ
        if (hourOfDay >= 8 && hourOfDay < 21) { // 8�ú��� 21�ñ��� �ð� ���� Ȯ��
            currentPeriod = (int) Math.floor((hourOfDay - 8) / 1.5); // 8�ú��� 1.5�ð� ���� ���� ���
        }

        // ���ο� ������ ����
        for (int i = 0; i < lectureTable.getRowCount(); i++) {
            for (int j = 0; j < lectureTable.getColumnCount(); j++) {
                lectureTable.getColumnModel().getColumn(j).setCellRenderer(new DefaultTableCellRenderer()); // �⺻ �� ��������
                // �ʱ�ȭ
            }
        }

        if (currentDayIndex != -1) { // ���� ������ ��ȿ�� ���
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.PINK, -1)); // ���� ���� �÷� ����
        }
        if (currentPeriod != -1 && currentDayIndex != -1) { // ���� ���ÿ� ������ ��ȿ�� ���
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.PINK, currentPeriod));
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.RED, currentPeriod, currentDayIndex)); // ���� ���ÿ� ���� �� ����
        }

        if (currentDayIndex != -1 && currentPeriod != -1) {
            for (int i = 0; i < lectureTable.getColumnCount(); i++) {
                if (i == currentDayIndex) {
                    lectureTable.getColumnModel().getColumn(i).setCellRenderer(new CustomRenderer(Color.RED, currentPeriod));
                } else {
                    lectureTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer());
                }
            }
        }
        lectureTable.repaint();

        lectureTable.repaint(); // ���̺� �ٽ� �׸���
    }

    public class CustomRenderer extends DefaultTableCellRenderer {
        private Color backgroundColor; // ����
        private int highlightedRow = -1; // ������ ��
        private int highlightedColumn = -1; // ������ ��

        public CustomRenderer(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public CustomRenderer(Color backgroundColor, int highlightedRow) {
            this.backgroundColor = backgroundColor;
            this.highlightedRow = highlightedRow;
        }

        public CustomRenderer(Color backgroundColor, int highlightedRow, int highlightedColumn) {
            this.backgroundColor = backgroundColor;
            this.highlightedRow = highlightedRow;
            this.highlightedColumn = highlightedColumn;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ((highlightedRow == -1 || row == highlightedRow)
                    && (highlightedColumn == -1 || column == highlightedColumn)) {
                c.setBackground(backgroundColor); // ������ ��� ���� �� ���� ����
            } else if (column == highlightedColumn) {
                c.setBackground(Color.PINK); // ������ ���� �� ���� ����
            } else {
                c.setBackground(Color.WHITE); // �⺻ �� ���� ����
            }
            return c;
        }



    }
    class ProfessorInfo {
        private String name; // ���� �̸�

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Lecture {
        private String lectureName; // ���� �̸�
        private String lectureTime1; // ���� �ð�1
        private String lectureTime2; // ���� �ð�2

        public String getLectureName() {
            return lectureName;
        }

        public void setLectureName(String lectureName) {
            this.lectureName = lectureName;
        }

        public String getLectureTime1() {
            return lectureTime1;
        }

        public void setLectureTime1(String lectureTime1) {
            this.lectureTime1 = lectureTime1;
        }

        public String getLectureTime2() {
            return lectureTime2;
        }

        public void setLectureTime2(String lectureTime2) {
            this.lectureTime2 = lectureTime2;
        }
    }

}