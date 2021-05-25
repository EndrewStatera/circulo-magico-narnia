
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoubleLinkedListOfInteger dl = new DoubleLinkedListOfInteger();
		for(int i = 0; i <= 20; i++) {
			dl.addNoCirculoMagico(i);
			System.out.println(dl.lista());
		}
		System.out.println("\n\n");
		for(int i = 21; i <= 500000; i++) {
			dl.addNoCirculoMagico(i);
			if(i % 100000 == 0) {
				System.out.println(dl.ladoRei());
				System.out.println("Numero de operacoes: "+dl.cont);
				System.out.println();
			}
		}
		System.out.println("End");
	}
	
	public static void automatiza(int limite, DoubleLinkedListOfInteger dl) {
		
	}

}
