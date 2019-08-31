package viscouswave;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class MyViscousWave extends JFrame {

    private double f = 50, rho = 1260, eta = 1.5, v0 = 1, hbig = 2, hsmall = 0.3, h = 6;
    private double omega, nu, delta;
    private double angle, Speed = 1200000000.0, angleRemember = 0, stop = 0;
    private JTextField TextField1, TextField2, TextField3, TextField4, TextField5;
    private int menuswitcher = 0, frame1switcher = 0, XSize = 300, YSize = 300;
    private JFrame menuwindow, frame1, frame2, frame3;
    private String h_delta;
    private Thread Thread1;
    private String x_axis_string = "x/\u03b4", y_axis_string = "V/V\u2080";
    private boolean stopper = true;
    private JButton pausebutton, helpmenubutton;
    private JPanel drawingpanel;
    private JLabel timelab;
    private WindowListener windowlistener;
    private HelpDialog helpdialog;

    public MyViscousWave() {
        menuwindow = new MenuWindow("Плоские вязкие волны. Меню");
        menuwindow.setLocationRelativeTo(null);
        menuwindow.setVisible(true);
    }

    public class MenuWindow extends JFrame {

        public MenuWindow(String s) throws HeadlessException {
            super(s);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);

            char onepoint = '.';
            String manypoint = "";
            for (int j = 0; j < 10; j++) {
                //    manypoint+=Character.toString(onepoint);
            }
            //manypoint не помог. надо узнать длину самого длинного str выражения
            //из него вычесть длину каждого и добавить точек в manypoint. 

            //Т.е. manypoint не статично

            JFrame menuframe = new JFrame();

            JPanel rootpanel = new JPanel();
            rootpanel.setLayout(new BoxLayout(rootpanel, BoxLayout.PAGE_AXIS));
            menuframe.add(rootpanel);

            JPanel menurootpanel = new JPanel();
            menurootpanel.setLayout(new GridLayout(2, 1));
            rootpanel.add(menurootpanel);

            JPanel menuswitchpanel = new JPanel();
            menuswitchpanel.setLayout(new BoxLayout(menuswitchpanel, BoxLayout.PAGE_AXIS));
            menuswitchpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Выберете задачу"));
            menurootpanel.add(menuswitchpanel);

            ButtonGroup bgroup1 = new ButtonGroup();

            JRadioButton rabiob1 = new JRadioButton("Плоская поперечная вязкая волна", true);
            bgroup1.add(rabiob1);
            menuswitchpanel.add(rabiob1);

            JRadioButton rabiob2 = new JRadioButton("Понятие фазовой скорости плоской вязкой волны", false);
            bgroup1.add(rabiob2);
            menuswitchpanel.add(rabiob2);

            JRadioButton rabiob3 = new JRadioButton("Отражение вязких волн в достаточно широком зазоре", false);
            bgroup1.add(rabiob3);
            menuswitchpanel.add(rabiob3);

            JRadioButton rabiob4 = new JRadioButton("Многократное отражение вязких волн в узких зазорах", false);
            bgroup1.add(rabiob4);
            menuswitchpanel.add(rabiob4);

            //JPanel menurootparameterpanel = new JPanel();
            //menurootparameterpanel.setLayout(null);
            JPanel menuparameterpanel = new JPanel();
            menuparameterpanel.setLayout(new BoxLayout(menuparameterpanel, BoxLayout.LINE_AXIS));
            menuparameterpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Характеристики задачи"));
            menurootpanel.add(menuparameterpanel);

            JPanel menuparameterpanel1 = new JPanel();
            menuparameterpanel1.setLayout(new GridLayout(4, 2));
            menuparameterpanel.add(menuparameterpanel1);

            JLabel lab11 = new JLabel("Частота колебаний пластины" + manypoint);
            lab11.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel1.add(lab11);


            JLabel lab1 = new JLabel(manypoint + "f" + " = ");


            lab1.setHorizontalAlignment(JLabel.RIGHT);
            menuparameterpanel1.add(lab1);

            JLabel lab22 = new JLabel("Плотность жидкости" + manypoint);
            lab22.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel1.add(lab22);

            JLabel lab2 = new JLabel(manypoint + "\u03c1" + " = ");
            lab2.setHorizontalAlignment(JLabel.RIGHT);
            menuparameterpanel1.add(lab2);

            JLabel lab33 = new JLabel("Динамическая вязкость" + manypoint);
            lab33.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel1.add(lab33);

            JLabel lab3 = new JLabel(manypoint + "\u03b7" + " = ");
            lab3.setHorizontalAlignment(JLabel.RIGHT);
            menuparameterpanel1.add(lab3);

            JLabel lab44 = new JLabel("Амплитуда rолебательной cкорости");
            lab44.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel1.add(lab44);

            JLabel lab4 = new JLabel("V\u2080" + " = ");
            lab4.setHorizontalAlignment(JLabel.RIGHT);
            menuparameterpanel1.add(lab4);

            JPanel menuparameterpanel2 = new JPanel();
            menuparameterpanel2.setLayout(new GridLayout(4, 1));
            menuparameterpanel.add(menuparameterpanel2);

            TextField1 = new JTextField();
            TextField1.setColumns(5);
            TextField1.setText(Double.toString(f));
            TextField1.setHorizontalAlignment(JTextField.RIGHT);
            menuparameterpanel2.add(TextField1);

            TextField2 = new JTextField();
            TextField2.setColumns(5);
            TextField2.setText(Double.toString(rho));
            TextField2.setHorizontalAlignment(JTextField.RIGHT);
            menuparameterpanel2.add(TextField2);

            TextField3 = new JTextField();
            TextField3.setColumns(5);
            TextField3.setText(Double.toString(eta));
            TextField3.setHorizontalAlignment(JTextField.RIGHT);
            menuparameterpanel2.add(TextField3);

            TextField4 = new JTextField();
            TextField4.setColumns(5);
            TextField4.setText(Double.toString(v0));
            TextField4.setHorizontalAlignment(JTextField.RIGHT);
            menuparameterpanel2.add(TextField4);

            JPanel menuparameterpanel3 = new JPanel();
            menuparameterpanel3.setLayout(new GridLayout(4, 1));
            menuparameterpanel.add(menuparameterpanel3);

            JLabel bottomlab1 = new JLabel("[Гц]");
            bottomlab1.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel3.add(bottomlab1);

            JLabel bottomlab2 = new JLabel("[кг/м" + "\u00B3]");
            bottomlab2.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel3.add(bottomlab2);

            JLabel bottomlab3 = new JLabel("[Па" + "\u00b7" + "с]");
            bottomlab3.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel3.add(bottomlab3);

            JLabel bottomlab4 = new JLabel("[м/с]");
            bottomlab4.setHorizontalAlignment(JLabel.LEFT);
            menuparameterpanel3.add(bottomlab4);

            menuswitchpanel.setSize(menuparameterpanel.getSize());

            JPanel buttonpanel = new JPanel();
            buttonpanel.setLayout(new FlowLayout());
            rootpanel.add(buttonpanel);

            JButton nextmenubutton = new JButton("Далее");
            buttonpanel.add(nextmenubutton);

            helpmenubutton = new JButton("Справка");
            buttonpanel.add(helpmenubutton);

            JButton exitmenubutton = new JButton("Выход");
            buttonpanel.add(exitmenubutton);

            setContentPane(rootpanel);
            pack();

            ActionListener exitactionlistener = new ExitActionListener();
            exitmenubutton.addActionListener(exitactionlistener);

            ActionListener helpactionlistener = new HelpActionListener();
            helpmenubutton.addActionListener(helpactionlistener);

            ActionListener nextactionlistener = new NextActionListener();
            nextmenubutton.addActionListener(nextactionlistener);

            ActionListener rb1actionlistener = new Radiobutton1ActionListener();
            rabiob1.addActionListener(rb1actionlistener);

            ActionListener rb2actionlistener = new Radiobutton2ActionListener();
            rabiob2.addActionListener(rb2actionlistener);

            ActionListener rb3actionlistener = new Radiobutton3ActionListener();
            rabiob3.addActionListener(rb3actionlistener);

            ActionListener rb4actionlistener = new Radiobutton4ActionListener();
            rabiob4.addActionListener(rb4actionlistener);

        }
    }

    public class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class HelpActionListener implements ActionListener {

        String HelpTitle = "О программе";
        String Helptext = "<html>"
                + "О программе:<br><br>"
                + "   Данная программа предназначена для демонстрации плоских поперечных вязких волн,<br>"
                + "распространяющихся в свободном пространстве и в зазорах с различными граничными.<br>"
                + "условиями в вязкой несжимаемой жидкости. <br>"
                + "Выберете один из четырех пунктов и введите характеристики.<br>"
                + "Характеристики вводить в формате double.<br>"
                + "<br>"
                + "1. Плоская поперечная вязкая волна:<br>"
                + "После нажатия кнопки \"Далее\" появится диалоговое окно, в котором предлагается выбрать<br>"
                + "либо представление в виде пространственно-временной зависимости колебательной скорости<br>"
                + "жидкости, либо представление в виде гипотетических частиц жидкости. После нажатия кнопки<br>"
                + " \"Далее\" появится окно результата, в котором в верхней части представлена выбранная <br>"
                + "зависимость, а в нижней расчитанные характеристики и кнопки управления<br>"
                + "<br>"
                + "2. Понятие фазовой скорости плоской вязкой волны<br>"
                + "После нажатия кнопки \"Далее\" сразу появится окно результата. Черными подвижными кружками<br>"
                + "показана скорость постоянной фазы волны.<br>"
                + "<br>"
                + "3. Отражение вязких волн в достаточно широком зазоре<br>"
                + "После нажатия кнопки \"Далее\" появится диалоговое окно, в котором предлагается выбрать<br>"
                + "граничные условия и при необходимости изменить ширину зазора h/\u03b4 > 1. В зазоре<br>"
                + "толшиной h/\u03b4 менее 1 используемого приближения не достаточно. Для узких зазоров используйте п.4.<br>"
                + "После нажатия кнопки \"Далее\" в окне результата появятся три зависимости:<br>"
                + "Синяя кривая - падающая волна, зеленая - отраженная, красная - сумма первых двух.<br>"
                + "<br>"
                + "4. Многократное отражение вязких волн в узких зазорах<br>"
                + "Единственное отличие от п. 3. состоит в том, что в данном представлении используется модель<br>"
                + "многократного отражения от стенок зазора. Это позволяет повысить точность расчета.<br>"
                + "Интерфейс и легенда аналогичны п.3.<br>"
                + "<br>"
                + "<br>С Уважением Павловский А. С."
                + "</html>";
                

        @Override
        public void actionPerformed(ActionEvent e) {
            helpdialog = new HelpDialog(HelpTitle, Helptext);
            helpdialog.setLocationRelativeTo(null);
            helpdialog.setVisible(true);
            helpmenubutton.setEnabled(false);
        }
    }

    public class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            menuwindow.setVisible(false);
            stopper = true;

            String tf1, tf2, tf3, tf4, MessageIfMenuWindowParameterIsBad;
            int i, errornumber = 0, errorvolume = 0;
            char ch;
            boolean filter, errormarker = false;
            tf1 = TextField1.getText();
            tf2 = TextField2.getText();
            tf3 = TextField3.getText();
            tf4 = TextField4.getText();

            int pointcounter = 0;
            for (i = 0; i < tf1.length(); i++) {
                ch = tf1.charAt(i);
                if (ch == '.') {
                    pointcounter++;
                }
                filter = (Character.isDigit(ch) || ch == '.' && pointcounter < 2);
                if (!filter) {
                    //System.out.println("error 1");
                    errormarker = true;
                    errornumber = 1;
                    errorvolume++;
                }
            }
            pointcounter = 0;
            for (i = 0; i < tf2.length(); i++) {
                ch = tf2.charAt(i);
                if (ch == '.') {
                    pointcounter++;
                }
                filter = (Character.isDigit(ch) || ch == '.' && pointcounter < 2);
                if (!filter) {
                    //System.out.println("error 2");
                    errormarker = true;
                    errornumber = 2;
                    errorvolume++;
                }
            }
            pointcounter = 0;
            for (i = 0; i < tf3.length(); i++) {
                ch = tf3.charAt(i);
                if (ch == '.') {
                    pointcounter++;
                }
                filter = (Character.isDigit(ch) || ch == '.' && pointcounter < 2);
                if (!filter) {
                    //System.out.println("error 3");
                    errormarker = true;
                    errornumber = 3;
                    errorvolume++;
                }
            }
            pointcounter = 0;
            for (i = 0; i < tf4.length(); i++) {
                ch = tf4.charAt(i);
                if (ch == '.') {
                    pointcounter++;
                }
                filter = (Character.isDigit(ch) || ch == '.' && pointcounter < 2);
                if (!filter) {
                    //System.out.println("error 4");
                    errormarker = true;
                    errornumber = 4;
                    errorvolume++;
                }
            }
            if (!errormarker) {
                f = Double.parseDouble(TextField1.getText());
                rho = Double.parseDouble(TextField2.getText());
                eta = Double.parseDouble(TextField3.getText());
                v0 = Double.parseDouble(TextField4.getText());



                omega = 2 * Math.PI * f;
                nu = eta / rho;
                delta = Math.sqrt(2 * nu / omega);
                //phase_speed = omega * delta;

                switch (menuswitcher) {
                    case 0:
                        frame1switcher = 0;
                        frame1 = new PropertiesWindow1("Плоская поперечная вязкая волна");
                        frame1.setLocationRelativeTo(null);
                        frame1.setVisible(true);
                        frame1.addWindowListener(windowlistener);
                        break;
                    case 1:
                        //menuswitcher = 0. Ноль здесь:
                        frame1switcher = 2;
                        //System.out.println("frame1switcher = " + frame1switcher);
                        frame3 = new DrawWindow("Понятие фазовой скорости");
                        frame3.setLocationRelativeTo(null);
                        frame3.setVisible(true);
                        break;
                    case 2:
                        frame1switcher = 3;
                        frame2 = new PropertiesWindow2("Отражение. h/\u03b4 >1");
                        frame2.setLocationRelativeTo(null);
                        frame2.setVisible(true);
                        break;
                    case 3:
                        frame1switcher = 5;
                        frame2 = new PropertiesWindow2("Отражение. h/\u03b4 < 1");
                        frame2.setLocationRelativeTo(null);
                        frame2.setVisible(true);
                        break;
                    default:
                        frame1switcher = 0;
                        frame1 = new PropertiesWindow1("Плоская поперечная вязкая волна");
                        frame1.setLocationRelativeTo(null);
                        frame1.setVisible(true);
                        frame1.addWindowListener(windowlistener);
                        break;
                }

                //System.out.println(menuswitcher);
            } else {
                if (errorvolume <= 1) {
                    switch (errornumber) {
                        case 1:
                            TextField1.setText(Double.toString(f));
                            break;
                        case 2:
                            TextField2.setText(Double.toString(rho));
                            break;
                        case 3:
                            TextField3.setText(Double.toString(eta));
                            break;
                        case 4:
                            TextField4.setText(Double.toString(v0));

                    }
                    MessageIfMenuWindowParameterIsBad =
                            "Вы указали неверное значение в поле характеристик № "
                            + Integer.toString(errornumber) + ".\n"
                            + "Введите положительное число типа double.";
                } else {
                    MessageIfMenuWindowParameterIsBad =
                            "Вы допустили более одной ошибки при вводе.\n"
                            + "Введите в поля положительные числа типа double.";
                    TextField1.setText(Double.toString(f));
                    TextField2.setText(Double.toString(rho));
                    TextField3.setText(Double.toString(eta));
                    TextField4.setText(Double.toString(v0));
                }
                menuwindow.setVisible(true);

                JOptionPane.showMessageDialog(menuwindow, MessageIfMenuWindowParameterIsBad, "Неверное значение характеристики!", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    public class Radiobutton1ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            menuswitcher = 0;
            h = 6;
        }
    }

    public class Radiobutton2ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            menuswitcher = 1;
            h = 6;
        }
    }

    public class Radiobutton3ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            menuswitcher = 2;
            h_delta = " >1";
            h = hbig;
        }
    }

    public class Radiobutton4ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            menuswitcher = 3;
            h_delta = " <1";
            h = hsmall;
        }
    }

    public class PropertiesWindow1 extends JFrame {

        public PropertiesWindow1(String s) throws HeadlessException {
            super(s);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setResizable(false);
            addWindowListener(new CloseListener());

            JFrame prop1frame = new JFrame();

            JPanel prop1rootpanel = new JPanel();
            prop1rootpanel.setLayout(new BoxLayout(prop1rootpanel, BoxLayout.PAGE_AXIS));
            prop1frame.add(prop1rootpanel);

            JPanel prop1panel = new JPanel();
            prop1panel.setLayout(new GridLayout(2, 1));
            prop1panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Выберете зависимость"));
            prop1rootpanel.add(prop1panel);

            ButtonGroup bgroup1 = new ButtonGroup();

            JRadioButton prop1rabiob1 = new JRadioButton("Зависимость колебательной скорости жидкости от x и t", true);
            bgroup1.add(prop1rabiob1);
            prop1panel.add(prop1rabiob1);

            JRadioButton prop1rabiob2 = new JRadioButton("Зависимость положения частиц жидкости от x и t", false);
            bgroup1.add(prop1rabiob2);
            prop1panel.add(prop1rabiob2);

            JPanel prop2panel = new JPanel();
            prop2panel.setLayout(new FlowLayout());
            prop1rootpanel.add(prop2panel);

            JButton nextbut = new JButton("Далее");
            prop2panel.add(nextbut);

            JButton backbut = new JButton("Назад");
            prop2panel.add(backbut);

            JButton exitbut = new JButton("Выход");
            prop2panel.add(exitbut);

            ActionListener nextactionlistener = new Prop1NextActionListener();
            nextbut.addActionListener(nextactionlistener);

            ActionListener backactionlistener = new BackActionListener();
            backbut.addActionListener(backactionlistener);

            ActionListener exitactionlistener = new ExitActionListener();
            exitbut.addActionListener(exitactionlistener);

            ActionListener rb1actionlistener = new Prop1Radiobutton1ActionListener();
            prop1rabiob1.addActionListener(rb1actionlistener);

            ActionListener rb2actionlistener = new Prop1Radiobutton2ActionListener();
            prop1rabiob2.addActionListener(rb2actionlistener);

            setContentPane(prop1rootpanel);
            pack();
        }
    }

    public class BackActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (menuswitcher) {
                case 0:
                    y_axis_string = "V/V\u2080";
                    frame1.setVisible(false);
                    frame1.removeAll();

                    break;
                case 1:
                    y_axis_string = "V/V\u2080";
                    frame3.setVisible(false);
                    frame3.removeAll();

                    break;
                case 2:
                    //switch()
                    frame2.setVisible(false);
                    frame2.removeAll();
                    break;
                case 3:
                    frame2.setVisible(false);
                    frame2.removeAll();
                    break;
                default:
                    y_axis_string = "V/V\u2080";
                    frame1.setVisible(false);
                    frame1.removeAll();

                    break;
            }
            menuwindow.setVisible(true);
            //System.out.println("back");
        }
    }

    public class Prop1NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            stopper = true;
            switch (frame1switcher) {
                case 0:
                    frame3 = new DrawWindow("Зависимость колебательной скорости жидкости от x и t");
                    frame3.setLocationRelativeTo(null);
                    frame3.setVisible(true);
                    break;
                case 1:
                    frame3 = new DrawWindow("Зависимость положения частиц жидкости от x и t");
                    frame3.setLocationRelativeTo(null);
                    frame3.setVisible(true);
                    break;
                default:
                    frame3 = new DrawWindow("Зависимость колебательной скорости жидкости от x и t");
                    frame3.setLocationRelativeTo(null);
                    frame3.setVisible(true);
            }
            frame1.setVisible(false);
            //System.out.println("next");
            //System.out.println("frame1switcher = " + frame1switcher);
        }
    }

    public class Prop1Radiobutton1ActionListener implements ActionListener {
        //Зависимость колебательной скорости жидкости от x и t

        @Override
        public void actionPerformed(ActionEvent e) {
            frame1switcher = 0;
            y_axis_string = "V/V\u2080";
            //System.out.println("win");

        }
    }

    public class Prop1Radiobutton2ActionListener implements ActionListener {
        //Зависимость положения частиц жидкости от x и t

        @Override
        public void actionPerformed(ActionEvent e) {
            frame1switcher = 1;
            y_axis_string = "A/A\u2080";
            //System.out.println("test");
        }
    }

    public class PropertiesWindow2 extends JFrame {

        public PropertiesWindow2(String s) throws HeadlessException {
            super(s);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setResizable(false);
            addWindowListener(new CloseListener());

            JFrame prop2frame = new JFrame();

            JPanel prop2rootpanel = new JPanel();
            prop2rootpanel.setLayout(new BoxLayout(prop2rootpanel, BoxLayout.PAGE_AXIS));
            prop2frame.add(prop2rootpanel);

            JPanel prop2panel1 = new JPanel();
            prop2panel1.setLayout(new FlowLayout());
            prop2panel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Введите толщину зазора"));
            prop2rootpanel.add(prop2panel1);

            JLabel labH = new JLabel("h/\u03b4 = ");
            prop2panel1.add(labH);

            TextField5 = new JTextField();
            TextField5.setColumns(5);
            TextField5.setText(Double.toString(h));
            TextField5.setHorizontalAlignment(JTextField.RIGHT);
            prop2panel1.add(TextField5);

            JLabel bottomlabH = new JLabel(h_delta);
            prop2panel1.add(bottomlabH);

            JPanel prop2panel2 = new JPanel();
            prop2panel2.setLayout(new GridLayout(2, 1));
            prop2panel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Выберете тип граничных условий"));
            prop2rootpanel.add(prop2panel2);

            ButtonGroup bgroup1 = new ButtonGroup();

            JRadioButton prop2rabiob1 = new JRadioButton("Жесткая стенка (Дирихле)", true);
            bgroup1.add(prop2rabiob1);
            prop2panel2.add(prop2rabiob1);

            JRadioButton prop2rabiob2 = new JRadioButton("Свободная поверхность (Нейман)", false);
            bgroup1.add(prop2rabiob2);
            prop2panel2.add(prop2rabiob2);

            JPanel prop2panel3 = new JPanel();
            prop2panel3.setLayout(new FlowLayout());
            prop2rootpanel.add(prop2panel3);

            JButton nextbut = new JButton("Далее");
            prop2panel3.add(nextbut);

            JButton backbut = new JButton("Назад");
            prop2panel3.add(backbut);

            JButton exitbut = new JButton("Выход");
            prop2panel3.add(exitbut);

            ActionListener nextactionlistener = new Prop2NextActionListener();
            nextbut.addActionListener(nextactionlistener);

            ActionListener backactionlistener = new BackActionListener();
            backbut.addActionListener(backactionlistener);

            ActionListener exitactionlistener = new ExitActionListener();
            exitbut.addActionListener(exitactionlistener);

            ActionListener rb1actionlistener = new Prop2Radiobutton1ActionListener();
            prop2rabiob1.addActionListener(rb1actionlistener);

            ActionListener rb2actionlistener = new Prop2Radiobutton2ActionListener();
            prop2rabiob2.addActionListener(rb2actionlistener);

            setContentPane(prop2rootpanel);
            pack();
        }
    }

    public class Prop2NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            stopper = true;

            int pointcounter = 0;
            int i;
            String tf5;
            char ch;
            boolean filter, errormarker = false;

            tf5 = TextField5.getText();
            for (i = 0; i < tf5.length(); i++) {
                ch = tf5.charAt(i);
                if (ch == '.') {
                    pointcounter++;
                }
                filter = (Character.isDigit(ch) || ch == '.' && pointcounter < 2);
                if (!filter) {
                    //System.out.println("error 1");
                    errormarker = true;

                }
            }
            boolean visible = true;
            if (!errormarker) {

                h = Double.parseDouble(TextField5.getText());

                switch (frame1switcher) {
                    case 3:
                        if (h < 1) {
                            int optionpane = JOptionPane.showConfirmDialog(frame2,
                                    "Вы указали значение h/\u03b4 меньше 1.\n"
                                    + "Это приведет к потере разрешения\n"
                                    + " и точности решения.\n"
                                    + "Действительно желаете продолжить?", "h/\u03b4 < 1. Продолжить?", JOptionPane.YES_NO_OPTION);
                            if (optionpane == JOptionPane.YES_OPTION) {
                                //System.out.println("yes");
                                frame3 = new DrawWindow("Жесткая стенка (Дирихле)");
                                frame3.setLocationRelativeTo(null);
                                frame3.setVisible(true);
                            } else {
                                //System.out.println("No");
                                visible = false;
                            }
                        } else {
                            frame3 = new DrawWindow("Жесткая стенка (Дирихле)");
                            frame3.setLocationRelativeTo(null);
                            frame3.setVisible(true);
                        }
                        break;
                    case 4:
                        if (h < 1) {
                            int optionpane = JOptionPane.showConfirmDialog(frame2,
                                    "Вы указали значение h/\u03b4 меньше 1.\n"
                                    + "Это приведет к потере разрешения\n"
                                    + " и точности решения.\n"
                                    + "Действительно желаете продолжить?", "h/\u03b4 < 1. Продолжить?", JOptionPane.YES_NO_OPTION);
                            if (optionpane == JOptionPane.YES_OPTION) {
                                frame3 = new DrawWindow("Свободная поверхность (Нейман)");
                                frame3.setLocationRelativeTo(null);
                                frame3.setVisible(true);
                            } else {
                                //System.out.println("No");
                                visible = false;
                            }
                        } else {
                            frame3 = new DrawWindow("Свободная поверхность (Нейман)");
                            frame3.setLocationRelativeTo(null);
                            frame3.setVisible(true);
                        }
                        break;
                    case 5:
                        if (h > 1) {
                            int optionpane = JOptionPane.showConfirmDialog(frame2,
                                    "Вы указали значение h/\u03b4 ,больше 1.\n"
                                    + "Это приведет к перегрузке графика\n"
                                    + "значительным количеством кривых.\n"
                                    + "Действительно желаете продолжить?", "h/\u03b4 > 1. Продолжить?", JOptionPane.YES_NO_OPTION);
                            if (optionpane == JOptionPane.YES_OPTION) {
                                frame3 = new DrawWindow("Жесткая стенка (Дирихле)");
                                frame3.setLocationRelativeTo(null);
                                frame3.setVisible(true);
                            } else {
                                //System.out.println("No");
                                visible = false;
                            }
                        } else {
                            frame3 = new DrawWindow("Жесткая стенка (Дирихле)");
                            frame3.setLocationRelativeTo(null);
                            frame3.setVisible(true);
                        }
                        break;
                    case 6:
                        if (h > 1) {
                            int optionpane = JOptionPane.showConfirmDialog(frame2,
                                    "Вы указали значение h/\u03b4 ,больше 1.\n"
                                    + "Это приведет к перегрузке графика\n"
                                    + "значительным количеством кривых.\n"
                                    + "Действительно желаете продолжить?", "h/\u03b4 > 1. Продолжить?", JOptionPane.YES_NO_OPTION);
                            if (optionpane == JOptionPane.YES_OPTION) {
                                frame3 = new DrawWindow("Свободная поверхность (Нейман)");
                                frame3.setLocationRelativeTo(null);
                                frame3.setVisible(true);
                            } else {
                                //System.out.println("No");
                                visible = false;
                            }
                        } else {
                            frame3 = new DrawWindow("Свободная поверхность (Нейман)");
                            frame3.setLocationRelativeTo(null);
                            frame3.setVisible(true);
                        }
                        break;
                    default:
                        frame3 = new DrawWindow("Жесткая стенка (Дирихле)");
                        frame3.setLocationRelativeTo(null);
                        frame3.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(menuwindow,
                        "Вы допустили ошибку при вводе ширины зазора h/\u03b4", "Неверное значение характеристики!", JOptionPane.ERROR_MESSAGE);
                TextField5.setText(Double.toString(h));
                visible = false;
                //System.out.println("Error!!!!!");
            }
            frame2.setVisible(!visible);

            //System.out.println("frame1switcher = ");
            //System.out.println(frame1switcher);
            //System.out.println("h= " + Double.toString(h));
        }
    }

    public class Prop2Radiobutton1ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (menuswitcher) {
                case 2:
                    frame1switcher = 3;
                    break;
                case 3:
                    frame1switcher = 5;
                    break;
                default:
                    frame1switcher = 3;
                    break;
            }
            /*
             if(menuswitcher==2){
             frame2switcher = 3;
             }else{
             frame2switcher = 5;    
             }
             */
        }
    }

    public class Prop2Radiobutton2ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (menuswitcher) {
                case 2:
                    frame1switcher = 4;
                    break;
                case 3:
                    frame1switcher = 6;
                    break;
                default:
                    frame1switcher = 4;
                    break;
            }
            /*
             if (menuswitcher == 3) {
             frame2switcher = 6;
             } else {
             frame2switcher = 4;
             }

             //frame2switcher = 4;
             */
        }
    }

    public class DrawWindow extends JFrame {

        public DrawWindow(String s) throws HeadlessException {
            // drawWinowSwitch = 1 ,2 ,3,... по порядку и должна влиять на конкретный вид панели Зависимость и Значения величин
            super(s);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            addWindowListener(new DrawWindowCloseListener());
            setResizable(false);


            JFrame drawframe = new JFrame();

            JPanel drawrootpanel = new JPanel();
            drawrootpanel.setLayout(new BoxLayout(drawrootpanel, BoxLayout.PAGE_AXIS));
            drawframe.add(drawrootpanel);

            drawingpanel = new JPanel();
            drawingpanel.setLayout(new FlowLayout());
            drawingpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Зависимость"));
            //drawingpanel.setPreferredSize(new Dimension(300, 300));
            drawrootpanel.add(drawingpanel);

            Drawpanel drawpanel = new Drawpanel(stopper, Thread1, frame1switcher);
            drawpanel.setPreferredSize(new Dimension(XSize, YSize));
            drawingpanel.add(drawpanel);

            JPanel parametAnDcontrolerpanel = new JPanel();
            parametAnDcontrolerpanel.setLayout(new GridLayout(1, 2));
            drawrootpanel.add(parametAnDcontrolerpanel);

            JPanel parameterpanel1 = new JPanel();
            parameterpanel1.setLayout(new BoxLayout(parameterpanel1, BoxLayout.LINE_AXIS));
            parameterpanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Характеристики"));
            parameterpanel1.add(Box.createHorizontalGlue());
            parametAnDcontrolerpanel.add(parameterpanel1);

            JPanel parameterpanel = new JPanel();
            parameterpanel.setLayout(new BoxLayout(parameterpanel, BoxLayout.PAGE_AXIS));
            parameterpanel.setMinimumSize(new Dimension(200, 200));
            parameterpanel1.add(parameterpanel);
            parameterpanel1.add(Box.createHorizontalGlue());

            JLabel deltalab = new JLabel("\u03b4 = " + Double.toString(Math.rint(delta * 100000) / 100) + "мм");
            parameterpanel.add(deltalab);

            switch (menuswitcher) {
                case 1:
                    JLabel speedlab = new JLabel("c = \u03b4\u0387\u03c9 = " + Double.toString(Math.rint(omega * delta * 100) / 100) + "м/c");
                    parameterpanel.add(speedlab);
                    break;
                case 2:
                    JLabel hlab = new JLabel("h = " + Double.toString(Math.rint(h * delta * 100000) / 100) + "мм");
                    parameterpanel.add(hlab);
                    JLabel hdlab = new JLabel("h/\u03b4 = " + Double.toString(Math.rint(h * 10) / 10));
                    parameterpanel.add(hdlab);
                    break;
                case 3:
                    hlab = new JLabel("h = " + Double.toString(Math.rint(h * delta * 100000) / 100) + "мм");
                    parameterpanel.add(hlab);
                    hdlab = new JLabel("h/\u03b4 = " + Double.toString(Math.rint(h * 10) / 10));
                    parameterpanel.add(hdlab);
                    break;
            }

            JLabel Tlab = new JLabel("T = " + Double.toString(Math.rint(1 / f * 1000) / 1000) + "c");
            parameterpanel.add(Tlab);

            timelab = new JLabel("t/T = ");
            parameterpanel.add(timelab);

            JPanel speedpanel1 = new JPanel();
            speedpanel1.setLayout(new BoxLayout(speedpanel1, BoxLayout.PAGE_AXIS));
            speedpanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Управление анимацией"));
            speedpanel1.add(Box.createVerticalGlue());
            parametAnDcontrolerpanel.add(speedpanel1);

            JPanel speedpanel = new JPanel();
            speedpanel.setLayout(new FlowLayout());

            speedpanel1.add(speedpanel);
            speedpanel1.add(Box.createVerticalGlue());

            JButton plusbutton = new JButton("+");
            speedpanel.add(plusbutton);

            pausebutton = new JButton("Pause");
            speedpanel.add(pausebutton);

            JButton minusbutton = new JButton("\u2212");
            speedpanel.add(minusbutton);

            JPanel buttonpanel = new JPanel();
            buttonpanel.setLayout(new FlowLayout());
            drawrootpanel.add(buttonpanel);

            JButton backbut = new JButton("Назад");
            buttonpanel.add(backbut);

            if (menuswitcher != 1) {
                JButton menubut = new JButton("В меню");
                buttonpanel.add(menubut);

                ActionListener vmenuactionlistener = new DrawWindowVmenuActionListener();
                menubut.addActionListener(vmenuactionlistener);

                ActionListener secondbackactionlistener = new DrawWindowBackActionListener();
                backbut.addActionListener(secondbackactionlistener);
            } else {
                ActionListener backactionlistener = new BackActionListener();
                backbut.addActionListener(backactionlistener);

            }

            JButton exitbut = new JButton("Выход");
            buttonpanel.add(exitbut);

            ActionListener exitactionlistener = new ExitActionListener();
            exitbut.addActionListener(exitactionlistener);

            ActionListener plusactionlistener = new DrawWindowPlusActionListener();
            plusbutton.addActionListener(plusactionlistener);

            ActionListener startactionlistener = new DrawWindowStartActionListener();
            pausebutton.addActionListener(startactionlistener);

            ActionListener minusactionlistener = new DrawWindowMinusActionListener();
            minusbutton.addActionListener(minusactionlistener);

            setContentPane(drawrootpanel);
            pack();
        }

        public class DrawWindowBackActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("ect6");

                frame3.setVisible(false);
                frame3.removeAll();
                angleRemember = 0;
                //Условие для frame 1 и frame 2!!!
                if (menuswitcher == 0) {
                    frame1.setVisible(true);
                } else {
                    frame2.setVisible(true);
                }
            }
        }
    }

    public class DrawWindowVmenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame3.removeAll();
            frame3.setVisible(false);
            angleRemember = 0;
            menuwindow.setVisible(true);

        }
    }

    public class DrawWindowStartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            stopper = !stopper;
            angleRemember = angle;
            drawingpanel.removeAll();
            drawingpanel.setVisible(false);
            Drawpanel drawpanel = new Drawpanel(stopper, Thread1, frame1switcher);
            drawpanel.setPreferredSize(new Dimension(XSize, YSize));
            drawingpanel.add(drawpanel);
            drawingpanel.setVisible(true);
            if (stopper) {
                pausebutton.setText("Pause");
            } else {
                pausebutton.setText("Start");
            }

        }
    }

    public class DrawWindowPlusActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Speed = Speed / 2;
            angleRemember = angle;
            drawingpanel.removeAll();
            drawingpanel.setVisible(false);
            Drawpanel drawpanel = new Drawpanel(stopper, Thread1, frame1switcher);
            drawpanel.setPreferredSize(new Dimension(XSize, YSize));
            drawingpanel.add(drawpanel);
            drawingpanel.setVisible(true);

        }
    }

    public class DrawWindowMinusActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Speed = Speed * 2;
            angleRemember = angle;
            drawingpanel.removeAll();
            drawingpanel.setVisible(false);
            Drawpanel drawpanel = new Drawpanel(stopper, Thread1, frame1switcher);
            drawpanel.setPreferredSize(new Dimension(XSize, YSize));
            drawingpanel.add(drawpanel);
            drawingpanel.setVisible(true);

        }
    }

    public static void main(String[] args) {
        MyViscousWave myviscouswave = new MyViscousWave();
        myviscouswave.setResizable(false);
    }

    public class Drawpanel extends JComponent implements Runnable {

        private long t = System.nanoTime();
        //private double phasespeed_angle;
        private int drSwitcher;

        public Drawpanel(boolean st, Thread myThread, int drawswitcher) {
            super();
            myThread = new Thread(this);
            drSwitcher = drawswitcher;
            if (st) {
                start(myThread);
            } else {
                stop(myThread);
            }
        }

        public final void start(Thread myThread) {
            myThread.start();
        }

        public final void stop(Thread myThread) {
            myThread.interrupt();
        }

        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(5);
                    timelab.setText("t/T = " + Math.rint(angle / 2 / Math.PI * 100) / 100);
                } catch (InterruptedException ex) {
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            long tm = System.nanoTime() - t;
            double gx = 0, gy, gyVel, gyVel2;//, h = 3;
            int axisOtstup = 20, i = 0;
            //int deltaLenght = (int) h;

            angle = tm / Speed + angleRemember;

            Color oldColor = g.getColor();

            //Оси
            g.drawLine(axisOtstup - 5, (int) (YSize / 2), (int) XSize, (int) (YSize / 2));
            g.drawLine(axisOtstup, 5, axisOtstup, YSize - 5);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));

            int axis_x = 0, axis_y = -100;
            int h_int;
            if (Math.rint(h * 10) == 10 * (int) h) {
                h_int = (int) h;
            } else {
                h_int = (int) h + 1;
            }

            String xlabelstring, xlabelstring1;
            int xlabelint, xlabeldrob, xlabeldecrement, xlabelokrugl;
            //if(frame1switcher==5 || frame1switcher==6){
            if (h <= 0.1) {
                xlabelint = 50;
                xlabelstring = "0.0";
                xlabeldrob = xlabelint;
                xlabeldecrement = 2;
            } else {
                if (h <= 0.6) {
                    xlabelint = 10;
                    xlabelstring = "0.";
                    xlabeldrob = xlabelint;
                    xlabeldecrement = 1;
                } else {
                    if (h <= 2) {
                        xlabelint = 2;
                        xlabelstring = "";
                        xlabeldrob = 2;
                        xlabeldecrement = 5;
                    } else {
                        xlabelint = 2;
                        xlabelstring = "";
                        xlabeldrob = 1;
                        xlabeldecrement = 1;
                    }
                }
            }
            while (axis_x < xlabelint * h_int) {
                g.drawLine((int) (((axis_x * (XSize - axisOtstup) / h) / xlabelint + axisOtstup)), (int) (YSize / 2 + 5), (int) (((axis_x * (XSize - axisOtstup) / h) / xlabelint + axisOtstup)), (int) (YSize / 2 - 5));
                xlabelokrugl = xlabeldecrement * axis_x;
                if (h > 0.6 && h <= 2) {
                    xlabelstring1 = Double.toString(Math.rint(xlabelokrugl) / 10);
                } else {
                    xlabelstring1 = Integer.toString(xlabelokrugl);
                }
                g.drawString(xlabelstring + xlabelstring1, (int) (((axis_x * (XSize - axisOtstup) / h / xlabeldrob) + axisOtstup)), (int) (YSize / 2) + 20);
                axis_x++;
            }
            /*
             if(frame1switcher==5 || frame1switcher==6){
             while (axis_x < 10 * h_int) {
             g.drawLine((int) (((axis_x * (XSize - axisOtstup) / h) / 10 + axisOtstup)), (int) (YSize / 2 + 5), (int) (((axis_x * (XSize - axisOtstup) / h) / 10 + axisOtstup)), (int) (YSize / 2 - 5));
             g.drawString("0."+Integer.toString(axis_x), (int) (((axis_x * (XSize - axisOtstup) / h/10) + axisOtstup)), (int) (YSize / 2) + 20);
             axis_x++;
             }
             }else{
             while (axis_x < 2 * h_int) {
             g.drawLine((int) (((axis_x * (XSize - axisOtstup) / h) / 2 + axisOtstup)), (int) (YSize / 2 + 5), (int) (((axis_x * (XSize - axisOtstup) / h) / 2 + axisOtstup)), (int) (YSize / 2 - 5));
             g.drawString(Integer.toString(axis_x), (int) (((axis_x * (XSize - axisOtstup) / h) + axisOtstup)), (int) (YSize / 2) + 20);
             axis_x++;
             }
             }*/
            while (axis_y <= 100) {
                g.drawLine(axisOtstup - 5, axis_y + YSize / 2, axisOtstup, axis_y + YSize / 2);
                //if(axis_y<=)
                g.drawString(Integer.toString((int) (-axis_y * 0.02)), axisOtstup - 20, 2 * axis_y + YSize / 2);
                axis_y += 50;
            }
            Font oldfont = g.getFont();
            g.setFont(new Font("TimesRoman", Font.ITALIC, 20));
            g.drawString(y_axis_string, 30, 30);
            g.drawString(x_axis_string, XSize - 40, (int) (YSize / 2) + 40);
            g.setFont(oldfont);
            double gyPad, gyOtr, gyOtr1, gyOtr2, gyOtr3, gyOtr4, gyOtr5, gyOtr6, gyOtr7, gyOtr8, gyOtr9, gyOtr10;
            switch (drSwitcher) {
                case 0:
                    g.setColor(Color.RED);
                    while (gx < h) {
                        gy = Math.exp(-gx) * Math.cos(angle - gx);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2), 2, 2);
                        gx += 0.01;
                    }
                    g.setColor(oldColor);
                    break;
                case 1:
                    g.setFont(new Font("TimesRoman", Font.BOLD, 10));
                    while (gx < h) {
                        gy = Math.exp(-gx) * Math.cos(angle - gx);
                        g.setColor(Color.RED);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2) - 10, 20, 20);
                        i++;
                        g.setColor(Color.BLACK);
                        g.drawString(Integer.toString(i), (int) (gx * (XSize - axisOtstup) / h) + 7 + axisOtstup, (int) (gy * 100 + YSize / 2) + 5);

                        gx += 0.5;
                    }
                    g.setColor(oldColor);
                    g.setFont(oldfont);
                    break;
                case 2:
                    g.setColor(Color.RED);
                    while (gx < h) {
                        gy = Math.exp(-gx) * Math.cos(angle - gx);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2), 2, 2);
                        gx += 0.01;
                    }
                    g.setColor(Color.BLACK);
                    double tau = angle - Math.PI / 4 - stop;
                    gyVel = Math.exp(-tau) * Math.cos(angle - tau);
                    gyVel2 = Math.exp(-tau - Math.PI) * Math.cos(angle - tau - Math.PI);
                    //if (tau < h) {
                    g.drawOval((int) (tau * (XSize - axisOtstup) / h) - 5 + axisOtstup, (int) (gyVel * 100 + YSize / 2 - 5), 10, 10);
                    g.drawOval((int) ((tau + Math.PI) * (XSize - axisOtstup) / h) - 5 + axisOtstup, (int) (gyVel2 * 100 + YSize / 2 - 5), 10, 10);
                    //}
                    if (tau > Math.PI) {
                        stop = angle - Math.PI / 4;
                    }
                    g.setColor(oldColor);
                    break;
                case 3:

                    while (gx < h) {
                        gyPad = Math.exp(-gx) * Math.cos(angle - gx);
                        gyOtr = Math.exp(-2 * h + gx) * Math.cos(angle - 2 * h + gx);
                        gy = gyPad - gyOtr;
                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyPad * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (-gyOtr * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.RED);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2), 2, 2);
                        g.setColor(oldColor);
                        gx += 0.01;
                    }
                    break;
                case 4:
                    while (gx < h) {
                        gyPad = Math.exp(-gx) * Math.cos(angle - gx);
                        gyOtr = Math.exp(-2 * h + gx) * Math.cos(angle - 2 * h + gx);
                        gy = gyPad + gyOtr;
                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyPad * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.RED);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2), 2, 2);
                        g.setColor(oldColor);
                        gx += 0.01;
                    }
                    break;
                case 5:
                    //Многократное отражение Дирихле. Сделать мельче ось х
                    while (gx < h) {
                        gyPad = Math.exp(-gx) * Math.cos(angle - gx);
                        gyOtr = Math.exp(-2 * h + gx) * Math.cos(angle - 2 * h + gx);
                        gyOtr1 = Math.exp(-2 * h - gx) * Math.cos(angle - 2 * h - gx);
                        gyOtr2 = Math.exp(-4 * h + gx) * Math.cos(angle + gx - 4 * h);
                        gyOtr3 = Math.exp(-4 * h - gx) * Math.cos(angle - gx - 4 * h);
                        gyOtr4 = Math.exp(-6 * h + gx) * Math.cos(angle + gx - 6 * h);
                        gyOtr5 = Math.exp(-6 * h - gx) * Math.cos(angle - gx - 6 * h);
                        gyOtr6 = Math.exp(-8 * h + gx) * Math.cos(angle + gx - 8 * h);
                        gyOtr7 = Math.exp(-8 * h - gx) * Math.cos(angle - gx - 8 * h);
                        gyOtr8 = Math.exp(-10 * h + gx) * Math.cos(angle + gx - 10 * h);
                        gyOtr9 = Math.exp(-10 * h - gx) * Math.cos(angle - gx - 10 * h);
                        gyOtr10 = Math.exp(-12 * h + gx) * Math.cos(angle + gx - 12 * h);
                        gy = gyPad - (gyOtr - (gyOtr1 - (gyOtr2 - (gyOtr3 - (gyOtr4 - (gyOtr5 - (gyOtr6 - (gyOtr7 - (gyOtr8 - (gyOtr9 - gyOtr10))))))))));
                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyPad * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (-gyOtr * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.RED);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2), 2, 2);

                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr1 * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (-gyOtr2 * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr3 * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (-gyOtr4 * 100 + YSize / 2), 2, 2);
                        g.setColor(oldColor);
                        if (h >= 0.5) {
                            gx += 0.005;
                        } else {
                            g.setColor(Color.BLUE);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr5 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.GREEN);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (-gyOtr6 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.BLUE);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr7 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.GREEN);

                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (-gyOtr8 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.BLUE);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr9 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.GREEN);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (-gyOtr10 * 100 + YSize / 2), 2, 2);

                            g.setColor(oldColor);
                            gx += 0.001;
                        }
                    }
                    break;
                case 6:
                    //Многократное отражение Нейман. Сделать мельче ось х
                    while (gx < h) {
                        gyPad = Math.exp(-gx) * Math.cos(angle - gx);
                        gyOtr = Math.exp(-2 * h + gx) * Math.cos(angle - 2 * h + gx);
                        gyOtr1 = Math.exp(-2 * h - gx) * Math.cos(angle - 2 * h - gx);
                        gyOtr2 = Math.exp(-4 * h + gx) * Math.cos(angle + gx - 4 * h);
                        gyOtr3 = Math.exp(-4 * h - gx) * Math.cos(angle - gx - 4 * h);
                        gyOtr4 = Math.exp(-6 * h + gx) * Math.cos(angle + gx - 6 * h);
                        gyOtr5 = Math.exp(-6 * h - gx) * Math.cos(angle - gx - 6 * h);
                        gyOtr6 = Math.exp(-8 * h + gx) * Math.cos(angle + gx - 8 * h);
                        gyOtr7 = Math.exp(-8 * h - gx) * Math.cos(angle - gx - 8 * h);
                        gyOtr8 = Math.exp(-10 * h + gx) * Math.cos(angle + gx - 10 * h);
                        gyOtr9 = Math.exp(-10 * h - gx) * Math.cos(angle - gx - 10 * h);
                        gyOtr10 = Math.exp(-12 * h + gx) * Math.cos(angle + gx - 12 * h);
                        gy = gyPad + gyOtr - (gyOtr1 + gyOtr2 - (gyOtr3 + gyOtr4 - (gyOtr5 + gyOtr6 - (gyOtr7 + gyOtr8 - (gyOtr9 + gyOtr10)))));
                        //gy =(gyOtr2+gyOtr3-(gyOtr4+gyOtr5-(gyOtr6+gyOtr7-(gyOtr7+gyOtr8 - (gyOtr9 + gyOtr10)))));
                        //gy = gyPad + (gyOtr + (gyOtr1 + (gyOtr2 + (gyOtr3 + (gyOtr4 + (gyOtr5 + (gyOtr6 + (gyOtr7 + (gyOtr8 + (gyOtr9 + gyOtr10))))))))));
                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyPad * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr * 100 + YSize / 2), 2, 2);

                        g.setColor(Color.RED);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2), 2, 2);

                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr1 * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr2 * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.BLUE);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr3 * 100 + YSize / 2), 2, 2);
                        g.setColor(Color.GREEN);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr4 * 100 + YSize / 2), 2, 2);
                        g.setColor(oldColor);
                        if (h >= 0.5) {
                            gx += 0.005;
                        } else {

                            g.setColor(Color.BLUE);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr5 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.GREEN);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr6 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.BLUE);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr7 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.GREEN);

                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr8 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.BLUE);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr9 * 100 + YSize / 2), 2, 2);
                            g.setColor(Color.GREEN);
                            g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gyOtr10 * 100 + YSize / 2), 2, 2);

                            g.setColor(oldColor);
                            gx += 0.001;
                        }
                    }
                    break;
                default:
                    g.setColor(Color.RED);
                    while (gx < h) {
                        gy = Math.exp(-gx) * Math.cos(angle - gx);
                        g.drawOval((int) (gx * (XSize - axisOtstup) / h) + axisOtstup, (int) (gy * 100 + YSize / 2), 2, 2);
                        gx += 0.01;
                    }
                    g.setColor(oldColor);
                    //System.out.println("default");
                    break;
            }
        }
    }

    private class CloseListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent event) {
            switch (menuswitcher) {
                case 0:
                    y_axis_string = "V/V\u2080";
                    frame1.setVisible(false);
                    frame1.removeAll();

                    break;
                case 1:
                    y_axis_string = "V/V\u2080";
                    frame3.setVisible(false);
                    frame3.removeAll();

                    break;
                case 2:
                    //switch()
                    frame2.setVisible(false);
                    frame2.removeAll();
                    break;
                case 3:
                    frame2.setVisible(false);
                    frame2.removeAll();
                    break;
                default:
                    y_axis_string = "V/V\u2080";
                    frame1.setVisible(false);
                    frame1.removeAll();

                    break;
            }
            menuwindow.setVisible(true);
            //System.out.println("Ура!!");
        }
    }

    private class DrawWindowCloseListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent event) {
            if (menuswitcher != 1) {
                frame3.setVisible(false);
                frame3.removeAll();
                angleRemember = 0;
                //Условие для frame 1 и frame 2!!!
                if (menuswitcher == 0) {
                    frame1.setVisible(true);
                } else {
                    frame2.setVisible(true);
                }
            } else {
                frame3.setVisible(false);
                frame3.removeAll();
                angleRemember = 0;
                menuwindow.setVisible(true);
            }
        }
    }

    public class HelpDialog extends JDialog {

        public HelpDialog(String s, String text) {
            JDialog helpdialog = new JDialog(menuwindow, false);
            setBounds(300, 300, 300, 300);
            setTitle(s);
            addWindowListener(new HelpCloseListener());
            //setIconImage("OptionPane.errorIcon", icon);

            JPanel helprootpanel = new JPanel();
            helprootpanel.setLayout(new BoxLayout(helprootpanel, BoxLayout.PAGE_AXIS));
            helpdialog.add(helprootpanel);

            JPanel helppanel = new JPanel();
            helppanel.setBorder(new EtchedBorder());
            helppanel.setLayout(new FlowLayout());
            helprootpanel.add(helppanel);

            JLabel helplabel = new JLabel(text);
            helppanel.add(helplabel);

            JPanel buttonpanel = new JPanel();
            buttonpanel.setLayout(new FlowLayout());
            helprootpanel.add(buttonpanel);

            JButton okbutton = new JButton("Ok");
            buttonpanel.add(okbutton);

            setContentPane(helprootpanel);
            pack();

            ActionListener oklistener = new OkListener();
            okbutton.addActionListener(oklistener);

        }

        private class OkListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                helpdialog.setVisible(false);
                helpdialog.removeAll();
                helpmenubutton.setEnabled(true);
            }
        }

        private class HelpCloseListener extends WindowAdapter {

            @Override
            public void windowClosing(WindowEvent event) {
                helpdialog.setVisible(false);
                helpdialog.removeAll();
                helpmenubutton.setEnabled(true);
                //System.out.println("Ура!!");
            }
        }
    }
}
