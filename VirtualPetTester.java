/**
 * Created by David on 10/15/2017.
 */
public class VirtualPetTester extends VirtualPet
{
    public static void main(String[] args)
    {
        System.out.println("Initializing new VirtualPet");
        VirtualPet face = new VirtualPet();

        System.out.println("The default stats are: ");
        face.printStats();

        System.out.println("Money after investing should be 120");
        face.invest();
        face.printStats();

        face.resetGame();
        System.out.println("Pet should go to jail and have 90 dollars");
        face.moneyLaunder();
        face.moneyLaunder();
        face.relax();
        face.relax();
        face.moneyLaunder();
        face.moneyLaunder();
        face.printStats();

        face.resetGame();
        System.out.println("Pet should die, game should restart");
        face.workOvertime();
        face.workOvertime();
        face.printStats();
    }
}