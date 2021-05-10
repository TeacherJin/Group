package cn.edu.vk.group.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class MainFrame extends JFrame {
    //选择文件部分的组件
    private JLabel fileLabel;//文件选择标签
    private JTextField fileTField;//文件选择文本框
    private JFileChooser fileChooser;//文件选择器
    private JButton fileButton;//文件选择按钮
    private JPanel filePanel;//存放以上组件的面板

    //分组方式设置部分的组件
    private JLabel numberLabel;//每组人数标签
    private JTextField numberTField;//输入每组人数的文本框
    private JLabel methonLabel;//分组方法标签
    private JLabel randomLabel;//标签，随机选择
    private JLabel manualLabel;//标签，手工设置
    private JRadioButton randomRB;//随机，单选框
    private JRadioButton manualRB;//手工，单选框
    private ButtonGroup bgMethod;//按钮组，存放两个单选框
    private boolean isRandom;//是否选择了随机分组方式，随机为true，否则为false
    private JLabel ALabel;//A等学生，标签和文本框
    private JTextField ATField;
    private JLabel BLabel;//B等学生，标签和文本框
    private JTextField BTField;
    private JLabel CLabel;//C等学生，标签和文本框
    private JTextField CTField;
    private JButton groupBtn;//分组按钮
    private JPanel groupPanel;//存放每组人数、分组方法
    private JPanel manualPanel;//存放ABC三组的手工设置的标准数值
    private JPanel GPanel;//存放groupPanel 和 manualPanel两个面板
    private JTextArea resultTArea;//存放分组结果的文本域
    private JScrollPane resultPanel;//存放文本域的面板

    //保存学生名单的List
    LinkedList<String> roll;


    public MainFrame() {
        //外观设置
        setSize(600, 500);//设置大小
        setLocationRelativeTo(null);//启动时在屏幕中间
        setTitle("学生分组");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭按钮
        //UI风格与系统保持一致
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //设置布局管理器为BorderLayout
        setLayout(new BorderLayout());

        //文件选择部分
        //初始化组件
        filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        fileLabel = new JLabel("请选择存储学生信息的文件");
        fileTField = new JTextField(40);
        fileButton = new JButton("选择文件");
        //将组件添加进panel
        filePanel.add(fileLabel);
        filePanel.add(fileTField);
        filePanel.add(fileButton);

        //分组方式设置部分
        //每组人数
        numberLabel = new JLabel("每组人数：");
        numberTField = new JTextField(10);
        numberTField.setText("5");
        methonLabel = new JLabel("分组方法：");
        //分组方法，随机还是手工设置
        randomLabel = new JLabel("完全随机");
        manualLabel = new JLabel("手工设置");
        randomRB = new JRadioButton();
        manualRB = new JRadioButton();
        bgMethod = new ButtonGroup();
        bgMethod.add(randomRB);
        bgMethod.add(manualRB);
        //手工设置时，每类学生的比例
        ALabel = new JLabel("A类学生人数：");
        ATField = new JTextField(10);
        ATField.setText("2");//默认每组2人
        BLabel = new JLabel("B类学生人数：");
        BTField = new JTextField(10);
        BTField.setText("2");//默认每组2人
        CLabel = new JLabel("C类学生人数：");
        CTField = new JTextField(10);
        CTField.setText("1");//默认每组1人
        groupBtn = new JButton("开始分组");
        groupPanel = new JPanel();
        manualPanel = new JPanel();
        GPanel = new JPanel(new BorderLayout());

        //分组设置部分panel设置
        groupPanel.add(numberLabel);
        groupPanel.add(numberTField);
        groupPanel.add(methonLabel);
        groupPanel.add(randomRB);
        groupPanel.add(randomLabel);
        groupPanel.add(manualRB);
        groupPanel.add(manualLabel);
        groupPanel.add(groupBtn);
        //学生分类标准划分
        manualPanel.add(ALabel);
        manualPanel.add(ATField);
        manualPanel.add(BLabel);
        manualPanel.add(BTField);
        manualPanel.add(CLabel);
        manualPanel.add(CTField);
        //groupPanel和manualPanel添加到Gpanel中
        GPanel.add(groupPanel, BorderLayout.PAGE_START);
        GPanel.add(manualPanel, BorderLayout.CENTER);

        //结果显示
        resultTArea = new JTextArea(18, 60);
        resultTArea.setText("分组结果。\n" +
                "1、打开记事本\n" +
                "2、将学生名单存于记事本中，每行一个，然后保存为文本文件。建议按学生成绩降序排列\n" +
                "3、单击“选择文件”按钮，选中刚才保存有学生名单的文本文件\n" +
                "4、填写每组人数\n" +
                "5、选择分组方式，完全随机或手工设置。如选择手工，需要设定每类学生人数\n" +
                "6、注意，各类学生人数之和应为每组人数\n" +
                "7、A类学生:成绩较好的学生，B类:成绩一般的，C类:后进学生\n" +
                "8、设置完毕后，单击开始分组按钮即可进行分组");
        resultPanel = new JScrollPane(resultTArea);

        //将组件添加进窗体
        add(filePanel, BorderLayout.PAGE_START);
        add(GPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.PAGE_END);

        //各个组件的事件处理

        fileChooser = new JFileChooser();//初始化文件选择器
        fileChooser.setDialogTitle("请选择保存学生姓名的文件");
        roll = new LinkedList<String>();//初始化保存名单的List
        //打开文件按钮的动作
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fileRes = fileChooser.showOpenDialog(null);//打开文件选择窗口
                roll.clear();
                if (fileRes == JFileChooser.APPROVE_OPTION) {
                    File list = fileChooser.getSelectedFile();//获取选中的文件对象
                    fileTField.setText(list.getPath());//将文件路径显示在文本框中
                    try {
                        //判断文件的编码格式
                        String fileCharsetName =getFileCharsetName(list.getPath());
                        resultTArea.setText(fileCharsetName);

                        //读取文件中的内容，写入列表roll
                        /*InputStream is = new FileInputStream(list.getPath());
                        InputStreamReader isr = new InputStreamReader(is, fileCharsetName);
                        BufferedReader reader = new BufferedReader(isr);*/
                        BufferedReader reader = new BufferedReader(new FileReader(list));
                        //StringBuilder content = new StringBuilder();
                        String temp;
                        while ((temp = reader.readLine()) != null) {
                            roll.add(temp);
                        }
                        reader.close();

                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }
        });

        //分组方式的单选按钮的事件处理
        //监听器
        ActionListener methodListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (randomRB.isSelected()) {
                    isRandom = true;
                    ATField.setEditable(false);
                    BTField.setEditable(false);
                    CTField.setEditable(false);
                }
                if (manualRB.isSelected()) {
                    isRandom = false;
                    ATField.setEditable(true);
                    BTField.setEditable(true);
                    CTField.setEditable(true);
                }
            }
        };
        //为单选按钮添加监听器
        randomRB.addActionListener(methodListener);
        manualRB.addActionListener(methodListener);
        manualRB.setSelected(true);
        isRandom = false;


        //分组按钮的事件处理
        groupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number = Integer.parseInt(numberTField.getText());
                int a = Integer.parseInt(ATField.getText());
                int b = Integer.parseInt(BTField.getText());
                int c = Integer.parseInt(CTField.getText());
                Random random = new Random();//产生随机数
                if (isRandom) {
                    resultTArea.setText("");//清空显示结果的文本域
                    LinkedList tempRoll = new LinkedList(roll);
                    int temp;
                    int count = 1;
                    StringBuffer rSB = new StringBuffer();
                    while (tempRoll.size() >= number) {
                        for (int i = 0; i < number; i++) {
                            temp = random.nextInt(tempRoll.size());
                            rSB.append(tempRoll.get(temp) + "\n");
                            tempRoll.remove(temp);
                        }
                        resultTArea.append("---------- 第" + count + "组 ----------" + "\n");
                        resultTArea.append(rSB.toString());
                        count++;
                        rSB.delete(0, rSB.length());
                    }
                    if (!tempRoll.isEmpty()) {
                        resultTArea.append("---------- 剩余学生，请手动分组 ---------- " + "\n");
                        resultTArea.append(tempRoll.toString());
                    }
                } else {
                    if (a + b + c == number) {
                        if (!roll.isEmpty()) {
                            resultTArea.setText("");//清空显示结果的文本域
                            //将列表拆分为三个
                            LinkedList<String> aList = new LinkedList<String>();
                            LinkedList<String> bList = new LinkedList<String>();
                            LinkedList<String> cList = new LinkedList<String>();
                            int size = roll.size();
                            aList = new LinkedList<String>(roll.subList(0, size * a / number));
                            bList = new LinkedList<String>(roll.subList(size * a / number, size * (a + b) / number));
                            cList = new LinkedList<String>(roll.subList(size * (a + b) / number, size));
                            StringBuffer aSB = new StringBuffer();
                            StringBuffer bSB = new StringBuffer();
                            StringBuffer cSB = new StringBuffer();

                            int count = 1;//记录组序号
                            int tempA, tempB, tempC;//用于记录随机索引值
                            while (!aList.isEmpty() && !bList.isEmpty() && !cList.isEmpty()) {
                                for (int i = 0; i < a; i++) {
                                    tempA = random.nextInt(aList.size());
                                    aSB.append(aList.get(tempA) + "\n");
                                    aList.remove(tempA);
                                }
                                for (int i = 0; i < b; i++) {
                                    tempB = random.nextInt(bList.size());
                                    bSB.append(bList.get(tempB) + "\n");
                                    bList.remove(tempB);
                                }
                                for (int i = 0; i < c; i++) {
                                    tempC = random.nextInt(cList.size());
                                    cSB.append(cList.get(tempC) + "\n");
                                    cList.remove(tempC);
                                }
                                resultTArea.append("---------- 第" + count + "组 ----------" + "\n");
                                resultTArea.append(aSB.toString() + bSB.toString() + cSB.toString());
                                count++;
                                aSB.delete(0, aSB.length());
                                bSB.delete(0, bSB.length());
                                cSB.delete(0, cSB.length());
                            }
                            if (!aList.isEmpty() || !bList.isEmpty() || !cList.isEmpty()) {
                                resultTArea.append("---------- 剩余学生，请手动分组 ---------- " + "\n");
                                resultTArea.append(aList.toString() + bList.toString() + cList.toString());
                            }
                        } else {
                            JOptionPane.showMessageDialog(groupBtn, "读取文件失败，请选择正确的文件");
                        }
                    } else {
                        JOptionPane.showMessageDialog(groupBtn, "输入有误，每组人数应为三类学生人数之和，请重新输入");
                    }
                }
            }
        });

        setVisible(true);
    }

    //判断文件字符编码格式
    public String getFileCharsetName(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);
        byte[] head = new byte[3];
        inputStream.read(head);

        String charsetName = "GBK";//或GB2312，即ANSI
        if (head[0] == -1 && head[1] == -2 ) //0xFFFE
            charsetName = "UTF-16";
        else if (head[0] == -2 && head[1] == -1 ) //0xFEFF
            charsetName = "Unicode";//包含两种编码格式：UCS2-Big-Endian和UCS2-Little-Endian
        else if(head[0]==-27 && head[1]==-101 && head[2] ==-98)
            charsetName = "UTF-8"; //UTF-8(不含BOM)
        else if(head[0]==-17 && head[1]==-69 && head[2] ==-65)
            charsetName = "UTF-8"; //UTF-8-BOM

        inputStream.close();

        //System.out.println(code);
        return charsetName;
    }

}
