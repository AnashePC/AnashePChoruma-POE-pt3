package anashepc.kanban;



import anashepc.kanban.Account;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class KanbanTasks {

    public static Account account = new Account();
    public static List<Task> taskList = new ArrayList<Task>();
    public static String[] developerDetails;
    public static String[] taskNames;
    public static String[] taskIds;
    public static String[] taskDurations;
    public static String[] taskStatuses;
    
  
    public static void main(String[] args) {

        /*
            Allow the user to register on the system
        */
        
//  capture firstname
        String firstName = JOptionPane.showInputDialog("Enter First Name");
        account.firstName = firstName;
        
//  capture surname
        String surname = JOptionPane.showInputDialog("Please Enter Surname");
        account.lastName = surname;
        
//  Register User
        String message = registerUser();
        JOptionPane.showMessageDialog(null, account.username + message);
        boolean isLoggedIn = false;
        
        /*
            Allow the user to Login in with his newly created login details - 
            validate the login details and continue to prompt user for login details 
            until such time as the correct details have been entered      
        */

        while (!isLoggedIn) {
            isLoggedIn = loginUser();
            JOptionPane.showMessageDialog(null, returnLoginStatus(isLoggedIn));
        }
        
        /*
           Asks the user to choose from a list of options
            Add Tasks
            Show Task Report
            Search By Task Name
            Search By Developer Name
            Delete a Task
            Quit
        */
        
        Task task = new Task();
        Object[] possibleValues = {"1) Add Task", "2) Show Report",
            "3) Search By Task Name", "4) Search by Developer Name",
            "5) Delete Task", "6) Quit"};
        Object selectedValue = null;
        while (selectedValue != "6) Quit") {
            selectedValue = JOptionPane.showInputDialog(null,
                    "Select One", "Input", JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, possibleValues[0]);

            int totalTasks = 0;
            switch (selectedValue.toString()) {
                case "1) Add Task":
                    /*
                        Prompt user to choose the number of tasks to be added
                    */
                    int totalDuration = 0;
                    String numTasks = JOptionPane.showInputDialog("How many tasks are you addding?");
                    int numberOfTasks = Integer.parseInt(numTasks);
                    System.out.println("Tasks to be added : " + numberOfTasks);
                    
                    taskNames = new String[numberOfTasks];
                    developerDetails = new String[numberOfTasks];
                    taskDurations = new String[numberOfTasks];
                    taskStatuses = new String[numberOfTasks];
                    taskIds = new String[numberOfTasks];
                    
                    /*
                        Allow user to capture the task details for as many
                        tasks as the user has requested to add
                    */
                    while (totalTasks < numberOfTasks) {
                        String taskName = JOptionPane.showInputDialog("Enter Task Name");
                        task.setTaskName(taskName);
                        taskNames[totalTasks] = taskName;
                        task.setTaskNumber(totalTasks);
                        boolean validDescr = false;
                        while (!validDescr) {
                            validDescr = task.checkTaskDescription();
                        }
                        JOptionPane.showMessageDialog(null, "Task Successfully Captured");
                        String devDetails = JOptionPane.showInputDialog("Please Enter Developer Details");
                        task.setDeveloperDetails(devDetails);
                        developerDetails[totalTasks]=devDetails;
                        String taskDuration = JOptionPane.showInputDialog("Enter Task Duration");
                        task.setTaskDuration(Integer.parseInt(taskDuration));
                        taskDurations[totalTasks]=taskDuration;
                        totalDuration = totalDuration + task.getTaskDuration();
                        task.setTaskId(task.createTaskID());
                        taskIds[totalTasks]=task.getTaskId();
                        Object[] values = {"To Do", "Done", "Doing"};
                        Object valueSelected = JOptionPane.showInputDialog(null,
                                "Select One", "Input", JOptionPane.INFORMATION_MESSAGE, null,
                                values, values[0]);
                        task.setTaskStatus((String) valueSelected);
                        taskStatuses[totalTasks]=task.getTaskStatus();
                        JOptionPane.showMessageDialog(null, "Task Details: " + task.printTaskDetails());
                        taskList.add(task);
                        task = new Task();
                        totalTasks++;
                    }
                    JOptionPane.showMessageDialog(null, "Total Duartion Of Tasks: " + task.returnTotalHours(taskList));
                    break;
                case "2) Show Report":
                    displayReports(taskList);
                    break;
                case "3) Search By Task Name":
                    searchByTaskName(taskList);
                    break;
                case "4) Search By Developer Name":
                    searchByDeveloper(taskList);
                    break;
                case "5) Delete a Task":
                    deleteTasks(taskList);
                    break;
            }
        }

    }

    public static boolean checkUserName(String username) {
//
        if (!username.contains("_")) {
            return false;
//        
        } else if (username.length() > 5) {
            return false;
        }

        return true;
    }

    public static boolean checkPasswordComplexity(String password) {
//    check if password is at least 8 chars
        if (password.length() < 8) {
            return false;
        }
//       check that password contains a special character
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        boolean hasSpecialChar = m.find();
        if (!hasSpecialChar) {
            return false;
        }
//        check that password contains a number
        p = Pattern.compile("[0-9]");
        m = p.matcher(password);
        boolean containsNumber = m.find();
        if (!containsNumber) {
            return false;
        }

//        check that password contains 1 Capital letter
        p = Pattern.compile("[A-Z]");
        m = p.matcher(password);
        boolean containsCapitals = m.find();
        return containsCapitals;
    }

    /*
        Allow the user to register. Verify username and passwords meet requirements
    */
    public static String registerUser() {
        //Allow user to capture username
        String userName = JOptionPane.showInputDialog("Please enter username\n(Max 5 characters and must include an underscore_) ");
        account.username = userName;
        boolean isValidUserName = false;
        //validate username
        while (!isValidUserName) {
            isValidUserName = checkUserName(userName);
            if (!isValidUserName) {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, \nplease ensure that your username contains an underscore\n and is no more than 5 characters in length.");
                userName = JOptionPane.showInputDialog("Please enter username\n(Max 5 characters and must include an underscore_) ");
                account.username = userName;
            }
        }

        //allow user to capture password
        String password = JOptionPane.showInputDialog("Please enter password \n(Must contain at least 8 characters, a capital letter,\n a number and a special character)");
        account.password = password;
        boolean isValidPassword = false;
        //  validate password
        while (!isValidPassword) {
            isValidPassword = checkPasswordComplexity(password);
            if (!isValidPassword) {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted, \nplease ensure that the password contains at least 8 characters,\n a capital letter, a number and a special character.");
                password = JOptionPane.showInputDialog("Please enter password \n(Must contain at least 8 characters, a capital letter,\n a number and a special character)");
                account.password = password;
            }
        }

        return " has been registered successfully";
    }

    /*
        Allow the user to login, verify login details
    */
    public static boolean loginUser() {
        //Allow User to login
        String username = JOptionPane.showInputDialog("Enter Username ");
        String password = JOptionPane.showInputDialog("Enter Password ");
        boolean usernameCorrect = false;
        boolean passwordCorrect = false;
        if (username.equalsIgnoreCase(account.getUsername())) {
            usernameCorrect = true;
        }
        if (password.equals(account.getPassword())) {
            passwordCorrect = true;
        }
        return usernameCorrect && passwordCorrect;
    }

    public static String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return ("Welcome to EasyKanban");
        } else {
            return (" Incorrect username or password");
        }
    }

    /*
        Display the follwoing reports:
            All the Tasks that are in Done State
            All Tasks captured
            The Task with the longest duration
    */
    private static void displayReports(List<Task> taskList) {
        int duration = 0;
        StringBuilder doneTaskList = new StringBuilder("List of All Done Tasks");
        StringBuilder fullTaskList = new StringBuilder("List Of All Tasks in the System");
        String largestDurationMessage = "";
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.taskDuration > duration){
                duration = task.taskDuration;
                largestDurationMessage = "Longest Duration Task:\nDeveloper Details : " + task.developerDetails 
                        + " task Duration : " + task.taskDuration;
            }
            if(task.taskStatus.equalsIgnoreCase("Done")){
//                System.out.println("Task List : " + task.developerDetails + " " +  
//                        task.taskName + " " + task.taskDuration);
                doneTaskList.append("\nDeveloper Details : " + task.developerDetails 
                        + " task Name : " +  task.taskName + " Task Duration : " + task.taskDuration);
//                Add this to the display ,essage thingie
            }
//                        Add this to display all tasks message thingie
            fullTaskList.append("\n Developer Details : " + task.developerDetails 
                    + " Task Id " +  task.taskId + " Task Name : " + task.taskName 
                    + " Task Description : " + task.taskDescription + " task Duration : " + task.taskDuration
                    + " Task Status : " + task.taskStatus );
        }
        JOptionPane.showMessageDialog(null, doneTaskList);
        JOptionPane.showMessageDialog(null, fullTaskList);
        JOptionPane.showMessageDialog(null,largestDurationMessage);
    }

    /*
        Search for a task by Task Name
    */
    private static void searchByTaskName(List<Task> taskList) {
       String taskName = JOptionPane.showInputDialog(null,
                    "Choose the task to search on ", "Input", JOptionPane.INFORMATION_MESSAGE, null,
                    taskNames, taskNames[0]).toString();
      
        for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
            Task task = iterator.next();
           
            if(task.taskName.equalsIgnoreCase(taskName)){
               JOptionPane.showMessageDialog(null, "Task Name : " + task.taskName
                       + "\nDeveloper : " + task.developerDetails 
                       + "\nTask Status : " + task.taskStatus);
            }
        }
    }

    /*
        Search for All Tasks by Developer Details
    */
    private static void searchByDeveloper(List<Task> taskList) {
        String developerDetail = JOptionPane.showInputDialog(null,
                    "Select Developer", "Input", JOptionPane.INFORMATION_MESSAGE, null,
                    developerDetails, developerDetails[0]).toString();
        StringBuilder developerTaskList = new StringBuilder("List Of All Tasks for " + developerDetail);
        for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
            Task task = iterator.next();
            if(task.developerDetails.equalsIgnoreCase(developerDetail)){
                developerTaskList.append("\n Task Name : " + task.taskName 
                    + " Task Status:  " + task.taskStatus );
               
            }
        }
        JOptionPane.showMessageDialog(null, developerTaskList);
    }

    /*
        Delete Tasks by Task Name
    */
    private static void deleteTasks(List<Task> taskList) {
         String taskName = JOptionPane.showInputDialog(null,
                    "Slect the task to ne delete", "Input", JOptionPane.INFORMATION_MESSAGE, null,
                    taskNames, taskNames[0]).toString();
       Task deleteTask = null;
        for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
            Task task = iterator.next();
            if(task.taskName.equalsIgnoreCase(taskName)){
              deleteTask = task;
              break;
            }
        }
        taskList.remove(deleteTask);
    }
}
