import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Carte {
	public static final String PATH="images\\pack";
	public static final String[] TYPECARTE= {"img1","img2","img3","img4","img5","img6","img7","img8"};
	private static int pack=1;
	private static int backpack=0;
	
	private int ligne;
	private int colonne;
	private int type;
	private int size;
	private boolean selection;
	private boolean hiden;
	private Image img;
	private Image def;

	
	public Carte() throws SlickException {
		//Constructeur par défeaut
		ligne=colonne=100;
		type=1; size=145;
		img= new Image(PATH+1+"\\"+TYPECARTE[0]+".jpg");
		def= new Image("images\\backcard\\def1.png");
		selection=false;
		hiden=true;
	}
	
	public Carte(int line, int col, int Type) throws SlickException {
		//Constructeur de carte à parametres spécifiant le type
			ligne=line;
			colonne=col;
			type=Type;
			size=145;
			img= new Image(PATH+pack+"\\"+TYPECARTE[type-1]+".jpg");
			def= new Image("images\\backcard\\def"+backpack+".png");
			selection=false;
			hiden=true;
	}
	
	public void toggleSelection() {
		//Donne à selection la valleur opposée
		selection=!selection;
	}
	
	public boolean selectionnee() {
		//Retourne la valeur de l'atribut selection 
		return selection;
	}
	
	public void toggleHide() {
		//Donne à hiden la valleur opposée
		hiden=!hiden;
	}
	
	public int getType() {
		return type;
	}
	
	public void dessinerCarte(Graphics g) {
		//Affiche la carte à l'écran
		g.drawImage(hiden?def:img, ligne, colonne);
		g.setColor(!selection?Color.orange:Color.green);
		g.drawRoundRect(ligne-2, colonne-2, size+4, size+4, 5);
		g.drawRoundRect(ligne-1, colonne-1, size+2, size+2, 5);
		g.drawRoundRect(ligne, colonne, size, size, 5);

	}

	public static int getPack() {
		return pack;
	}

	public static int getBackpack() {
		return backpack;
	}

	public static void setPack(int pack) {
		Carte.pack = pack;
	}

	public static void setBackpack(int backpack) {
		Carte.backpack = backpack;
	}
	
	
}
