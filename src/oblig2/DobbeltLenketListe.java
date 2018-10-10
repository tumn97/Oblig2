package oblig2;

import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        Node<T> p;

        if (indeks < antall / 2) {
            p = hode;
            for (int i = 0; i < indeks; i++) {
                p = p.neste;
            }
        } else {
            p = hale;
            for (int i = antall - 1; i > indeks; i--) {
                p = p.forrige;
            }
        }
        return p;
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        //throw new UnsupportedOperationException("Ikke laget ennå!");

        Objects.requireNonNull(a, "Tabellen er null");

        if (a.length == 0) {
            hode = hale = null;
            endringer = 0;
            antall = 0;
        }

        else {
            Node<T> forrige = null;
            for (T t : a) {
                if (t != null) {
                    if (antall == 0) {
                        hode = hale = forrige = new Node<>(t, null, null);
                        antall++;
                    } else {
                        hale = forrige = forrige.neste = new Node<>(t, forrige, null);
                        antall++;
                    }
                }
            }
        }


    }


    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall, fra, til);

        Liste<T> subListe = new DobbeltLenketListe<>();

        Node<T> current = finnNode(fra);

        for (int i = fra; i < til; i++) {
            subListe.leggInn(current.verdi);
            current = current.neste;
        }

        return subListe;
    }

    @Override
    public int antall()
    {
        // throw new UnsupportedOperationException("Ikke laget ennå!");
        return antall;
    }

    @Override
    public boolean tom()
    {
        //throw new UnsupportedOperationException("Ikke laget ennå!");
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi)
    {

        //throw new UnsupportedOperationException("Ikke laget ennå!");

        Objects.requireNonNull(verdi,"Kan ikke ha nullverdier");

        if ((antall == 0)) {
            hale = new Node<T>(verdi,null,null);
            hode = hale;
        } else {
            hale.neste = new Node<T>(verdi,hale,null);
            hale = hale.neste;

        }

        antall++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi);

        if (indeks < 0 || indeks > antall) {
            throw new IndexOutOfBoundsException();
        }

        if (antall == 0) {
            hode = hale = new Node<>(verdi, null, null);
        } else if (indeks == 0) {
            Node<T> p = new Node<>(verdi, null, hode);
            hode.forrige = p;
            hode = p;
        } else if (indeks == antall) {
            Node<T> q = new Node<>(verdi, hale, null);
            hale.neste = q;
            hale = q;
        } else  {
            Node<T> p = finnNode(indeks - 1);
            Node<T> q = finnNode(indeks);

            Node<T> r = new Node<>(verdi, q.forrige, p.neste);

            p.neste = r;
            q.forrige = r;
        }
        antall++;
        endringer++;

    }

    @Override
    public boolean inneholder(T verdi)
    {
        //  throw new UnsupportedOperationException("Ikke laget ennå!");

        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);

        Node<T> tmp = finnNode(indeks);
        return tmp.verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {
        //throw new UnsupportedOperationException("Ikke laget ennå!");
        if(verdi == null){
            return -1;
        }
        Node<T> p = hode;
        for(int i = 0; i < antall; i++){

            //hvis indeksen til hode stemmer med verdien, så skal den returnere i
            if(p.verdi.equals(verdi)){
                return i;
            }
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        if(indeks < 0 || indeks > antall - 1) {
            throw new IndexOutOfBoundsException();
        }

        if(nyverdi == null) {
            throw new NullPointerException();
        }

        Node<T> p = finnNode(indeks);
        T gmlVerdi = p.verdi;
        p.verdi = nyverdi;
        endringer++;
        return gmlVerdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        if (verdi == null || antall == 0) {
            return false;
        }

        if (hode.verdi.equals(verdi) && antall == 1) {
            hode = hale = null;
            endringer++;
            antall--;
            return true;
        }
        if (hode.verdi.equals(verdi)) {
            hode.neste.forrige = null;
            hode = hode.neste;
            endringer++;
            antall--;
            return true;
        } else if (hale.verdi.equals(verdi)) {
            hale = hale.forrige;
            hale.neste = null;
            endringer++;
            antall--;
            return true;
        }
        Node<T> temp = hode;
        while (temp != null) {
            if (temp.verdi.equals(verdi)) {
                temp.verdi = null;
                Node<T> byttPeker = temp.forrige;
                temp.neste.forrige = temp.forrige;
                byttPeker.neste = temp.neste;
                endringer++;
                antall--;
                return true;
            }
            temp = temp.neste;
        }
        return false;
    }

    @Override
    public T fjern(int indeks)
    {
        indeksKontroll(indeks, false);
        Node<T> temp = hode;
        Node<T> tempHale = hale;
        //int teller = 0;

        if (indeks == 0 && antall == 1) {
            hode = hale = null;
            endringer++;
            antall--;
            return temp.verdi;

        }
        if (indeks == 0) {
            hode.neste.forrige = null;
            hode = hode.neste;
            endringer++;
            antall--;
            return temp.verdi;
        } else if (indeks == antall-1) {
            hale = hale.forrige;
            hale.neste = null;
            endringer++;
            antall--;
            return tempHale.verdi;
        }
        /*
            while (temp != null) {
                if (teller == indeks) {
                    Node<T> byttPeker = temp.forrige;
                    temp.neste.forrige = temp.forrige;
                    byttPeker.neste = temp.neste;
                    endringer++;
                    antall--;
                    return temp.verdi;
                }
                teller++;
                temp = temp.neste;
            }
*/

        for (int i = 0; i <= antall; i++) {
            if (i == indeks) {
                Node<T> byttPeker = temp.forrige;
                temp.neste.forrige = temp.forrige;
                byttPeker.neste = temp.neste;
                endringer++;
                antall--;
                break;
                //return temp.verdi;
            }
            temp = temp.neste;
        }


        return temp.verdi;

    }

    @Override
    public void nullstill()
    {

        //Metode 1 104 - 113 - 120
        //Raskest på korte intervaller
        //Ville anbefalt denne siden den ikke bruker hjelpemetoder
        /*
        for (Node current = hode; current != null; current = current.neste) {
            current.verdi = null;
        }
        */

        //Metode 2 53 - 65 - 74
        //Raskest på høye intervaller
        //Er kjappere selv om den bruker hjelpemetode

        Node<T> p = hode;
        for (int i = 0; i < antall; i++) {
            fjern(p.verdi);
            p = p.neste;
        }

        //Felles for begge to
        hode = null;
        hale = null;
        antall = 0;

    }

    @Override
    public String toString() {

        StringBuilder text = new StringBuilder();

        text.append("[");

        for (Node current = hode; current != null; current = current.neste) {

            text.append(current.verdi);

            if (current != hale) {
                text.append(", ");
            }
        }

        text.append("]");

        return text.toString();
    }

    public String omvendtString() {

        StringBuilder omvendt = new StringBuilder();

        omvendt.append("[");

        for (Node current = hale; current != null; current = current.forrige) {

            omvendt.append(current.verdi);

            if (current != hode) {
                omvendt.append(", ");
            }
        }

        omvendt.append("]");

        return omvendt.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        if (liste.tom()) {

        }

        for (int i = 1; i < liste.antall(); i++)
        {
            T temp = liste.hent(i);  // flytter a[i] til en hjelpevariabel

            int j = i-1;    // starter med neste tabellposisjon

            // en og en verdi flyttes inntil rett sortert plass er funnet
            for (; j >= 0 && c.compare(temp,liste.hent(j)) < 0; j--) {
                liste.oppdater(j+1, liste.hent(j));
            }

            liste.oppdater(j+1, temp); // temp legges inn på rett plass
        }
    }

    @Override
    public Iterator<T> iterator() {
        DobbeltLenketListeIterator iterator = new DobbeltLenketListeIterator();

        return iterator;

    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, true);

        DobbeltLenketListeIterator iterator = new DobbeltLenketListeIterator(indeks);

        return iterator;
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            Node<T> p = finnNode(indeks);
            denne = p;
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer

        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            if(!(endringer == iteratorendringer)) {
                throw new ConcurrentModificationException("Endringer og iterasjoner endringer er ulike");
            }

            if(!hasNext()) {
                throw new NoSuchElementException("Listen har ikke flere elementer");
            }

            fjernOK = true;
            denne = denne.neste;
            return denne.verdi;

        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

}