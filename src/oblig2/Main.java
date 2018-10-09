package oblig2;

public class Main {


    public static void main(String[] args) {
        Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());

        String[] s = {null,null,null};
        Liste<String> liste1 = new DobbeltLenketListe<>(s);

        System.out.println(liste1.antall() + " "+ liste1.tom());

    }



}
