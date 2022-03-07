import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class MainMemory {
	public static int[] dimension= {660,750};
	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		boolean fullscreen=false, launch=true;
		int option=5;
		//Option = valeur retournée par la fenetre swing
		//fullscreen vérifie plein écran ou pas
		//launch vérifie si on lance le jeu ou pas
		
		Gamebox monJeu = new Gamebox("Memory Game");
		AppGameContainer app = new AppGameContainer((Game) monJeu);
		app.setDisplayMode(dimension[0], dimension[1], false);
		option=javax.swing.JOptionPane.showConfirmDialog(null, "Full screen ?");
		//System.out.println(option);
		fullscreen=(option==0);
		launch=(option!=2 && option!=-1);
		if(launch) {
			if(fullscreen) {
				app.setDisplayMode(1024, 768, true);
				Tableau.setMove(182);
			}
			app.setShowFPS(false);
			app.setVSync(true);
			app.start();
		}
		
	}

}
