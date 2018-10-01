

/////////// DobbeltLenketListe ////////////////////////////////////

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
      Node<T> p = hode;
      for(int i = 0; i < indeks; i++){
          p = p.neste;

      }
      return p;
      //  throw new UnsupportedOperationException("Ikke laget ennå!");
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

        this();

        Objects.requireNonNull(a);

        if(a == null){
            throw new NullPointerException("Tabellen a er null!");
        }



        for( T verdi :a){
            if(verdi != null) a[antall++]  = verdi;
        }


    }








    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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

        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public String omvendtString()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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

} // DobbeltLenketListe
