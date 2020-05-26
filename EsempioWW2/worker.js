var i=0;  //variabile contatore i inizializzata al valore intero 0

function timedCount() { //funzione ww per contare
    i++;  //incremento della variabile
    postMessage(i); //la funzione postMessage è una funzione utilizzata dai ww per inviare messaggi al programma principale che sarà in ascolto con la funzione 'onmessage'
    setTimeout("timedCount()", 500); //funzione che permette di "mettere in pausa" l'esecuzione della funzione per un tempo prestabilito in millisecondi
}

timedCount(); //richiamata della funzione in modo da realizzare il loop
