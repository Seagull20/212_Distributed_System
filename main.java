public class main {

	public static void main(String[] args){
		network mainRing=new network(true,1);
        LCR lcrForTask1 = new LCR(mainRing);
        System.out.println("the max id: "+mainRing.getMaxID());
	
	}
}