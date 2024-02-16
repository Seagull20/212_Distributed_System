class processor{
private
    int myID;
    int inID; //ID received from neighbour
    int sendID;
    state state;
    int parentID;
    int startRound;
public
processor(int inputID){
    state=state.awake;
    myID=inputID;
}
String msgGenerator(){
    return "a";
}
void msgsender(){
    
}



}

enum state{
    awake,asleep,unkown,leader,
}