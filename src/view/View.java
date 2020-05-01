package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    public void printMenu()
		{
			System.out.println("1. Carga de datos");
			System.out.println("2. Requerimiento funcional 2");
			System.out.println("3. Requerimiento funcional 3");
			System.out.println("4. Dar Informacion del RedBlackBST");
			System.out.println("5. Exit");
			System.out.println("------------------------------------------------------------------------");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
			System.out.println("------------------------------------------------------------------------");

		}
		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
			for( int i = 0; i<modelo.giveSizeRedBlackBST(); i++){
				System.out.println("");
				
			}
		}
}
