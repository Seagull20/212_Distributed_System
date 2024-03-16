import java.util.List;

public class LCR {
    private List<processor> processors;
    private boolean leaderElected;
    private int round;

    public LCR(network ring) {
        this.processors = ring.getList();
        this.leaderElected = false;
        this.round = 0;
        startElection();
    }

    private void startElection() {
        System.out.println("startElection Called");
        // Logic to determine the max rounds if needed, or use a while loop with a
        // condition for leader election
        for (processor processor : processors) {
            System.out.println("ID: "+processor.getId()+" "+"start round: "+processor.getStartRound());
        }
        while (!leaderElected) {
            round++;

            // Wake up the processors that should start in this round
                for (processor proc : processors) {
                    if(proc.roundCheck(round)){
                        proc.sendMessage(new Message(proc.getId(), proc.getMaxID(), Message.MessageType.ELECTION)); 
                    }
                }
                for (processor processor : processors) {
                    if (processor.isLeader()) {
                        leaderElected = true;
                        break;
                    }
                    
                }
    }
    System.out.println("The time complexity is " + round);
}

}
