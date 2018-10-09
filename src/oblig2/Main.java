package oblig2;

public class Main {


    public static void main(String[] args) {
        Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());

        String[] s = {null,null,null};
        Liste<String> liste1 = new DobbeltLenketListe<>(s);

        System.out.println(liste1.antall() + " "+ liste1.tom());

        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>();
        System.out.println(liste2.toString() + " " + liste2.omvendtString());
        for (int i = 1; i <= 1000000; i++) {
            liste2.leggInn(i);
            //System.out.println(liste2.toString() + " " + liste2.omvendtString());
        }

        long tid1 = System.currentTimeMillis();
        liste2.nullstill();
        tid1 = System.currentTimeMillis() - tid1;

        System.out.println("Nullstill: " + tid1);

        liste2.nullstill();

        System.out.println(liste2.toString() + " " + liste2.omvendtString());

    }



}
