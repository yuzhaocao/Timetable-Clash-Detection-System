import scala.collection.immutable.List;
import java.util.*;
import javax.swing.*;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.*;

import static scala.ClashDetection.*;

class Clash_Detection implements ActionListener {
    //create only one instance of that "static" member that is shared across all instances of the class
    static JFrame home_page;//Home screen frame
    static JFrame setup_manage_page;//Set up & Manage frame
    static JFrame timetable_page;//Timetable page frame
    static JFrame clash_detection_page;//Clash Detection frame
    static JFrame setup_module_page;//Set up modules frame
    static JFrame manage_module_page;//Manage modules frame
    static JFrame setup_programme_page;//Set up modules frame
    static JFrame manage_programme_page;//Manage modules frame


    static JButton setup_manage_button;
    static JButton clash_button;
    static JButton timetable_button;
    static JButton setup_button_module;
    static JButton setup_button_programme;
    static JButton manage_button_module;
    static JButton manage_button_programme;

    static JTextField sp_programme_name_tf;
    static JComboBox sp_type_dp;

    static JComboBox cd_type_dp;
    static JTextField cd_programme_name_tf;
    static JComboBox cd_year_dp;
    static JComboBox cd_term_dp;

    static JComboBox t_type_dp;
    static JTextField t_programme_name_tf;
    static JComboBox t_year_dp;
    static JComboBox t_term_dp;

    static JComboBox mp_type_dp;
    static JTextField mp_programme_name_tf;

    static JComboBox sm_type_dp;
    static JTextField sm_programme_name_tf;
    static JComboBox sm_year_dp;
    static JComboBox sm_term_dp;
    static JComboBox sm_mtype_dp;
    static JTextField sm_module_name_tf;
    static JComboBox sm_activity_dp;
    static JComboBox sm_day_dp;
    static JComboBox sm_start_time_dp;
    static JComboBox sm_duration_dp;

    static JComboBox mm_type_dp;
    static JTextField mm_programme_name_tf;
    static JComboBox mm_year_dp;
    static JComboBox mm_term_dp;
    static JComboBox mm_mtype_dp;
    static JTextField mm_module_name_tf;
    static JComboBox mm_activity_dp;


    static JButton add_button;
    static JButton delete_button;
    static JButton search_button;
    static JButton check_button_kotlin;
    static JButton check_button_scala;


    //Driver function
    public static void main(String args[])
    {
        //Create page
        home_page = new JFrame("Timetable Clash Detection System");
        home_page.setSize(600,400);
        home_page.setLayout(null);
        home_page.setBackground(Color.white);
        //Create Label
        JLabel title = new JLabel("Timetable Clash Detection System");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(100,100,400,50);
        home_page.add(title);
        //Create buttons
        setup_manage_button = new JButton("Set up & Manage");
        clash_button = new JButton("Clash Detection");
        timetable_button = new JButton("View Timetable");
        setup_manage_button.setBounds(75,200,200,50);
        clash_button.setBounds(325,200,200,50);
        timetable_button.setBounds(200,275,200,50);

        //Add the buttons to frame
        home_page.add(setup_manage_button);
        home_page.add(clash_button);
        home_page.add(timetable_button);

        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        setup_manage_button.addActionListener(obj);
        clash_button.addActionListener(obj);
        timetable_button.addActionListener(obj);

        //Display frame
        home_page.setVisible(true);
    }

    //Function to perform actions related to button clicked
    public void actionPerformed(ActionEvent e) {
        String button=e.getActionCommand();
        if(button.equals("Set up & Manage")) {
            //brings up setup manage page
            create_setup_manage_page();
        }
        if(button.equals("Clash Detection")) {
            //brings up clash detection page
            create_clash_detection_page();
        }
        if(button.equals("View Timetable")) {
            //brings up view timetable page
            create_timetable_page();
        }
        if(button.equals("Set up module")) {
            //brings up setup module page
            create_setup_module_page();
        }
        if(button.equals("Manage module")) {
            //brings up manage module page
            create_manage_module_page();
        }
        if(button.equals("Set up programme")) {
            //brings up setup programme page
            create_setup_programme_page();
        }
        if(button.equals("Manage programme")) {
            //brings up manage programme page
            create_manage_programme_page();
        }
        if(button.equals("Check (Kotlin)")) {
            //retrieve strings of information from gui component
            String tName = cd_type_dp.getSelectedItem().toString();
            String pName = cd_programme_name_tf.getText();
            String tmName = cd_term_dp.getSelectedItem().toString();
            String yName = cd_year_dp.getSelectedItem().toString();

            //pass down to kotlin class
            Module check = new Module(tName,pName,yName,tmName,"Compulsory Modules Only","","","",9,1);
            check.searchFile();
            ArrayList<String> clash = (ArrayList<String>) check.ovlp(check.getTimetableList());

            String clashInfo = "";
            for(String item:clash){
                clashInfo += item+"\n";
            }
            //stores clash information and then show it on the dialog window
            JOptionPane.showMessageDialog(clash_detection_page, clashInfo,"Clash Detail",JOptionPane.PLAIN_MESSAGE);

        }
        if(button.equals("Check (Scala)")) {
            //retrieve strings of information from gui component
            String tName = cd_type_dp.getSelectedItem().toString();
            String pName = cd_programme_name_tf.getText();
            String yName = cd_year_dp.getSelectedItem().toString();
            String tmName = cd_term_dp.getSelectedItem().toString();

            //pass down to scala
            ProgrammeS programmes = new ProgrammeS(tName,pName,yName,tmName);
            programmes.findClash();

            List<String> clash = programmes.clash();
            String clashInfo = clash.mkString();
            //stores the clash data in to string and show on the dialog window
            JOptionPane.showMessageDialog(clash_detection_page, clashInfo,"Clash Detail",JOptionPane.PLAIN_MESSAGE);

        }
        if(button.equals("Search")) {
            //retrieve strings of information from gui component
            String tName = t_type_dp.getSelectedItem().toString();
            String pName = t_programme_name_tf.getText();
            String yName = t_year_dp.getSelectedItem().toString();
            String tmName = t_term_dp.getSelectedItem().toString();
            //pass down to kotlin class
            Module search = new Module(tName,pName,yName,tmName,"","","","",9,1);
            search.searchFile();
            search.sort();
            ArrayList<String> timetable = (ArrayList<String>) search.getTimetableList();

            String timetableInfo = "";
            for (String item : timetable) {
                timetableInfo += item+"\n";
            }
            //stores timetable information into the string and show on dialog window
            JOptionPane.showMessageDialog(clash_detection_page, timetableInfo, "Timetable List", JOptionPane.PLAIN_MESSAGE);
        }
        if(button.equals("Add Programme")) {
            //retrieve strings of information from gui component
            String tName = sp_type_dp.getSelectedItem().toString(); //.toString make the .getSelectedItem from objective to string
            String pName = sp_programme_name_tf.getText();
            //pass the info down to kotlin classes
            Programme programme = new Programme(tName ,pName);
            programme.writeToFile();


        }
        if(button.equals("Delete Programme")) {
            //retrieve strings of information from gui component
            String tName = mp_type_dp.getSelectedItem().toString();
            String pName = mp_programme_name_tf.getText();
            //pass down to the kotlin class
            Programme programme = new Programme(tName ,pName);
            programme.deleteLine();
        }
        if(button.equals("Add Module")) {
            //retrieve strings of information from gui component
            String tName = sm_type_dp.getSelectedItem().toString();
            String pName = sm_programme_name_tf.getText();
            String yName = sm_year_dp.getSelectedItem().toString();
            String tmName = sm_term_dp.getSelectedItem().toString();
            String mType = sm_mtype_dp.getSelectedItem().toString();
            String mName = sm_module_name_tf.getText();
            String aName = sm_activity_dp.getSelectedItem().toString();
            String dName = sm_day_dp.getSelectedItem().toString();
            String sName = sm_start_time_dp.getSelectedItem().toString();
            String dtName = sm_duration_dp.getSelectedItem().toString();
            int time = Integer.parseInt(sName);
            int length = Integer.parseInt(dtName);
            //pass down to kotlin class
            Module module = new Module(tName,pName,yName,tmName,mType,mName,aName,dName,time,length);
            module.writeToFile();
            //if certain information didn't pass the check, error message will be raised
            if (!module.getPostgraduateCheck()){
                JOptionPane.showMessageDialog(setup_module_page, "Postgraduate can only have one year.");
            }
            if (!module.getCompulsoryCheck()){
                JOptionPane.showMessageDialog(setup_module_page, "Already have 4 Compulsory modules.");
            }
            if (!module.getTimeCheck()){
                JOptionPane.showMessageDialog(setup_module_page, "Activity cannot Finish after 21:00.");
            }

            if (module.getPassWrite()) {
                //inform user if certain clashes caused
                Module check = new Module(tName, pName, yName, tmName, "Compulsory Modules Only", "", "", "", 9, 1);
                check.searchFile();
                ArrayList<String> clash = (ArrayList<String>) check.ovlp(check.getTimetableList());

                if (clash.size() != 0) {
                    String clashInfo = "There are Clash/es in this Programme's Term\n";
                    for (String item : clash) {
                        clashInfo += item + "\n";
                    }

                    JOptionPane.showMessageDialog(clash_detection_page, clashInfo);
                }
            }



        }
        if(button.equals("Delete Module")) {
            //retrieve strings of information from gui component
            String tName = mm_type_dp.getSelectedItem().toString();
            String pName = mm_programme_name_tf.getText();
            String yName = mm_year_dp.getSelectedItem().toString();
            String tmName = mm_term_dp.getSelectedItem().toString();
            String mType = mm_mtype_dp.getSelectedItem().toString();
            String mName = mm_module_name_tf.getText();
            String aName = mm_activity_dp.getSelectedItem().toString();
            //pass down to kotlin class
            Module module = new Module(tName,pName,yName,tmName,mType,mName,aName,"",9,1);
            module.deleteLine();

        }


    }


    //function to create Set up & Manage page
    public static void create_setup_manage_page()
    {
        //Create page
        setup_manage_page = new JFrame("Set up & Manage");
        setup_manage_page.setSize(600,400);
        setup_manage_page.setLayout(null);
        setup_manage_page.setBackground(Color.white);

        JLabel title = new JLabel("Set up & Manage");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(10,0,600,50);
        setup_manage_page.add(title);

        //Create buttons
        setup_button_programme = new JButton("Set up programme");
        manage_button_programme = new JButton("Manage programme");
        setup_button_programme.setBounds(75,100,200,50);
        manage_button_programme.setBounds(325,100,200,50);

        setup_button_module = new JButton("Set up module");
        manage_button_module = new JButton("Manage module");
        setup_button_module.setBounds(75,200,200,50);
        manage_button_module.setBounds(325,200,200,50);

        //Add the buttons to frame
        setup_manage_page.add(setup_button_module);
        setup_manage_page.add(manage_button_module);
        setup_manage_page.add(setup_button_programme);
        setup_manage_page.add(manage_button_programme);

        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        setup_button_module.addActionListener(obj);
        manage_button_module.addActionListener(obj);
        setup_button_programme.addActionListener(obj);
        manage_button_programme.addActionListener(obj);


        //Display frame
        setup_manage_page.setVisible(true);
    }

    //function to create Clash Detection page
    public static void create_clash_detection_page()
    {
        //Create page
        clash_detection_page = new JFrame("Clash Detection");
        clash_detection_page.setSize(600,400);
        clash_detection_page.setLayout(null);
        clash_detection_page.setBackground(Color.white);

        JLabel title = new JLabel("Clash Detection");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(10,0,600,50);
        clash_detection_page.add(title);

        check_button_scala = new JButton("Check (Scala)");
        check_button_scala.setBounds(425,300,150,50);
        check_button_kotlin = new JButton("Check (Kotlin)");
        check_button_kotlin.setBounds(265,300,150,50);

        //Add the button to frame
        clash_detection_page.add(check_button_scala);
        clash_detection_page.add(check_button_kotlin);
        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        check_button_scala.addActionListener(obj);
        check_button_kotlin.addActionListener(obj);

        JLabel programme_type = new JLabel("Programme Type: ");
        programme_type.setBounds(10,50,200,20);

        JLabel programme_name = new JLabel("Programme Name: ");
        programme_name.setBounds(10,80,200,20);

        JLabel programme_year = new JLabel("Programme Year: ");
        programme_year.setBounds(10,110,200,20);

        JLabel programme_term = new JLabel("Programme Term: ");
        programme_term.setBounds(10,140,200,20);


        String[] typeList = {"Undergraduate", "Postgraduate"};
        cd_type_dp = new JComboBox(typeList);
        //type_dp.setEditable(true);
        cd_type_dp.setBounds(130,50,200,20);
        //patternList.addActionListener(do_this);

        cd_programme_name_tf = new JTextField(50);
        cd_programme_name_tf.setBounds(130,80,200,20);

        String[] yearList = {"Year 1", "Year 2", "Year 3"};
        cd_year_dp = new JComboBox(yearList);
        cd_year_dp.setBounds(130,110,200,20);

        String[] termList = {"Term 1", "Term 2"};
        cd_term_dp = new JComboBox(termList);
        cd_term_dp.setBounds(130,140,200,20);


        clash_detection_page.add(programme_name);
        clash_detection_page.add(programme_type);
        clash_detection_page.add(programme_year);
        clash_detection_page.add(programme_term);

        clash_detection_page.add(cd_type_dp);
        clash_detection_page.add(cd_programme_name_tf);
        clash_detection_page.add(cd_year_dp);
        clash_detection_page.add(cd_term_dp);



        //Display frame
        clash_detection_page.setVisible(true);
    }

    public static void create_timetable_page()
    {
        //Create page
        timetable_page = new JFrame("Timetable");
        timetable_page.setSize(600,400);
        timetable_page.setLayout(null);
        timetable_page.setBackground(Color.white);

        JLabel title = new JLabel("Timetable");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(10,0,600,50);
        timetable_page.add(title);

        search_button = new JButton("Search");
        search_button.setBounds(475,300,100,50);
        //Add the button to frame
        timetable_page.add(search_button);
        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        search_button.addActionListener(obj);

        JLabel programme_type = new JLabel("Programme Type: ");
        programme_type.setBounds(10,50,200,20);

        JLabel programme_name = new JLabel("Programme Name: ");
        programme_name.setBounds(10,80,200,20);

        JLabel programme_year = new JLabel("Programme Year: ");
        programme_year.setBounds(10,110,200,20);

        JLabel programme_term = new JLabel("Programme Term: ");
        programme_term.setBounds(10,140,200,20);


        String[] typeList = {"Undergraduate", "Postgraduate"};
        t_type_dp = new JComboBox(typeList);
        //type_dp.setEditable(true);
        t_type_dp.setBounds(130,50,200,20);
        //patternList.addActionListener(do_this);

        t_programme_name_tf = new JTextField(50);
        t_programme_name_tf.setBounds(130,80,200,20);

        String[] yearList = {"Year 1", "Year 2", "Year 3"};
        t_year_dp = new JComboBox(yearList);
        t_year_dp.setBounds(130,110,200,20);

        String[] termList = {"Term 1", "Term 2"};
        t_term_dp = new JComboBox(termList);
        t_term_dp.setBounds(130,140,200,20);


        timetable_page.add(programme_name);
        timetable_page.add(programme_type);
        timetable_page.add(programme_year);
        timetable_page.add(programme_term);

        timetable_page.add(t_type_dp);
        timetable_page.add(t_programme_name_tf);
        timetable_page.add(t_year_dp);
        timetable_page.add(t_term_dp);


        timetable_page.setVisible(true);

    }

    public static void create_setup_module_page()
    {
        //Create page
        setup_module_page = new JFrame("Set up Modules");
        setup_module_page.setSize(600,400);
        setup_module_page.setLayout(null);
        setup_module_page.setBackground(Color.white);

        JLabel title = new JLabel("Set Up Modules");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(10,0,600,50);
        setup_module_page.add(title);

        JLabel programme_type = new JLabel("Programme Type: ");
        programme_type.setBounds(10,50,200,20);

        JLabel programme_name = new JLabel("Programme Name: ");
        programme_name.setBounds(10,80,200,20);

        JLabel programme_year = new JLabel("Programme Year: ");
        programme_year.setBounds(10,110,200,20);

        JLabel programme_term = new JLabel("Programme Term: ");
        programme_term.setBounds(10,140,200,20);

        JLabel module_type = new JLabel("Module Type: ");
        module_type.setBounds(10,170,200,20);

        JLabel module_name = new JLabel("Module Name: ");
        module_name.setBounds(10,200,200,20);

        JLabel activity_name = new JLabel("Activity Name: ");
        activity_name.setBounds(10,230,200,20);

        JLabel day = new JLabel("Day in Week: ");
        day.setBounds(10,260,200,20);

        JLabel start_time = new JLabel("Starting Hour: ");
        start_time.setBounds(10,290,200,20);

        JLabel duration = new JLabel("Duration (hr): ");
        duration.setBounds(10,320,200,20);

        String[] typeList = {"Undergraduate", "Postgraduate"};
        sm_type_dp = new JComboBox(typeList);
        sm_type_dp.setBounds(130,50,200,20);


        sm_programme_name_tf = new JTextField(50);
        sm_programme_name_tf.setBounds(130,80,200,20);

        String[] yearList = {"Year 1", "Year 2", "Year 3"};
        sm_year_dp = new JComboBox(yearList);
        sm_year_dp.setBounds(130,110,200,20);

        String[] termList = {"Term 1", "Term 2"};
        sm_term_dp = new JComboBox(termList);
        sm_term_dp.setBounds(130,140,200,20);

        String[] mtermList = {"Compulsory", "Optional"};
        sm_mtype_dp = new JComboBox(mtermList);
        sm_mtype_dp.setBounds(130,170,200,20);

        sm_module_name_tf = new JTextField(50);
        sm_module_name_tf.setBounds(130,200,200,20);

        String[] activityList = {"Lecture", "Lab", "Tutorial"};
        sm_activity_dp = new JComboBox(activityList);
        sm_activity_dp.setBounds(130,230,200,20);

        String[] dayList = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        sm_day_dp = new JComboBox(dayList);
        sm_day_dp.setBounds(130,260,200,20);

        Integer[] start_timeList = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        sm_start_time_dp = new JComboBox(start_timeList);
        sm_start_time_dp.setBounds(130,290,200,20);

        Integer[] durationList = {1, 2};
        sm_duration_dp = new JComboBox(durationList);
        sm_duration_dp.setBounds(130,320,200,20);

        setup_module_page.add(programme_name);
        setup_module_page.add(programme_type);
        setup_module_page.add(programme_year);
        setup_module_page.add(programme_term);
        setup_module_page.add(module_type);
        setup_module_page.add(module_name);
        setup_module_page.add(activity_name);
        setup_module_page.add(day);
        setup_module_page.add(start_time);
        setup_module_page.add(duration);

        setup_module_page.add(sm_module_name_tf);

        setup_module_page.add(sm_activity_dp);
        setup_module_page.add(sm_type_dp);
        setup_module_page.add(sm_mtype_dp);
        setup_module_page.add(sm_programme_name_tf);
        setup_module_page.add(sm_year_dp);
        setup_module_page.add(sm_term_dp);
        setup_module_page.add(sm_day_dp);
        setup_module_page.add(sm_start_time_dp);
        setup_module_page.add(sm_duration_dp);


        add_button = new JButton("Add Module");
        add_button.setBounds(425,300,150,50);
        //Add the button to frame
        setup_module_page.add(add_button);
        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        add_button.addActionListener(obj);

        setup_module_page.setVisible(true);

    }

    public static void create_manage_module_page()
    {
        //Create page
        manage_module_page = new JFrame("Manage Modules");
        manage_module_page.setSize(600,400);
        manage_module_page.setLayout(null);
        manage_module_page.setBackground(Color.white);

        JLabel title = new JLabel("Manage Modules");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(10,0,600,50);
        manage_module_page.add(title);


        delete_button = new JButton("Delete Module");
        delete_button.setBounds(425,300,150,50);
        manage_module_page.add(delete_button);


        JLabel programme_type = new JLabel("Programme Type: ");
        programme_type.setBounds(10,50,200,20);

        JLabel programme_name = new JLabel("Programme Name: ");
        programme_name.setBounds(10,80,200,20);

        JLabel programme_year = new JLabel("Programme Year: ");
        programme_year.setBounds(10,110,200,20);

        JLabel programme_term = new JLabel("Programme Term: ");
        programme_term.setBounds(10,140,200,20);

        JLabel module_type = new JLabel("Module Type: ");
        module_type.setBounds(10,170,200,20);

        JLabel module_name = new JLabel("Module Name: ");
        module_name.setBounds(10,200,200,20);

        JLabel activity_name = new JLabel("Activity Name: ");
        activity_name.setBounds(10,230,200,20);


        String[] typeList = {"Undergraduate", "Postgraduate"};
        mm_type_dp = new JComboBox(typeList);
        mm_type_dp.setBounds(130,50,200,20);


        mm_programme_name_tf = new JTextField(50);
        mm_programme_name_tf.setBounds(130,80,200,20);

        String[] yearList = {"Year 1", "Year 2", "Year 3"};
        mm_year_dp = new JComboBox(yearList);
        mm_year_dp.setBounds(130,110,200,20);

        String[] termList = {"Term 1", "Term 2"};
        mm_term_dp = new JComboBox(termList);
        mm_term_dp.setBounds(130,140,200,20);

        String[] mtypeList = {"Compulsory", "Optional"};
        mm_mtype_dp = new JComboBox(mtypeList);
        mm_mtype_dp.setBounds(130,170,200,20);

        mm_module_name_tf = new JTextField(50);
        mm_module_name_tf.setBounds(130,200,200,20);

        String[] activityList = {"Lecture", "Lab", "Tutorial"};
        mm_activity_dp = new JComboBox(activityList);
        mm_activity_dp.setBounds(130,230,200,20);


        manage_module_page.add(programme_name);
        manage_module_page.add(programme_type);
        manage_module_page.add(programme_year);
        manage_module_page.add(programme_term);
        manage_module_page.add(module_type);
        manage_module_page.add(module_name);
        manage_module_page.add(activity_name);

        manage_module_page.add(mm_module_name_tf);
        manage_module_page.add(mm_activity_dp);
        manage_module_page.add(mm_type_dp);
        manage_module_page.add(mm_programme_name_tf);
        manage_module_page.add(mm_mtype_dp);
        manage_module_page.add(mm_year_dp);
        manage_module_page.add(mm_term_dp);


        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        delete_button.addActionListener(obj);

        manage_module_page.setVisible(true);

    }

    public static void create_setup_programme_page()
    {
        //Create page
        setup_programme_page = new JFrame("Set up Programme");
        setup_programme_page.setSize(600,400);
        setup_programme_page.setLayout(null);
        setup_programme_page.setBackground(Color.white);

        JLabel title = new JLabel("Set Up Programme");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(10,0,600,50);
        setup_programme_page.add(title);

        JLabel programme_type = new JLabel("Programme Type: ");
        programme_type.setBounds(10,50,200,20);

        String[] typeList = {"Undergraduate", "Postgraduate"};
        sp_type_dp = new JComboBox(typeList);
        //type_dp.setEditable(true);
        sp_type_dp.setBounds(130,50,200,20);
        //patternList.addActionListener(do_this);

        JLabel programme_name = new JLabel("Programme Name: ");
        programme_name.setBounds(10,80,200,20);

        sp_programme_name_tf = new JTextField(50);
        sp_programme_name_tf.setBounds(130,80,200,20);

        setup_programme_page.add(programme_name);
        setup_programme_page.add(programme_type);

        setup_programme_page.add(sp_programme_name_tf);
        setup_programme_page.add(sp_type_dp);


        add_button = new JButton("Add Programme");
        add_button.setBounds(425,300,150,50);
        //Add the button to frame
        setup_programme_page.add(add_button);

        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        add_button.addActionListener(obj);

        setup_programme_page.setVisible(true);

    }

    public static void create_manage_programme_page()
    {
        //Create page
        manage_programme_page = new JFrame("Manage Programme");
        manage_programme_page.setSize(600,400);
        manage_programme_page.setLayout(null);
        manage_programme_page.setBackground(Color.white);

        JLabel title = new JLabel("Manage Programme");
        title.setFont(new Font("Serif", Font.PLAIN, 28));
        title.setBounds(10,0,600,50);
        manage_programme_page.add(title);


        delete_button = new JButton("Delete Programme");
        delete_button.setBounds(425,300,150,50);
        manage_programme_page.add(delete_button);


        JLabel programme_type = new JLabel("Programme Type: ");
        programme_type.setBounds(10,50,200,20);

        String[] typeList = {"Undergraduate", "Postgraduate"};
        mp_type_dp = new JComboBox(typeList);
        //type_dp.setEditable(true);
        mp_type_dp.setBounds(130,50,200,20);
        //patternList.addActionListener(do_this);

        JLabel programme_name = new JLabel("Programme Name: ");
        programme_name.setBounds(10,80,200,20);

        mp_programme_name_tf = new JTextField(50);
        mp_programme_name_tf.setBounds(130,80,200,20);

        manage_programme_page.add(programme_name);
        manage_programme_page.add(programme_type);

        manage_programme_page.add(mp_programme_name_tf);
        manage_programme_page.add(mp_type_dp);

        //Create an object
        Clash_Detection obj=new Clash_Detection();
        //Associate ActionListener with the buttons
        delete_button.addActionListener(obj);


        manage_programme_page.setVisible(true);

    }



}