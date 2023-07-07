package anashepc.kanban;



import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Anashe P Choruma
 */
public class Task {

    String taskName = "";
    String taskDescription = "";
    String developerDetails = "";
    String taskId = "";
    String taskStatus = "";
    int taskDuration = 0;
    int taskNumber = 0;
    
 public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getDeveloperDetails() {
        return developerDetails;
    }

    public void setDeveloperDetails(String developerDetails) {
        this.developerDetails = developerDetails;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
  
    public int getTaskDuration() {
        return taskDuration;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    public int getTaskNumber() {
        return taskNumber;
    }

    public void setTaskDuration(int taskDuration) {
        this.taskDuration = taskDuration;
    }
    
    public boolean checkTaskDescription(){
        String displayValue = "Please enter a task description of less than 50 characters";
        if (taskDescription.isEmpty()){
            displayValue = "Please enter task description";
        }
        taskDescription = JOptionPane.showInputDialog(displayValue);
        return taskDescription.length() <= 50;
    }
    
    public String createTaskID() {
        String taskID = (taskName.substring(0, 2) + ":" + taskNumber + ":"
                + developerDetails.substring(developerDetails.length() - 3, developerDetails.length())).toUpperCase();
        return taskID;
    }
    
    public String printTaskDetails(){
        return taskStatus + " " + developerDetails + " " + taskNumber
                        + " " + taskName + " " + taskDescription
                        + " " + taskId + "  " + taskDuration;
    }
    
    public int returnTotalHours(List<Task> taskList){
        int totalHours = 0;
        for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
            Task nextTask = iterator.next();
            totalHours = totalHours + nextTask.getTaskDuration();
            
        }
        return totalHours;
    }
    
}
