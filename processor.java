class processor {
    private 
    int myID; // unique ID
    processor nextProcessor;
    boolean isActive;
    boolean isLeader;
    boolean isleaderAnnouncementReceived;
    int maxID;
    int startRound;
    public
    
    processor() {
    }

    processor(int inputmyID, int startRound) {
        myID = inputmyID;
        isActive=false;
        if (startRound==1) {
            isActive=true;
        }
        maxID=myID;
        this.startRound=startRound;
    }

    void receiveMsg(Message message) {
        if (isActive) {
            switch (message.getType()) {
                case ELECTION:
                    processElectionMsg(message);
                    break;
                case LEADER_ANNOUNCEMENT:
                
                processLeaderAnnouncement(message);
                break;
                default:
                    break;
            }
        }
    }

    void processElectionMsg(Message message) {
        if (isActive) {
            if (message.getContent() > maxID) {
            maxID = message.getContent();
            sendMessage(new Message(myID, maxID, Message.MessageType.ELECTION));
        } else if (message.getContent() == myID) {
            isLeader = true;
            System.out.println("Leader elected: " + myID);
            // Broadcast leader announcement
            sendMessage(new Message(myID, myID, Message.MessageType.LEADER_ANNOUNCEMENT));
        }
        }
        
    }

    void processLeaderAnnouncement(Message message) {
        isLeader = message.getContent() == this.myID;
        if (!isLeader && !isleaderAnnouncementReceived) {
            // Forward the leader announcement to the next processor
            sendMessage(new Message(myID, message.getContent(), Message.MessageType.LEADER_ANNOUNCEMENT));
            isleaderAnnouncementReceived=true;
        } else {
            System.out.println("Leader announcement received by all: " + myID);
            System.out.println("Communication complexity: "+ new Message(0, 0, null).getMessageCount());
        }
    }

    public void sendMessage(Message message) {
        if (nextProcessor != null && isActive) {
            nextProcessor.receiveMsg(message);
        }
    }


    public void setNextProcessor(processor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    boolean roundCheck(int currentRound){
        if(currentRound>=startRound){
            if (currentRound==startRound) {
                isActive=true;
            }
            return true;
        }else{
            return false;
        }
        
    }

    public boolean isLeader() {
        return isLeader;
    }

    public int getId() {
        return myID;
    }

    int getMaxID(){
        return maxID;
    }

    int getStartRound(){
        return startRound;
    }
}