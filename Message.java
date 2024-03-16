public class Message {
    private final int senderId;
    private final int content;
    private final MessageType type;
    private static int messageCount;

    // Enum to define message types
    public enum MessageType {
        ELECTION,
        LEADER_ANNOUNCEMENT
    }
    public Message(int senderId, int content, MessageType type) {
        this.senderId = senderId;
        this.content = content;
        this.type = type;
        ++messageCount;
    }

    

    public int getSenderId() {
        return senderId;
    }

    public int getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public int getMessageCount(){
        return messageCount;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId=" + senderId +
                ", content=" + content +
                ", type=" + type +
                '}';
    }
}
