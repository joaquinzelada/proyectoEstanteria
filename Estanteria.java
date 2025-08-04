import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Estanteria {
    public static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<String[]> librosLeidos = new ArrayList<>();
        ArrayList<String[]> librosPendientes = new ArrayList<>(); 
        cargarEstanteriaLeidosEnArchivo(librosLeidos);
        cargarEstanteriaPendientesEnArchivo(librosPendientes);
        //Array list es más fácil que un arreglo común
        programa(librosLeidos, librosPendientes);


    }
    public static void programa(ArrayList<String[]> librosLeidos, ArrayList<String[]> librosPendientes){
        String input = "";
        while (!input.equalsIgnoreCase("Q")){
  
            System.out.println("------------[Libros leídos:]---------------");
            imprimirEstanteria(librosLeidos);
            System.out.println(" ");
            System.out.println("------------[Libros pendientes:]-----------");
            imprimirEstanteria(librosPendientes);        
            System.out.println(" ");

            menuPrincipal(librosLeidos, librosPendientes);
            cargaDeDatos(librosLeidos, librosPendientes);
            System.out.println("Pulsá 'Enter' para continuar o 'Q' para salir.");
            input = entrada.nextLine();
        }
        System.out.println("------------[Libros leídos:]---------------");
        imprimirEstanteria(librosLeidos);
        System.out.println(" ");
        System.out.println("------------[Libros pendientes:]-----------");
        imprimirEstanteria(librosPendientes);

        System.out.println("Saliendo de la biblioteca xd");

    }
    public static void menuPrincipal(ArrayList<String[]> librosLeidos, ArrayList<String[]> librosPendientes){
        System.out.println("Presione Enter si desea cargar libros");
        System.out.println("Presiona 'Q' para salir");
        System.out.println("Presiona R para removar algún libro");

        String input = entrada.nextLine();
        if (input.equalsIgnoreCase("Q")) {
            System.exit(0);
        }
        else if (input.equalsIgnoreCase("R")){
            int tipoCarga = obtenerCarga();
            if (tipoCarga == 1) {
                borrarLibroEstanteriaL(librosLeidos);
            } else {
                borrarLibroEstanteriaP(librosPendientes);
            }
            menuPrincipal(librosLeidos, librosPendientes);
        }


    }

    public static void cargaDeDatos(ArrayList<String[]> estanteriaLeidos, ArrayList<String[]> estanteriaPendientes) {
        char continuar = 's';
        while(continuar == 's'){
            String libro = obtenerLibro();
            String autor = obtenerAutor();
            String fecha = obtenerFecha();
        
            int tipoCarga = obtenerCarga();

            String[] nuevoLibro = {libro, autor, fecha}; //String[], es un arreglo con 3 columnas, las 3 posiciones: 0: libro; 1: autor; 2: fechaIngreso

            if (tipoCarga == 1) {
                estanteriaLeidos.add(nuevoLibro);
                guardarEstanteriaLeidosEnArchivo(nuevoLibro);
            } else {
                estanteriaPendientes.add(nuevoLibro);
                guardarEstanteriaPendientesEnArchivo(nuevoLibro);
            }

            continuar = continuarAgregando();

        }
    }

    public static int obtenerCarga() {
        int tipoCarga = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.println(" ");
                System.out.println("¿A qué estantería?");
                System.out.println("[1] Libros leídos");
                System.out.println("[2] Libros pendientes");
                tipoCarga = Integer.parseInt(entrada.nextLine());
                if (tipoCarga == 1 || tipoCarga == 2) {
                    valido = true;
                } else {
                    System.out.println("Opción no válida");
                }
            } catch (Exception exc) {
                System.out.println("Entrada inválida. Intente nuevamente.");
            }
        }
        return tipoCarga;
    }

    public static String obtenerLibro() {
        System.out.println("Ingrese el nombre del libro, ej: Ficciones");
        return entrada.nextLine();
    }

    public static String obtenerAutor() {
        System.out.println("Ingrese el nombre del autor, ej: Jorge Luis Borges");
        return entrada.nextLine();
    }

    public static String obtenerFecha() {
        System.out.println("Ingrese la fecha de ingreso, ej: 13/5/2025");
        return entrada.nextLine();
    }
    
    public static void imprimirEstanteria(ArrayList<String[]> estanteria){
        if (estanteria.isEmpty()){
            System.out.println("La estanteria está vacía.");
        }
        else{
            for (int k = 0; k < estanteria.size();k++){
                System.out.println(k); 
                String [] libro = estanteria.get(k); //Primero recorre el libro, obtiene el libro y lo guarda en el arreglo (de 3 columnas)
                //Ahora a las secciones: nombre |  autor  | fecha 
                for (int i = 0; i < libro.length; i++){
                        System.out.print(libro[i] + " |");

                }
                System.out.println();
            }
           
        }

    }
    public static char continuarAgregando(){
        boolean valido = false;
        char continuar = 'n';
        while (!valido) {
            try {
                System.out.println("¿Desea continuar agregando libros?  s/n");
                String input = entrada.nextLine();
                if (input.length() > 0) {
                    continuar = input.charAt(0);
                    if (continuar == 's' || continuar == 'n') {
                        valido = true;
                    } else {
                        System.out.println("Opción no válida, ingrese 's' o 'n'");
                    }
                } else{
                    System.out.print("Entrada vacía, intentá denuevo");
                }
            } catch (Exception exc) {
                System.out.println("Entrada inválida. Intente nuevamente.");
            }
        }
        return continuar;
    }
    public static void guardarEstanteriaLeidosEnArchivo(String[] libro){
        String filaLibro = libro[0] + " | " + libro[1] + " | " + libro[2] + " | \n"; //Básicamente, creo un String con el formato de: titulo | autor | fecha. Este String es guardado como una fila de texto,en un archivo estanteria.txt
        try {
            Files.write(Paths.get("estanteriaL.txt"), filaLibro.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Libro guardado");       
        }
        catch (IOException e) {
            System.out.println("Error de guardado");

        }

    }
    public static void guardarEstanteriaPendientesEnArchivo(String[] libro){
        String filaLibro = " | " + libro[0] + " | " + libro[1] + " | " + libro[2] + " | \n";  //Básicamente, creo un String con el formato de: titulo | autor | fecha. Este String es guardado como una fila de texto,en un archivo estanteria.txt
        try {
            Files.write(Paths.get("estanteriaP.txt"), filaLibro.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Libro guardado");       
        }
        catch (IOException e) {
            System.out.println("Error de guardado");

        }

    }
    public static void cargarEstanteriaLeidosEnArchivo(ArrayList<String[]> estanteriaLeidos){
        try {
            List <String> filasLibrolist = Files.readAllLines(Paths.get("estanteriaL.txt"));
            for (String filaLista : filasLibrolist){
                String[] datos = filaLista.split("\\|");
                for (int i = 0; i < datos.length; i++){
                    datos [i] = datos[i].trim();
                }
                estanteriaLeidos.add(datos);
            }

        }
        catch(IOException e) {
            System.out.println("No se encontró el archivo 'estanteriaL.txt', se comenzará con la estanteria vacía");
        }
    }
    public static void cargarEstanteriaPendientesEnArchivo(ArrayList<String[]> estanteriaPendientes){
        try {
            List <String> filasLibrolist = Files.readAllLines(Paths.get("estanteriaP.txt"));
            for (String filaLista : filasLibrolist){
                String[] datos = filaLista.split("\\|");
                for (int i = 0; i < datos.length; i++){
                    datos [i] = datos[i].trim();
                }
                estanteriaPendientes.add(datos);
            }
  
        }
        catch(IOException e) {
            System.out.println("No se encontró el archivo 'estanteriaP.txt', se comenzará con la estanteria vacía");
        }
    }
    public static void borrarLibroEstanteriaL(ArrayList<String[]> estanteriaLeidos){
        int filaARemover = obtenerFila();
        if (filaARemover >= 0 && filaARemover < estanteriaLeidos.size()){
            estanteriaLeidos.remove(filaARemover);
            System.out.println("Fila: " + filaARemover + " removida." );
            sobreescribirArchivo(estanteriaLeidos, "estanteriaL.txt");
        }
        else {
            System.out.println("No existe ese libro");
        }

    }
    public static void borrarLibroEstanteriaP(ArrayList<String[]> estanteriaPendientes){
        int filaARemover = obtenerFila();
        if (filaARemover >= 0 && filaARemover < estanteriaPendientes.size()){
            estanteriaPendientes.remove(filaARemover);
            System.out.println("Fila: " + filaARemover + " removida." );
            sobreescribirArchivo(estanteriaPendientes, "estanteriaP.txt");
        }
        else {
            System.out.println("No existe ese libro");
        }

    }
    public static int obtenerFila(){
	    boolean valido = false;
	    int valor = 0;
	    while (!valido){
	        try{
	            System.out.print("Que fila desea borrar? ");
	            valor = entrada.nextInt();
                entrada.nextLine();
	            valido = true;
	        }
	        catch(Exception exc){
                System.out.println("Error, entrada inválida");
        	    System.out.println(exc);  
        	    entrada.next();
            }
	    }
	    return valor;
	}
    public static void sobreescribirArchivo(ArrayList<String[]> estanteria, String nombreArchivo) {
        StringBuilder contenido = new StringBuilder();
        try {
            for (String[] libro : estanteria) {
                contenido.append(libro[0]).append(" | ").append(libro[1]).append(" | ").append(libro[2]).append(" | \n");
            }
            Files.write(Paths.get(nombreArchivo), contenido.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Archivo: " + nombreArchivo + " guardado");       
        }
        catch (IOException e) {
            System.out.println("Error de guardado en el archivo: " + nombreArchivo);
        }
    }


}

