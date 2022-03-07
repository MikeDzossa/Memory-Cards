import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	public static Sound hover;
	public static Sound click;
	public static Sound choice;
	public static Sound end;
	public static Sound mismarch;
	public static Sound found;
	public static boolean volume;
	
	
	public static void initialise() throws SlickException {
		//Initialise tout les sons déclarés
		hover= new Sound("sounds\\hover2.wav");
		click= new Sound("sounds\\click.wav");
		choice= new Sound("sounds\\choice.wav");
		mismarch= new Sound("sounds\\mismarch.wav");
		found= new Sound("sounds\\found.wav");
		end= new Sound("sounds\\end.wav");
		volume=true;
	}
	
	public static void toggleVolume() {
		//Donne a volume la valeur contraire pour alterner entre voix et muet
		volume=!volume;
	}
	
	
	
}
