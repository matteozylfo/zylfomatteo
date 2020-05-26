var i = 0;

function contatore(){
  postMessage("Contatore Web Worker " + i);
  i++;
  setTimeout("contatore()", 1000); //funzione che permette di "mettere in pausa" l'esecuzione della funzione per un tempo prestabilito in millisecondi
}

contatore();
