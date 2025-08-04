# proyectoEstanteria
Ayer a la noche tenia ganas de hacer algo en Java, y se me ocurrió esta estantería sencilla
Es simple, aún no me acostumbro a las clases y objetos asique no me fui por el camino de hacer un class libros

# EJECUCIÓN DEL PROGRAMA:
# en bash (terminal), ejecutá estos comandos:
```bash
git clone https://github.com/joaquinzelada/proyectoEstanteria.git
cd proyectoEstanteria
java Estanteria.java
java Estanteria


#FUNCIÓN PARA AGREGAR AL .BASHRC
# En .bashrc ejecuto esta función estanteria(): 

estanteria() {
    cd ~/proyectoEstanteria || return
    javac Estanteria.java
    java Estanteria
}
#Luego recargá la terminal con:
source ~/.bashrc
# y podés ejecutar simplemente: 
estanteria
#Gracias!
