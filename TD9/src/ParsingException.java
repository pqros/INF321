@SuppressWarnings("serial")
public class ParsingException extends Exception{
	
	ParsingException(String s){
		System.out.println("variable $" + s + "inexistante");
	}
	ParsingException(Operator o){
		System.out.println("operande manquant pour l'operateur " + Calculator.toChar(o));
	}
	
	ParsingException(boolean b){//b true -> left parenthesis missing
		if(!b) System.out.println("parenthese fermante manquante");else {
			System.out.println("parenthese ouverte manquante");
		}
	}
	
	ParsingException(double d){
		System.out.println("point inattendu apres le nombre " + d);
	}
	
	ParsingException(char c){
		System.out.println("caractere "+ c + " inconnu");
	}
}
