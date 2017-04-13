package mplftaskmanager;
import java.io.Serializable;

public class TargetObject implements Serializable{
    private String client;
    private String task;
    private String hasMade;
    private String stage;
    private String spentTime;
    private int spentMoney;
    private boolean state;

    TargetObject(String client,String task,String hasMade,String stage,
            String spentTime, int spentMoney,boolean state){
        this.client=client;
        this.task=task;
        this.hasMade=hasMade;
        this.stage=stage;
        this.spentTime=spentTime;
        this.spentMoney=spentMoney;
        this.state=state;
    }
    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }
    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }
    /**
     * @return the task
     */
    public String getTask() {
        return task;
    }
    /**
     * @param task the task to set
     */
    public void setTask(String task) {
        this.task = task;
    }
    /**
     * @return the hasMade
     */
    public String getHasMade() {
        return hasMade;
    }
    /**
     * @param hasMade the hasMade to set
     */
    public void setHasMade(String hasMade) {
        this.hasMade = hasMade;
    }
    /**
     * @return the stage
     */
    public String getStage() {
        return stage;
    }
    /**
     * @param stage the stage to set
     */
    public void setStage(String stage) {
        this.stage = stage;
    }
    /**
     * @return the spentTime
     */
    public String getSpendTime() {
        return spentTime;
    }
    /**
     * @param spentTime the spentTime to set
     */
    public void setSpendTime(String spentTime) {
        this.spentTime = spentTime;
    }
    /**
     * @return the spentMoney
     */
    public int getCosts() {
        return spentMoney;
    }
    /**
     * @param spentMoney the spentMoney to set
     */
    public void setCosts(int spentMoney) {
        this.spentMoney = spentMoney;
    }
    /**
     * @return the state
     */
    public boolean getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(boolean state) {
        this.state=state;        
    }
}
